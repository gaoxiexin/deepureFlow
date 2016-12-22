package com.tasly.deepureflow.dto;

import java.io.Serializable;

import com.tasly.deepureflow.domain.deepureflow.AgentFlowShipmentItem;

public class AgentFlowShipmentParams implements Serializable {

	private static final long serialVersionUID = 2547775323127537651L;
	
	private String agentId;
	private String agentFlowId;
	private String agentFlowDate;

	private AgentFlowShipmentItem[]  agentFlowShipmentItemList;
	private String[] delAgentFlowItemIds;


	public String[] getDelAgentFlowItemIds() {
		return delAgentFlowItemIds;
	}

	public void setDelAgentFlowItemIds(String[] delAgentFlowItemIds) {
		this.delAgentFlowItemIds = delAgentFlowItemIds;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgentFlowId() {
		return agentFlowId;
	}

	public void setAgentFlowId(String agentFlowId) {
		this.agentFlowId = agentFlowId;
	}

	public String getAgentFlowDate() {
		return agentFlowDate;
	}

	public void setAgentFlowDate(String agentFlowDate) {
		this.agentFlowDate = agentFlowDate;
	}

	public AgentFlowShipmentItem[] getAgentFlowShipmentItemList() {
		return agentFlowShipmentItemList;
	}

	public void setAgentFlowShipmentItemList(AgentFlowShipmentItem[] agentFlowShipmentItemList) {
		this.agentFlowShipmentItemList = agentFlowShipmentItemList;
	}

	
	
	

}
