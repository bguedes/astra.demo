package com.datastax.astra.astra.demo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;

import com.datastax.oss.driver.api.core.CqlSession;

@SpringBootApplication
public class Application implements CommandLineRunner 	{

	@Autowired
	private CqlSession astraSession;
	
    @Value("classpath:cql/products.cql")
    private Resource cqlImport;	
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		loadDataUsingCQL();
	}	

	private void loadDataUsingCQL() {

		List<String> cqlScripts = new ArrayList<>();

		try (InputStream inputStream = cqlImport.getInputStream()) {
			
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) != -1) {
			    result.write(buffer, 0, length);
			}
			
			cqlScripts = Arrays.asList(result.toString("UTF-8").split(";"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String cqlScript : cqlScripts) {
			astraSession.execute(cqlScript);
		}
	}	

}
