package com.lims.project.scheduled;

import com.lims.common.utils.DateUtils;
import com.lims.project.experiment.service.IExperimentOrderService;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import com.lims.project.monitor.domain.SysOperLog;
import com.lims.project.monitor.service.ISysOperLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author KING
 * @version V1.0
 * @Title: ScheduledApplication
 * @Package com.lims.project.scheduled
 * @Description: 定时任务(这里用一句话描述这个类的作用)
 * @date 2020/3/17 22:44
 */
@Component
public class ScheduledApplication {

    @Autowired
    private IExperimentOrderService experimentOrderService;
    @Autowired
    private ISysOperLogService   iSysOperLogService;

    private Logger log = LoggerFactory.getLogger(ScheduledApplication.class);

    /**
     * 每五分钟执行一次 查询订单信息
     */
    @Scheduled(cron = "0 */5 * * * ?")
    public void scheduledTask() {
       LocalDateTime nowDateTime = LocalDateTime.now();
      /*   String fromTime = nowDateTime.minusMinutes(5).toString();
        String toTime = nowDateTime.toString();*/

        String toTime= DateUtils.getTimeYYMMDDHHMM();
        Date oldTime= DateUtils.dateRoll(new Date(), Calendar.MINUTE, -5);
        String fromTime= DateUtils.parseDateToStr("yyyy-MM-dd HH:mm",oldTime);
        int rows=0;
        try {
            log.info("任务执行时间：{},查询时间区间：{}-{}", nowDateTime, fromTime, toTime);
         //失败的话连续循环5次
         for (int i=0;i<5;i++){
              rows= experimentOrderService.insertExperimentOrder(fromTime, toTime);
              if(rows<=0){
                  //如果5次都失败将停掉定时任务 同时记录失败
                  if(i==4){
                      SysOperLog sysOperLog=new SysOperLog();
                      sysOperLog.setTitle("拉取lims数据的定时任务");
                      sysOperLog.setBusinessType(1);
                      sysOperLog.setMethod("com.lims.project.scheduled.ScheduledApplication.scheduledTask()");
                      sysOperLog.setRequestMethod("定时任务调用");
                      sysOperLog.setOperatorType(0);
                      sysOperLog.setOperName("");
                      sysOperLog.setDeptName("");
                      sysOperLog.setOperUrl("com.lims.project.scheduled.ScheduledApplication.scheduledTask()");
                      sysOperLog.setOperIp("");
                      sysOperLog.setOperLocation("");
                      sysOperLog.setOperParam("fromTime:"+fromTime+" ;toTime: "+toTime);
                      sysOperLog.setJsonResult("定时任务同步失败");
                      sysOperLog.setStatus(1);
                      sysOperLog.setErrorMsg("定时任务同步失败");
                      iSysOperLogService.insertOperlog(sysOperLog);
                  }
               continue;
              }else{
                  break;
              }
         }
            log.info("任务执行结束时间：{},查询时间区间：{}-{}", DateUtils.getNowDate(), fromTime, toTime);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
