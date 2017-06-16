package com.cle.jobtime.model;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Embeddable
@Entity
@Table(name = "tasktype")
public class TaskType {

	@Id
	@Type(type="pg-uuid")
	private java.util.UUID  id;
	
	private String type;

	public java.util.UUID getId()
	{
		return id;
	}

	public void setId(java.util.UUID id)
	{
		this.id = id;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}
	
	
}
