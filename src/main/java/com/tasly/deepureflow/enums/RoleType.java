package com.tasly.deepureflow.enums;
/**
 * 
 * @author gxx
 *
 */
public enum RoleType {

	HEADQUARTERS_OPERATOR(1 , "headquartersOperator", "总部营运"), 
	REGIONAL_MANAGER(2, "regionalManager", "大区经理"), 
	OFFICT_MANAGER(3, "officeManager", "办事处经理"), 
	SALESMAN(4, "salesMan", "销售代表");
	
	
	private  int value;
	private String name;
	private String description;

	RoleType(int value, String name, String description) {
		this.value = value;
		this.name = name;
		this.description = description;
    }
	/**
	 * @return the type
	 */
	public int getValue() {return value;}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	
	public static RoleType getRoleType(String name){
		for(RoleType roleType : values()){
			if(roleType.getName().equals(name)){
				return roleType;
			}
		}
		return null;
	}
}
