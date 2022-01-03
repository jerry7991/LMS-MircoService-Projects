package com.lms.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.user.api.UserServices;
import com.lms.user.dto.UserDto;
import com.lms.user.entity.User;
import com.lms.user.exceptions.DuplicateUserException;
import com.lms.user.exceptions.UserNotFoundException;
import com.lms.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserServices {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<UserDto> findAllUser() {
		return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public UserDto findByUserName(String name) throws UserNotFoundException {
		return modelMapper.map(userRepository.findById(name).orElseThrow(UserNotFoundException::new), UserDto.class);
	}

	@Override
	public UserDto addUser(UserDto userDto) throws DuplicateUserException {
		if (userRepository.existsById(userDto.getUserName()))
			throw new DuplicateUserException("User already exist.");
		User user = modelMapper.map(userDto, User.class);
		return modelMapper.map(userRepository.save(user), UserDto.class);
	}

	@Override
	public boolean deleteByName(String name) throws UserNotFoundException {
		if (!userRepository.existsById(name))
			throw new UserNotFoundException("User doesn't exit.");
		userRepository.deleteById(name);
		return !userRepository.existsById(name);
	}

	@Override
	public UserDto updateUser(String userName, UserDto userDto) throws UserNotFoundException, DuplicateUserException {
		if (!userName.equals(userDto.getUserName()) && !userRepository.existsById(userDto.getUserName()))
			throw new DuplicateUserException("Trying to update User name which already exist.");
		User user = userRepository.findById(userName).orElseThrow(UserNotFoundException::new);
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		user.setUserName(userDto.getUserName());
		user.setEmail(userDto.getEmail());
		return modelMapper.map(userRepository.save(user), UserDto.class);
	}

}
