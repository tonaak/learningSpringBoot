package com.troke.troke.hoax.vm;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.troke.troke.file.FileAttachment;
import com.troke.troke.shared.FileAttachmentForHoax;

import lombok.Data;

@Data
public class HoaxUpdateVM {
	
	@NotNull
	@Size(min = 10, max = 5000)
	@Column(length = 5000)
	private String content;
	
	@FileAttachmentForHoax
	private FileAttachment attachment;
}
