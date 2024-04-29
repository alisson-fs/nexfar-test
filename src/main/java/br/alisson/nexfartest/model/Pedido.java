package br.alisson.nexfartest.model;

import java.util.ArrayList;
import java.util.Date;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "order")
public class Pedido {
	private Integer _id;
	private Integer orderId;
	private String status;
	private Cliente client;
	private ArrayList<Item> items;
	private Double netTotal;
	private Double totalWithTaxes;
	private Double originalNetTotal;
	private Double originalTotalWithTaxes;
	private Date createdAt;
	private Date updateAt;
}
