var MissionViewModel = function()
{
	var self = this;

	var missionModel = function()
	{
		var that = this;
		that.id = ko.observable();
		that.name = ko.observable();
		that.isDefault = ko.observable();
		that.projects = ko.observableArray();
	};

	self.missions = ko.observableArray();
	self.selectedMission = ko.observable();
	self.selectedMission.subscribe(function(newValue)
	{
		msgPublisher.notifySubscribers(newValue, 'selectedMissionUpdated');

	})

	msgPublisher.subscribe(function(job)
	{
		if (typeof job != 'undefined')
		{
			self.missions(job.missions);
			$(job.missions).each(function(index, value)
			{
				if (value.isDefault == true)
				{
					self.selectedMission(value);
				}

			});
		}
	}, self, 'selectedJobUpdated');
}