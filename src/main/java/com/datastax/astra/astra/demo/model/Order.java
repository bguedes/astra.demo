package com.datastax.astra.astra.demo.model;

import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;

@Table(value = "orders")
@Data
public class Order {

	@Column("customer_name")
	@PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)	
    private String customerName;
	
    private UUID id;
    
    private String address;
    
	@Column("prod_id")
    private UUID prouctdId;
	
	@Column("prod_name")
    private String productName;
	
	
    private String description;
    
    private float price;
    
	@Column("sell_price")
    private float sellPrice;
	
}