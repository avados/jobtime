package com.cle.jobtime.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cle.jobtime.dao.MainDao;
import com.cle.jobtime.dao.MainDaoImpl;
import com.cle.jobtime.model.Job;
import com.cle.jobtime.model.JobDone;
import com.cle.jobtime.model.Mission;
import com.cle.jobtime.model.Project;
import com.cle.jobtime.model.TaskType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("mainService")
@Transactional
public class MainServiceImpl implements MainService {
	// TODO add verifications and business logic
	@Autowired(required = true)
	private MainDao dao;

	
	private static final Logger logger = LoggerFactory.getLogger(MainServiceImpl.class);

	@Override
	public String getJobs()
	{
		ObjectMapper om = new ObjectMapper();
		om.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
		String s = "{}";
		try
		{
			s = om.writerWithView(com.cle.jobtime.model.JsonViews.JobMissionProject.class).writeValueAsString(dao.getJobs());
		}
		catch (JsonProcessingException e)
		{
			logger.debug("merde2",e);
		}
		return s;
	}

	@Override
	public Job editJob(Job job)
	{
		UUID uid ;
		if(job.getId() == null)
		{
			uid = UUID.randomUUID();
			job.setId(uid);
			
		}
		else
		{
			uid =  dao.editJob(job);
		}
		return dao.getJob(uid);
	}

	public void testCreateJob()
	{
		UUID uid ;
		Job _job = new Job();
//		_job.setId(UUID.randomUUID());
		_job.setId(UUID.fromString("5d41afd9-6ec2-4496-afcf-202bd6e59265"));
		_job.setName("test"+  RandomStringUtils.random(56));
		List<Mission> missions = new ArrayList<Mission>();
		Mission mission = new Mission();
//		mission.setId(UUID.randomUUID());
		mission.setId(UUID.fromString("af5e1e33-e772-4abc-879e-c6245a7a16d9"));
		mission.setDefault(false);
		mission.setName("missionName");
		mission.setJob(_job);
		
		Project project = new Project();
		project.setDefault(true);
		project.setId(UUID.randomUUID());
		project.setMission(mission);
		project.setName("testproject");
		
		List<JobDone> jobdones = new ArrayList<JobDone>();
		JobDone job1 = new JobDone();
		job1.setDate(new Date());
		job1.setId(UUID.randomUUID());
		job1.setNote("note");
		TaskType tt = new TaskType();
		tt.setId(UUID.randomUUID());
		tt.setType("type");
		dao.addTaskType(tt);
		job1.setTaskType(tt);
		job1.setProject(project);
		job1.setTimeSpent(50);
		jobdones.add(job1);
		
		JobDone job2 = new JobDone();
		job2.setDate(new Date());
		job2.setId(UUID.randomUUID());
		job2.setNote("note2");
		
		
		TaskType tt2 = new TaskType();
		tt2.setId(UUID.randomUUID());
		tt2.setType("type2");
		dao.addTaskType(tt2);
		
		job2.setTaskType(tt2);
		job2.setTimeSpent(5);
		job2.setProject(project);
		jobdones.add(job2);
		
		project.setJobdones(jobdones);
		
		List<Project> projects = new ArrayList<Project>();
		projects.add(project);
		
		mission.setProjects(projects);
		missions.add(mission);
//		seems that hibernate does not know how to create if the parent is already created
//		voir cascade
		//https://stackoverflow.com/questions/9650453/hibernate-onetomany-save-children-by-cascade
		_job.setMissions(missions);
		uid = dao.editJob(_job);
	}

	@Override
	public String getTaskType()
	{
		ObjectMapper om = new ObjectMapper();
		//om.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
		String s = "{}";
		try
		{
			s = om.writeValueAsString(dao.getTaskType());
		}
		catch (JsonProcessingException e)
		{
			logger.debug("merde2",e);
		}
		return s;	}

	@Override
	public void addJobDone(JobDone jd)
	{
//		Project project = getProject(jd.getProject().getId());
//		project.getJobdones().add(jd);
		if(jd.getId() == null)
		{
			jd.setId(UUID.randomUUID());
		}
		dao.addJobDone(jd);
	}

	private Project getProject(UUID id)
	{
		
		return dao.getProject(id);
	}

	@Override
	public List<Object[]> getSumByDaySince(Date firstDayOfWeekDate)
	{
		return dao.getSumByDaySince(firstDayOfWeekDate);
	}

	
}
