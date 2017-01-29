function validateLogin() {
	
	var result = true;
	
	var 
	errInfo = document.getElementById("err-info");	
	
	var
	log = document.frmLog.log.value,
	pass = document.frmLog.pass.value;

	if (!log){
		errInfo.style.visibility='visible';
		result = false;
	} else {
		errInfo.style.visibility = 'hidden';
	}
	
	if (!pass){
		errInfo.style.visibility='visible';
		result = false;
	}else {
		errInfo.style.visibility = 'hidden';
	}
	
	return result;
}
