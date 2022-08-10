package com.troke.troke.hoax.react;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.troke.troke.hoax.Hoax;
import com.troke.troke.user.User;

import lombok.Data;

@Data
@Entity
public class HoaxReact {

	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	private Hoax hoax;
	
	@ManyToOne
	private User user;
	
	private boolean hoaxLike;
	
	private boolean hoaxDislike;
}
