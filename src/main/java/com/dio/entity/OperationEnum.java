package com.dio.entity;

import java.util.stream.Stream;

public enum OperationEnum {
	INSERT, UPDATE, DELETE;

	public static OperationEnum getByDbOperation(final String dbOperation) {
		return Stream.of(OperationEnum.values())
				.filter(value -> value.name()
						.startsWith(dbOperation.toUpperCase()))
				.findFirst()
				.orElseThrow();
	}

}
