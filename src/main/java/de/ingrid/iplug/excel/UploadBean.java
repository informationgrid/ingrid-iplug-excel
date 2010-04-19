package de.ingrid.iplug.excel;

import org.springframework.web.multipart.MultipartFile;

public class UploadBean {

	private MultipartFile _multipartFile;

	/**
	 * Get file for upload.
	 * 
	 * @return 
	 * 		A multipart file.
	 */
	public MultipartFile getFile() {
		return _multipartFile;
	}

	/** Set file for upload.
	 * 
	 * @param multipartFile
	 */
	public void setFile(MultipartFile multipartFile) {
		_multipartFile = multipartFile;
	}

}
