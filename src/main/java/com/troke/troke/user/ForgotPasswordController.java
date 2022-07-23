package com.troke.troke.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.troke.troke.error.NotFoundException;
import com.troke.troke.utility.Utility;

import net.bytebuddy.utility.RandomString;

@RestController
@RequestMapping("/api/1.0")
public class ForgotPasswordController {

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	UserService userService;

	@PostMapping("/forgot_password")
	public void processForgotPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String email = request.getParameter("email");
		if(email != null) {
			String token = RandomString.make(30);

			try {
				userService.updateResetPasswordToken(token, email);
				String resetPasswordLink = Utility.getSiteURL() + "/reset?token=" + token;
				sendEmail(email, resetPasswordLink);
				response.setStatus(HttpStatus.SC_OK);

			} catch (NotFoundException ex) {
				response.sendError(HttpStatus.SC_NOT_FOUND, ex.getMessage());
			} catch (UnsupportedEncodingException | MessagingException e) {
				response.sendError(HttpStatus.SC_BAD_REQUEST, "Error while sending email");
			}
		} else {
			response.sendError(HttpStatus.SC_BAD_REQUEST, "Must not be null");
		}
	}

	public void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("Troke", "Troke Support");
		helper.setTo(recipientEmail);

		String subject = "Here's the link to reset your password";

		String content = "<p>Hello,</p>" + "<p>You have requested to reset your password.</p>"
				+ "<p>Click the link below to change your password:</p>" + "<p><a href=\"" + link
				+ "\">Change my password</a></p>" + "<br>" + "<p>Ignore this email if you do remember your password, "
				+ "or you have not made the request.</p>";

		helper.setSubject(subject);

		helper.setText(content, true);

		mailSender.send(message);
	}

	@PostMapping("/reset_password")
	public void processResetPassword(@RequestBody User userFromRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
	    String token = request.getParameter("token");
	    String password = userFromRequest.getPassword();
	    User user = userService.getByResetPasswordToken(token);
	     
	    if (user == null) {
	        response.sendError(HttpStatus.SC_NOT_FOUND, "Invalid token");
	    } else {           
	        userService.updatePassword(user, password);
		    response.setStatus(HttpStatus.SC_OK);
	    }
	}
}
