package com.datastax.astra.astra.demo.dao;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.datastax.astra.astra.demo.model.Product;

@Repository
public interface IProductRepository extends CassandraRepository<Product, String> {

	void deleteByName(String name);
	
	Product findByName(String name);
}
