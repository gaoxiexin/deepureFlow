package com.tasly.deepureflow.schedule;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Transactional;

import com.tasly.deepureflow.dao.IZoneDao;
import com.tasly.deepureflow.domain.system.Zone;
import com.tasly.deepureflow.domain.system.ZonePlan;
import com.tasly.deepureflow.enums.ZoneStatus;

public class PlanForZoneJob extends QuartzJobBean {
	private final Logger logger = Logger.getLogger(PlanForZoneJob.class
			.getName());
	@Autowired
	private IZoneDao zoneDao;

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {

		List<Zone> zoneList = zoneDao.findZoneForJob();
		try {
			if (CollectionUtils.isNotEmpty(zoneList)) {
				for (Zone zone : zoneList) {
					System.out.println(zone.getZoneName());
					if (CollectionUtils.isNotEmpty(zone.getZonePlanList())) {
						for (ZonePlan zonePlan : zone.getZonePlanList()) {
							int type = zonePlan.getPlanType();
							int startDay = zonePlan.getPlanStartDay();
							int endDay = zonePlan.getPlanEndDay();
							changeZoneStatus(zone, type, startDay, endDay);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("定时任务报错：" + e.getMessage(), e);
		}
	}

	private boolean changeZoneStatus(Zone zone, int type, int startDay,
			int endDay) {
		Calendar now = Calendar.getInstance();
		int nowDay = now.get(Calendar.DATE);
		boolean shouldUpdate = false;

		int status = -1;
		if (type == 0) {
			status = zone.getZonePlanStatus();
			if (nowDay >= startDay && nowDay <= endDay) {
				if (status == ZoneStatus.UNACTIVE.getValue()) {
					zone.setZonePlanStatus(ZoneStatus.ACTIVE.getValue());
					shouldUpdate = true;
				}
			} else {
				if (status == ZoneStatus.ACTIVE.getValue()) {
					zone.setZonePlanStatus(ZoneStatus.UNACTIVE.getValue());
					shouldUpdate = true;
				}
			}
		} else if (type == 1) {
			status = zone.getZoneFlowStatus();
			if (nowDay >= startDay && nowDay <= endDay) {
				if (status == ZoneStatus.UNACTIVE.getValue()) {
					zone.setZoneFlowStatus(ZoneStatus.ACTIVE.getValue());
					shouldUpdate = true;
				}
			} else {
				if (status == ZoneStatus.ACTIVE.getValue()) {
					zone.setZoneFlowStatus(ZoneStatus.UNACTIVE.getValue());
					shouldUpdate = true;
				}
			}
		}
		if (shouldUpdate) {
			boolean updateResult = zoneDao.editZone(zone);
			return updateResult;
		}
		return false;
	}

}
