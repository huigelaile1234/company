package com.diamond.system.controller;

import com.diamond.common.constant.BaseConstant;
import com.diamond.common.controller.BaseController;
import com.diamond.common.entity.Tree;
import com.diamond.common.base.CustomResponse;
import com.diamond.system.entity.Dept;
import com.diamond.system.service.DeptService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门管理
 */

@Controller
@RequestMapping("/system/sysDept")
public class DeptController extends BaseController {
    private String prefix = "system/dept";
    @Autowired
    private DeptService sysDeptService;

    @GetMapping()
    @RequiresPermissions("system:sysDept:sysDept")
    String dept() {
        return prefix + "/dept";
    }

    @ApiOperation(value = "获取部门列表", notes = "")
    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("system:sysDept:sysDept")
    public List<Dept> list() {
        Map<String, Object> query = new HashMap<>(16);
        List<Dept> sysDeptList = sysDeptService.list(query);
        return sysDeptList;
    }

    @GetMapping("/add/{pId}")
    @RequiresPermissions("system:sysDept:add")
    String add(@PathVariable("pId") Long pId, Model model) {
        model.addAttribute("pId", pId);
        if (pId == 0) {
            model.addAttribute("pName", "总部门");
        } else {
            model.addAttribute("pName", sysDeptService.get(pId).getName());
        }
        return prefix + "/add";
    }

    @GetMapping("/edit/{deptId}")
    @RequiresPermissions("system:sysDept:edit")
    String edit(@PathVariable("deptId") Long deptId, Model model) {
        Dept sysDept = sysDeptService.get(deptId);
        model.addAttribute("sysDept", sysDept);
        if (BaseConstant.DEPT_ROOT_ID.equals(sysDept.getParentId())) {
            model.addAttribute("parentDeptName", "无");
        } else {
            Dept parDept = sysDeptService.get(sysDept.getParentId());
            model.addAttribute("parentDeptName", parDept.getName());
        }
        return prefix + "/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("system:sysDept:add")
    public CustomResponse save(Dept sysDept) {
        if (sysDeptService.save(sysDept) > 0) {
            return CustomResponse.ok();
        }
        return CustomResponse.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("system:sysDept:edit")
    public CustomResponse update(Dept sysDept) {
        if (sysDeptService.update(sysDept) > 0) {
            return CustomResponse.ok();
        }
        return CustomResponse.error();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("system:sysDept:remove")
    public CustomResponse remove(Long deptId) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("parentId", deptId);
        if (sysDeptService.count(map) > 0) {
            return CustomResponse.error(1, "包含下级部门,不允许修改");
        }
        if (sysDeptService.checkDeptHasUser(deptId)) {
            if (sysDeptService.remove(deptId) > 0) {
                return CustomResponse.ok();
            }
        } else {
            return CustomResponse.error(1, "部门包含用户,不允许修改");
        }
        return CustomResponse.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("system:sysDept:batchRemove")
    public CustomResponse remove(@RequestParam("ids[]") Long[] deptIds) {

        sysDeptService.batchRemove(deptIds);
        return CustomResponse.ok();
    }

    @GetMapping("/tree")
    @ResponseBody
    public Tree<Dept> tree() {
        Tree<Dept> tree = new Tree<Dept>();
        tree = sysDeptService.getTree();
        return tree;
    }

    @GetMapping("/treeView")
    String treeView() {
        return prefix + "/deptTree";
    }

}
