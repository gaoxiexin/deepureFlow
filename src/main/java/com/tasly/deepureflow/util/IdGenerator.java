package com.tasly.deepureflow.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;

public class IdGenerator {
	public static final String ORDER_ITEM_PREFIX = "ITEM"; // 行项目
	public static final String SALE_PLAN_PREFIX = "SAP";// 销售计划
	public static final String TERMINAL_FLOW_PREFIX = "TEF";// 终端流向
	public static final String AGENT_FLOW_PREFIX = "AGF";// 经销商流向
	public static final String USER_PREFIX="USR";
	public static final String TERMINAL_PREFIX="TER";
	public static final String AGENT_PREFIX="AGE";
	private IdGenerator() {

	}
	
	public static String generateCode(String keyPrefix, int randomNum) {
		String num = RandomStringUtils.random(randomNum, false, true);
		String dateStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String id = new StringBuilder().append(keyPrefix).append(dateStr)
				.append(num).toString();
		return id;
	}

	public static String generateItemCode(String orderCode, int index) {
		String number = String.format("%03d", index);
		String id = new StringBuilder().append(orderCode).append("-")
				.append(ORDER_ITEM_PREFIX).append("-").append(number).toString();
		return id;
	}
}
