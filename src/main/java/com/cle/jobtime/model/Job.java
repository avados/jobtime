package com.cle.jobtime.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@Embeddable
@Entity
@Table(name = "job")
public class Job implements Serializable{

	@Id
	@Type(type="pg-uuid")
	@JsonView(JsonViews.Job.class)
	private java.util.UUID  id;
	
	@JsonView(JsonViews.Job.class)
	private String name = null;
	
	@JsonView(JsonViews.Job.class)
	@Column(name = "isdefault")
	private boolean isDefault = false;
	
	@JsonView(JsonViews.JobMission.class)
	@OneToMany(mappedBy="job")  
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
	private List<Mission> missions = new ArrayList<Mission>();
	
	public Job() {
	
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

	public List<Mission> getMissions()
	{
		return missions;
	}

	public void setMissions(List<Mission> missions)
	{
		this.missions = missions;
	}
	
}
