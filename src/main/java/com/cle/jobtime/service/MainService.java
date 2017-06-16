package com.cle.jobtime.service;

import java.util.Date;
import java.util.List;

import com.cle.jobtime.model.Job;
import com.cle.jobtime.model.JobDone;

public interface MainService {

	Job editJob(Job job);

	String getJobs();

	String getTaskType();

	void addJobDone(JobDone jd);

	List<Object[]> getSumByDaySince(Date firstDayOfWeekDate);
}
