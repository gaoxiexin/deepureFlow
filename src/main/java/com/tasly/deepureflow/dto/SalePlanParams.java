package com.tasly.deepureflow.dto;

import java.io.Serializable;

import com.tasly.deepureflow.domain.deepureflow.PlanItem;

public class SalePlanParams implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PlanItem[] planItemList;
	private String terminalId;
	private String salePlanId;
	private String salePlanDate;

	public PlanItem[] getPlanItemList() {
		return planItemList;
	}
	public void setPlanItemList(PlanItem[] planItemList) {
		this.planItemList = planItemList;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public String getSalePlanId() {
		return salePlanId;
	}
	public void setSalePlanId(String salePlanId) {
		this.salePlanId = salePlanId;
	}
	public String getSalePlanDate() {
		return salePlanDate;
	}
	public void setSalePlanDate(String salePlanDate) {
		this.salePlanDate = salePlanDate;
	}
	
	
}
