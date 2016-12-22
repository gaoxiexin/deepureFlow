package com.tasly.deepureflow.enums;
/**
 * 
 * @author gxx
 *
 */
public enum ZonePlanEnum {

	PLAN(0 , "计划"),
	FLOW(1,"流向");
	
	
	private  int value;
	private String name;

	ZonePlanEnum(int value, String name) {
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
