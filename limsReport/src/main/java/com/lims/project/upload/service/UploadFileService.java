package com.lims.project.upload.service;

import com.lims.project.system.domain.SysUser;
import com.lims.project.upload.domain.UploadFilePO;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author KING
 * @version V1.0
 * @Title: UploadFileService
 * @Package com.lims.project.uploadfile.service
 * @Description: 上传文件(这里用一句话描述这个类的作用)
 * @date 2020/3/18 14:38
 */
public interface UploadFileService {

    /**
     * 上传文件
     *
     * @param file
     * @param user
     * @return
     */
    public UploadFilePO uploadFile(MultipartFile file) throws IOException;

    /**
     * 保存实验订单和上传文件的关系
     *
     * @param sysUser
     * @param experimentOrderId
     * @param fileIdArr
     * @throws Exception
     */
    public void saveExperimentOrderFileRelation(SysUser sysUser, Integer experimentOrderId, Integer... fileIdArr)
        throws Exception;

    public List<UploadFilePO> getFileList(Long experimentOrderId);

}
