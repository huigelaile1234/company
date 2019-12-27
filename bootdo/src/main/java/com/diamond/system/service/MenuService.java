package com.diamond.system.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.diamond.common.entity.Tree;
import com.diamond.system.entity.Menu;

@Service
public interface MenuService {
    Tree<Menu> getSysMenuTree(Long id);

    List<Tree<Menu>> listMenuTree(Long id);

    Tree<Menu> getTree();

    Tree<Menu> getTree(Long id);

    List<Menu> list(Map<String, Object> params);

    int remove(Long id);

    int save(Menu menu);

    int update(Menu menu);

    Menu get(Long id);

    Set<String> listPerms(Long userId);
}
