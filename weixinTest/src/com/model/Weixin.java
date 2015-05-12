package com.model;

public class Weixin implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6749156626074838857L;
	Integer id;
	String name;
	String value;

	public Weixin() {
		super();
	}

	public Weixin(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}