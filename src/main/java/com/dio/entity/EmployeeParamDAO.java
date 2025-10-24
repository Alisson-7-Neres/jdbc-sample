package com.dio.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import com.dio.persistence.ConnectionUtil;
import com.mysql.cj.jdbc.StatementImpl;

public class EmployeeParamDAO {
	
	public void insert(final EmployeeEntity employeeEntity) {
		try {
			Connection connection = ConnectionUtil.getConnection(); // Criando conexão
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO employees (name,salary, birthday) VALUES (? , ? , ?)");
			
			preparedStatement.setString(1, employeeEntity.getName());
			preparedStatement.setBigDecimal(2, employeeEntity.getSalary());
			preparedStatement.setTimestamp(3, Timestamp.valueOf(employeeEntity.getBithday().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()));
			preparedStatement.executeUpdate();
			if (preparedStatement instanceof StatementImpl impl)
				employeeEntity.setId(impl.getLastInsertID());
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void insertWithProcedure(final EmployeeEntity employeeEntity) {
		try {
			Connection connection = ConnectionUtil.getConnection(); // Criando conexão
			java.sql.CallableStatement statement = connection.prepareCall
					("CALL prc_insert_employee(?, ?, ?, ?);");
			
			statement.registerOutParameter(1, TimeZone.LONG); 
			statement.setString(2, employeeEntity.getName());
			statement.setBigDecimal(3, employeeEntity.getSalary());
			statement.setTimestamp(4, Timestamp.valueOf(employeeEntity.getBithday().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()));
			statement.execute();
			employeeEntity.setId(statement.getLong(1));
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	// Metódo para inserção de vários registros
	public void insertBatch(final List<EmployeeEntity> employeeEntity) throws SQLException {
		Connection connection = ConnectionUtil.getConnection(); // Criando conexão
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO employees (name,salary, birthday) VALUES (? , ? , ?)");
		try {
			connection.setAutoCommit(false);
			// A cada 1000 registros, ele comita, evitando comitar tudo de uma vez
			for (int countRegister = 1; countRegister < employeeEntity.size(); countRegister++) {
				preparedStatement.setString(1, employeeEntity.get(countRegister).getName());
				preparedStatement.setBigDecimal(2, employeeEntity.get(countRegister).getSalary());
				preparedStatement.setTimestamp(3, Timestamp.valueOf(employeeEntity.get(countRegister)
						.getBithday().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()));
				preparedStatement.addBatch();
				if (countRegister % 1000 == 0 || countRegister == employeeEntity.size() -1) {
					preparedStatement.executeBatch();
				}
			}
			preparedStatement.executeBatch();
			connection.commit();
		} catch (SQLException ex) {
			connection.rollback(); // Caso a operação falhe
			ex.printStackTrace();
		}
	}
	
	public void update(final EmployeeEntity employeeEntity) {
		try {
			Connection connection = ConnectionUtil.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(""
					+ "UPDATE employees SET name =?, salary =?, birthday =? WHERE id =?");
			
			preparedStatement.setString(1, employeeEntity.getName());
			preparedStatement.setBigDecimal(2, employeeEntity.getSalary());
			preparedStatement.setTimestamp(3, Timestamp.valueOf(employeeEntity.getBithday().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()));
			preparedStatement.setLong(4, employeeEntity.getId());
			
			preparedStatement.executeUpdate();

		} catch(SQLException ex) {
			ex.printStackTrace();
			}
	}

	public void delete(final long id) {		
		try {
		Connection connection = ConnectionUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(""
				+ "DELETE FROM employees WHERE id =?");
		
		preparedStatement.setLong(1, id);
		
		preparedStatement.execute();
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
			PreparedStatement preparedStatement = connection.prepareStatement(""
					+ "SELECT * FROM employees HWERE id =?");
			
			ResultSet resultSet = preparedStatement.getResultSet();
			
			preparedStatement.setLong(1, id);
			preparedStatement.executeQuery();
			
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
