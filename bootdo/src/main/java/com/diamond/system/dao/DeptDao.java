package com.diamond.system.dao;

import com.diamond.system.entity.Dept;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 部门管理
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-03 15:35:39
 */
@Mapper
public interface DeptDao {

    Dept get(Long deptId);

    List<Dept> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(Dept dept);

    int update(Dept dept);

    int remove(Long deptId);

    int batchRemove(Long[] deptIds);

    Long[] listParentDept();

    int getDeptUserNumber(Long deptId);
}
