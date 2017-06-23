var _host = window.location.protocol + "//" + window.location.host + '/jobtime';

// global message publisher
var msgPublisher = ko.observable();

var AppViewModel = function()
{
	var self = this;
	var defaultAction = {
		id : "addJobDone",
		name : "Ajouter un temps"
	};
	self.actions = ko.observableArray([ {
		id : "addJob",
		name : "Ajouter un job"
	}, {
		id : "addMission",
		name : "Ajouter une mission"
	}, {
		id : "addTaskType",
		name : "Ajouter un type de tache"
	}, {
		id : "addProject",
		name : "Ajouter un projet"
	}, defaultAction ]);
	self.selectedAction = ko.observable(defaultAction);
	self.selectedAction.subscribe(function(action)
	{
		// ici il faut relancer les categories par defauts, etant donné que si je choisis different objetselected et que
		// je change daction je lai danms los

	})

	self.jobViewModel = ko.observable(new JobViewModel());
	self.missionViewModel = ko.observable(new MissionViewModel(self.jobViewModel().selectedJob));
	self.projectViewModel = ko.observable(new ProjectViewModel(self.missionViewModel().selectedMission));
	self.taskTypeViewModel = ko.observable(new TaskTypeViewModel());
	self.jobDoneViewModel = ko.observable(new JobDoneViewModel(self.projectViewModel().selectedProject, self
			.taskTypeViewModel().selectedTaskType));

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
			},
			error : function(response, status, xhr)
			{
				console.log(response);
				alert("error getting jobs")
			},
		});
	}

}

var appViewModel = new AppViewModel();

ko.applyBindings(appViewModel);

appViewModel.init();