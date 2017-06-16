package com.cle.jobtime.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
@Table(name = "project")
public class Project implements Serializable{

	@JsonView(JsonViews.JobMissionProject.class)
	private String name = null;
	
	@JsonView(JsonViews.JobMissionProject.class)
	private boolean isDefault = false;
	
	@OneToMany(mappedBy="project")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
	@JsonView(JsonViews.All.class)
	private List<JobDone> jobdones = new ArrayList<JobDone>();

	@ManyToOne
    @JoinColumn(name="mission_id", nullable=false)
    private Mission mission;
	
	@Id
	@Type(type="pg-uuid")
	@JsonView(JsonViews.JobMissionProject.class)
	public java.util.UUID  id;
	
	
	public Project() {
	
	}
	
	public java.util.UUID  getId()
	{
		return id;
	}

	public void setId(java.util.UUID  id)
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

	public List<JobDone> getJobdones()
	{
		return jobdones;
	}

	public void setJobdones(List<JobDone> jobdones)
	{
		this.jobdones = jobdones;
	}

	public Mission getMission()
	{
		return mission;
	}

	public void setMission(Mission mission)
	{
		this.mission = mission;
	}

}
