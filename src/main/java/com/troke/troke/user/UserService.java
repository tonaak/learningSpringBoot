package com.troke.troke.user;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.troke.troke.error.NotFoundException;
import com.troke.troke.file.FileService;
import com.troke.troke.user.vm.UserUpdateVM;

@Service
public class UserService {

	UserRepository userRepository;

	PasswordEncoder passwordEncoder;

	FileService fileService;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, FileService fileService) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.fileService = fileService;
	}

	public User save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public Page<User> getUsers(User loggedInUser, Pageable pageable) {
		if (loggedInUser != null) {
			return userRepository.findByUsernameNot(loggedInUser.getUsername(), pageable);
		}
		return userRepository.findAll(pageable);
	}

	public User getByUsername(String username) {
		User inDB = userRepository.findByUsername(username);
		if (inDB == null) {
			throw new NotFoundException(username + " not found");
		}
		return inDB;
	}

	public User update(long id, UserUpdateVM userUpdate) {
		User inDB = userRepository.getById(id);
		inDB.setDisplayName(userUpdate.getDisplayName());
		if (userUpdate.getImage() != null) {
			String savedImageName;
			try {
				savedImageName = fileService.saveProfileImage(userUpdate.getImage());
				fileService.deleteProfileImage(inDB.getImage());
				inDB.setImage(savedImageName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return userRepository.save(inDB);
	}
	
	public void updateResetPasswordToken(String token, String email) throws NotFoundException {
        List<User> userList = userRepository.findByEmail(email);
        if (userList.size() > 0) {
            User user = userList.get(0);
        	user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new NotFoundException("Could not find any user with the email " + email);
        }
    }
     
    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }
     
    public void updatePassword(User user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));     
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }
}
