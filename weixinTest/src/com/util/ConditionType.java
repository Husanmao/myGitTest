package com.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ConditionType {

	// 操作符
	public static final String OPER_EQ = " = ";
	public static final String OPER_LE = " <= ";
	public static final String OPER_GE = " >= ";
	public static final String OPER_LIKE = " LIKE ";
	public static final String OPER_NULL = " IS NULL ";
	public static final String OPER_NONULL = " IS NOT NULL ";
	public static final String OPER_GT = " > ";
	public static final String OPER_LT = " < ";
	public static final String OPER_NE = " != ";
	public static final String OPER_EQProperty = " = ";
	public static final String OPER_LEProperty = " <= ";
	public static final String OPER_GEProperty = " >= ";
	public static final String OPER_GTProperty = " > ";
	public static final String OPER_LTProperty = " < ";
	public static final String OPER_NEProperty = " != ";

	public static final int IOPER_EQ = 0;
	public static final int IOPER_LE = 1;
	public static final int IOPER_GE = 2;
	public static final int IOPER_LIKE = 3;
	public static final int IOPER_NULL = 4;
	public static final int IOPER_NONULL = 5;
	public static final int IOPER_GT = 6;
	public static final int IOPER_LT = 7;
	public static final int IOPER_NE = 8;
	public static final int IOPER_IN = 9;
	public static final int IOPER_EQProperty = 10;
	public static final int IOPER_LEProperty = 11;
	public static final int IOPER_GEProperty = 12;
	public static final int IOPER_GTProperty = 13;
	public static final int IOPER_LTProperty = 14;
	public static final int IOPER_NEProperty = 15;

	// 数据类型
	public static final int DATA_STRING = 1;
	public static final int DATA_NUMBER = 2;
	public static final int DATA_DATE = 3;

	// 连接类型
	public static final int LINK_AND = 11;
	public static final int LINK_OR = 12;

	// 名-值对
	private String name;
	private String otherName;
	private Object value;
	private Object[] values;

	private String operator;
	private int ioperator;
	private Integer dataType;
	private int linkType;

	List<ConditionType> conditionTypes = null;

	public ConditionType() {
	}

	public ConditionType(String name, String otherName, String operator) {
		this.name = name;
		this.otherName = otherName;
		this.operator = operator;
	}

	public ConditionType(String name, String otherName, int ioperator) {
		this.name = name;
		this.otherName = otherName;
		this.ioperator = ioperator;
	}

	public ConditionType(String name, Object value, String operator) {
		this.name = name;
		this.value = value;
		this.operator = operator;
	}

	public ConditionType(String name, Object value, int ioperator) {
		this.name = name;
		this.value = value;
		this.ioperator = ioperator;
	}

	public ConditionType(String name, Object[] values, String operator) {
		this.name = name;
		this.values = values;
		this.operator = operator;
	}

	public ConditionType(String name, Object[] values, int ioperator) {
		this.name = name;
		this.values = values;
		this.ioperator = ioperator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object[] getValues() {
		return values;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public int getIoperator() {
		return ioperator;
	}

	public void setIoperator(int ioperator) {
		this.ioperator = ioperator;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public int getLinkType() {
		return linkType;
	}

	public void setLinkType(int linkType) {
		this.linkType = linkType;
	}

	public List<ConditionType> getConditionTypes() {
		return conditionTypes;
	}

	public void setConditionTypes(List<ConditionType> conditionTypes) {
		this.conditionTypes = conditionTypes;
	}

	public static ConditionType eq(String name, Object value) {
		return new ConditionType(name, value, ConditionType.IOPER_EQ);
	}

	public static ConditionType le(String name, Object value) {
		return new ConditionType(name, value, ConditionType.IOPER_LE);
	}

	public static ConditionType ge(String name, Object value) {
		return new ConditionType(name, value, ConditionType.IOPER_GE);
	}

	public static ConditionType like(String name, Object value) {
		return new ConditionType(name, value, ConditionType.IOPER_LIKE);
	}

	public static ConditionType isNull(String name, Integer dataType) {
		return new ConditionType(name, "", ConditionType.IOPER_NULL);
	}

	public static ConditionType noNull(String name, Integer dataType) {
		return new ConditionType(name, "", ConditionType.IOPER_NONULL);
	}

	public static ConditionType gt(String name, Object value) {
		return new ConditionType(name, value, ConditionType.IOPER_GT);
	}

	public static ConditionType lt(String name, Object value) {
		return new ConditionType(name, value, ConditionType.IOPER_LT);
	}

	public static ConditionType ne(String name, Object value) {
		return new ConditionType(name, value, ConditionType.IOPER_NE);
	}

	public static ConditionType in(String name, Object[] values) {
		return new ConditionType(name, values, ConditionType.IOPER_IN);
	}

	public static ConditionType or(ConditionType... conditionTypes) {
		ConditionType ct = new ConditionType();
		ct.setLinkType(LINK_OR);
		List<ConditionType> cts = new LinkedList<ConditionType>();

		for (ConditionType c : conditionTypes) {
			cts.add(c);
		}
		ct.setConditionTypes(cts);
		return ct;
	}

	public static ConditionType EQProperty(String name, String otherName) {
		return new ConditionType(name, otherName,ConditionType.IOPER_EQProperty);
	}
	public static ConditionType LEProperty(String name, String otherName) {
		return new ConditionType(name, otherName,ConditionType.IOPER_LEProperty);
	}
	public static ConditionType GEProperty(String name, String otherName) {
		return new ConditionType(name, otherName,ConditionType.IOPER_GEProperty);
	}
	public static ConditionType GTProperty(String name, String otherName) {
		return new ConditionType(name, otherName,ConditionType.IOPER_GTProperty);
	}
	public static ConditionType LTProperty(String name, String otherName) {
		return new ConditionType(name, otherName,ConditionType.IOPER_LTProperty);
	}
	public static ConditionType NEProperty(String name, String otherName) {
		return new ConditionType(name, otherName,ConditionType.IOPER_NEProperty);
	}

	@Override
	public String toString() {
		return "ConditionType [name=" + name + ", otherName=" + otherName
				+ ", value=" + value + ", values=" + Arrays.toString(values)
				+ ", operator=" + operator + ", ioperator=" + ioperator
				+ ", dataType=" + dataType + ", linkType=" + linkType + "]";
	}


	
	
}
