package com.dio.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class EmployeeEntity {
	
	private long id;
	private String name;
	private OffsetDateTime bithday;
	private BigDecimal salary;
}
