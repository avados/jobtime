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
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Embeddable
@Entity
@Table(name = "project")
@JsonIdentityInfo(generator =  ObjectIdGenerators.PropertyGenerator.class, property = "id")
@FilterDef(name="jobDoneFilter", parameters={
		@ParamDef( name="oneDay", type="date" )
})
//@Filters( {
//    @Filter(name="jobDoneFilter", condition=" date = :oneDay ")
//} )
public class Project implements Serializable{

	@JsonView(JsonViews.JobMissionProject.class)
	private String name = null;
	
	@JsonView(JsonViews.JobMissionProject.class)
	private boolean isDefault = false;
	
	@OneToMany(mappedBy="project")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
	@JsonView(JsonViews.All.class)
	@JsonManagedReference
	@Filter(name="jobDoneFilter", condition=" date = :oneDay ")
	private List<JobDone> jobdones = new ArrayList<JobDone>();

	@ManyToOne
    @JoinColumn(name="mission_id", nullable=false)
	@JsonBackReference
    private Mission mission;
	
	@Id
	@Type(type="pg-uuid")
	@JsonView(JsonViews.JobMissionProject.class)
	private java.util.UUID  id;
	
	
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
