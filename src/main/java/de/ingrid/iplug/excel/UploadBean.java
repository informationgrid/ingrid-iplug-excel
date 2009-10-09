package de.ingrid.iplug.excel;

import org.springframework.web.multipart.MultipartFile;

public class UploadBean {

	private MultipartFile _multipartFile;

	public MultipartFile getFile() {
		return _multipartFile;
	}

	public void setFile(MultipartFile multipartFile) {
		_multipartFile = multipartFile;
	}

}
