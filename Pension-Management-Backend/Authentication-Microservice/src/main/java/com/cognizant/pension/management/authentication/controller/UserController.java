package com.cognizant.pension.management.authentication.controller;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cognizant.pension.management.authentication.constant.EmailConstant;
import com.cognizant.pension.management.authentication.constant.FileConstant;
import com.cognizant.pension.management.authentication.constant.SecurityConstant;
import com.cognizant.pension.management.authentication.domain.HttpResponse;
import com.cognizant.pension.management.authentication.domain.User;
import com.cognizant.pension.management.authentication.domain.UserPrincipal;
import com.cognizant.pension.management.authentication.exception.domain.EmailExistException;
import com.cognizant.pension.management.authentication.exception.domain.EmailNotFoundException;
import com.cognizant.pension.management.authentication.exception.domain.ExceptionHandling;
import com.cognizant.pension.management.authentication.exception.domain.NotAnImageFileException;
import com.cognizant.pension.management.authentication.exception.domain.UserNotFoundException;
import com.cognizant.pension.management.authentication.exception.domain.UsernameExistException;
import com.cognizant.pension.management.authentication.service.UserService;
import com.cognizant.pension.management.authentication.utility.JWTTokenProvider;

@RestController
@RequestMapping(path = { "/", "/user" })
public class UserController extends ExceptionHandling {

	private static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";

	private UserService userService;

	private AuthenticationManager authenticationManager;

	private JWTTokenProvider jwtTokenProvider;

	@Autowired
	public UserController(UserService userService, AuthenticationManager authenticationManager,
			JWTTokenProvider jwtTokenProvider) {
		super();
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	/** End-point for user login
	 * @param user
	 * @return
	 */
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody User user) {
		authenticate(user.getUsername(), user.getPassword());
		User loginUser = userService.findByUsername(user.getUsername());
		UserPrincipal userPrincipal = new UserPrincipal(loginUser);
		HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
		return new ResponseEntity<>(loginUser, jwtHeader, HttpStatus.OK);
	}

	/** End-point for registering new user/sign up
	 * @param user
	 * @return
	 * @throws UserNotFoundException
	 * @throws UsernameExistException
	 * @throws EmailExistException
	 */
	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody User user)
			throws UserNotFoundException, UsernameExistException, EmailExistException {
		User newUser = userService.register(user.getFirstName(), user.getLastName(), user.getUsername(),
				user.getEmail(), user.getPassword());
		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}

	/** End-point for Admin to add new user
	 * @param firstName
	 * @param lastName
	 * @param username
	 * @param email
	 * @param role
	 * @param isActive
	 * @param isNonLocked
	 * @param profileImage
	 * @return
	 * @throws UserNotFoundException
	 * @throws UsernameExistException
	 * @throws EmailExistException
	 * @throws IOException
	 * @throws NotAnImageFileException
	 */
	@PostMapping("/add")
	public ResponseEntity<User> addNewUser(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, @RequestParam("username") String username,
			@RequestParam("email") String email, @RequestParam("role") String role,
			@RequestParam("isActive") String isActive, @RequestParam("isNonLocked") String isNonLocked,
			@RequestParam(value = "profileImage", required = false) MultipartFile profileImage)
			throws UserNotFoundException, UsernameExistException, EmailExistException, IOException,
			NotAnImageFileException {
		User newUser = userService.addNewUser(firstName, lastName, username, email, role,
				Boolean.parseBoolean(isNonLocked), Boolean.parseBoolean(isActive), profileImage);
		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}

	/** End-point for updating user details
	 * @param currentUsername
	 * @param firstName
	 * @param lastName
	 * @param username
	 * @param email
	 * @param role
	 * @param isActive
	 * @param isNonLocked
	 * @param profileImage
	 * @return
	 * @throws UserNotFoundException
	 * @throws UsernameExistException
	 * @throws EmailExistException
	 * @throws IOException
	 * @throws NotAnImageFileException
	 */
	@PostMapping("/update")
	public ResponseEntity<User> update(@RequestParam("currentUsername") String currentUsername,
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("username") String username, @RequestParam("email") String email,
			@RequestParam("role") String role, @RequestParam("isActive") String isActive,
			@RequestParam("isNonLocked") String isNonLocked,
			@RequestParam(value = "profileImage", required = false) MultipartFile profileImage)
			throws UserNotFoundException, UsernameExistException, EmailExistException, IOException,
			NotAnImageFileException {
		User updatedUser = userService.updateUser(currentUsername, firstName, lastName, username, email, role,
				Boolean.parseBoolean(isNonLocked), Boolean.parseBoolean(isActive), profileImage);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

	/** End-point for updating profile image
	 * @param username
	 * @param profileImage
	 * @return
	 * @throws UserNotFoundException
	 * @throws UsernameExistException
	 * @throws EmailExistException
	 * @throws IOException
	 */
	@PostMapping("/updateProfileImage")
	public ResponseEntity<User> updateProfileImage(@RequestParam("username") String username,
			@RequestParam(value = "profileImage") MultipartFile profileImage)
			throws UserNotFoundException, UsernameExistException, EmailExistException, IOException {
		User user = userService.updateProfileImage(username, profileImage);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	/** End-point to find user by username
	 * @param username
	 * @return
	 */
	@GetMapping("/find/{username}")
	public ResponseEntity<User> getUser(@PathVariable("username") String username) {
		User user = userService.findByUsername(username);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	/** End-point to fetch all users
	 * @return
	 */
	@GetMapping("/list")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	/** End-point to reset password
	 * @param email
	 * @return
	 * @throws MessagingException
	 * @throws EmailNotFoundException
	 */
	@GetMapping("/resetpassword/{email}")
	public ResponseEntity<HttpResponse> resetPassword(@PathVariable("email") String email)
			throws MessagingException, EmailNotFoundException {
		userService.resetPassword(email);
		return response(HttpStatus.OK, EmailConstant.EMAIL_SENT + email);
	}

	/** End-point for Admin to delete user
	 * @param id
	 * @return
	 */
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAnyAuthority('user:delete')")
	public ResponseEntity<HttpResponse> deleteUser(@PathVariable("id") Long id) {
		userService.deleteUser(id);
		return response(HttpStatus.NO_CONTENT, USER_DELETED_SUCCESSFULLY.toUpperCase());
	}

	@GetMapping(path = "/image/{username}/{fileName}", produces = IMAGE_JPEG_VALUE)
	public byte[] getProfileImage(@PathVariable("username") String username, @PathVariable("fileName") String fileName)
			throws IOException {
		return Files
				.readAllBytes(Paths.get(FileConstant.USER_FOLDER + username + FileConstant.FORWARD_SLASH + fileName));
	}

	@GetMapping(path = "/image/profile/{username}", produces = IMAGE_JPEG_VALUE)
	public byte[] getTemporaryProfileImage(@PathVariable("username") String username) throws IOException {
		URL url = new URL(FileConstant.TEMP_PROFILE_IMAGE_BASE_URL + username);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try (InputStream inputStream = url.openStream()) {
			int bytesRead;
			byte[] chunk = new byte[1024];
			while ((bytesRead = inputStream.read(chunk)) > 0) {
				byteArrayOutputStream.write(chunk, 0, bytesRead);
			}
		}
		return byteArrayOutputStream.toByteArray();
	}

	/** End-point to validate JWT in Request Header
	 * @param inputToken
	 * @return
	 */
	@GetMapping("/authorize")
	public ResponseEntity<Boolean> authorization(@RequestHeader("Authorization") String inputToken) {
		String token = inputToken.substring(SecurityConstant.TOKEN_HEADER.length());
		User user = userService.findByUsername(jwtTokenProvider.getSubject(token));
		if (jwtTokenProvider.isTokenValid(user.getUsername(), token)) {
			return new ResponseEntity<>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
		}

	}

	/** Create HttpResponse Entity
	 * @param httpStatus
	 * @param message
	 * @return
	 */
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
				httpStatus.getReasonPhrase().toUpperCase(), message.toUpperCase()), httpStatus);
	}

	private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(SecurityConstant.JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(userPrincipal));
		return headers;
	}

	private void authenticate(String username, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}

}
