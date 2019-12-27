package com.diamond.system.dao;

import com.diamond.system.entity.Menu;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单管理
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-03 09:45:09
 */
@Mapper
public interface MenuDao {

    Menu get(Long menuId);

    List<Menu> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(Menu menu);

    int update(Menu menu);

    int remove(Long menuId);

    int batchRemove(Long[] menuIds);

    List<Menu> listMenuByUserId(Long id);

    List<String> listUserPerms(Long id);
}
