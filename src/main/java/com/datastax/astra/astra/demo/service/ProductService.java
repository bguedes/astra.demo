package com.datastax.astra.astra.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.astra.astra.demo.dao.IProductRepository;
import com.datastax.astra.astra.demo.model.Product;

@Service
public class ProductService {

	@Autowired
	private IProductRepository productRepository;	
	
	public List<Product> findAll() {
		return productRepository.findAll();
	}
	
	public void add(Product product) {
		productRepository.save(product);
	}
	
	public void remove(String productName) {		
		productRepository.deleteByName(productName);
	}

	public Product findByName(String productName) {
		return productRepository.findByName(productName);
	}
}
