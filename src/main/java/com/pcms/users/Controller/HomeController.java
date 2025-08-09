package com.pcms.users.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import com.pcms.users.Config.Model.Plans;
import com.pcms.users.Config.Model.UserPlans;
import com.pcms.users.Services.PlansService;
import com.pcms.users.Services.UserPlansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.pcms.users.Config.Model.Users;
import com.pcms.users.Model.UserDTO;
import com.pcms.users.Repository.UserRepository;
import com.pcms.users.Services.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/home")
public class HomeController {
	@Autowired
	UserService userService;

	@Autowired
	private UserPlansService userPlansService;

	@Autowired
	private PlansService plansService;

	@GetMapping("/getUsersList")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserDTO>> fetchUsers() {
		List<UserDTO> listOfUsers = userService.fetchUsers();
		return new ResponseEntity<>(listOfUsers, HttpStatus.OK);
	}

	@GetMapping("/getNewUsersList")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserDTO>>getNewUsers()
	{
		List<UserDTO> newUsers=userService.getNewUsers();
		return new ResponseEntity<>(newUsers,HttpStatus.OK);
	}

	@PutMapping("/updateUserStatus")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Users> approveRejectUser(@RequestBody Users users) {
		Users updatedUser = userService.updateUserStatus(users);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}
	@GetMapping("/getUserById/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Optional<Users>>getUserById(@PathVariable int userId){
		Optional<Users> particularUser = userService.getUserById(userId);
		return new ResponseEntity<>(particularUser,HttpStatus.OK);
	}

	@PutMapping("/approveOrRejectNewUsers")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> approveOrRejectAllNewUsers(@RequestParam String status) {
		userService.approveOrRejectAllNewUsers(status);
		return new ResponseEntity<>("All new users have been updated to status: " + status, HttpStatus.OK);
	}


	@PostMapping("/addPlan")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Plans> createPlan(@RequestBody Plans plan) {
		Plans createdPlan = plansService.createPlan(plan);
		return new ResponseEntity<>(createdPlan, HttpStatus.OK);
	}

	@PutMapping("/updatePlan/{planId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Plans> updatePlan(@PathVariable int planId, @RequestBody Plans plan) {
		Plans updatedPlan = plansService.updatePlan(planId, plan);
		return new ResponseEntity<>(updatedPlan, HttpStatus.OK);
	}

	@GetMapping("/getAllPlans")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Plans>> getAllPlans() {
		System.out.println("Get all plans");
		List<Plans> plans = plansService.getAllPlans();
		return new ResponseEntity<>(plans, HttpStatus.OK);
	}

	@PutMapping("/deletePlan/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deletePlan(@PathVariable int id) {
		plansService.deletePlan(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@PutMapping("/approveRejectPlans")
	public ResponseEntity<?> approveRejectApplication(@RequestBody UserPlans userPlans) {
		try {
			UserPlans updatedUserPlan = userPlansService.approveRejectApplications(userPlans);
			return new ResponseEntity<>(updatedUserPlan, HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to approve/reject application", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("updatePlanStatus/{id}")
	public UserPlans updatePlanStatusAndSubscription(@PathVariable Long id, @RequestParam String planStatus, @RequestParam boolean isSubscribed) {
		return userPlansService.updatePlanStatusAndSubscription(id, planStatus, isSubscribed);
	}

	@GetMapping("/pendingSubscribed")
	@PreAuthorize("hasRole('ADMIN')")
	public List<UserPlans> getPendingUnsubscribedPlans() {
		return userPlansService.getPendingUnsubscribedPlans();
	}


}
