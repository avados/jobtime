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

	self.jobs = ko.observableArray();
	self.selectedJob = ko.observable();
	self.selectedJob.subscribe(function(job)
	{
		msgPublisher.notifySubscribers(job, 'selectedJobUpdated');

	})

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