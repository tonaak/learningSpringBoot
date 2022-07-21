package com.troke.troke.shared;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.troke.troke.file.FileAttachment;

public class FileAttachmentForHoaxValidator implements ConstraintValidator<FileAttachmentForHoax, FileAttachment> {

	@Override
	public boolean isValid(FileAttachment value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}

		String fileType = value.getFileType();
		System.out.println(fileType);
		if (fileType.equalsIgnoreCase("image/png") 
				|| fileType.equalsIgnoreCase("image/jpeg")
				|| fileType.equalsIgnoreCase("video/quicktime")
				|| fileType.equalsIgnoreCase("video/mp4")
				|| fileType.equalsIgnoreCase("audio/mpeg")) {
			return true;
		}
		return false;
	}

}
