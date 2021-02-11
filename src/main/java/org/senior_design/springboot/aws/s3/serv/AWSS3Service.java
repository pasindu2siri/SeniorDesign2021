package org.senior_design.springboot.aws.s3.serv;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;

public interface AWSS3Service {

	void uploadFile(MultipartFile multipartFile);
	
	byte[] downloadFile(String keyName);

	List<String> listFiles();

	S3Object downloadPDF(String keyName);

	S3Object downloadTemplate(String keyName);
	
	
	
}
