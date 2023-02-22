package com.coursenet.springbasic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.coursenet.springbasic.entity.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findByGoodsName(String name);

    @Query(
            value = "SELECT * FROM orders where goods_name = :goodsName LIMIT 1"
            , nativeQuery = true
    )
    Optional<Orders> findByGoodsNameFirst(@Param("goodsName") String name);

    @Query("SELECT o FROM Orders o WHERE o.goodsName = :goodsName AND o.receiverName= :receiverName")
    Optional<Orders> findByGoodsNameAndReceiverName(
            @Param("goodsName") String goodsName,
            @Param("receiverName") String receiverName
    );
}

