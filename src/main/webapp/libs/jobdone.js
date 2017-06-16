var JobDoneViewModel = function()
{
	var self = this;

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
		var _d = new Date();
		that.date = ko.observable(_d.toISOString().substring(0, 10));
		that.project = ko.observable();
	};

	self.currentJobDone = new jobdoneModel();

	self.sendJobTime = function()
	{
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
			},
		});

	}
}