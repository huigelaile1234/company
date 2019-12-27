package com.diamond.system.controller;

import com.diamond.common.base.CustomResponse;
import com.diamond.common.base.Query;
import com.diamond.common.controller.BaseController;
import com.diamond.common.entity.Tree;
import com.diamond.common.service.DictService;
import com.diamond.common.utils.*;
import com.diamond.system.entity.Dept;
import com.diamond.system.entity.Role;
import com.diamond.system.entity.User;
import com.diamond.system.service.RoleService;
import com.diamond.system.service.UserService;
import com.diamond.system.vo.UserVO;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/sys/user")
@Controller
public class UserController extends BaseController {
    private String prefix = "system/user";
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    DictService dictService;

    @RequiresPermissions("sys:user:user")
    @GetMapping("")
    String user(Model model) {
        return prefix + "/user";
    }

    @GetMapping("/list")
    @ResponseBody
    PageUtils list(@RequestParam Map<String, Object> params) {
        // 查询列表数据
        Query query = new Query(params);
        List<User> sysUserList = userService.list(query);
        int total = userService.count(query);
        PageUtils pageUtil = new PageUtils(sysUserList, total);
        return pageUtil;
    }

    @RequiresPermissions("sys:user:add")
    @GetMapping("/add")
    String add(Model model) {
        List<Role> roles = roleService.list();
        model.addAttribute("roles", roles);
        return prefix + "/add";
    }

    @RequiresPermissions("sys:user:edit")
    @GetMapping("/edit/{id}")
    String edit(Model model, @PathVariable("id") Long id) {
        User user = userService.get(id);
        model.addAttribute("user", user);
        List<Role> roles = roleService.list(id);
        model.addAttribute("roles", roles);
        return prefix + "/edit";
    }

    @RequiresPermissions("sys:user:add")
    @PostMapping("/save")
    @ResponseBody
    CustomResponse save(User user) {

        user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
        if (userService.save(user) > 0) {
            return CustomResponse.ok();
        }
        return CustomResponse.error();
    }

    @RequiresPermissions("sys:user:edit")
    @PostMapping("/update")
    @ResponseBody
    CustomResponse update(User user) {

        if (userService.update(user) > 0) {
            return CustomResponse.ok();
        }
        return CustomResponse.error();
    }


    @RequiresPermissions("sys:user:edit")
    @PostMapping("/updatePeronal")
    @ResponseBody
    CustomResponse updatePeronal(User user) {

        if (userService.updatePersonal(user) > 0) {
            return CustomResponse.ok();
        }
        return CustomResponse.error();
    }


    @RequiresPermissions("sys:user:remove")
    @PostMapping("/remove")
    @ResponseBody
    CustomResponse remove(Long id) {

        if (userService.remove(id) > 0) {
            return CustomResponse.ok();
        }
        return CustomResponse.error();
    }

    @RequiresPermissions("sys:user:batchRemove")
    @PostMapping("/batchRemove")
    @ResponseBody
    CustomResponse batchRemove(@RequestParam("ids[]") Long[] userIds) {

        int r = userService.batchremove(userIds);
        if (r > 0) {
            return CustomResponse.ok();
        }
        return CustomResponse.error();
    }

    @PostMapping("/exit")
    @ResponseBody
    boolean exit(@RequestParam Map<String, Object> params) {
        // 存在，不通过，false
        return !userService.exit(params);
    }

    @RequiresPermissions("sys:user:resetPwd")
    @GetMapping("/resetPwd/{id}")
    String resetPwd(@PathVariable("id") Long userId, Model model) {

        User user = new User();
        user.setUserId(userId);
        model.addAttribute("user", user);
        return prefix + "/reset_pwd";
    }

    @PostMapping("/resetPwd")
    @ResponseBody
    CustomResponse resetPwd(UserVO userVO) {

        try {
            userService.resetPwd(userVO, getUser());
            return CustomResponse.ok();
        } catch (Exception e) {
            return CustomResponse.error(1, e.getMessage());
        }

    }

    @RequiresPermissions("sys:user:resetPwd")
    @PostMapping("/adminResetPwd")
    @ResponseBody
    CustomResponse adminResetPwd(UserVO userVO) {

        try {
            userService.adminResetPwd(userVO);
            return CustomResponse.ok();
        } catch (Exception e) {
            return CustomResponse.error(1, e.getMessage());
        }

    }

    @GetMapping("/tree")
    @ResponseBody
    public Tree<Dept> tree() {
        Tree<Dept> tree = new Tree<Dept>();
        tree = userService.getTree();
        return tree;
    }

    @GetMapping("/treeView")
    String treeView() {
        return prefix + "/userTree";
    }

    @GetMapping("/personal")
    String personal(Model model) {
        User user = userService.get(getUserId());
        model.addAttribute("user", user);
        model.addAttribute("hobbyList", dictService.getHobbyList(user));
        model.addAttribute("sexList", dictService.getSexList());
        return prefix + "/personal";
    }
}
