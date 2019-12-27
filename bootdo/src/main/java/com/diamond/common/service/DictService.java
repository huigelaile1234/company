package com.diamond.common.service;

import com.diamond.common.entity.Dict;
import com.diamond.system.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 字典表
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-29 18:28:07
 */
public interface DictService {

    Dict get(Long id);

    List<Dict> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(Dict dict);

    int update(Dict dict);

    int remove(Long id);

    int batchRemove(Long[] ids);

    List<Dict> batchQuery(Long[] ids);

    String batchInsert(List<Dict> list, boolean updateSupport, Long userId);

    List<Dict> listType();

    String getName(String type, String value);

    /**
     * 获取爱好列表
     *
     * @param user
     * @return
     */
    List<Dict> getHobbyList(User user);

    /**
     * 获取性别列表
     *
     * @return
     */
    List<Dict> getSexList();

    /**
     * 根据type获取数据
     *
     * @param map
     * @return
     */
    List<Dict> listByType(String type);

}
