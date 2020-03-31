package com.lims.project.experiment.controller;

import com.lims.common.utils.poi.ExcelUtil;
import com.lims.framework.aspectj.lang.annotation.Log;
import com.lims.framework.aspectj.lang.enums.BusinessType;
import com.lims.framework.web.controller.BaseController;
import com.lims.framework.web.domain.AjaxResult;
import com.lims.project.experiment.domain.ExperimentResult;
import com.lims.project.experiment.domain.json.ExperimentResultJson;
import com.lims.project.experiment.service.IExperimentResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实验单实验结果Controller
 *
 * @author yanglin
 * @date 2020-03-18
 */
@Api("试验单")
@RestController
@RequestMapping("/experiment/result")
public class ExperimentResultController extends BaseController {

    @Autowired
    private IExperimentResultService experimentResultService;

    /**
     * 查询实验单实验结果列表
     */
    @ApiOperation("查询实验单实验结果列表")
    @GetMapping("/list")
    @ApiImplicitParam(name = "experimentCode", value = "实验单号", dataType = "string", paramType = "query", required = true)
    public AjaxResult list(String experimentCode) {
        try {
            ExperimentResultJson experimentResultJson = experimentResultService
                .selectExperimentResultList(experimentCode);
            return AjaxResult.success("数据查询成功", experimentResultJson);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 导出实验单实验结果列表
     */
    @PreAuthorize("@ss.hasPermi('experiment:result:export')")
    @Log(title = "实验单实验结果", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ExperimentResult experimentResult) {
        List<ExperimentResult> list = null;// experimentResultService.selectExperimentResultList(experimentResult);
        ExcelUtil<ExperimentResult> util = new ExcelUtil<ExperimentResult>(ExperimentResult.class);
        return util.exportExcel(list, "result");
    }

    /**
     * 获取实验单实验结果详细信息
     */
    @PreAuthorize("@ss.hasPermi('experiment:result:query')")
    @GetMapping(value = "/{resultId}")
    public AjaxResult getInfo(@PathVariable("resultId") Long resultId) {
        return AjaxResult.success(experimentResultService.selectExperimentResultById(resultId));
    }

    /**
     * 新增实验单实验结果
     */
    @PreAuthorize("@ss.hasPermi('experiment:result:add')")
    @Log(title = "实验单实验结果", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExperimentResult experimentResult) {
        return toAjax(experimentResultService.insertExperimentResult(experimentResult));
    }

    /**
     * 修改实验单实验结果
     */
    @PreAuthorize("@ss.hasPermi('experiment:result:edit')")
    @Log(title = "实验单实验结果", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExperimentResult experimentResult) {
        return toAjax(experimentResultService.updateExperimentResult(experimentResult));
    }

    /**
     * 删除实验单实验结果
     */
    @PreAuthorize("@ss.hasPermi('experiment:result:remove')")
    @Log(title = "实验单实验结果", businessType = BusinessType.DELETE)
    @DeleteMapping("/{resultIds}")
    public AjaxResult remove(@PathVariable Long[] resultIds) {
        return toAjax(experimentResultService.deleteExperimentResultByIds(resultIds));
    }
}
