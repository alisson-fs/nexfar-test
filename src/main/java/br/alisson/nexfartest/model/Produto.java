package br.alisson.nexfartest.model;

import lombok.Getter;

import org.bson.types.ObjectId;

@Getter
public class Produto {
	private ObjectId id;
	private String sku;
	private String ncmNumber;
	private String ean;
	private String barcode;
	private String name;
	private String maker;
	private String category;
	private String principle;
	private Double boxQuantity;
	private String imageURL;
	private External externalRef;
	private Double averagePrice;
}
