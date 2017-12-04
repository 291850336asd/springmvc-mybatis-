package org.sekill.quratz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class ScheduleJob extends QuartzJobBean{

    private AnotherBean anotherBean;

    public void setAnotherBean(AnotherBean anotherBean){
        this.anotherBean = anotherBean;
    }
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("CronTrigger ScheduleJob todo something");
        this.anotherBean.printAnotherMessage();
    }
}
