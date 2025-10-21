package com.dio.main;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.flywaydb.core.Flyway;

import com.dio.entity.EmployeeDAO;
import com.dio.entity.EmployeeEntity;

public class Main {
	
	private final static EmployeeDAO employeeDao = new EmployeeDAO();

	public static void main(String[] args) {
		Flyway flyway = Flyway.configure()
				.dataSource("jdbc:mysql://localhost/jdbc_sample", "root", "admin")
				.locations("classpath:db/migration") // Caso não ache o arquivo de migração
				.load();
		//flyway.repair();
		flyway.migrate();
		
		/*
		var employee = new EmployeeEntity();
		employee.setName("Miguel Da Silva");
		employee.setSalary(new BigDecimal("3200"));
		employee.setBithday(OffsetDateTime.now().minusYears(23));
		System.out.println(employee);
		employeeDao.insert(employee);
		System.out.println(employee);
		 */
		
		// employeeDao.findAll();
		
		//employeeDao.findById(2);
		
		var employee = new EmployeeEntity();
		employee.setId(1);
		employee.setName("Alisson Neres Ribeiro");
		employee.setSalary(new BigDecimal("2500"));
		employee.setBithday(OffsetDateTime.now().minusYears(32));
		System.out.println(employee);
		employeeDao.update(employee);
		
	}

}
