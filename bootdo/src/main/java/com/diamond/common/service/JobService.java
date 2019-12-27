package com.diamond.common.service;

import com.diamond.common.entity.Task;

import java.util.List;
import java.util.Map;

import org.quartz.SchedulerException;

public interface JobService {
	
	Task get(Long id);
	
	List<Task> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(Task taskScheduleJob);
	
	int update(Task taskScheduleJob);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	void initSchedule() throws SchedulerException;

	void changeStatus(Long jobId, String cmd) throws SchedulerException;

	void updateCron(Long jobId) throws SchedulerException;
}
