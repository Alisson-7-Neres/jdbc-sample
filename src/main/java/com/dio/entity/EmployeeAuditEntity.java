package com.dio.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record EmployeeAuditEntity(
	long employee_id,
	String oldName,
    String name,
	BigDecimal oldSalary,
    BigDecimal salary,
	OffsetDateTime oldBirthday,
	OffsetDateTime birthday,
    OperationEnum operation
    ) {
}
