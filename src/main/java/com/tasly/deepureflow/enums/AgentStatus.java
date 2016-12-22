package com.tasly.deepureflow.enums;
/**
 * 
 * @author gxx
 *
 */
public enum AgentStatus{

	UNDEVELOPED(0 , "待开发"),
	DEVELOPED(1,"正式");
	
	
	private  int value;
	private String name;

	AgentStatus(int value, String name) {
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
