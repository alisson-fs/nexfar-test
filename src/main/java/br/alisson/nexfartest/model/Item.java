package br.alisson.nexfartest.model;

import lombok.Getter;

@Getter
public class Item {
	private String branchId;
	private String warehouseId;
	private Produto product;
	private Integer quantity;
	private Integer quantityAttended;
	private Double price;
	private Double finalPrice;
	private Double taxes;
	private Double pmcPrince;
	private Double discount;
	private Boolean subsidized;
}
