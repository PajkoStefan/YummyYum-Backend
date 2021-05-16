package com.yummyyum.Repositories;

import com.yummyyum.Model.OrderMeals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderMealsRepository extends JpaRepository<OrderMeals, Long> {

    @Query(value = "SELECT om.* FROM order_info as oi JOIN order_meals om on oi.id = om.order_info_id " +
            "WHERE oi.order_id = :orderId",
            nativeQuery = true)
    List<OrderMeals> getOrderMealsByOrderId(String orderId);

}

