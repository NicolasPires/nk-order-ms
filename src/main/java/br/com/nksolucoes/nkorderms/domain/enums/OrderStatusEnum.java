package br.com.nksolucoes.nkorderms.domain.enums;

public enum OrderStatusEnum {

	CREATED("Created"),
	CALCULATED("Calculated");

	private final String value;

	OrderStatusEnum(String value) { this.value = value; }

	public String getValue() { return value;}
}
