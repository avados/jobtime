var TaskTypeViewModel = function()
{
	var self = this;

	self.taskType = ko.observableArray();
	self.selectedTaskType = ko.observable();

	var TaskTypeModel = function()
	{
		var that = this;
		that.id = ko.observable();
		that.type = ko.observable();
	};

	msgPublisher.subscribe(function(taskTypes)
	{
		if (typeof taskTypes != 'undefined')
		{
			self.taskType(taskTypes)

		}
	}, self, 'taskTypeFromServer');
}