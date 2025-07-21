package com.prashant.theatre.dto;

import com.prashant.theatre.model.Role;
import com.prashant.theatre.model.User;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class UserRequestDto {

	@NotBlank(message = "User Name cannot be Blank")
	private String userName;
	@Email(message = "Enter Valid Email")
	@NotBlank
	private String userEmail;
	@NotBlank(message = "Password cannot be Blank")
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;


	public User toEntity() {
		User user = new User();
		user.setUserName(this.userName);
		user.setUserEmail(this.userEmail);
		user.setPassword(this.password);
		user.setRole(this.role);
		return user;
	}

	public static UserRequestDto fromEntity(User user) {
		return new UserRequestDto(user.getUserName(), user.getUserEmail(), user.getPassword(), user.getRole());
	}
}