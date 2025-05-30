package com.institute.utility;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

 @Component
public class CommonUtils {

	public String encryptPassword(String userPassword) {
		StringBuilder encryptPassword = new StringBuilder();
		for (int i = 0; i < userPassword.length(); i++) {
			encryptPassword = encryptPassword.append(userPassword.charAt(i)).append(i);
		}
		String password = encryptPassword.reverse().toString();
		encryptPassword = null;
		return password;
	}

	public String decryptPassword(String encryptedPassword) {
		StringBuilder reversed = new StringBuilder(encryptedPassword).reverse();
		StringBuilder originalPassword = new StringBuilder();
		int j = 2;
		System.out.println(reversed.length());
		for (int i = 0; i < reversed.length() - 1; i += j) {
			originalPassword.append(reversed.charAt(i));
			if (i >= 19) {
				j = 3;
			}
		}
		return originalPassword.toString();
	}
	
	private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

	public static String appendLikeOperator(String input) {
		StringBuilder sb = new StringBuilder();
		sb.append("%");
		sb.append(input);
		sb.append("%");
		return sb.toString();
	}

	public static Double convertToZeroIfNull(Double input) {
		return Objects.isNull(input) ? Double.valueOf("0") : input;
	}

	public static Long convertToZeroIfNull(Long input) {
		return Objects.isNull(input) ? Long.valueOf("0") : input;
	}

	public static String convertToEmptyIfNull(String input) {
		return Objects.isNull(input) ? "" : input;
	}

	public static String moveFileToServer(MultipartFile file, Path targetLocation) {
		logger.debug("Service :: moveFileToServer :: Entered");
		String path = null;
		try {

			File directory = new File(targetLocation.toString());

			if (!directory.exists()) {
				directory.mkdirs();
			}

			String fileName = file.getOriginalFilename();
			targetLocation = targetLocation.resolve(fileName);

			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			path = targetLocation.toString();
		} catch (Exception e) {
			logger.error("Service :: moveFileToServer :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: moveFileToServer :: Exited");
		return path;
	}

	public static byte[] downloadFileFromServer(Path targetLocation) {
		logger.debug("Service :: downloadFileFromServer :: Entered");
		byte[] fileArray = null;
		try {
			fileArray = Files.readAllBytes(targetLocation);
		} catch (Exception e) {
			logger.error("Service :: downloadFileFromServer :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: downloadFileFromServer :: Exited");
		return fileArray;
	}

	public static void deleteFileFromServer(Path targetLocation) {
		logger.debug("Service :: deleteFileFromServer :: Entered");
		try {
			Files.deleteIfExists(targetLocation);
		} catch (Exception e) {
			logger.error("Service :: deleteFileFromServer :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: deleteFileFromServer :: Exited");
	}

	public static String renameString(String input) {
		return Arrays.asList(input.split(" ")).stream().collect(Collectors.joining("_")).toLowerCase();
	}

	public static String moveImageFileToServer(MultipartFile file, Path targetLocation) {
		logger.debug("Service :: moveFileToServer :: Entered");
		String path = null;

		try {

			File dir = new File(targetLocation.toString());
			if (!dir.exists()) {
				dir.mkdirs(); 
			}

			// Create destination file path
			String originalFilename = file.getOriginalFilename();
			File destinationFile = new File(targetLocation.toString() + File.separator + originalFilename);

			// Save the file
			file.transferTo(destinationFile);

			path = targetLocation.toString() + "\\" + originalFilename;
		} catch (Exception e) {
			logger.error("Service :: moveFileToServer :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: moveFileToServer :: Exited");
		return path;
	}

}
