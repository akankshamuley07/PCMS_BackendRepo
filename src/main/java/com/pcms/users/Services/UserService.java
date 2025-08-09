package com.pcms.users.Services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pcms.users.Config.Model.Users;
import com.pcms.users.Exception.UserAlreadyExixtsException;
import com.pcms.users.Exception.UserDoesNotExistsException;
import com.pcms.users.Model.UserDTO;
import com.pcms.users.Repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private UserDTO convertToDTO(Users user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setFullName(user.getFullName());
        userDTO.setMailId(user.getMailId());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setSsn(user.getSsn());
        userDTO.setAddressLine1(user.getAddressLine1());
		userDTO.setZipcode(user.getZipcode());
        userDTO.setStatus(user.getStatus());
        userDTO.setRejectionComment(user.getRejectionComment());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

	public List<UserDTO> fetchUsers() {
        List<Users> allUsers = userRepository.findAll();
        return allUsers.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

	public List<UserDTO> getNewUsers() {
		List<Users> allNewUsers = userRepository.getAllNewUsers("NEW");
		return allNewUsers.stream().map(this::convertToDTO).collect(Collectors.toList());
//		return userRepository.getAllNewUsers("NEW");
	}

	public void approveOrRejectAllNewUsers(String status) {
		List<Users> newUsers = userRepository.getAllNewUsers("NEW");
		for (Users user : newUsers) {
			user.setStatus(status);
		}
		userRepository.saveAll(newUsers); }

	public Users saveUser(Users user) {
		Optional<Users> isUserAlreadyExists = userRepository.findByMailId(user.getMailId());
		if (isUserAlreadyExists.isPresent()) {
			throw new UserAlreadyExixtsException("User with this email is already exixts!!!");
		} else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setConfirmPassword(passwordEncoder.encode(user.getConfirmPassword()));

			return userRepository.save(user);
		}

	}

	public Users updateUserStatus(Users users) {
		Optional<Users> oldUser = userRepository.findById(users.getUserId());
		if (oldUser.isPresent()) {
			Users users2 = oldUser.get();
			users2.setStatus(users.getStatus());
			users2.setRejectionComment(users.getRejectionComment());
			userRepository.save(users2);
			return users2;
		} else {
			throw new UserDoesNotExistsException("User does not exists to update status!!");
		}
	}

	public Optional<Users> getParticularUser(String users) {
		Optional<Users> oldUser = userRepository.findByMailId(users);
		return oldUser;
	}
	
	public Optional<Users>getUserById(int userId){
		Optional<Users> particularUser= userRepository.findById(userId);
		return particularUser;
	}

	public String getUserRole(String username) {
		Optional<Users> user = userRepository.findByMailId(username);
		return user.map(Users::getRole).orElse(null);
	}
	
	

}
