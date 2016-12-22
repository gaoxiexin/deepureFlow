package com.tasly.deepureflow.enums;

public enum SearchEnum {
	AGENT(1 , "查询经销商"),
	TERMINAL(2,"查询终端"),
	SALEPLAN(3,"查询销售计划"),
	AGENTFLOW(4,"查询经销商产品流向"),
	TERMINALFLOW(5,"查询终端产品流向");
	
	private  int value;
	private String name;
	
	SearchEnum(int value, String name) {
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
