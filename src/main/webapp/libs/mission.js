var MissionViewModel = function(selectedJob)
{
	var self = this;

	var _selectedJob = selectedJob;
	var missionModel = function()
	{
		var that = this;
		that.id = ko.observable();
		that.name = ko.observable();
		that.isDefault = ko.observable();
		that.projects = ko.observableArray();
		that.job = ko.observable();
	};

	self.missions = ko.observableArray();
	self.newMissionName = ko.observable();
	self.selectedMission = ko.observable();
	self.selectedMission.subscribe(function(newValue)
	{
		msgPublisher.notifySubscribers(newValue, 'selectedMissionUpdated');

	})

	self.setDefault = function(clicked)
	{
		clicked.isDefault = true;
		self.addNewMission(clicked);
	}

	self.addNewMission = function(updated, kovariable)
	{
		var newMission;
		if (typeof kovariable == 'undefined')
		{
			// existing project
			newMission = updated;
		}
		else
		{
			newMission = new missionModel();
			newMission.name(self.newMissionName());
			newMission.job(selectedJob);
		}

		$.ajax({
			type : "POST",
			dataType : "json",
			contentType : "application/json",

			url : _host + "/editMission",
			data : ko.toJSON(newMission),
			success : function(response, status, xhr)
			{
				self.newMissionName(null);
				msgPublisher.notifySubscribers(response, 'jobsFromServer');
			},
			error : function(response, status, xhr)
			{
				alert(response.responseJSON.message)
			},
		});

	}

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