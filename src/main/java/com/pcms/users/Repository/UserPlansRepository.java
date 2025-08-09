package com.pcms.users.Repository;

import com.pcms.users.Config.Model.Plans;
import com.pcms.users.Config.Model.UserPlans;
import com.pcms.users.Config.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserPlansRepository extends JpaRepository<UserPlans, Long> {

    List<UserPlans> findByUsersUserId(int userId);

    @Query("SELECT u FROM UserPlans u WHERE u.users.userId = :userId AND u.isSubscribed = :isSubscribed")
    Optional<List<UserPlans>> findByUserId(@Param("userId") int userId, @Param("isSubscribed") boolean isSubscribed);

    List<UserPlans> findByUsers(Users users);

    @Query("SELECT u FROM UserPlans u WHERE u.isSubscribed = false AND u.planStatus = 'pending'")
    List<UserPlans> findPendingUnsubscribedPlans();

    @Query(value = "select * from userPlans where status= :val", nativeQuery = true)
    List<UserPlans> getNewUserPlans(@Param ("val") String status);

    List<UserPlans> findByIsSubscribedAndUsersUserId(boolean isSubscribed, int userId);


    void deleteByPlansPlanId(int planId);
}
