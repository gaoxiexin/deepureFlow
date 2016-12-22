package com.tasly.deepureflow.enums;

public enum MenuEnum {
		MASTER(1,"主数据管理"),
		PLANFLOW(2,"计划流向管理"),
		SYSTEM(3,"系统管理"),
		AGENT(4,"经销商管理"),
		TERMINAL(5,"终端管理"),
		TERMINALPLAN(6,"终端销售计划管理"),
		AGENTPRODUCT(7,"经销商产品流向管理"),
		TERMINALPRODUCT(8,"终端产品流向管理"),
		EMPLOYEE(9,"内部员工管理"),
		ROLE(10,"角色权限管理"),
		PLANOPEN(11,"计划流向开放管理"),
		HIERARCHY(12,"体系管理"),
		CHANNEL(13,"渠道管理"),
		ZONE(14,"大区管理"),
		JOB(15,"任务管理"),
		OFFICE(16,"办事处管理"),
		STATION(17,"岗位管理"),
		PRODUCT(18,"产品管理"),
		PRODUCT_CATEGORY(19,"产品类型管理");
		
	    private int id;
	    private String name;

	    MenuEnum(int id, String name) {
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
