package com.diamond.system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.diamond.system.entity.Role;

@Service
public interface RoleService {

    Role get(Long id);

    List<Role> list();

    int save(Role role);

    int update(Role role);

    int remove(Long id);

    List<Role> list(Long userId);

    int batchremove(Long[] ids);
}
