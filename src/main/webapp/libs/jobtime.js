var _host = window.location.protocol + "//" + window.location.host + '/jobtime';

// global message publisher
var msgPublisher = ko.observable();

var AppViewModel = function()
{
	var self = this;

	self.jobViewModel = ko.observable(new JobViewModel());
	self.missionViewModel = ko.observable(new MissionViewModel());
	self.projectViewModel = ko.observable(new ProjectViewModel());
	self.taskTypeViewModel = ko.observable(new TaskTypeViewModel());
	self.jobDoneViewModel = ko.observable(new JobDoneViewModel());

	self.init = function()
	{
		self.getJobs();
		self.getTaskType();
	}

	self.getTaskType = function()
	{
		$.ajax({
			type : "GET",
			dataType : "json",
			contentType : "application/json",
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			url : _host + "/getTaskType",

			success : function(response, status, xhr)
			{

				msgPublisher.notifySubscribers(response, 'taskTypeFromServer');

				console.log(response)
			},
			error : function(response, status, xhr)
			{
				alert("error getting task types")
			},
		});
	}

	self.getJobs = function()
	{
		$.ajax({
			type : "GET",
			dataType : "json",
			contentType : "application/json",
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			url : _host + "/getJobs",

			success : function(response, status, xhr)
			{
				msgPublisher.notifySubscribers(response, 'jobsFromServer');

				console.log(response)
			},
			error : function(response, status, xhr)
			{
				alert("error getting jobs")
			},
		});
	}

}

var appViewModel = new AppViewModel();

ko.applyBindings(appViewModel);

appViewModel.init();