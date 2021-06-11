package com.yummyyum.Repositories;

import com.yummyyum.Model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query(value = "SELECT s.* FROM subscription AS s JOIN subscription_plan AS sp on s.subscription_plan_id = sp.id " +
            "WHERE sp.name = 'plan15'",
            nativeQuery = true)
    Optional<Subscription> getSubscriptionBySubscriptionPlanName(String subscriptionPlanName);

    @Query(value = "SELECT s.id FROM subscription AS s JOIN subscription_plan AS sp on s.subscription_plan_id = sp.id " +
            "WHERE sp.name = :subscriptionPlanName",
            nativeQuery = true)
    Long getSubscriptionIdBySubscriptionPlanName(String subscriptionPlanName);


    List<Subscription> getAllSubscriptionsByIsCanceled(Boolean isCanceled);

    @Query(value = "SELECT * FROM subscription AS s WHERE s.activation_date = :activationDate",
            nativeQuery = true)
    List<Subscription> getAllSubscriptionsByActivationDate(LocalDate activationDate);

    List<Subscription> getAllSubscriptionsByCanceledDate(LocalDate canceledDate);

}
