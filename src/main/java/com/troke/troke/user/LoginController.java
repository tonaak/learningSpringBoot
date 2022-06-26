package com.troke.troke.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.troke.troke.shared.CurrentUser;
import com.troke.troke.user.vm.UserVM;

@RestController
public class LoginController {

	@PostMapping("/api/1.0/login")
	UserVM handleLogin(@CurrentUser User loggedInUser) {
		return new UserVM(loggedInUser);
	}

}
