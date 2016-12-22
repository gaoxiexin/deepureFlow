package com.tasly.deepureflow.enums;
/**
 * 
 * @author gxx
 *
 */
public enum DeleteStatus{

	NODELETE(0 , "未删除"),
	DELETE(1,"已删除");
	
	
	private  int value;
	private String name;

	DeleteStatus(int value, String name) {
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
