package com.cognizant.pension.management.authentication.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Adrish
 *
 */
public class AddNewUser {
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String role;
	private String isNotLocked;
	private String isActive;
	private MultipartFile profileImage;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String isNotLocked() {
		return isNotLocked;
	}

	public void setNotLocked(String isNotLocked) {
		this.isNotLocked = isNotLocked;
	}

	public String isActive() {
		return isActive;
	}

	public void setActive(String isActive) {
		this.isActive = isActive;
	}

	public MultipartFile getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(MultipartFile profileImage) {
		this.profileImage = profileImage;
	}
}
