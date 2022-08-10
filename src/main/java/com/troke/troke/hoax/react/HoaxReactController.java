package com.troke.troke.hoax.react;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.troke.troke.hoax.Hoax;
import com.troke.troke.hoax.HoaxService;
import com.troke.troke.shared.CurrentUser;
import com.troke.troke.user.User;

@RestController
@RequestMapping("/api/1.0")
public class HoaxReactController {
	
	@Autowired
	HoaxService hoaxService;
	
	@Autowired
	HoaxReactService hoaxReactService;
	
	@PostMapping("/hoaxes/{id:[0-9]+}/{react}")
	public void saveReact(@PathVariable long id, @PathVariable String react, @CurrentUser User user) {
		Hoax hoax = hoaxService.getById(id);
		boolean hoaxLike = false;
		boolean hoaxDislike = false;
		if(react != null) {
			if(react.equalsIgnoreCase("like")) {
				hoaxLike = true;
			} else if(react.equalsIgnoreCase("dislike")) {
				hoaxDislike = true;
			} else if(react.equalsIgnoreCase("unlike")) {
				hoaxLike = false;
			} else if(react.equalsIgnoreCase("undislike")) {
				hoaxDislike = false;
			}
		}
		hoaxReactService.saveReact(hoax, user, hoaxLike, hoaxDislike);
	}
	
	@GetMapping("/hoaxes/{id:[0-9]+}/react")
	public String findUserReact(@PathVariable long id, @CurrentUser User user) {
		Hoax hoax = hoaxService.getById(id);
		return hoaxReactService.findUserReact(hoax, user);
	}
	
	@GetMapping("/hoaxes/{id:[0-9]+}/likecount")
	public long likeCount(@PathVariable long id) {
		Hoax hoax = hoaxService.getById(id);
		return hoaxReactService.countLikeByHoax(hoax);
	}
	
	@GetMapping("/hoaxes/{id:[0-9]+}/dislikecount")
	public long dislikeCount(@PathVariable long id) {
		Hoax hoax = hoaxService.getById(id);
		return hoaxReactService.countDislikeByHoax(hoax);
	}
}
