package by.zhdanovich.rat.test;

import org.junit.Assert;
import org.junit.Test;
import by.zhdanovich.rat.command.util.Validator;

public class ValidatorTest {

	@Test
	public void checkPasswordTest() {
		Boolean condition = Validator.checkPassword("45jr76s");
		Assert.assertTrue(condition);
	}

	@Test
	public void checkTitleTest() {
		Boolean condition = Validator.checkTitle("Ping-Pong");
		Assert.assertTrue(condition);
	}

	@Test
	public void checkLoginTest() {
		Boolean condition = Validator.checkLogin("Анна");
		Assert.assertFalse(condition);
	}

	@Test
	public void checkNameTest() {
		Boolean condition = Validator.checkName("Anna");
		Assert.assertTrue(condition);
	}

	@Test
	public void checkEmailTest() {
		Boolean condition = Validator.checkEmail("nivie@gmail.com");
		Assert.assertTrue(condition);
	}

}
