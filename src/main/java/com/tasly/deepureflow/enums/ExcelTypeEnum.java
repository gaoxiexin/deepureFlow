package com.tasly.deepureflow.enums;

public enum ExcelTypeEnum {
	PRODUCT(1,"产品"),
	PRODUCT_CATEGORY(2,"产品种类"),
	ZONE(3,"大区"),
	OFFICE(4,"办事处"),
	STATION(5,"岗位"),
	EMPLOYEE(6,"内部员工"),
	AGENT(7,"经销商"),
	TERMINAL(8,"终端"),
	SALE_PLAN(9,"终端销售计划"),
	AGENT_FLOW(10,"经销商产品流向"),
	TERMINAL_FLOW(11,"终端产品流向");
	
    private int id;
    private String name;

    ExcelTypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    /**
     * @return the type
     */
    public String getName() {
        return name;
    }
}
