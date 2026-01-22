package com.gopizza.service;

import com.gopizza.dto.CreateUserDTO;
import com.gopizza.dto.UserResponseDTO;
import com.gopizza.model.User;
import com.gopizza.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
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
		user.setPassword(createUserDTO.getPassword()); // TODO: Implementar hash da senha
		user.setBirthday(createUserDTO.getBirthday());
		user.setCpf(createUserDTO.getCpf());

		User savedUser = userRepository.save(user);
		return convertToDTO(savedUser);
	}

	@Transactional(readOnly = true)
	public UserResponseDTO getUserById(Long id) {
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
	public UserResponseDTO updateUser(Long id, CreateUserDTO updateUserDTO) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + id));

		// Verificar se o email está sendo alterado e se já existe
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
			user.setPassword(updateUserDTO.getPassword()); // TODO: Implementar hash da senha
		}

		User updatedUser = userRepository.save(user);
		return convertToDTO(updatedUser);
	}

	@Transactional
	public void deleteUser(Long id) {
		if (!userRepository.existsById(id)) {
			throw new IllegalArgumentException("Usuário não encontrado com ID: " + id);
		}
		userRepository.deleteById(id);
	}

	private UserResponseDTO convertToDTO(User user) {
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
