package org.bars.fileupload.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {
	@RequestMapping(value="/test" , method = RequestMethod.GET)
	public String test(){
		/*HashMap<String, String> returnData = new HashMap<String, String>();
		returnData.put("Hi", "Hi");*/
		return "Hello World";
	}
	
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	/**
	 * Thanks to https://github.com/Insaf-Innam/SpringRestFileUpload/blob/master/src/main/java/com/spring/rest/controller/FileUploadController.java
	 * For getting the multipart stuff sample from which this example is carved out for angularjs kind of stuff and other standards
	 */
	
	/**
	 * Upload single file using Spring Controller
	 * 
	 * @param rootPath
	 *            file will be saved in local disk F
	 * @param makes
	 *            a directory "tmpfiles" in local disk F
	 * @param name
	 *            represented file name
	 * @param file
	 *            represented single file object
	 * 
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<HashMap<String,String>> uploadFileHandler(@RequestParam("filename") String name,
			@RequestParam("file") MultipartFile file) {
		HashMap<String, String> returndata = new HashMap<String, String>();
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = "f://";
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location=" + serverFile.getAbsolutePath());
				returndata.put("message", "You successfully uploaded file=" + name);
				return new ResponseEntity<HashMap<String, String>>(returndata, new HttpHeaders(), HttpStatus.OK);
			} catch (IOException IoException) {
				returndata.put("message", "You failed to upload " + name + " => " + IoException.getMessage());
				return new ResponseEntity<HashMap<String, String>>(returndata, new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}catch (Exception exception) {
				returndata.put("message", "You failed to upload " + name + " => " + exception.getMessage());
				return new ResponseEntity<HashMap<String, String>>(returndata, new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
		} else {
			returndata.put("message", "You failed to upload " + name + " because the file was empty.");
			return new ResponseEntity<HashMap<String, String>>(returndata, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Upload multiple file using Spring Controller
	 * 
	 * @param rootPath
	 *            file will be saved in local disk F
	 * @param dir
	 *            makes a directory "tmpfiles" in local disk F
	 * @param name
	 *            represented file name
	 * @param file
	 *            represented multiple file objects
	 */
	@RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<HashMap<String,String>> uploadMultipleFileHandler(@RequestParam("filename") String[] names,
			@RequestParam("file") MultipartFile[] files) {
		HashMap<String, String> returndata = new HashMap<String, String>();
		if (files.length != names.length)
		{
			returndata.put("message", "Filenames is lesser");
			return new ResponseEntity<HashMap<String, String>>(returndata, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}

		String message = "";
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			String name = names[i];
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = "f://";
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location=" + serverFile.getAbsolutePath());

				message = message + "You successfully uploaded file=" + name + "<br />";
			} 
			catch (IOException IoException) {
				returndata.put("message", "You failed to upload " + name + " => " + IoException.getMessage());
				return new ResponseEntity<HashMap<String, String>>(returndata, new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
			catch (Exception exception) {
				returndata.put("message", "You failed to upload " + name + " => " + exception.getMessage());
				return new ResponseEntity<HashMap<String, String>>(returndata, new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
		}
		returndata.put("message", message);
		return new ResponseEntity<HashMap<String, String>>(returndata, new HttpHeaders(), HttpStatus.OK);
	}
}
