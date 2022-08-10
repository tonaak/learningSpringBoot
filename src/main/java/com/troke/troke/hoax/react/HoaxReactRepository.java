package com.troke.troke.hoax.react;

import org.springframework.data.jpa.repository.JpaRepository;

import com.troke.troke.hoax.Hoax;
import com.troke.troke.user.User;

public interface HoaxReactRepository extends JpaRepository<HoaxReact, Long> {
	
	HoaxReact findByHoaxAndUser(Hoax hoax, User user);
	
	long countByHoaxLikeTrueAndHoax(Hoax hoax);
	
	long countByHoaxDislikeTrueAndHoax(Hoax hoax);
}
