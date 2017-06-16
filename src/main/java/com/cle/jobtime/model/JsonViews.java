package com.cle.jobtime.model;

public class JsonViews {
	public static class Job{}
	
	public static class JobMission extends Job {}
	
	public static class JobMissionProject extends JobMission{}
	
	public static class All extends JobMissionProject{}
}
