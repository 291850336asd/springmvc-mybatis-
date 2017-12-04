package com.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class HelloScheduler {

    public static void main(String[] args) throws SchedulerException {

        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("time is : " + sf.format(date));


        //创建jobdetail.将该实例与hellojob绑定
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity("myjob","group1")
//                .usingJobData("message","hello myjob1")
//                .usingJobData("FloatJobValue",3.14F)
                .build();


        //获取当前时间三秒后的时间
//        date.setTime(date.getTime() + 3000);
//        Date endTIme = new Date();
//        endTIme.setTime(date.getTime() + 6000);


        //创建trigger实例。定义该job执行方式
//        Trigger trigger = TriggerBuilder
//                .newTrigger().
//                withIdentity("myTrigger","tGroup1")
//                .usingJobData("message","group2")
//                .usingJobData("DoubleJobValue",2.0D)
 //               .startNow()
//                .startAt(date)
//                .endAt(endTIme)
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//                        .withIntervalInSeconds(2).
//                                repeatForever())
//                .build();

//        Date startTime = new Date();
//        startTime.setTime(startTime.getTime() + 4000);
        //距离当前时间4秒后执行且仅执行一次
//        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder
//                .newTrigger().
//                        withIdentity("myTrigger","tGroup1")
//                .startAt(startTime)
//                .build();

        //距离当前时间4秒后执行且每隔2秒重复执行一次任务
//        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder
//                .newTrigger().
//                        withIdentity("myTrigger","tGroup1")
//                .startAt(startTime)
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//                        .withIntervalInSeconds(2)
//                        .withRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY))
//                .build();

                CronTrigger trigger = (CronTrigger) TriggerBuilder
                .newTrigger().
                        withIdentity("myTrigger","tGroup1")
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("* * * * * ? *"))//秒分时天月周年
                .build();
        //创建schedule实例
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.scheduleJob(jobDetail, trigger);

        scheduler.start();
    }
}
