package com.pcms.users.Config;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.pcms.users.Config.Model.*;
import com.pcms.users.Repository.UserRepository;
import com.pcms.users.Services.BillService;
import com.pcms.users.Services.PlansService;
import com.pcms.users.Services.UserPlansService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.pcms.users.Exception.UserDoesNotExistsException;
import com.pcms.users.Security.JwtHelper;
import com.pcms.users.Services.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager manager;
	@Autowired
	private UserService userService;
	@Autowired
	private PlansService plansService;
	@Autowired
	private UserPlansService userPlansService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtHelper helper;

	private Logger logger = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
		Optional<Users> user = userService.getParticularUser(request.getUserName());
		if (user.isEmpty()) {
			throw new UserDoesNotExistsException("USER NOT EXISTS");
		}
		this.doAuthenticate(request.getUserName(), request.getPassword());
		if (user.get().getStatus().charAt(0) == 'R' ) {
			return (ResponseEntity<JwtResponse>) ResponseEntity
					.ok(new JwtResponse(request.getUserName(), "", "YOUR PROFILE IS REJECTED DUE TO "+
							user.get().getRejectionComment().toUpperCase(),userRepository.findUserIdByMailId(request.getUserName())));
		} else if (user.get().getStatus().charAt(0) == 'N') {
			return (ResponseEntity<JwtResponse>) ResponseEntity
					.ok(new JwtResponse(request.getUserName(), "", "YOUR PROFILE IS NOT YET APPROVED DUE TO YOU ARE NOT ALLOWED TO LOGIN,"
							+ " ONCE YOUR PROFILE IS APPROVED YOU WILL BE ABLE TO LOGIN",userRepository.findUserIdByMailId(request.getUserName())));
		} else {

			UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
			String token = this.helper.generateToken(userDetails);

			return ResponseEntity.ok(new JwtResponse(userDetails.getUsername(), token, "LOGIN_SUCCESSFULL!!!",userRepository.findUserIdByMailId(request.getUserName())));
		}

	}

	private void doAuthenticate(String email, String password) {

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
		try {
			manager.authenticate(authentication);

		} catch (BadCredentialsException e) {
			throw new BadCredentialsException(" Invalid Username or Password  !!");
		}

	}

	@ExceptionHandler(BadCredentialsException.class)
	public String exceptionHandler() {
		return "INVALID CREDENTIALS";
	}

	@PostMapping("/register")
	public ResponseEntity<Users> createUser(@RequestBody Users user) {
		Users createdUser = userService.saveUser(user);
		return new ResponseEntity<Users>(createdUser, HttpStatus.CREATED);
	}
	
	@GetMapping("/getUserById/{userId}")
	public ResponseEntity<Optional<Users>>getUserById(@PathVariable int userId){
		Optional<Users> particularUser = userService.getUserById(userId);
		return new ResponseEntity<>(particularUser,HttpStatus.OK);
	}

	@GetMapping("/user-role")
	public ResponseEntity<?> getUserRole() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		String role = userService.getUserRole(username);
		if (role != null) {
			return ResponseEntity.ok().body(Collections.singletonMap("role", role));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "User not found"));
		}
	}

	@GetMapping("/plans")
	public List<Plans> getAllPlans() {
		return plansService.getAllPlans();
	}

	@GetMapping("/userPlans/{userId}")
	public List<UserPlans> getUserPlans(@PathVariable int userId){
		return userPlansService.getUserPlansByUserId(userId);
	}

	@GetMapping("/{planId}")
	public Plans getPlanById(@PathVariable int planId) {
		return plansService.getPlanById(planId);
	}

	@GetMapping("/searchPlans")
	public List<Plans> searchPlans(@RequestParam String location, @RequestParam String planName) {
		return plansService.getPlansByLocationAndPlanName(location, planName);
	}

	@PostMapping("/subscribe")
	public ResponseEntity<UserPlans> subscribePlan(@RequestBody UserPlans userPlans) {
		UserPlans subscribePlan = userPlansService.subscribePlan(userPlans);
		return new ResponseEntity<UserPlans>(subscribePlan, HttpStatus.CREATED);
	}

	@GetMapping("/subscribedPlans/{isSubscribed},{userId}")
	public List<UserPlans> getAllSubscribedUserPlans(@PathVariable boolean isSubscribed,@PathVariable int userId) {
		return userPlansService.getAllSubscribedUserPlans(isSubscribed,userId);
	}

//	@PutMapping("/unSubscribe/{userId}")
//	public ResponseEntity<List<UserPlans>> updateSubscriptions(@PathVariable int userId) {
//		try {
//			List<UserPlans> updatedUserPlans = userPlansService.updateSubscriptions(userId);
//			return ResponseEntity.ok(updatedUserPlans);
//		} catch (RuntimeException e) {
//			return ResponseEntity.notFound().build();
//		}
//	}
@PutMapping("/unSubscribe/{userPlanId}")
public UserPlans unsubscribeUserPlan(@PathVariable Long userPlanId) {
	return userPlansService.unsubscribeUserPlan(userPlanId);
}

	@GetMapping("/unsubscribedPlans/{userId}")
	public List<Plans> getUnsubscribedPlans(@PathVariable int userId) {
		return plansService.getUnsubscribedPlans(userId);
	}

	@GetMapping("/status")
	public List<Plans> getPlansByStatusAndUserId(@RequestParam int userId) {
		return plansService.getPlansByStatusAndUserId(userId);
	}

	@GetMapping("/exclude")
	public List<Plans> getPlansExcludingUserPlans(@RequestParam int userId) {
		return plansService.getPlansExcludingUserPlans(userId);
	}

	@Autowired
	private BillService billService;

	@GetMapping("/generate/{userPlanId}")
	public ResponseEntity<byte[]> generateBill(@PathVariable Long userPlanId) {
		ByteArrayInputStream bis = billService.generateBill(userPlanId);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=bill.pdf");

		return ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(bis.readAllBytes());
	}

	@GetMapping("/usageDetails/{userId}")
	public ResponseEntity<List<UsageResponse>> usageDetails(@PathVariable int userId) {
		List<UsageResponse> totalUsage = billService.findUsage(userId);
//		HttpStatus status = newPlansResponse.getPlans().isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
		return new ResponseEntity<>(totalUsage, HttpStatus.OK);
	}

	@GetMapping("/usageDetailsMonth/{userId}")
	public ResponseEntity<Map<String, Map<String, Long>>> usageDetailsMonth(@PathVariable int userId) {
		Map<String, Map<String, Long>> totalUsage = billService.findUsageMonth(userId);
//		HttpStatus status = newPlansResponse.getPlans().isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
		return new ResponseEntity<>(totalUsage, HttpStatus.OK);
	}




}
