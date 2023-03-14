/**
 * Time creation: Dec 4, 2022, 1:38:05 PM
 * 
 * Package name: com.exam.service
 */
package com.exam.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author Tien Tran
 *
 * class FileCommon
 */
@Component
public class FileCommon {
	
	public String getPathToDatabase(String folder, CommonsMultipartFile file) {
		
		String fileName = file.getOriginalFilename();
		
		return folder + fileName;
	}
	
	public String getPathToUpload(String originalFilename, String urlUpload, ServletContext context) {
		
		createFolder(urlUpload, context);
		
		String path = context.getRealPath(urlUpload);
		
		return path + originalFilename;
	}

	public void uploadFile(byte[] bytes, String pathUpload) throws IOException {
		
		Path path = Paths.get(pathUpload);
	    Files.write(path, bytes);
		
//		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(  
//		         new File(pathUpload)));
	    
//		stream.write(bytes);
//	    stream.flush();
//	    stream.close();
	}
	
	private void createFolder(String folderString, ServletContext context) {
	
		File folder = new File(context.getRealPath(folderString));
		
		if (!folder.exists() && !folder.isDirectory()) {
		    folder.mkdirs();
		}
	}
}
