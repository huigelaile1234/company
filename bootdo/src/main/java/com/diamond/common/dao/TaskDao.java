package com.diamond.common.dao;

import com.diamond.common.entity.Task;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskDao {

	Task get(Long id);
	
	List<Task> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(Task task);
	
	int update(Task task);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
