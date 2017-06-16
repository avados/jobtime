var ProjectViewModel = function()
{
	var self = this;

	var projectModel = function()
	{
		var that = this;
		that.id = ko.observable();
		that.name = ko.observable();
		that.isDefault = ko.observable();
		that.jobdones = ko.observableArray();
	};

	self.projects = ko.observableArray();
	self.selectedProject = ko.observable();

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