package com.tasly.deepureflow.service;

import java.util.List;

import org.quartz.SchedulerException;

import com.tasly.deepureflow.domain.system.JobDTO;

public interface IJobService {

	List<JobDTO> getAllJobs() throws SchedulerException;

	boolean pauseJob(String key);

	boolean resumeJob(String key);
	
	boolean triggerJob(String key);
	
	boolean deleteJob(String key) throws SchedulerException;
	
	boolean rescheduleTrigger(String key, String cronExpression) throws SchedulerException;

}