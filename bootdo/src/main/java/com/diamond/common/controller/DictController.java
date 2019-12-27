package com.diamond.common.controller;

import com.diamond.common.base.CustomResponse;
import com.diamond.common.base.Query;
import com.diamond.common.entity.Dict;
import com.diamond.common.service.DictService;
import com.diamond.common.utils.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/common/dict")
public class DictController extends BaseController {

    @Autowired
    private DictService dictService;

    @GetMapping()
    @RequiresPermissions("common:dict:dict")
    String dict() {
        return "common/dict/dict";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("common:dict:dict")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        // 查询列表数据
        Query query = new Query(params);
        List<Dict> dictList = dictService.list(query);
        int total = dictService.count(query);
        PageUtils pageUtils = new PageUtils(dictList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("common:dict:add")
    String add() {
        return "common/dict/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("common:dict:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        Dict dict = dictService.get(id);
        model.addAttribute("dict", dict);
        return "common/dict/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("common:dict:add")
    public CustomResponse save(Dict dict) {
        if (dictService.save(dict) > 0) {
            return CustomResponse.ok();
        }
        return CustomResponse.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("common:dict:edit")
    public CustomResponse update(Dict dict) {
        dictService.update(dict);
        return CustomResponse.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("common:dict:remove")
    public CustomResponse remove(Long id) {
        if (dictService.remove(id) > 0) {
            return CustomResponse.ok();
        }
        return CustomResponse.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("common:dict:batchRemove")
    public CustomResponse remove(@RequestParam("ids[]") Long[] ids) {
        dictService.batchRemove(ids);
        return CustomResponse.ok();
    }

    /**
     * 导出数据
     */
    @PostMapping("/export")
    @ResponseBody
    @RequiresPermissions("common:dict:export")
    public CustomResponse exportExcel(@RequestParam("ids[]") Long[] ids, HttpServletRequest request, HttpServletResponse response) {
        List<Dict> list = dictService.batchQuery(ids);
        ExcelUtil<Dict> util = new ExcelUtil<Dict>(Dict.class);
        String filePath = util.exportExcel(list, "用户数据");
        String fileName = filePath.split("_")[1];
        return CustomResponse.ok(fileName);
    }

    /**
     * 将Excel通过Browser下载
     *
     * @param fileName
     * @param request
     * @param response
     */
    @GetMapping("/downloadExcel")
    @ResponseBody
    private void downloadExcelByBrowser(String fileName, Boolean delete, HttpServletRequest request, HttpServletResponse response) {
        ExcelUtil<Dict> util = new ExcelUtil<Dict>(Dict.class);
        String filePath = util.getAbsoluteFile(fileName);
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        try {
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + FileUtil.setFileDownloadHeader(request, fileName));
            FileUtil.writeBytes(filePath, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (delete) {
            FileUtil.deleteFile(filePath);
        }
    }

    @RequiresPermissions("common:dict:import")
    @PostMapping("/importExcel")
    @ResponseBody
    public CustomResponse importExcel(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<Dict> util = new ExcelUtil<Dict>(Dict.class);
        List<Dict> userList = util.importExcel(file.getInputStream());
        Long userId = getUserId();
        final String message = dictService.batchInsert(userList, updateSupport, userId);
        return CustomResponse.ok(message);
    }

    @GetMapping("/type")
    @ResponseBody
    public List<Dict> listType() {
        return dictService.listType();
    }

    ;

    // 类别已经指定增加
    @GetMapping("/add/{type}/{description}")
    @RequiresPermissions("common:dict:add")
    String addD(Model model, @PathVariable("type") String type, @PathVariable("description") String description) {
        model.addAttribute("type", type);
        model.addAttribute("description", description);
        return "common/dict/add";
    }

    @ResponseBody
    @GetMapping("/list/{type}")
    public List<Dict> listByType(@PathVariable("type") String type) {
        // 查询列表数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("type", type);
        List<Dict> dictList = dictService.list(map);
        return dictList;
    }

}
