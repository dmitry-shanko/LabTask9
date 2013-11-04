function deleteGroupOfNews() {
	var chks = document.getElementsByClassName("checkIndex");
	for ( var i = 0; i < chks.length; i++) {
		if (chks[i].checked) {
			return confirmDialog();
		}
	}
	alert(notChecked);
	return false;
}

function confirmDialog() {
	var answer = confirm(deleteDialog);
	if (answer) {
		return true;
	} else {
		return false;
	}
}

function validateAddEditNewsForm() {
	var newsDate = document.getElementById("newsDate");

	var briefLength = document.getElementById("newsBrief").value.length;
	var titleLength = document.getElementById("newsTitle").value.length;
	var contentLength = document.getElementById("newsContent").value.length;

	var errorList = [];
	expr = new RegExp(datePattern);
	if (!(newsDate.value.match(expr))) 
	{
		errorList.push(dateFormat);
	} 
	else 
	{
		if (newsDate.value == "") 
		{
			errorList.push(dateRequired);
		}
	}

	if (titleLength < 1) 
	{
		errorList.push(titleRequired);
	} 
	else
	{
		if (titleLength > 100) 
		{
			errorList.push(titleLength);
		}
	}

	if (briefLength < 1) 
	{
		errorList.push(briefRequired);
	} 
	else
	{
		if (briefLength > 200) 
		{
			errorList.push(briefLength);
		}
	}

	if (contentLength < 1) 
	{
		errorList.push(contentRequired);
	} 
	else
	{
		if (contentLength > 2000) 
		{
			errorList.push(contentLength);
		}
	}

	if (errorList.length == 0) 
	{
		return true;
	} 
	else 
	{
		var errorMessage = "";
		for (var i = 0; i < errorList.length; i++) 
		{
			errorMessage += errorList[i] + "\n";
		}
		alert(errorMessage);
	}
	return false;
}