package com.troke.troke.hoax;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.troke.troke.file.FileAttachment;
import com.troke.troke.file.FileAttachmentRepository;
import com.troke.troke.file.FileService;
import com.troke.troke.user.User;
import com.troke.troke.user.UserService;

@Service
public class HoaxService {

	HoaxRepository hoaxRepository;

	UserService userService;

	FileAttachmentRepository fileAttachmentRepository;

	FileService fileService;

	public HoaxService(HoaxRepository hoaxRepository, UserService userService,
			FileAttachmentRepository fileAttachmentRepository, FileService fileService) {
		super();
		this.hoaxRepository = hoaxRepository;
		this.userService = userService;
		this.fileAttachmentRepository = fileAttachmentRepository;
		this.fileService = fileService;
	}

	public Hoax save(User user, Hoax hoax) {
		hoax.setTimestamp(new Date());
		hoax.setUser(user);
		if (hoax.getAttachment() != null) {
			FileAttachment inDB = fileAttachmentRepository.findById(hoax.getAttachment().getId()).get();
			inDB.setHoax(hoax);
			hoax.setAttachment(inDB);
		}
		return hoaxRepository.save(hoax);
	}

	public Page<Hoax> getAllHoaxes(Pageable pageable) {
		return hoaxRepository.findAll(pageable);
	}

	public Page<Hoax> getHoaxesOfUser(String username, Pageable pageable) {
		User inDB = userService.getByUsername(username);
		return hoaxRepository.findByUser(inDB, pageable);
	}

	public Page<Hoax> getOldHoaxes(long id, Pageable pageable) {
		return hoaxRepository.findByIdLessThan(id, pageable);
	}

	public Page<Hoax> getOldHoaxesOfUser(long id, String username, Pageable pageable) {
		User inDB = userService.getByUsername(username);
		return hoaxRepository.findByIdLessThanAndUser(id, inDB, pageable);
	}

	public List<Hoax> getNewHoaxes(long id, Pageable pageable) {
		return hoaxRepository.findByIdGreaterThan(id, pageable.getSort());
	}

	public List<Hoax> getNewHoaxesOfUser(long id, String username, Pageable pageable) {
		User inDB = userService.getByUsername(username);
		return hoaxRepository.findByIdGreaterThanAndUser(id, inDB, pageable.getSort());
	}

	public long getNewHoaxesCount(long id) {
		return hoaxRepository.countByIdGreaterThan(id);
	}

	public long getNewHoaxesCountOfUser(long id, String username) {
		User inDB = userService.getByUsername(username);
		return hoaxRepository.countByIdGreaterThanAndUser(id, inDB);
	}

	public void deleteHoax(long id) {
		Hoax hoax = hoaxRepository.getById(id);
		if (hoax.getAttachment() != null) {
			fileService.deleteAttachmentImage(hoax.getAttachment().getName());
		}
		hoaxRepository.deleteById(id);
	}
}
