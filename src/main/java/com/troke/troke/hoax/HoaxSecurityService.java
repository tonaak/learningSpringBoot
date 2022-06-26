package com.troke.troke.hoax;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.troke.troke.user.User;

@Service
public class HoaxSecurityService {

	HoaxRepository hoaxRepository;

	public HoaxSecurityService(HoaxRepository hoaxRepository) {
		this.hoaxRepository = hoaxRepository;
	}

	public boolean isAllowedToDelete(long hoaxId, User loggedInUser) {
		Optional<Hoax> optionalHoax = hoaxRepository.findById(hoaxId);
		if (optionalHoax.isPresent()) {
			Hoax inDB = optionalHoax.get();
			return inDB.getUser().getId() == loggedInUser.getId();
		}
		return false;
	}
}
