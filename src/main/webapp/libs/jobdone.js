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
	self.currentDisplayedDate = ko.observable(moment(self.currentJobDone.date()).format('DD MMM YYYY'));

	self.currentJobDone.date.subscribe(function(newValue)
	{
		if (!isNaN(newValue.getDate()))
		{
			self.currentDisplayedDate(moment(self.currentJobDone.date()).format('DD MMM YYYY'));
			self.getDateJobTime();
		}

	})

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
				self.currentJobDone.note(undefined);
				self.currentJobDone.timeSpent(undefined);
				self.getDateJobTime();
				console.log("ok");
			},
			error : function(response, status, xhr)
			{
				console.log("ko");
				alert(response.responseJSON.message);
			},
		});

	}

	self.dateJobTime = ko.observableArray([]);
	self.currentDisplayedTotalTime = ko.computed(function()
	{
		var _temp = 0;
		for (pi = 0; pi < self.dateJobTime().length; pi++)
		{
			for (jdi = 0; jdi < self.dateJobTime()[pi].jobdones.length; jdi++)
			{
				_temp += self.dateJobTime()[pi].jobdones[jdi].timeSpent;
			}

		}
		return _temp;
	}, this);
	self.getDateJobTime = function()
	{
		$.ajax({
			type : "GET",
			dataType : "json",
			contentType : "application/json",

			url : _host + "/getDateJobDone/" + moment(self.currentJobDone.date()).format('DDMMYYYY').toString(),
			data : ko.toJSON(self.currentJobDone),
			success : function(response, status, xhr)
			{
				self.dateJobTime(response)
				console.log("ok");
			},
			error : function(response, status, xhr)
			{
				console.log("ko");
				alert(response.responseJSON.message);
			},
		});

	}
	// TODO use an init event
	self.getDateJobTime();
}