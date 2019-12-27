package com.diamond.system.controller;

import com.diamond.common.controller.BaseController;
import com.diamond.common.entity.Tree;
import com.diamond.common.base.CustomResponse;
import com.diamond.system.entity.Menu;
import com.diamond.system.service.MenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/sys/menu")
@Controller
public class MenuController extends BaseController {
    String prefix = "system/menu";
    @Autowired
    MenuService menuService;

    @RequiresPermissions("sys:menu:menu")
    @GetMapping()
    String menu(Model model) {
        return prefix + "/menu";
    }

    @RequiresPermissions("sys:menu:menu")
    @RequestMapping("/list")
    @ResponseBody
    List<Menu> list(@RequestParam Map<String, Object> params) {
        List<Menu> menus = menuService.list(params);
        return menus;
    }

    @RequiresPermissions("sys:menu:add")
    @GetMapping("/add/{pId}")
    String add(Model model, @PathVariable("pId") Long pId) {
        model.addAttribute("pId", pId);
        if (pId == 0) {
            model.addAttribute("pName", "根目录");
        } else {
            model.addAttribute("pName", menuService.get(pId).getName());
        }
        return prefix + "/add";
    }

    @RequiresPermissions("sys:menu:edit")
    @GetMapping("/edit/{id}")
    String edit(Model model, @PathVariable("id") Long id) {
        Menu mdo = menuService.get(id);
        Long pId = mdo.getParentId();
        model.addAttribute("pId", pId);
        if (pId == 0) {
            model.addAttribute("pName", "根目录");
        } else {
            model.addAttribute("pName", menuService.get(pId).getName());
        }
        model.addAttribute("menu", mdo);
        return prefix + "/edit";
    }

    @RequiresPermissions("sys:menu:add")
    @PostMapping("/save")
    @ResponseBody
    CustomResponse save(Menu menu) {

        if (menuService.save(menu) > 0) {
            return CustomResponse.ok();
        } else {
            return CustomResponse.error(1, "保存失败");
        }
    }

    @RequiresPermissions("sys:menu:edit")
    @PostMapping("/update")
    @ResponseBody
    CustomResponse update(Menu menu) {

        if (menuService.update(menu) > 0) {
            return CustomResponse.ok();
        } else {
            return CustomResponse.error(1, "更新失败");
        }
    }

    @RequiresPermissions("sys:menu:remove")
    @PostMapping("/remove")
    @ResponseBody
    CustomResponse remove(Long id) {

        if (menuService.remove(id) > 0) {
            return CustomResponse.ok();
        } else {
            return CustomResponse.error(1, "删除失败");
        }
    }

    @GetMapping("/tree")
    @ResponseBody
    Tree<Menu> tree() {
        Tree<Menu> tree = menuService.getTree();
        return tree;
    }

    @GetMapping("/tree/{roleId}")
    @ResponseBody
    Tree<Menu> tree(@PathVariable("roleId") Long roleId) {
        Tree<Menu> tree = menuService.getTree(roleId);
        return tree;
    }
}
