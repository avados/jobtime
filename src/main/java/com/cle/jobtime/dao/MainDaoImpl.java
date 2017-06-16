package com.cle.jobtime.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cle.jobtime.model.Job;
import com.cle.jobtime.model.JobDone;
import com.cle.jobtime.model.Project;
import com.cle.jobtime.model.TaskType;

@Repository("mainDao")
public class MainDaoImpl implements MainDao {

	private static final Logger logger = LoggerFactory.getLogger(MainDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession()
	{
		return sessionFactory.getCurrentSession();
	}

	@Override
	public UUID editJob(Job job)
	{
		getSession().saveOrUpdate(job);
		return job.getId() ;
	}

	@Override
	public Job getJob(UUID uid)
	{
		
		return (Job) getSession().get(Job.class, uid);
	}

	@Override
	public void addTaskType(TaskType tt2)
	{
		getSession().save(tt2);
		
	}

	@Override
	public List<Job> getJobs()
	{
		
		return getSession().createCriteria(Job.class).list();
	}

	@Override
	public List<TaskType> getTaskType()
	{
		 Criteria c = getSession().createCriteria(TaskType.class);
			
			c.addOrder(Order.asc("type"));
			return  c.list();
//		return getSession().createCriteria(TaskType.class).list();
	}

	@Override
	public Project getProject(UUID id)
	{
		return (Project) getSession().get(Project.class, id);
	}

	@Override
	public UUID addJobDone(JobDone jd)
	{
		getSession().saveOrUpdate(jd);
		return jd.getId() ;
	}

	@Override
	public List<JobDone> getJobDoneSince(Date firstDayOfWeekDate)
	{
		Criteria c = getSession().createCriteria(JobDone.class);
		
		c.add(Restrictions.ge("date", firstDayOfWeekDate));
		c.addOrder(Order.asc("date"));
		return c.list();
	}

	@Override
	public List getSumByDaySince(Date firstDayOfWeekDate)
	{
		Query query=getSession().createQuery("select date, sum(timeSpent) from JobDone where date >= :mydate group by date");
		query.setDate("mydate", firstDayOfWeekDate);
		return query.list();
	}
}
