package com.tasly.deepureflow.dto;

import java.io.Serializable;

import com.tasly.deepureflow.domain.deepureflow.TerminalFlowItem;

public class TerminalFlowParams implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3786989150206846892L;
	private String terminalId;
	private String terminalFlowId;
	private String terminalFlowDate;

	private TerminalFlowItem[]  terminalFlowItemList;
	
	public TerminalFlowItem[] getTerminalFlowItemList() {
		return terminalFlowItemList;
	}
	public void setTerminalFlowItemList(TerminalFlowItem[] terminalFlowItemList) {
		this.terminalFlowItemList = terminalFlowItemList;

}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public String getTerminalFlowId() {
		return terminalFlowId;
	}
	public void setTerminalFlowId(String terminalFlowId) {
		this.terminalFlowId = terminalFlowId;
	}
	public String getTerminalFlowDate() {
		return terminalFlowDate;
	}
	public void setTerminalFlowDate(String terminalFlowDate) {
		this.terminalFlowDate = terminalFlowDate;
	}
}
