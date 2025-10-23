package com.dio.main;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.flywaydb.core.Flyway;

import com.dio.entity.EmployeeAuditDAO;
import com.dio.entity.EmployeeDAO;
import com.dio.entity.EmployeeEntity;
import com.dio.entity.EmployeeParamDAO;

public class Main {
	
	private final static EmployeeParamDAO employeeParamDao = new EmployeeParamDAO();
	private final static EmployeeAuditDAO employeeAuditDAO =  new EmployeeAuditDAO();

	public static void main(String[] args) {
		Flyway flyway = Flyway.configure()
				.dataSource("jdbc:mysql://localhost/jdbc_sample", "root", "admin")
				.locations("classpath:db/migration") // Caso não ache o arquivo de migração
				.load();
		//flyway.repair();
		flyway.migrate();
		/*
		var employeeMiguel = new EmployeeEntity();
		employeeMiguel.setName("Miguel Da Silva");
		employeeMiguel.setSalary(new BigDecimal("3200"));
		employeeMiguel.setBithday(OffsetDateTime.now().minusYears(23));
		System.out.println(employeeMiguel);
		employeeParamDao.insert(employeeMiguel);
		System.out.println(employeeMiguel);
		
		
		var employeeRenato = new EmployeeEntity();
		employeeRenato.setName("Renato Tavares Almeida");
		employeeRenato.setSalary(new BigDecimal("2500"));
		employeeRenato.setBithday(OffsetDateTime.now().minusYears(32));
		System.out.println(employeeRenato);
		employeeParamDao.insert(employeeRenato);
		
		var employeeJessica = new EmployeeEntity();
		employeeJessica.setName("Jessica Henrique Chagas");
		employeeJessica.setSalary(new BigDecimal("4200"));
		employeeJessica.setBithday(OffsetDateTime.now().minusYears(27));
		System.out.println(employeeJessica);
		employeeParamDao.insert(employeeJessica);
		
		var employeeAlbert = new EmployeeEntity();
		employeeAlbert.setName("Albert Sneijder Lopez");
		employeeAlbert.setSalary(new BigDecimal("1500"));
		employeeAlbert.setBithday(OffsetDateTime.now().minusYears(38));
		System.out.println(employeeAlbert);
		employeeParamDao.insert(employeeAlbert);
		
		
		var employeeAndre = new EmployeeEntity();
		employeeAndre.setName("André Pereira Santos");
		employeeAndre.setSalary(new BigDecimal("1900"));
		employeeAndre.setBithday(OffsetDateTime.now().minusYears(18));
		System.out.println(employeeAndre);
		employeeParamDao.insert(employeeAndre);
		*/
		
		var employeeAndrea = new EmployeeEntity();
		employeeAndrea.setName("Andrea Freire");
		employeeAndrea.setSalary(new BigDecimal("2900"));
		employeeAndrea.setBithday(OffsetDateTime.now().minusYears(22));
		System.out.println(employeeAndrea);
		employeeParamDao.insertWithProcedure(employeeAndrea);
		
		
		//employeeParamDao.findAll();
		
		//employeeParamDao.findById(2);
		
		// employeeParamDao.delete(2);
		
		//employeeAuditDAO.findAll().forEach(System.out::println);
	}

}
