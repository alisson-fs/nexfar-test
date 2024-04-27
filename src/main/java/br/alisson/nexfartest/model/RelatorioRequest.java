package br.alisson.nexfartest.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatorioRequest {
	private String key;
	private String format;
	private List<FilterRequest> filters;
}
