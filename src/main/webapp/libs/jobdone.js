var JobDoneViewModel = function(selectedProject, selectedTaskType)
{
	var self = this;
	self._selectedProject = selectedProject;
	self._selectedTaskType = selectedTaskType;
	// for jobDone
	// self.note = ko.observable();

	// self.jddate = ko.observable(d.toISOString().substring(0, 10));
	// self.timespent = ko.observable();

	var jobdoneModel = function()
	{
		var that = this;
		that.id = ko.observable();
		that.taskType = ko.observable();
		that.note = ko.observable();
		that.timeSpent = ko.observable();

		that.date = ko.observable(new Date());
		// that.date = ko.observable(_d.toISOString().substring(0, 10));
		that.project = ko.observable();
	};

	self.currentJobDone = new jobdoneModel();

	self.sendJobTime = function()
	{
		self.currentJobDone.project(self._selectedProject);
		self.currentJobDone.taskType(self._selectedTaskType);

		$.ajax({
			type : "POST",
			dataType : "json",
			contentType : "application/json",

			url : _host + "/addJobDone",
			data : ko.toJSON(self.currentJobDone),
			success : function(response, status, xhr)
			{
				console.log("ok");
			},
			error : function(response, status, xhr)
			{
				console.log("ko");
				alert(response.responseJSON.message);
			},
		});

	}
}