package com.datastax.astra.astra.demo.controller;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.datastax.astra.astra.demo.model.Product;
import com.datastax.astra.astra.demo.service.ProductService;

@Controller
@RequestMapping("/api/v1/")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")	
	public String products(@ModelAttribute("model") ModelMap model) {
		model.addAttribute("products", productService.findAll());
		return "products";
	}
	
	@GetMapping("/product/{productName}")	
	public ResponseEntity<Product> findProductsByName(@PathVariable String productName) {
		Product productRetreived = productService.findByName(productName);
        return new ResponseEntity<Product>(productRetreived, HttpStatus.OK);	
	}	
	
	
	@PostMapping("/product")	
	public String add(Product product) {
		product.setId(UUID.randomUUID());
		product.setCreated(new Date(System.currentTimeMillis()));
		productService.add(product);
		return "redirect:/api/v1/products";
	}

	@DeleteMapping("/product/{productName}")
	public ResponseEntity<String> delete(@PathVariable String productName) {
		productService.remove(productName);
        return new ResponseEntity<String>(productName, HttpStatus.OK);		
	}	
}
