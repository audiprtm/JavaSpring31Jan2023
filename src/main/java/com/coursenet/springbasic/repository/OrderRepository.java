package com.coursenet.springbasic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coursenet.springbasic.entity.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long>{
	Optional<Orders> findByGoodsName(String name);

}

