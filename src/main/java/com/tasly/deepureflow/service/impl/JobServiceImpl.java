package com.tasly.deepureflow.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.stereotype.Service;

import com.tasly.deepureflow.domain.system.JobDTO;
import com.tasly.deepureflow.service.IJobService;
import com.tasly.deepureflow.util.DateUtil;

@Service("jobService") 
public class JobServiceImpl implements IJobService {
	private final Logger logger = Logger.getLogger(HierarchyServiceImpl.class.getName());
	
	@Resource(name="kaslyScheduler")
	private Scheduler scheduler;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<JobDTO> getAllJobs() throws SchedulerException{
		List<JobDTO> jobs = new ArrayList<JobDTO>();
		List<String> jobGroups=scheduler.getJobGroupNames();
		if(CollectionUtils.isNotEmpty(jobGroups)){
			for (String groupName : jobGroups) {
				Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName));
				for (JobKey jobKey : jobKeys) {			
					String jobName = jobKey.getName();
					String jobGroup = jobKey.getGroup();
					//get job's trigger
					JobDTO jobDTO = new JobDTO();
					jobDTO.setJobGroup(jobGroup);
					jobDTO.setJobName(jobName);
					List<Trigger> triggers = (List<Trigger>)scheduler.getTriggersOfJob(jobKey);
					if(!CollectionUtils.isEmpty(triggers)){
						for(Trigger trigger : triggers){
							
							Date nextFireTime = trigger.getNextFireTime();
							if(trigger instanceof CronTrigger){
								String cronExpression = ((CronTrigger)trigger).getCronExpression();
								jobDTO.setCronExpression(cronExpression);
							}
							TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
							jobDTO.setNextFireTime(DateUtil.formatDateByFormat(nextFireTime, DateUtil.DATE_TIME_FORMAT));
							jobDTO.setState(triggerState.name());
							jobDTO.setTriggerKey(trigger.getKey().getName());
						}
					}
					jobs.add(jobDTO);
					
				}
			}
		}
		
		return jobs;
	}
	
	@Override
	public boolean pauseJob(String key){
		boolean result=false;
		try{
			JobKey jobKey = new JobKey(key);
			scheduler.pauseJob(jobKey);
			result=true;
		}catch(SchedulerException e){
			logger.error("暂停任务出错："+e.getMessage(),e);
			result=false;
		}
		return result;
	}
	
	@Override
	public boolean resumeJob(String key){
		boolean result=false;
		try{
			JobKey jobKey = new JobKey(key);
			scheduler.resumeJob(jobKey);
			result=true;
		}catch(SchedulerException e){
			logger.error("恢复任务出错："+e.getMessage(),e);
			result=false;
		}
		return result;
	}

	@Override
	public boolean triggerJob(String key){
		boolean result=false;
		try{
			JobKey jobKey = new JobKey(key);
			scheduler.triggerJob(jobKey);
			result=true;
		}catch(SchedulerException e){
			logger.error("手动触发任务出错："+e.getMessage(),e);
			result=false;
		}
		return result;
	}

	@Override
	public boolean deleteJob(String key) throws SchedulerException {
		JobKey jobKey = new JobKey(key);
		return scheduler.deleteJob(jobKey);
		
	}
	
	public boolean rescheduleTrigger(String key, String cronExpression) throws SchedulerException{
		TriggerKey triggerKey = new TriggerKey(key);
		Trigger trigger = scheduler.getTrigger(triggerKey);
		if(trigger instanceof CronTrigger){
			try {
				((CronTriggerImpl)trigger).setCronExpression(cronExpression);
				scheduler.rescheduleJob(triggerKey, trigger);
			} catch (ParseException e) {
				logger.error("转换格式出错："+e.getMessage(),e);
			}
		}
		return true;
	}

}
