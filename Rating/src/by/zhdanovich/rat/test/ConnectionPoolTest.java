package by.zhdanovich.rat.test;

import org.junit.Assert;
import org.junit.Test;

import by.zhdanovich.rat.dao.pool.ConnectionPool;

public class ConnectionPoolTest {

	@Test
	public void getInstanceTest() {
		ConnectionPool expected = ConnectionPool.getInstance();
		ConnectionPool actual = ConnectionPool.getInstance();
		Assert.assertSame(expected, actual);
	}

}
