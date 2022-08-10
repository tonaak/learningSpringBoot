package com.troke.troke.hoax.react;

import org.springframework.stereotype.Service;

import com.troke.troke.hoax.Hoax;
import com.troke.troke.user.User;

@Service
public class HoaxReactService {
	
	HoaxReactRepository hoaxReactRepository;
	
	public HoaxReactService(HoaxReactRepository hoaxReactRepository) {
		this.hoaxReactRepository = hoaxReactRepository;
	}
	
	public void saveReact(Hoax hoax, User user, boolean hoaxLike, boolean hoaxDislike) {	
		HoaxReact react = hoaxReactRepository.findByHoaxAndUser(hoax, user);
		if(react == null) {
			react = new HoaxReact();
		}
		react.setHoax(hoax);
		react.setUser(user);
		react.setHoaxLike(hoaxLike);
		react.setHoaxDislike(hoaxDislike);
		hoaxReactRepository.save(react);
	}
	
	public long countLikeByHoax(Hoax hoax) {
		return hoaxReactRepository.countByHoaxLikeTrueAndHoax(hoax);
	}
	
	public long countDislikeByHoax(Hoax hoax) {
		return hoaxReactRepository.countByHoaxDislikeTrueAndHoax(hoax);
	}
	
	public String findUserReact(Hoax hoax, User user) {
		HoaxReact react = hoaxReactRepository.findByHoaxAndUser(hoax, user);
		if(react != null) {
			if(react.isHoaxLike()) {
				return "like";
			} else if(react.isHoaxDislike()) {
				return "dislike";
			} else {
				return "nothing";
			}
		} else {
			return "nothing";
		}
	}
}
