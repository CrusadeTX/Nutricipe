package com.project.nutricipe.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class Utilities {
	
	public static boolean tryParseInt(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	public static boolean tryParseDouble(String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	public static String uploadImage(MultipartFile file) {
		if(file!=null) {
			String userDirectory = Paths.get("")
			        .toAbsolutePath()
			        .toString();
			String uploadsPath = userDirectory + "\\src\\main\\resources\\public\\assets\\images\\";
			//Path path = Paths.get(uploadsPath);
			//Files.copy(file.getInputStream(), path);
			//Files.co
			//return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
			String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
			String filename = new Random().nextInt(999999) + "_" + System.currentTimeMillis();
			File targetFile = new File(uploadsPath + filename + fileExtension);
			try {
			byte[] bytes = file.getBytes();
			file.transferTo(targetFile);
			}
			catch(IOException e) {
				e.printStackTrace();
				return "Image failed to upload!";
			}
			String uploadedDirectory = "/assets/images/"+filename+fileExtension;
			
			return uploadedDirectory;
		}
		else {
			return "No image was provided!";
		}
		//return null;
	}

}
