package com.gopizza.service;

import com.gopizza.dto.CreateUserDTO;
import com.gopizza.dto.UpdateUserDTO;
import com.gopizza.dto.UserResponseDTO;
import com.gopizza.model.User;
import com.gopizza.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public UserResponseDTO createUser(CreateUserDTO createUserDTO) {
		if (userRepository.existsByEmail(createUserDTO.getEmail())) {
			throw new IllegalArgumentException("Email já está em uso: " + createUserDTO.getEmail());
		}

		if (createUserDTO.getCpf() != null && !createUserDTO.getCpf().isEmpty()) {
			if (userRepository.existsByCpf(createUserDTO.getCpf())) {
				throw new IllegalArgumentException("CPF já está em uso: " + createUserDTO.getCpf());
			}
		}

		User user = new User();
		user.setEmail(createUserDTO.getEmail());
		user.setName(createUserDTO.getName());
		user.setPhone(createUserDTO.getPhone());
		user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
		user.setBirthday(createUserDTO.getBirthday());
		user.setCpf(createUserDTO.getCpf());

		User savedUser = userRepository.save(user);
		return convertToDTO(savedUser);
	}

	@Transactional(readOnly = true)
	public UserResponseDTO getUserById(UUID id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + id));
		return convertToDTO(user);
	}

	@Transactional(readOnly = true)
	public UserResponseDTO getUserByEmail(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com email: " + email));
		return convertToDTO(user);
	}

	@Transactional(readOnly = true)
	public List<UserResponseDTO> getAllUsers() {
		return userRepository.findAll().stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());
	}

	@Transactional
	public UserResponseDTO updateUser(UUID id, CreateUserDTO updateUserDTO) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + id));

		if (!user.getEmail().equals(updateUserDTO.getEmail()) &&
				userRepository.existsByEmail(updateUserDTO.getEmail())) {
			throw new IllegalArgumentException("Email já está em uso: " + updateUserDTO.getEmail());
		}

		if (updateUserDTO.getCpf() != null && !updateUserDTO.getCpf().isEmpty() &&
				!updateUserDTO.getCpf().equals(user.getCpf()) &&
				userRepository.existsByCpf(updateUserDTO.getCpf())) {
			throw new IllegalArgumentException("CPF já está em uso: " + updateUserDTO.getCpf());
		}

		user.setEmail(updateUserDTO.getEmail());
		user.setName(updateUserDTO.getName());
		user.setPhone(updateUserDTO.getPhone());
		user.setBirthday(updateUserDTO.getBirthday());
		user.setCpf(updateUserDTO.getCpf());
		if (updateUserDTO.getPassword() != null && !updateUserDTO.getPassword().isEmpty()) {
			user.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
		}

		User updatedUser = userRepository.save(user);
		return convertToDTO(updatedUser);
	}

	@Transactional
	public UserResponseDTO updateUserPartial(UUID id, UpdateUserDTO updateUserDTO) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + id));

		if (updateUserDTO.getEmail() != null && !updateUserDTO.getEmail().trim().isEmpty()) {
			String newEmail = updateUserDTO.getEmail().trim();
			if (!user.getEmail().equals(newEmail) &&
					userRepository.existsByEmail(newEmail)) {
				throw new IllegalArgumentException("Email já está em uso: " + newEmail);
			}
			user.setEmail(newEmail);
		}

		if (updateUserDTO.getName() != null && !updateUserDTO.getName().trim().isEmpty()) {
			user.setName(updateUserDTO.getName().trim());
		}

		if (updateUserDTO.getPhone() != null && !updateUserDTO.getPhone().trim().isEmpty()) {
			user.setPhone(updateUserDTO.getPhone().trim());
		}

		if (updateUserDTO.getPassword() != null && !updateUserDTO.getPassword().trim().isEmpty()) {
			user.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
		}

		if (updateUserDTO.getBirthday() != null) {
			user.setBirthday(updateUserDTO.getBirthday());
		}

		if (updateUserDTO.getCpf() != null && !updateUserDTO.getCpf().trim().isEmpty()) {
			String newCpf = updateUserDTO.getCpf().trim();
			if (user.getCpf() == null || !newCpf.equals(user.getCpf())) {
				if (userRepository.existsByCpf(newCpf)) {
					throw new IllegalArgumentException("CPF já está em uso: " + newCpf);
				}
			}
			user.setCpf(newCpf);
		}

		User updatedUser = userRepository.save(user);
		return convertToDTO(updatedUser);
	}

	@Transactional
	public void deleteUser(UUID id) {
		if (!userRepository.existsById(id)) {
			throw new IllegalArgumentException("Usuário não encontrado com ID: " + id);
		}
		userRepository.deleteById(id);
	}

	public UserResponseDTO convertToDTO(User user) {
		return new UserResponseDTO(
				user.getId(),
				user.getEmail(),
				user.getName(),
				user.getPhone(),
				user.getBirthday(),
				user.getCpf(),
				user.getCreatedAt(),
				user.getUpdatedAt()
		);
	}
}
