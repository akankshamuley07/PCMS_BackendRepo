package com.pcms.users.Repository;

import com.pcms.users.Config.Model.Plans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlansRepository extends JpaRepository<Plans, Integer> {
    List<Plans> findByLocationAndPlanName(String location, String planName);

    @Query("SELECT p FROM Plans p JOIN UserPlans up ON p.planId = up.plans.planId WHERE up.users.userId = :userId AND up.planStatus IN ('pending', 'approved')")
    List<Plans> findPlansByStatusAndUserId(@Param("userId") int userId);

    @Query("SELECT p FROM Plans p WHERE p.planId NOT IN (SELECT up.plans.planId FROM UserPlans up WHERE up.users.userId = :userId AND up.planStatus IN ('pending', 'approved'))")
    List<Plans> findPlansExcludingUserPlans(@Param("userId") int userId);

}
