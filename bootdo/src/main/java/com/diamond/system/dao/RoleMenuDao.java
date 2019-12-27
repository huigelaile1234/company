package com.diamond.system.dao;

import com.diamond.system.entity.RoleMenu;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 角色与菜单对应关系
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-03 11:08:59
 */
@Mapper
public interface RoleMenuDao {

    RoleMenu get(Long id);

    List<RoleMenu> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(RoleMenu roleMenu);

    int update(RoleMenu roleMenu);

    int remove(Long id);

    int batchRemove(Long[] ids);

    List<Long> listMenuIdByRoleId(Long roleId);

    int removeByRoleId(Long roleId);

    int removeByMenuId(Long menuId);

    int batchSave(List<RoleMenu> list);
}
