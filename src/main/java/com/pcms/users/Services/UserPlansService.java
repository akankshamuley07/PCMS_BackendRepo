package com.pcms.users.Services;

import com.pcms.users.Config.Model.Plans;
import com.pcms.users.Config.Model.UserPlans;
import com.pcms.users.Config.Model.Users;
import com.pcms.users.Repository.PlansRepository;
import com.pcms.users.Repository.UserPlansRepository;
import com.pcms.users.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserPlansService {

    @Autowired
    private UserPlansRepository userPlansRepository;

    @Autowired
    private PlansRepository plansRepository;

    @Autowired
    private UserRepository userRepository;

    public UserPlans subscribePlan(UserPlans userPlans) {
        Plans plans = plansRepository.findById(userPlans.getPlans().getPlanId()).orElse(null);
        Users users = userRepository.findById(userPlans.getUsers().getUserId()).orElse(null);

        if (plans != null && users != null) {
            userPlans.setPlans(plans);
            userPlans.setUsers(users);
            return userPlansRepository.save(userPlans);
        } else {
            throw new RuntimeException("Invalid Plan ID or User ID");
        }
    }

    public List<UserPlans> getUserPlansByUserId(int userId) {
        return userPlansRepository.findByUsersUserId(userId);
    }
    public List<UserPlans> getNewUserPlans() {
         return userPlansRepository.getNewUserPlans("NEW");
    }
    public List<UserPlans> getAllSubscribedUserPlans(boolean isSubscribed,int userId) {
        return userPlansRepository.findByIsSubscribedAndUsersUserId(isSubscribed, userId);
    }
    public UserPlans approveRejectApplications(UserPlans userPlans) {
        try {
            Optional<UserPlans> userPlansOptional = userPlansRepository.findById(userPlans.getUserPlanId());
            if (userPlansOptional.isPresent()) {
                UserPlans existingUserPlan = userPlansOptional.get();
                if ("Approved".equals(userPlans.getPlanStatus())) {
                    existingUserPlan.setPlanStatus(userPlans.getPlanStatus());
                    existingUserPlan.setSubscribed(true);
                } else {
                    existingUserPlan.setPlanStatus(userPlans.getPlanStatus());
                    existingUserPlan.setRejectionComment(userPlans.getRejectionComment());
                }
                return userPlansRepository.save(existingUserPlan);
            } else {
                throw new RuntimeException("Application does not exist!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to process application: " + e.getMessage(), e);
        }
    }


    public UserPlans updatePlanStatusAndSubscription(Long userPlanId, String planStatus, boolean isSubscribed) {
        Optional<UserPlans> optionalUserPlans = userPlansRepository.findById(userPlanId);
        if (optionalUserPlans.isPresent()) {
            UserPlans userPlans = optionalUserPlans.get();
            userPlans.setPlanStatus(planStatus);
            userPlans.setSubscribed(isSubscribed);
            return userPlansRepository.save(userPlans);
        } else {
            throw new RuntimeException("UserPlan not found with id " + userPlanId);
        }
    }

public UserPlans unsubscribeUserPlan(Long userPlanId) {
    Optional<UserPlans> userPlanOptional = userPlansRepository.findById(userPlanId);
    if (userPlanOptional.isPresent()) {
        UserPlans userPlan = userPlanOptional.get();
        userPlan.setSubscribed(false);
        userPlan.setPlanStatus("new");
        return userPlansRepository.save(userPlan);
    } else {
        throw new RuntimeException("User plan not found with id: " + userPlanId);
    }
}
    public List<UserPlans> getPendingUnsubscribedPlans() {
        return userPlansRepository.findPendingUnsubscribedPlans();
    }

}


//    public UserPlans updateStatus(int userId, String status, String planStatus) {
//        Optional<UserPlans> requestedUser= userPlansRepository.findByUsersUserId(userId));
//        if(requestedUser.isPresent()) {
//            UserPlans newUserPlan =requestedUser.get();
//            if ("approved".equalsIgnoreCase(status)) {
//                newUserPlan.setSubscribed(true);
//            } else if ("rejected".equalsIgnoreCase(status)) {
//                newUserPlan.setSubscribed(false);
//            }
//            newUserPlan.setPlanStatus(planStatus);
//            return userPlansRepository.save(newUserPlan);
//        }else {
//            return null;
//        }


