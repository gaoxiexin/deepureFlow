package com.tasly.deepureflow.enums;

public enum ZoneFunctionEnum {
	FLOW(1 , "流向功能"),
	PLAN(2,"计划功能"),
	FLOWANDPLAN(3,"流向计划功能");
	
	private  int value;
	private String name;
	
	ZoneFunctionEnum(int value, String name) {
		this.value = value;
		this.name = name;
    }
	/**
	 * @return the type
	 */
	public int getValue() {return value;}
	public String getName() {
		return name;
	}
}
