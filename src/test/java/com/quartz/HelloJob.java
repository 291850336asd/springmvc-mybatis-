package com.quartz;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class HelloJob implements Job{

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Hello Job time is : " + sf.format(date));
        System.out.println("Hello");

//        Trigger currentTrigger = jobExecutionContext.getTrigger();
//        System.out.println("Hello Job trigger startTime : " + sf.format(currentTrigger.getStartTime()));
//        System.out.println("Hello Job trigger endTime : " + sf.format(currentTrigger.getEndTime()));
//        JobKey jobKey = currentTrigger.getJobKey();
//        System.out.println("Hello Job trigger jobkey : " + jobKey.getName() + " : " + jobKey.getGroup());



//        JobKey key = jobExecutionContext.getJobDetail().getKey();
//        System.out.println("JobDetail name and group info : " + key.getName() + " : " + key.getGroup());
//        TriggerKey triggerKey = jobExecutionContext.getTrigger().getKey();
//        System.out.println("TriggerKey name and group info : " + triggerKey.getName() + " : " + triggerKey.getGroup());

//        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
//        JobDataMap triggerDataMap = jobExecutionContext.getTrigger().getJobDataMap();
//        String dMsg = jobDataMap.getString("message");
//        Float dFloatMsg = jobDataMap.getFloat("FloatJobValue");
//        System.out.println("jobdetail msg : " + dMsg + " : " + dFloatMsg);
//        String tMsg = triggerDataMap.getString("message");
//        Double tFloatMsg = triggerDataMap.getDouble("DoubleJobValue");
//        System.out.println("trigger msg : " + tMsg + " : " + tFloatMsg);
//        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
//        Set<String> setKey = jobDataMap.keySet();
//        for (String item : setKey){
//            System.out.println(item + " : " + jobDataMap.get(item).toString());
//        }
    }
}
