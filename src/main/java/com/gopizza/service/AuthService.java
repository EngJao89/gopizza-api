package com.gopizza.service;

import com.gopizza.dto.AuthResponseDTO;
import com.gopizza.dto.LoginRequestDTO;
import com.gopizza.dto.UserResponseDTO;
import com.gopizza.model.User;
import com.gopizza.repository.UserRepository;
import com.gopizza.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserService userService;

	@Autowired
	public AuthService(
			UserRepository userRepository,
			PasswordEncoder passwordEncoder,
			JwtTokenProvider jwtTokenProvider,
			UserService userService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.userService = userService;
	}

	@Transactional
	public AuthResponseDTO login(LoginRequestDTO loginRequest) {
		User user = userRepository.findByEmail(loginRequest.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("Email ou senha inválidos"));

		boolean isPasswordHashed = user.getPassword().startsWith("$2a$") || 
		                          user.getPassword().startsWith("$2b$") || 
		                          user.getPassword().startsWith("$2y$");

		boolean passwordMatches;
		if (isPasswordHashed) {
			passwordMatches = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
		} else {
			passwordMatches = user.getPassword().equals(loginRequest.getPassword());
			if (passwordMatches) {
				user.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
				userRepository.save(user);
			}
		}

		if (!passwordMatches) {
			throw new IllegalArgumentException("Email ou senha inválidos");
		}

		String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail());
		UserResponseDTO userResponse = userService.convertToDTO(user);

		return new AuthResponseDTO(token, userResponse);
	}
}
