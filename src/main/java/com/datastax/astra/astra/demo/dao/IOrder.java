package com.datastax.astra.astra.demo.dao;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.datastax.astra.astra.demo.model.Order;

@Repository
public interface IOrder extends CassandraRepository<Order, String> {

}
