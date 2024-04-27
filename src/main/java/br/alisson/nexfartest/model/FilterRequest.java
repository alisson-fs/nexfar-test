package br.alisson.nexfartest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterRequest {
	private String key;
	private String operation;
	private String value1;
	private String value2;
}
