package org.senior_design.springboot.aws.s3.ctrl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.senior_design.springboot.aws.s3.serv.AWSS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

@Controller
@RequestMapping(value = "/s3")
public class AWSS3Ctrl {

	@Autowired
	private AWSS3Service service;

	@PostMapping(value = "/upload")
	public ResponseEntity<String> uploadFile(@RequestPart(value = "file") final MultipartFile multipartFile) {
		service.uploadFile(multipartFile);
		final String response = "[" + multipartFile.getOriginalFilename() + "] uploaded successfully.";
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/download")
	public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam(value = "fileName") final String keyName) {
		final byte[] data = service.downloadFile(keyName);
		final ByteArrayResource resource = new ByteArrayResource(data);
		return ResponseEntity.ok().contentLength(data.length).header("Content-type", "application/octet-stream")
				.header("Content-disposition", "attachment; filename=\"" + keyName + "\"").body(resource);
	}

	@GetMapping("/files")
	public String listAllFiles(Model model) {
		List<String> fileList = service.listFiles();
		List<String> newList = new ArrayList<String>();

		for(String file: fileList) {
			int loc = file.indexOf("_", 25)+1;
			String name = file.substring(loc);
			newList.add(name);
		}
		
		
		model.addAttribute("fileList", fileList);
		model.addAttribute("newList", newList);
		return "listOfFiles";
	}

	@GetMapping(value = "/documentPDF")
	public ResponseEntity<InputStreamResource> getDocument(@RequestParam(value = "fileName") final String keyName)
			throws IOException {

		S3Object object = service.downloadPDF(keyName);
		S3ObjectInputStream s3is = object.getObjectContent();

		return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_PDF)
				.cacheControl(CacheControl.noCache())
				.header("Content-Disposition", "attachment; filename=" + "testing.pdf")
				.body(new InputStreamResource(s3is));
	}
	
	@GetMapping(value = "/template")
	public ResponseEntity<InputStreamResource> getTemplate(@RequestParam(value = "templateName") final String keyName)
			throws IOException {

		S3Object object = service.downloadTemplate(keyName);
		S3ObjectInputStream s3is = object.getObjectContent();
		
		

			return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_XHTML_XML)
					.cacheControl(CacheControl.noCache())
					.header("Content-Disposition", "attachment; filename=" + "testing.html")
					.body(new InputStreamResource(s3is));
		}
		
		


//		return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_XHTML_XML)
//				.cacheControl(CacheControl.noCache())
//				.header("Content-Disposition", "attachment; filename=" + "testing.html")
//				.body(new InputStreamResource(s3is));
	
	
	
}
