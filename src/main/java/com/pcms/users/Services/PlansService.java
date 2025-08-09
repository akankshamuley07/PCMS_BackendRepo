package com.pcms.users.Services;

import com.pcms.users.Config.Model.Plans;
import com.pcms.users.Config.Model.UserPlans;
import com.pcms.users.Repository.PlansRepository;
import com.pcms.users.Repository.UserPlansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlansService {
    @Autowired
    private PlansRepository plansRepository;

    @Autowired
    private UserPlansRepository userPlansRepository;

    @Transactional
    public void deletePlan(int planId) {
        userPlansRepository.deleteByPlansPlanId(planId);
        plansRepository.deleteById(planId);
    }

    public Plans createPlan(Plans plan) {
        return plansRepository.save(plan);
    }

    public Plans updatePlan(int planId, Plans updatedPlan) {
        Optional<Plans> existingPlan = plansRepository.findById(planId);
        if (existingPlan.isPresent()) {
            Plans plan = existingPlan.get();
            plan.setPlanName(updatedPlan.getPlanName());
            plan.setLocation(updatedPlan.getLocation());
            plan.setPrice(updatedPlan.getPrice());
            plan.setDescription(updatedPlan.getDescription());
            return plansRepository.save(plan);
        } else {
            throw new IllegalArgumentException("Plan not found");
        }
    }

//    public List<Plans> getPlansByLocation(String location) {
//        return plansRepository.findByLocation(location);
//    }

//    public void deletePlan(int planId) {
//        plansRepository.deleteById(planId);
//    }

    public List<Plans> getAllPlans() {
        return plansRepository.findAll();
    }
    public List<Plans> getPlansByLocationAndPlanName(String location, String planName) {
        return plansRepository.findByLocationAndPlanName(location, planName);
    }

    public List<Plans> getUnsubscribedPlans(int userId) {
        List<UserPlans> userPlans = userPlansRepository.findByUsersUserId(userId);
        List<Integer> subscribedPlanIds = userPlans.stream()
                .filter(up -> up.isSubscribed())
                .map(up -> up.getPlans().getPlanId())
                .collect(Collectors.toList());

        return plansRepository.findAll().stream()
                .filter(plan -> !subscribedPlanIds.contains(plan.getPlanId()))
                .collect(Collectors.toList());
    }

    public List<Plans> getPlansByStatusAndUserId(int userId) {
        return plansRepository.findPlansByStatusAndUserId(userId);
    }

    public List<Plans> getPlansExcludingUserPlans(int userId) {
        return plansRepository.findPlansExcludingUserPlans(userId);
    }

    public Plans getPlanById(int planId) {
        return plansRepository.findById(planId).orElse(null);
    }
}
