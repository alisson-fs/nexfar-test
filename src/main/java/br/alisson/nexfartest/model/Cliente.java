package br.alisson.nexfartest.model;

import lombok.Getter;

@Getter
public class Cliente {
	private Integer id;
	private String name;
	private String razaoSocial;
	private String cnpj;
	private String externalClientId;
}
