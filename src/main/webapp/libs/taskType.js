var TaskTypeViewModel = function()
{
	var self = this;

	self.taskType = ko.observableArray();
	self.selectedTaskType = ko.observable();
	self.newTaskType = ko.observable();

	var TaskTypeModel = function()
	{
		var that = this;
		that.id = ko.observable();
		that.type = ko.observable();
	};
	self.addNewTaskType = function()
	{
		var _taskType = new TaskTypeModel();
		_taskType.type(self.newTaskType());

		$.ajax({
			type : "POST",
			dataType : "json",
			contentType : "application/json",

			url : _host + "/editTaskType",
			data : ko.toJSON(_taskType),
			success : function(response, status, xhr)
			{
				self.newTaskType(null);
				msgPublisher.notifySubscribers(response, 'taskTypeFromServer');
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
	msgPublisher.subscribe(function(taskTypes)
	{
		if (typeof taskTypes != 'undefined')
		{
			self.taskType(taskTypes)

		}
	}, self, 'taskTypeFromServer');
}