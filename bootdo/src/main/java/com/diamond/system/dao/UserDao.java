package com.diamond.system.dao;

import com.diamond.system.entity.User;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-03 09:45:11
 */
@Mapper
public interface UserDao {

    User get(Long userId);

    List<User> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(User user);

    int update(User user);

    int remove(Long userId);

    int batchRemove(Long[] userIds);

    Long[] listAllDept();

}
