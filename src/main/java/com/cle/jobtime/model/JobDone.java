package com.cle.jobtime.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

//@FilterDef(name="jobDoneFilter", parameters={
//		@ParamDef( name="oneDay", type="date" )
//})
//@Filters( {
//    @Filter(name="jobDoneFilter", condition=" date = :oneDay ")
//} )
@Embeddable
@Entity
@Table(name = "jobdone")
@JsonIdentityInfo(generator =  ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class JobDone implements Serializable{

	@Id
	@Type(type="pg-uuid")
	@JsonView(JsonViews.All.class)
	private java.util.UUID  id ;
	
	@ManyToOne
	@JoinColumn(name="tasktype_id", nullable=false)
	@JsonView(JsonViews.All.class)
	private TaskType taskType = new TaskType();
	
	@JsonView(JsonViews.All.class)
	private String note ;
	
	@JsonView(JsonViews.All.class)
	private Date date ;
	
	@JsonView(JsonViews.All.class)
	private float timeSpent ;
	
	@ManyToOne
    @JoinColumn(name="project_id", nullable=false)
//	@JsonIdentityReference(alwaysAsId = true)
	@JsonBackReference
    private Project project;
	
	public JobDone() {
	
	}

	public java.util.UUID getId()
	{
		return id;
	}

	public void setId(java.util.UUID id)
	{
		this.id = id;
	}

	public TaskType getTaskType()
	{
		return taskType;
	}

	public void setTaskType(TaskType taskType)
	{
		this.taskType = taskType;
	}

	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public float getTimeSpent()
	{
		return timeSpent;
	}

	public void setTimeSpent(float timeSpent)
	{
		this.timeSpent = timeSpent;
	}

	public Project getProject()
	{
		return project;
	}

	public void setProject(Project project)
	{
		this.project = project;
	}
}
