package com.cognizant.pension.management.authentication.impl;

import static com.cognizant.pension.management.authentication.constant.FileConstant.DEFAULT_USER_IMAGE_PATH;
import static com.cognizant.pension.management.authentication.constant.UserServiceImplConstant.EMAIL_ALREADY_EXISTS;
import static com.cognizant.pension.management.authentication.constant.UserServiceImplConstant.NO_USER_FOUND_BY_EMAIL;
import static com.cognizant.pension.management.authentication.constant.UserServiceImplConstant.NO_USER_FOUND_BY_USERNAME;
import static com.cognizant.pension.management.authentication.constant.UserServiceImplConstant.RETURNING_USER_FOUND_BY_USERNAME_S;
import static com.cognizant.pension.management.authentication.constant.UserServiceImplConstant.USERNAME_ALREADY_EXISTS;
import static com.cognizant.pension.management.authentication.constant.UserServiceImplConstant.USER_NOT_FOUND_BY_USERNAME_S;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cognizant.pension.management.authentication.constant.FileConstant;
import com.cognizant.pension.management.authentication.domain.User;
import com.cognizant.pension.management.authentication.domain.UserPrincipal;
import com.cognizant.pension.management.authentication.enumeration.Role;
import com.cognizant.pension.management.authentication.exception.domain.EmailExistException;
import com.cognizant.pension.management.authentication.exception.domain.EmailNotFoundException;
import com.cognizant.pension.management.authentication.exception.domain.NotAnImageFileException;
import com.cognizant.pension.management.authentication.exception.domain.UserNotFoundException;
import com.cognizant.pension.management.authentication.exception.domain.UsernameExistException;
import com.cognizant.pension.management.authentication.repository.UserRepository;
import com.cognizant.pension.management.authentication.service.EmailService;
import com.cognizant.pension.management.authentication.service.LoginAttemptService;
import com.cognizant.pension.management.authentication.service.UserService;

/**
 * @author Adrish
 *
 */
@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	LoginAttemptService loginAttemptService;

	@Autowired
	EmailService emailService;

	/**
	 * Load username while log in
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			logger.error(String.format(USER_NOT_FOUND_BY_USERNAME_S, username));
			throw new UsernameNotFoundException("User not found by username: " + username);
		} else {
			validateLoginAttempt(user);
			user.setLastLoginDateDisplay(user.getLastLoginDate());
			user.setLastLoginDate(new Date());
			userRepository.save(user);
			UserPrincipal userPrincipal = new UserPrincipal(user);
			logger.info(String.format(RETURNING_USER_FOUND_BY_USERNAME_S, username));
			return userPrincipal;
		}
	}

	/**
	 * Lock the user account if he fails to log in successfully multiple times
	 * 
	 * @param user
	 */
	private void validateLoginAttempt(User user) {
		if (user.isNotLocked()) {
			if (loginAttemptService.hasExceededMaximumAttempts(user.getUsername())) {
				user.setNotLocked(false);
			} else {
				user.setNotLocked(true);
			}
		} else {
			loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
		}
	}

	/**
	 * Register new user in case of sign up
	 */
	@Override
	public User register(String firstName, String lastName, String username, String email, String password)
			throws UserNotFoundException, UsernameExistException, EmailExistException {
		validateNewUsernameAndEmail(StringUtils.EMPTY, username, email);
		User user = new User();
		user.setUserId(generateUserId());
		String encodedPassword = encodePassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		user.setEmail(email);
		user.setJoinDate(new Date());
		user.setPassword(encodedPassword);
		user.setActive(true);
		user.setNotLocked(true);
		user.setRole(Role.ROLE_USER.name());
		user.setAuthorities(Role.ROLE_USER.getAuthorities());
		user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
		userRepository.save(user);
		// email should be sent here
		return user;
	}

	/**
	 * Return list of all registered users
	 */
	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	/**
	 * Return user based on username
	 */
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	/**
	 * Return user based on email
	 */
	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	/**
	 * Admin facility to add new user
	 */
	@Override
	public User addNewUser(String firstName, String lastName, String username, String email, String role,
			boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException,
			UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
		validateNewUsernameAndEmail(StringUtils.EMPTY, username, email);
		User user = new User();
		String password = generatePassword();
		user.setUserId(generateUserId());
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setJoinDate(new Date());
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(encodePassword(password));
		user.setActive(isActive);
		user.setNotLocked(isNonLocked);
		user.setRole(getRoleEnumName(role).name());
		user.setAuthorities(getRoleEnumName(role).getAuthorities());
		user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
		userRepository.save(user);
		saveProfileImage(user, profileImage);
		logger.info("New user password: " + password);
		return user;
	}

	/**
	 * Update user details
	 */
	@Override
	public User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername,
			String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage)
			throws UserNotFoundException, UsernameExistException, EmailExistException, IOException,
			NotAnImageFileException {
		User currentUser = validateNewUsernameAndEmail(currentUsername, newUsername, newEmail);
		currentUser.setFirstName(newFirstName);
		currentUser.setLastName(newLastName);
		currentUser.setUsername(newUsername);
		currentUser.setEmail(newEmail);
		currentUser.setActive(isActive);
		currentUser.setNotLocked(isNonLocked);
		currentUser.setRole(getRoleEnumName(role).name());
		currentUser.setAuthorities(getRoleEnumName(role).getAuthorities());
		userRepository.save(currentUser);
		saveProfileImage(currentUser, profileImage);
		return currentUser;
	}

	/**
	 * Admin facility to delete user based on id
	 */
	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	/**
	 * Reset Password
	 */
	@Override
	public void resetPassword(String email) throws EmailNotFoundException {
		User user = findByEmail(email);
		if (user == null) {
			throw new EmailNotFoundException(NO_USER_FOUND_BY_EMAIL + email);
		}
		String password = generatePassword();
		String encodedPassword = encodePassword(password);
		user.setPassword(encodedPassword);
		userRepository.save(user);
		// send email here
		logger.info("New password after reset: " + password);
	}

	/**
	 * Update profile image of the user
	 */
	@Override
	public User updateProfileImage(String username, MultipartFile profileImage)
			throws UserNotFoundException, UsernameExistException, EmailExistException, IOException {
		User user = validateNewUsernameAndEmail(username, null, null);
		saveProfileImage(user, profileImage);
		return user;
	}

	/** Save profile image of the user
	 * @param user
	 * @param profileImage
	 * @throws IOException
	 */
	private void saveProfileImage(User user, MultipartFile profileImage) throws IOException {
		if (profileImage != null) {
			Path userFolder = Paths.get(FileConstant.USER_FOLDER + user.getUsername()).toAbsolutePath().normalize();
			if (!Files.exists(userFolder)) {
				Files.createDirectories(userFolder);
				logger.info(FileConstant.DIRECTORY_CREATED + userFolder);
			}
			Files.deleteIfExists(
					Paths.get(userFolder + user.getUsername() + FileConstant.DOT + FileConstant.JPG_EXTENSION));
			Files.copy(profileImage.getInputStream(),
					userFolder.resolve(user.getUsername() + FileConstant.DOT + FileConstant.JPG_EXTENSION),
					StandardCopyOption.REPLACE_EXISTING);
			user.setProfileImageUrl(setProfileImageUrl(user.getUsername()));
			userRepository.save(user);
			logger.info(FileConstant.FILE_SAVED_IN_FILE_SYSTEM + profileImage.getOriginalFilename());
		}
	}

	private String setProfileImageUrl(String username) {
		return ServletUriComponentsBuilder.fromCurrentContextPath().path(FileConstant.USER_IMAGE_PATH + username
				+ FileConstant.FORWARD_SLASH + username + FileConstant.DOT + FileConstant.JPG_EXTENSION).toUriString();
	}

	private Role getRoleEnumName(String role) {
		return Role.valueOf(role.toUpperCase());
	}

	private String getTemporaryProfileImageUrl(String username) {
		return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + username)
				.toUriString();
	}

	private String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

	private String generatePassword() {
		return RandomStringUtils.randomAlphanumeric(10);
	}

	private String generateUserId() {
		return RandomStringUtils.randomNumeric(10);
	}

	/** Validate username and email while adding new user
	 * @param currentUsername
	 * @param newUsername
	 * @param newEmail
	 * @return
	 * @throws UserNotFoundException
	 * @throws UsernameExistException
	 * @throws EmailExistException
	 */
	private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail)
			throws UserNotFoundException, UsernameExistException, EmailExistException {
		User userByNewUsername = findByUsername(newUsername);
		User userByNewEmail = findByEmail(newEmail);

		if (StringUtils.isNotBlank(currentUsername)) {
			User currentUser = findByUsername(currentUsername);
			if (currentUser == null) {
				throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);
			}

			if (userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())) {
				throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
			}

			if (userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())) {
				throw new EmailExistException(EMAIL_ALREADY_EXISTS);
			}
			return currentUser;
		} else {
			if (userByNewUsername != null) {
				throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
			}

			if (userByNewEmail != null) {
				throw new EmailExistException(EMAIL_ALREADY_EXISTS);
			}
			return null;
		}
	}

}
