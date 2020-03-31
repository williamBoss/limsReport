package com.lims.project.upload.service.impl;

import com.lims.common.utils.file.FileUploadUtils;
import com.lims.framework.config.RuoYiConfig;
import com.lims.project.system.domain.SysUser;
import com.lims.project.upload.domain.ExperimentOrderFilePO;
import com.lims.project.upload.domain.UploadFilePO;
import com.lims.project.upload.mapper.ExperimentOrderFileMapper;
import com.lims.project.upload.mapper.UploadFileMapper;
import com.lims.project.upload.service.UploadFileService;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author KING
 * @version V1.0
 * @Title: UploadFileServiceImpl
 * @Package com.lims.project.uploadfile.service.impl
 * @Description: (这里用一句话描述这个类的作用)
 * @date 2020/3/18 14:44
 */
@Service
public class UploadFileServiceImpl implements UploadFileService {

    @Autowired
    private UploadFileMapper uploadFileMapper;

    @Autowired
    private ExperimentOrderFileMapper experimentOrderFileMapper;

    @Override
    public UploadFilePO uploadFile(MultipartFile file) throws IOException {
        // 上传文件路径
        String uploadPath = RuoYiConfig.getUploadPath() + "/report";
        // 原文件名
        String originFileName = file.getOriginalFilename();
        // 上传并返回新文件保存路径
        String saveFilePath = FileUploadUtils.upload(uploadPath, file);
        // 获取新文件名称
        String newFileName = saveFilePath.substring(saveFilePath.lastIndexOf("/") + 1);
        UploadFilePO uploadFile = new UploadFilePO();
        uploadFile.setOriginFileName(originFileName);
        uploadFile.setNewFileName(newFileName);
        uploadFile.setSavePath(saveFilePath);
        uploadFile.setDeleteFlag(0);
        uploadFileMapper.insert(uploadFile);
        return uploadFile;
    }

    @Override
    public void saveExperimentOrderFileRelation(SysUser sysUser, Integer experimentOrderId, Integer... fileIdArr)
        throws Exception {
        experimentOrderFileMapper.updateDeleteFlagByExperimentOrderId(experimentOrderId);
        ExperimentOrderFilePO experimentOrderFile = new ExperimentOrderFilePO();
        experimentOrderFile.setExperimentOrderId(experimentOrderId);
        experimentOrderFile.setDeleteFlag(0);
        experimentOrderFile.setCreateUser(sysUser.getUserId().intValue());
        experimentOrderFile.setUpdateUser(sysUser.getUserId().intValue());
        for (Integer fileId : fileIdArr) {
            experimentOrderFile.setFileId(fileId);
            experimentOrderFileMapper.insertSelective(experimentOrderFile);
        }
    }

    @Override
    public List<UploadFilePO> getFileList(Long experimentOrderId) {
        return uploadFileMapper.selectByPrimarOrderId(experimentOrderId);
    }

}
