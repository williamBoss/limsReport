package com.lims.project.sendAPP.controller;

import com.lims.framework.config.RuoYiConfig;
import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author KING
 * @version V1.0
 * @Title: ReceiveFileController
 * @Package com.lims.project.sendAPP.controller
 * @Description: 接收推送的文件(这里用一句话描述这个类的作用)
 * @date 2020/3/25 22:46
 */
@RestController
@RequestMapping("receiveFile")
public class ReceiveFileController {

    @RequestMapping("receiveReportFile")
    public String fileImgSave(@RequestParam("file") MultipartFile[] files, HttpServletRequest request) {
        //保存文件的路径
        String realPath = RuoYiConfig.getUploadPath();
        File path = new File(realPath);
        if (!path.exists()) {
            path.mkdirs();
        }
        //判断file数组不能为空并且长度大于0
        if (files != null && files.length > 0) {
            //循环获取file数组中得文件
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                //保存文件
                if (!file.isEmpty()) {
                    try {
                        //转存文件 file.getOriginalFilename();文件原名称包括后缀名
                        file.transferTo(new File(realPath + File.separator + file.getOriginalFilename()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return "ok";
    }

}
