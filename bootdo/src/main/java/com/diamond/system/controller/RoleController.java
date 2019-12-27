package com.diamond.system.controller;

import com.diamond.common.controller.BaseController;
import com.diamond.common.base.CustomResponse;
import com.diamond.system.entity.Role;
import com.diamond.system.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/sys/role")
@Controller
public class RoleController extends BaseController {
    String prefix = "system/role";
    @Autowired
    RoleService roleService;

    @RequiresPermissions("sys:role:role")
    @GetMapping()
    String role() {
        return prefix + "/role";
    }

    @RequiresPermissions("sys:role:role")
    @GetMapping("/list")
    @ResponseBody()
    List<Role> list() {
        List<Role> roles = roleService.list();
        return roles;
    }

    @RequiresPermissions("sys:role:add")
    @GetMapping("/add")
    String add() {
        return prefix + "/add";
    }

    @RequiresPermissions("sys:role:edit")
    @GetMapping("/edit/{id}")
    String edit(@PathVariable("id") Long id, Model model) {
        Role role = roleService.get(id);
        model.addAttribute("role", role);
        return prefix + "/edit";
    }

    @RequiresPermissions("sys:role:add")
    @PostMapping("/save")
    @ResponseBody()
    CustomResponse save(Role role) {

        if (roleService.save(role) > 0) {
            return CustomResponse.ok();
        } else {
            return CustomResponse.error(1, "保存失败");
        }
    }

    @RequiresPermissions("sys:role:edit")
    @PostMapping("/update")
    @ResponseBody()
    CustomResponse update(Role role) {

        if (roleService.update(role) > 0) {
            return CustomResponse.ok();
        } else {
            return CustomResponse.error(1, "保存失败");
        }
    }

    @RequiresPermissions("sys:role:remove")
    @PostMapping("/remove")
    @ResponseBody()
    CustomResponse save(Long id) {

        if (roleService.remove(id) > 0) {
            return CustomResponse.ok();
        } else {
            return CustomResponse.error(1, "删除失败");
        }
    }

    @RequiresPermissions("sys:role:batchRemove")
    @PostMapping("/batchRemove")
    @ResponseBody
    CustomResponse batchRemove(@RequestParam("ids[]") Long[] ids) {

        int r = roleService.batchremove(ids);
        if (r > 0) {
            return CustomResponse.ok();
        }
        return CustomResponse.error();
    }
}
