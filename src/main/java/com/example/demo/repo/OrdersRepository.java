package com.example.demo.repo;

import com.example.demo.model.Orders;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<Orders, Long> {
    @Query("select u from Orders u where u.status = '2' order by u.date desc ")
    Iterable<Orders> findAllSend();

    @Query("select u from Orders u where u.status <> '1' order by u.date desc ")
    Iterable<Orders> findAllNotDraft();

    @Query("select u from Orders u where u.status = '1' order by u.date desc ")
    Iterable<Orders> findAllDraft();
}
