package com.lims.project.upload.controller;

import com.lims.common.utils.text.LoginUtils;
import com.lims.framework.web.controller.BaseController;
import com.lims.framework.web.domain.AjaxResult;
import com.lims.project.system.domain.SysUser;
import com.lims.project.upload.domain.UploadFilePO;
import com.lims.project.upload.service.UploadFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author KING
 * @version V1.0
 * @Title: UploadFileController
 * @Package com.lims.project.uploadfile.controller
 * @Description: 上传文件(这里用一句话描述这个类的作用)
 * @date 2020/3/18 14:23
 */
@Api("文件上传")
@RestController
@RequestMapping("/upload/file")
public class UploadFileController extends BaseController {

    private Logger log = LoggerFactory.getLogger(UploadFileController.class);

    @Autowired
    private UploadFileService uploadFileService;

    @ApiOperation(value = "文件上传", notes = "报告文件上传", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "上传成功！"), @ApiResponse(code = 500, message = "上传失败！")})
    @PostMapping(value = "/addUploadFile", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    @ResponseBody
    public AjaxResult addUploadFile(
        @ApiParam(value = "上传报告文件", required = true) @RequestParam("file") MultipartFile file) throws IOException {
        try {
            UploadFilePO fileInfo = uploadFileService.uploadFile(file);
            return AjaxResult.success("上传成功", fileInfo);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return AjaxResult.error("上传失败");
    }

    @ApiOperation(value = "保存上传文件", notes = "保存上传文件与订单关系", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "上传成功！"), @ApiResponse(code = 500, message = "上传失败！")})
    @ApiImplicitParams({
        @ApiImplicitParam(name = "experimentOrderId", value = "实验订单id", required = true, dataType = "int"),
        @ApiImplicitParam(name = "fileId", value = "文件id", required = true, allowMultiple = true, dataType = "int")})
    @PostMapping(value = "/save")
    public AjaxResult add(@RequestParam(value = "experimentOrderId", required = true) Integer experimentOrderId,
        @RequestParam(value = "fileId", required = true) Integer[] fileId) {
        SysUser sysUser = LoginUtils.getSessionUserInfo();
        try {
            uploadFileService.saveExperimentOrderFileRelation(sysUser, experimentOrderId, fileId);
            return AjaxResult.success("保存上传文件成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return AjaxResult.error("保存上传文件失败");
    }

}
