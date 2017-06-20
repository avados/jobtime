var ProjectViewModel = function(selectedMission)
{
	var self = this;
	self._selectedMission = selectedMission;

	var projectModel = function()
	{
		var that = this;
		that.id = ko.observable();
		that.name = ko.observable();
		that.isDefault = ko.observable();
		that.jobdones = ko.observableArray();
		that.mission = ko.observable();
	};

	self.projects = ko.observableArray();
	self.selectedProject = ko.observable();
	self.newProjectName = ko.observable();

	self.addNewProject = function()
	{
		var newProject = new projectModel();
		newProject.name(self.newProjectName());
		newProject.mission(self._selectedMission);

		$.ajax({
			type : "POST",
			dataType : "json",
			contentType : "application/json",

			url : _host + "/editProject",
			data : ko.toJSON(newProject),
			success : function(response, status, xhr)
			{
				self.newProjectName(null);
				msgPublisher.notifySubscribers(response, 'jobsFromServer');
			},
			error : function(response, status, xhr)
			{
				if (typeof response.responseJSON != 'undefined')
				{
					alert(response.responseJSON.message)
				}
			},
		});

	}

	msgPublisher.subscribe(function(newValue)
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
	}, self, 'selectedMissionUpdated');
}