package com.yulken.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yulken.workshopmongo.domain.User;
import com.yulken.workshopmongo.dto.UserDTO;
import com.yulken.workshopmongo.repository.UserRepository;
import com.yulken.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;

	public List<User> findAll() {
		return repo.findAll();
	}

	public User findById(String id) {
		Optional<User> user = repo.findById(id);
		if (!user.isPresent()) {
			throw new ObjectNotFoundException("Objeto não encontrado");
		}
		return user.get();
	}

	public void insert(User user) {
		repo.insert(user);
	}
	
	public void delete(String id) {
		findById(id);
		repo.deleteById(id);

	}
	
	public User update(User user) {
		User newUser = findById(user.getId());
		newUser.setName(user.getName());
		newUser.setEmail(user.getEmail());
		return repo.save(newUser);
	}
	

	public User fromDTO(UserDTO userDto) {
		return new User(userDto.getId(), userDto.getName(), userDto.getEmail());
	}

}
