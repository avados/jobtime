package com.cle.jobtime.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.cle.jobtime.model.Job;
import com.cle.jobtime.model.JobDone;
import com.cle.jobtime.model.Mission;
import com.cle.jobtime.model.Project;
import com.cle.jobtime.model.TaskType;

public interface MainDao {
	public UUID editJob(Job job);

	public Job getJob(UUID uid);

	public void addTaskType(TaskType tt2);

	public List<Job> getJobs();

	public List<TaskType> getTaskType();

	public Project getProject(UUID id);

	public UUID addJobDone(JobDone jd);

	public List<JobDone> getJobDoneSince(Date firstDayOfWeekDate);
	
	public List<Object[]> getSumByDaySince(Date firstDayOfWeekDate);

	public void setAllJobsDefaultFalse(UUID id);

	public void setAllMissionDefaultFalse(UUID jobid, UUID missionid);

	public UUID editMission(Mission mission);

	public Mission getMission(UUID uid);

	public void setAllProjectDefaultFalse(UUID missionId, UUID projectId);

	public UUID editProject(Project project);

	public UUID editTaskType(TaskType taskType);

	public TaskType getTaskType(UUID uid);
}
