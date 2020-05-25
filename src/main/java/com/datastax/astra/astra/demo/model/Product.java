package com.datastax.astra.astra.demo.model;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;

@Table(value = "products")
@Data
public class Product {

	private UUID id;
	
	@PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)	
	private String name;
	
	private String description;	
	
	@CassandraType(type = Name.DECIMAL)
	private Float price;
	
	@CassandraType(type = Name.TIMESTAMP)
	private Date created;
 
}
