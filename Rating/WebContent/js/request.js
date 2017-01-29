function validateRequest() {

	var result = true;

	var errLogin = document.getElementById("err-log"), errPwd1 = document
			.getElementById("err-pass");
	errText = document.getElementById("err-text");

	var

	login = document.frm2.login.value, text = document.frm2.text.value, pwd1 = document.frm2.password.value;

	
	if (!login) {
		errLogin.style.visibility = 'visible';
		result = false;
	} else {
		errLogin.style.visibility = 'hidden';
	}

	if (login.search(/[A-Za-z0-9_-\s]{2,}/) == -1) {
		errLogin.style.visibility = 'visible';
		result = false;
	} else {
		errLogin.style.visibility = 'hidden';
	}
	

	if (!pwd1) {
		errPwd1.style.visibility = 'visible';
		result = false;
	} else {
		errPwd1.style.visibility = 'hidden';
	}

	if (pwd1.search(/[А-Яа-яЁёa-zA-Z0-9]{5,}/) == -1) {
		errPwd1.style.visibility = 'visible';
		result = false;
	} else {
		errPwd1.style.visibility = 'hidden';
	}
	
	
	if (!text) {
		errText.style.visibility = 'visible';
		result = false;
	} else {
		errText.style.visibility = 'hidden';
	}
	
	return result;
}
