var JobViewModel = function()
{
	var self = this;

	var jobModel = function()
	{
		var that = this;
		that.id = ko.observable();
		that.name = ko.observable();
		that.isDefault = ko.observable();
		that.missions = ko.observableArray();

	};

	self.newJobName = ko.observable();

	self.jobs = ko.observableArray();
	self.selectedJob = ko.observable();
	self.selectedJob.subscribe(function(job)
	{
		msgPublisher.notifySubscribers(job, 'selectedJobUpdated');

	})

	self.addNewJob = function()
	{
		var newJob = new jobModel();
		newJob.name(self.newJobName());

		$.ajax({
			type : "POST",
			dataType : "json",
			contentType : "application/json",

			url : _host + "/editJob",
			data : ko.toJSON(newJob),
			success : function(response, status, xhr)
			{
				self.newJobName(null);
				msgPublisher.notifySubscribers(response, 'jobsFromServer');
			},
			error : function(response, status, xhr)
			{
				if (typeof response.responseJSON != 'undefined')
				{
					alert(response.responseJSON.message)
				}
				console.log("error");
			},
		});

	}

	msgPublisher.subscribe(function(jobs)
	{
		if (typeof jobs != 'undefined')
		{
			self.jobs(jobs)
			$(jobs).each(function(index, value)
			{
				if (value.isDefault == true)
				{
					self.selectedJob(value);
				}

			});
		}
	}, self, 'jobsFromServer');
}