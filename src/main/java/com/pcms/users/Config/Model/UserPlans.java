package com.pcms.users.Config.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class UserPlans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userPlanId;

    @ManyToOne
    @JoinColumn(referencedColumnName = "planId")
    private Plans plans;

    @ManyToOne
    @JoinColumn(referencedColumnName="userId")
    private Users users;
    private boolean autoTerminated ;
    private boolean alertRequired ;
    private Date requiredFrom;
    private Date requiredTo;
    private String planStatus;
    private String rejectionComment;
    private boolean isSubscribed;

    public Long getUserPlanId() {
        return userPlanId;
    }

    public void setUserPlanId(Long userPlanId) {
        this.userPlanId = userPlanId;
    }

    public Plans getPlans() {
        return plans;
    }

    public void setPlans(Plans plans) {
        this.plans = plans;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public boolean isAutoTerminated() {
        return autoTerminated;
    }

    public void setAutoTerminated(boolean autoTerminated) {
        this.autoTerminated = autoTerminated;
    }

    public boolean isAlertRequired() {
        return alertRequired;
    }

    public void setAlertRequired(boolean alertRequired) {
        this.alertRequired = alertRequired;
    }

    public Date getRequiredFrom() {
        return requiredFrom;
    }

    public void setRequiredFrom(Date requiredFrom) {
        this.requiredFrom = requiredFrom;
    }

    public Date getRequiredTo() {
        return requiredTo;
    }

    public void setRequiredTo(Date requiredTo) {
        this.requiredTo = requiredTo;
    }

    public String getPlanStatus() {
        return planStatus;
    }
    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }
    public String getRejectionComment() {
        return rejectionComment;
    }
    public void setRejectionComment(String rejectionComment) {
        this.rejectionComment = rejectionComment;
    }
    public boolean isSubscribed() {
        return isSubscribed;
    }
    public void setSubscribed(boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    public UserPlans(Long userPlanId, Plans plans, Users users, boolean autoTerminated, boolean alertRequired, Date requiredFrom, Date requiredTo, String planStatus,String rejectionComment,boolean isSubscribed) {
        super();
        this.userPlanId = userPlanId;
        this.plans = plans;
        this.users = users;
        this.autoTerminated = autoTerminated;
        this.alertRequired =alertRequired;
        this.requiredFrom = requiredFrom;
        this.requiredTo = requiredTo;
        this.planStatus =planStatus;
        this.rejectionComment =rejectionComment;
        this.isSubscribed =isSubscribed;

    }

    public  UserPlans(){
        super();
    }
}