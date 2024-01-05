package com.billnex.billManage;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BillManagementApplication {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		// Set property condition to not map null values
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

		return modelMapper;
	}

	public static void main(String[] args) {
		SpringApplication.run(BillManagementApplication.class, args);
	}

}
