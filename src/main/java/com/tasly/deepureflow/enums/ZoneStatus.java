package com.tasly.deepureflow.enums;
/**
 * 
 * @author gxx
 *
 */
public enum ZoneStatus {

	UNACTIVE(0 , "未启用"),
	ACTIVE(1,"启用");
	
	
	private  int value;
	private String name;

	ZoneStatus(int value, String name) {
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
