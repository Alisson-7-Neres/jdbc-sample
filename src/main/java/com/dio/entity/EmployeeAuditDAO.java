package com.dio.entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import com.dio.persistence.ConnectionUtil;

public class EmployeeAuditDAO {
/*
	public List<EmployeeAuditEntity> findAll() {
		List<EmployeeAuditEntity> entities = new ArrayList<>();
		try {
			Connection connection = ConnectionUtil.getConnection();
			Statement statement = connection.createStatement();
			
			String sql = 
			"SELECT * FROM view_employees_audit;";
			statement.executeQuery(sql);
			ResultSet resultSet = statement.getResultSet(); // Guarda o resultado de uma consulta SQL (SELECT)
			while(resultSet.next()) { // Enquanto tiver resultados
				Instant birthdayInstant = resultSet.getTimestamp("birthday").toInstant();
				Instant oldBirthdayInstant = resultSet.getTimestamp("birthday").toInstant();

				entities.add(new EmployeeAuditEntity(
						resultSet.getLong("employee_id"),
						resultSet.getString("name"),
						resultSet.getString("old_name"),
						resultSet.getBigDecimal("salary"),
						resultSet.getBigDecimal("old_salary"),
						OffsetDateTime.ofInstant(birthdayInstant, ZoneOffset.UTC),
						OffsetDateTime.ofInstant(oldBirthdayInstant, ZoneOffset.UTC),
						OperationEnum.getByDbOperation(resultSet.getString("operation"))
						));
				}
			} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
*/
}
