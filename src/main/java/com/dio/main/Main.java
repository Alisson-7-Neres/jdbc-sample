package com.dio.main;

import org.flywaydb.core.Flyway;

import com.dio.entity.EmployeeDAO;

public class Main {
	
	private final static EmployeeDAO employeeDao = new EmployeeDAO();

	public static void main(String[] args) {
		Flyway flyway = Flyway.configure()
				.dataSource("jdbc:mysql://localhost/jdbc_sample", "root", "admin")
				.locations("classpath:db/migration") // Caso não ache o arquivo de migração
				.load();
		//flyway.repair();
		flyway.migrate();
	}

}
