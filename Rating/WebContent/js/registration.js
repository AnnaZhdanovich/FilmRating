function validate() {

	var result = true;

	var

	errFname = document.getElementById("err-fname"), errLogin = document
			.getElementById("err-login"), errEmail = document
			.getElementById("err-email"), errPwd1 = document
			.getElementById("err-pwd1"), errPwd2 = document
			.getElementById("err-pwd2");

	var

	fname = document.frm1.firstname.value, login = document.frm1.login.value, email = document.frm1.email.value, pwd1 = document.frm1.pwd1.value, pwd2 = document.frm1.pwd2.value;

	if (!fname) {
		errFname.style.visibility = 'visible';
		result = false;
	} else {
		errFname.style.visibility = 'hidden';
	}

	if (fname.search(/^[A-ZA-ЯЁ][A-ZA-ЯЁёа-яa-z-_\s]{2,}/) == -1) {
		errFname.style.visibility = 'visible';
		result = false;
	} else {
		errFname.style.visibility = 'hidden';
	}

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

	if (!email) {
		errEmail.style.visibility = 'visible';
		result = false;
	} else {
		errEmail.style.visibility = 'hidden';
	}

	if (email.search(/^[^@]+@[^@.]+\.[^@]+$/) == -1) {
		errEmail.style.visibility = 'visible';
		result = false;
	} else {
		errEmail.style.visibility = 'hidden';
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
	
	if (pwd1 !== pwd2) {
		errPwd2.style.visibility = 'visible';
		result = false;
	} else {
		errPwd2.style.visibility = 'hidden';
	}

	if (!pwd2) {
		errPwd2.style.visibility = 'visible';
		result = false;
	} else {
		errPwd2.style.visibility = 'hidden';
	}

	return result;
}
