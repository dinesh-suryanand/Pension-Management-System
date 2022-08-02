package com.cognizant.pension.management.authentication.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cognizant.pension.management.authentication.domain.User;
import com.cognizant.pension.management.authentication.exception.domain.EmailExistException;
import com.cognizant.pension.management.authentication.exception.domain.EmailNotFoundException;
import com.cognizant.pension.management.authentication.exception.domain.NotAnImageFileException;
import com.cognizant.pension.management.authentication.exception.domain.UserNotFoundException;
import com.cognizant.pension.management.authentication.exception.domain.UsernameExistException;

public interface UserService {

	User register(String firstName, String lastName, String username, String email, String password) throws UserNotFoundException, UsernameExistException, EmailExistException;
	
	List<User> getUsers();
	
	User findByUsername(String username);
	
	User findByEmail(String email);
	
	User addNewUser(String firstName, String lastName, String username, String email, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;

    User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;
	
    void deleteUser(Long id);
	
	void resetPassword(String email) throws EmailNotFoundException;
	
	User updateProfileImage(String username, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;
}
