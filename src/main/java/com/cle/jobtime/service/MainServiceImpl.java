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
import com.cle.jobtime.model.RestException;
import com.cle.jobtime.model.TaskType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("mainService")
@Transactional
public class MainServiceImpl implements MainService {
	
	@Autowired(required = true)
	private MainDao dao;

	
	private static final Logger logger = LoggerFactory.getLogger(MainServiceImpl.class);

	@Override
	public String getJobsAsJson()
	{
		ObjectMapper om = new ObjectMapper();
		om.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
		String s = "{}";
		try
		{
			s = om.writerWithView(com.cle.jobtime.model.JsonViews.JobMissionProject.class).writeValueAsString(getJobs());
		}
		catch (JsonProcessingException e)
		{
			logger.debug("getJobsAsJson",e);
		}
		return s;
	}
	
	@Override
	public List<Job> getJobs()
	{
		try
		{
			return dao.getJobs();
		}
		catch (Exception e)
		{
			logger.debug("getJobs",e);
		}
		return null;
	}

	@Override
	public Job editJob(Job job) throws RestException
	{
		
		UUID uid ;
		if(job.getId() == null)
		{
			if(job.getName() == null || job.getName().trim().length() == 0)
			{
				throw new RestException("ko", "Nom incorrect");
			}
			for(Job _job : getJobs())
			{
				if(_job.getName().equals(job.getName()))
				{
					throw new RestException("ko", "Nom déjà utilisé");
				}
			}
			uid = UUID.randomUUID();
			job.setId(uid);
			job.setDefault(true);

			dao.setAllJobsDefaultFalse(job.getId());
			uid =  dao.editJob(job);
		}
		else
		{
			Job oldJob = dao.getJob(job.getId());
			if(job.isDefault()  && !oldJob.isDefault())
			{
				dao.setAllJobsDefaultFalse(job.getId());
			}
			oldJob.setDefault(job.isDefault());
			oldJob.setName(job.getName());

			uid =  dao.editJob(oldJob);
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
	public String getJobDoneOnAsJson(Date date)
	{
		ObjectMapper om = new ObjectMapper();
		//om.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
		String s = "{}";
		try
		{
			//ici c'est foireux, il faut utiliser une vue pour ne pas recuperer les projet dans les jobdone
			//s = om.writerWithView(com.cle.jobtime.model.JsonViews.JobMissionProject.class).writeValueAsString(getJobs());
			s = om.writeValueAsString(dao.getProjectWithJobDoneOn(date));
		}
		catch (JsonProcessingException e)
		{
			logger.debug("getTaskType",e);
		}
		return s;	
	}

	
	@Override
	public String getTaskTypeAsJson()
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
			logger.debug("getTaskType",e);
		}
		return s;	
	}

	@Override
	public void addJobDone(JobDone jd) throws RestException
	{

		if(jd.getDate() == null || jd.getProject() == null || jd.getTaskType() == null || jd.getTimeSpent() == 0)
		{
			throw new RestException("ko", "Null value");
		}
		if(jd.getTaskType().getId() == null)
		{
			throw new RestException("ko", "incorrect Task type");
		}
		if(jd.getTimeSpent() % 0.25 != 0 || jd.getTimeSpent() > 24)
		{
			throw new RestException("ko", "Temps incorrect");
		}
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

	@Override
	public Mission editMission(Mission mission) throws RestException
	{
		UUID uid ;
		if(mission.getId() == null)
		{
			if(mission.getName() == null || mission.getName().trim().length() == 0)
			{
				throw new RestException("ko", "Nom incorrect");
			}
			if(mission.getJob() == null)
			{
				throw new RestException("ko", "Job requis");
			}
			Job _job = dao.getJob(mission.getJob().getId());
			if(_job == null)
			{
				throw new RestException("ko", "Job non trouvé en BD");
			}
			for(Mission _mission : _job.getMissions())
			{
				if(_mission.getName().equals(mission.getName()))
				{
					throw new RestException("ko", "Nom déjà utilisé");
				}
			}
			uid = UUID.randomUUID();
			mission.setId(uid);
			mission.setDefault(true);
			
			dao.setAllMissionDefaultFalse(mission.getJob().getId(), mission.getId());
			uid =  dao.editMission(mission);
		}
		else
		{
			Mission oldMission = dao.getMission(mission.getId());
			if(mission.isDefault()  && !oldMission.isDefault())
			{
				dao.setAllMissionDefaultFalse(oldMission.getJob().getId(), mission.getId());
			}
			oldMission.setDefault(mission.isDefault());
			oldMission.setName(mission.getName());
			uid =  dao.editMission(oldMission);
		}
		//TODO quand edition possible, alors verifier nom non null, etc...
		
//		
		return dao.getMission(uid);
	}

	@Override
	public Project editProject(Project project) throws RestException
	{
		UUID uid ;
		if(project.getId() == null)
		{
			if(project.getName() == null || project.getName().trim().length() == 0)
			{
				throw new RestException("ko", "Nom incorrect");
			}
			if(project.getMission() == null)
			{
				throw new RestException("ko", "Mission requise");
			}
			
			Mission _mission = dao.getMission(project.getMission().getId());
			if(_mission  == null)
			{
				throw new RestException("ko", "Mission non trouvée en BD");
			}
			
			for(Project _project  : _mission.getProjects())
			{
				if(_project .getName().equals(project.getName()))
				{
					throw new RestException("ko", "Nom déjà utilisé");
				}
			}
			uid = UUID.randomUUID();
			project.setId(uid);
			project.setDefault(true);
			dao.setAllProjectDefaultFalse(project.getMission().getId(), project.getId());
			uid =  dao.editProject(project);
		}
		else
		{
			Project oldProject = dao.getProject(project.getId());
			if(project.isDefault()  && !oldProject.isDefault())
			{
				dao.setAllProjectDefaultFalse(oldProject.getMission().getId(), project.getId());
			}
			oldProject.setDefault(project.isDefault());
			oldProject.setName(project.getName());
			uid =  dao.editProject(oldProject);
		}
		//TODO quand edition possible, alors verifier nom non null, etc...
		
		
	
		return dao.getProject(uid);
	}

	@Override
	public TaskType editTaskType(TaskType taskType) throws RestException
	{
		UUID uid ;
		if(taskType.getId() == null)
		{
			if(taskType.getType() == null || taskType.getType().trim().equals("") )
			{
				throw new RestException("ko", "Nom incorrect");
			}
			uid = UUID.randomUUID();
			taskType.setId(uid);
		}
		uid = dao.editTaskType(taskType);
		return dao.getTaskType(uid);
	}

	
}
