package by.zhdanovich.rat.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import by.zhdanovich.rat.service.util.BuilderPath;

public class BuilderPathTest {
	private BuilderPath builder;

	@Before
	public  void init() {
		builder = new BuilderPath();
	}

	@After
	public void clear() {
		builder = null;
	}

	@Test
	public void takeNameTest() {
		String expected = "153e1778690.jpg";
		String header = "C:/Users/Электросила/Desktop/pages of my project/Posters/153e1778690.jpg";
		String actual = builder.takeName(header);
		Assert.assertEquals(expected, actual);
		
	}
}
