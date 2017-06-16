package com.cle.jobtime.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;


@Embeddable
@Entity
@Table(name = "mission")
public class Mission implements Serializable {

	@Id
	@Type(type="pg-uuid")
	@JsonView(JsonViews.JobMission.class)
	public java.util.UUID  id;
	
	@JsonView(JsonViews.JobMission.class)
	public String name = null;
	
	@Column(name = "isdefault")
	@JsonView(JsonViews.JobMission.class)
	public boolean isDefault = false;
	
	@ManyToOne
    @JoinColumn(name="job_id", nullable=false)
    private Job job;
	
	@OneToMany(mappedBy="mission")
	@JsonView(JsonViews.JobMissionProject.class)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
	public List<Project> projects = new ArrayList<Project>();
	
	
	public Mission() {
	
	}
	
	public java.util.UUID getId()
	{
		return id;
	}

	public void setId(java.util.UUID id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isDefault()
	{
		return isDefault;
	}

	public void setDefault(boolean isDefault)
	{
		this.isDefault = isDefault;
	}

	public Job getJob()
	{
		return job;
	}

	public void setJob(Job job)
	{
		this.job = job;
	}

	public List<Project> getProjects()
	{
		return projects;
	}

	public void setProjects(List<Project> projects)
	{
		this.projects = projects;
	}

}
