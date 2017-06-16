var _host = window.location.protocol + "//" + window.location.host + '/jobtime';

// global message publisher
var msgPublisher = ko.observable();

var AppViewModel = function()
{
	var self = this;

	self.jobs = ko.observableArray();
	self.selectedJob = ko.observable();
	self.selectedJob.subscribe(function(newValue)
	{
		if (typeof newValue != 'undefined')
		{
			self.missions(newValue.missions);
			$(newValue.missions).each(function(index, value)
			{
				if (value.isDefault == true)
				{
					self.selectedMission(value);
				}

			});
		}
	})
	self.missions = ko.observableArray();
	self.selectedMission = ko.observable();
	self.selectedMission.subscribe(function(newValue)
	{
		if (typeof newValue != 'undefined')
		{
			self.projects(newValue.projects);
			$(newValue.projects).each(function(index, value)
			{
				if (value.isDefault == true)
				{
					self.selectedProject(value);
				}

			});

		}
	})
	self.projects = ko.observableArray();
	self.selectedProject = ko.observable();
	self.note = ko.observable();
	self.taskType = ko.observableArray();
	self.selectedTaskType = ko.observable();
	var d = new Date();
	self.jddate = ko.observable(d.toISOString().substring(0, 10));
	self.timespent = ko.observable();

	var jobModel = function()
	{
		var that = this;
		that.id = ko.observable();
		that.name = ko.observable();
		that.isDefault = ko.observable();
		that.missions = ko.observableArray();

		// that.imageFile.subscribe(function(newValue)
		// {
		// if (typeof newValue != 'undefined' && newValue != null)
		// {
		// self.saveSlot(that);
		// }
		// })
		//
		// that.fileSize = ko.computed(function()
		// {
		// var file = this.imageFile();
		// return file ? file.size : 0;
		// }, that);

	};

	var missionModel = function()
	{
		var that = this;
		that.id = ko.observable();
		that.name = ko.observable();
		that.isDefault = ko.observable();
		that.projects = ko.observableArray();
	};

	var projectModel = function()
	{
		var that = this;
		that.id = ko.observable();
		that.name = ko.observable();
		that.isDefault = ko.observable();
		that.jobdones = ko.observableArray();
	};

	var jobdoneModel = function()
	{
		var that = this;
		that.id = ko.observable();
		that.taskType = ko.observable();
		that.note = ko.observable();
		that.timeSpent = ko.observableArray();
	};

	var taskTypeModel = function()
	{
		var that = this;
		that.id = ko.observable();
		that.type = ko.observable();
	};

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

				if (typeof response != 'undefined')
				{
					self.taskType(response)

				}
				console.log(response)
			},
			error : function(response, status, xhr)
			{
				console.log("error getting jobs")
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

				if (typeof response != 'undefined')
				{
					self.jobs(response)
					$(response).each(function(index, value)
					{
						if (value.isDefault == true)
						{
							self.selectedJob(value);
						}

					});
				}
				console.log(response)
			},
			error : function(response, status, xhr)
			{
				console.log("error getting jobs")
			},
		});
	}

	self.sendJobTime = function()
	{
		var jd = {
			"timeSpent" : self.timespent(),
			"date" : self.jddate(),
			"note" : self.note(),
			"taskType" : self.selectedTaskType(),
			"project" : self.selectedProject()
		}

		$.ajax({
			type : "POST",
			dataType : "json",
			contentType : "application/json",

			url : _host + "/addJobDone",
			data : JSON.stringify(jd),
			success : function(response, status, xhr)
			{
				console.log("ok");
			},
			error : function(response, status, xhr)
			{
				console.log("ko");
			},
		});

	}
}

var appViewModel = new AppViewModel();

ko.applyBindings(appViewModel);

appViewModel.getJobs();
appViewModel.getTaskType();