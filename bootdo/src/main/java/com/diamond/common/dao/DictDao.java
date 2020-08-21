package com.diamond.common.dao;

import com.diamond.common.entity.Dict;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DictDao {

    Dict get(Long id);

    List<Dict> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(Dict dict);

    int batchInsert(List<Dict> list);

    int update(Dict dict);

    int remove(Long id);

    int batchRemove(Long[] ids);

    List<Dict> batchQuery(Long[] ids);

    List<Dict> listType();
}
