package com.cle.jobtime.service;

import java.util.Date;
import java.util.List;

import com.cle.jobtime.model.Job;
import com.cle.jobtime.model.JobDone;
import com.cle.jobtime.model.Mission;
import com.cle.jobtime.model.Project;
import com.cle.jobtime.model.RestException;
import com.cle.jobtime.model.TaskType;

public interface MainService {

	Job editJob(Job job) throws RestException;

	Mission editMission(Mission mission) throws RestException;

	void addJobDone(JobDone jd) throws RestException;

	List<Object[]> getSumByDaySince(Date firstDayOfWeekDate);

	List<Job> getJobs();

	String getJobsAsJson();

	Project editProject(Project project) throws RestException;

	String getTaskTypeAsJson();

	TaskType editTaskType(TaskType taskType) throws RestException;
}
