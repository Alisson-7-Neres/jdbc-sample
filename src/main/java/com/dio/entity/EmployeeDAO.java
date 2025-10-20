package com.dio.entity;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.dio.persistence.ConnectionUtil;
import com.mysql.cj.jdbc.StatementImpl;

public class EmployeeDAO {
	
	public void insert(final EmployeeEntity employeeEntity) {
		try {
			Connection connection = ConnectionUtil.getConnection();
			var statement = connection.createStatement();
			
			String sql = 
			"INSERT INTO employees (name,salary, birthday) VALUES "
			+ "('"+ employeeEntity.getName() +"' , " 
					+ employeeEntity.getSalary().toString() +" , '"
					+formatOffsetDateTime(employeeEntity.getBithday())+"');";
			statement.executeUpdate(sql);
			if (statement instanceof StatementImpl impl)
				employeeEntity.setId(impl.getLastInsertID());
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void update(final EmployeeEntity employeeEntity) {
	}

	public void delete(final long id) {
	}

	public List<EmployeeEntity> findAll() {
		return null;
	}

	public EmployeeEntity findById(final long id) {
		return null;
	}
	
	private String formatOffsetDateTime(final OffsetDateTime dateTime) {
		return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm:ss"));
	}

}
