package com.coursenet.springbasic.repository;

import com.coursenet.springbasic.entity.Orders;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    public List<Orders> findOrderByDynamicField(Map<String, Object> mapQuery) {
        String queryString = "SELECT o FROM Orders o WHERE o.";

        List<String> keys = new ArrayList(mapQuery.keySet());
        for (int i = 0; i < keys.size(); i++) {
            if (i>0){
                queryString = queryString+ " AND o." + keys.get(i) + " = :"+keys.get(i);
            }else{
                queryString = queryString + keys.get(i) + " = :"+keys.get(i);
            }
        }
        TypedQuery<Orders> query = entityManager.createQuery(queryString, Orders.class);
        for (int i = 0; i < keys.size(); i++) {
            query.setParameter(keys.get(i), mapQuery.get(keys.get(i)));
        }
        return query.getResultList();
    }
}
