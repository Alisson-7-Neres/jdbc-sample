package com.dio.entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.dio.persistence.ConnectionUtil;
import com.mysql.cj.jdbc.StatementImpl;

public class EmployeeDAO {
	
	public void insert(final EmployeeEntity employeeEntity) {
		try {
			Connection connection = ConnectionUtil.getConnection(); // Criando conexão
			Statement statement = connection.createStatement();
			
			String sql = 
			"INSERT INTO employees (name,salary, birthday) VALUES "
			+ "('"+ employeeEntity.getName() +"' , " 
					+ employeeEntity.getSalary().toString() +" , '"
					+formatOffsetDateTime(employeeEntity.getBithday())+"');";
			statement.executeUpdate(sql); // Executa a query
			if (statement instanceof StatementImpl impl)
				employeeEntity.setId(impl.getLastInsertID());
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void update(final EmployeeEntity employeeEntity) {
		try {
			Connection connection = ConnectionUtil.getConnection();
			Statement statement = connection.createStatement();
			
			String sql =
			"UPDATE employees SET "
			+ "name = '" + employeeEntity.getName() + "'"
			+ ", salary = " + employeeEntity.getSalary()
			+ ", birthday = '" + formatOffsetDateTime(employeeEntity.getBithday()) + "' "
			+ "WHERE id = " + employeeEntity.getId();
			statement.executeUpdate(sql);

		} catch(SQLException ex) {
			ex.printStackTrace();
			}
	}

	public void delete(final long id) {		
		try {
		Connection connection = ConnectionUtil.getConnection();
		Statement statement = connection.createStatement();
		
		String sql = 
		"DELETE FROM employees WHERE id = " + id;
		statement.execute(sql);
		findAll();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public List<EmployeeEntity> findAll() {
		List<EmployeeEntity> entities = new ArrayList<>();
		try {
			Connection connection = ConnectionUtil.getConnection();
			Statement statement = connection.createStatement();
			
			String sql = 
			"SELECT * FROM employees;";
			statement.executeQuery(sql);
			ResultSet resultSet = statement.getResultSet(); // Guarda o resultado de uma consulta SQL (SELECT)
			while(resultSet.next()) { // Enquanto tiver resultados
				var entity = new EmployeeEntity();
				entity.setId(resultSet.getLong("id"));
				entity.setName(resultSet.getString("name"));
				entity.setSalary(resultSet.getBigDecimal("salary"));
				Instant birthdayInstant = resultSet.getTimestamp("birthday").toInstant();
				entity.setBithday(OffsetDateTime.ofInstant(birthdayInstant, ZoneOffset.UTC));
				entities.add(entity);
				System.out.println(entity);
				}
			} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public EmployeeEntity findById(final long id) {
		EmployeeEntity entity = new EmployeeEntity();
		try {
			Connection connection = ConnectionUtil.getConnection();
			Statement statement = connection.createStatement();
			
			statement.executeQuery("SELECT*FROM employees WHERE id = " + id);
			ResultSet resultSet = statement.getResultSet();
			
			if (resultSet.next()) { // Como só vai exibir só uma usuário, usa-se a condicional ao inves do laço de repetição
			entity.setId(resultSet.getLong("id"));
			entity.setName(resultSet.getString("name"));
			entity.setSalary(resultSet.getBigDecimal("salary"));
			Instant birthdayInstant = resultSet.getTimestamp("birthday").toInstant();
			entity.setBithday(OffsetDateTime.ofInstant(birthdayInstant, ZoneOffset.UTC));
			System.out.println(entity);
			} else
				System.err.println("ID não encontrado!");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	private String formatOffsetDateTime(final OffsetDateTime dateTime) {
		Object utdcDateTime = dateTime.withOffsetSameInstant(ZoneOffset.UTC); // Converte o horário para o fuso UTC, mantendo o instante exato do tempo
		return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm:ss"));
	}

}
