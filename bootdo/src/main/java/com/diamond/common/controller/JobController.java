package com.diamond.common.controller;

import com.diamond.common.constant.Constant;
import com.diamond.common.entity.Task;
import com.diamond.common.service.JobService;
import com.diamond.common.utils.PageUtils;
import com.diamond.common.base.Query;
import com.diamond.common.base.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/common/job")
public class JobController extends BaseController{
	@Autowired
	private JobService taskScheduleJobService;

	@GetMapping()
	String taskScheduleJob() {
		return "common/job/job";
	}

	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<Task> taskScheduleJobList = taskScheduleJobService.list(query);
		int total = taskScheduleJobService.count(query);
		PageUtils pageUtils = new PageUtils(taskScheduleJobList, total);
		return pageUtils;
	}

	@GetMapping("/add")
	String add() {
		return "common/job/add";
	}

	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") Long id, Model model) {
		Task job = taskScheduleJobService.get(id);
		model.addAttribute("job", job);
		return "common/job/edit";
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public CustomResponse info(@PathVariable("id") Long id) {
		Task taskScheduleJob = taskScheduleJobService.get(id);
		return CustomResponse.ok().put("taskScheduleJob", taskScheduleJob);
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public CustomResponse save(Task taskScheduleJob) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return CustomResponse.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if (taskScheduleJobService.save(taskScheduleJob) > 0) {
			return CustomResponse.ok();
		}
		return CustomResponse.error();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@PostMapping("/update")
	public CustomResponse update(Task taskScheduleJob) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return CustomResponse.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		taskScheduleJobService.update(taskScheduleJob);
		return CustomResponse.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	public CustomResponse remove(Long id) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return CustomResponse.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if (taskScheduleJobService.remove(id) > 0) {
			return CustomResponse.ok();
		}
		return CustomResponse.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	public CustomResponse remove(@RequestParam("ids[]") Long[] ids) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return CustomResponse.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		taskScheduleJobService.batchRemove(ids);

		return CustomResponse.ok();
	}

	@PostMapping(value = "/changeJobStatus")
	@ResponseBody
	public CustomResponse changeJobStatus(Long id,String cmd ) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return CustomResponse.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		String label = "停止";
		if ("start".equals(cmd)) {
			label = "启动";
		} else {
			label = "停止";
		}
		try {
			taskScheduleJobService.changeStatus(id, cmd);
			return CustomResponse.ok("任务" + label + "成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CustomResponse.ok("任务" + label + "失败");
	}

}
