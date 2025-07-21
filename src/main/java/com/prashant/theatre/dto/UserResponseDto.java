package com.prashant.theatre.dto;

import com.prashant.theatre.model.Role;
import com.prashant.theatre.model.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
	
	@NotBlank(message = "User Name cannot be Blank")
	private String userName;
	
	@Email(message = "Enter Valid Email")
	@NotBlank
	private String userEmail;
	private Role role;
	
	public User toEntity () {
		User user = new User();
		user.setUserName(this.userName);
		user.setUserEmail(this.userEmail);
		user.setRole(this.role);
		return user;
	}
	
	public static UserResponseDto fromEntity (User entity) {
		return new UserResponseDto(
				entity.getUserName(),
				entity.getUserEmail(), entity.getRole());
	}
}
