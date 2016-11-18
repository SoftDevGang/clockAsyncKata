import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ClockTest {

	@Test
	public void workingClockIn() throws ExecutionException, InterruptedException {
		Client client = new Client(new FakeServer(false));

		Boolean isSuccessful = clockIn(client, "Steve", "5:45");

		assertTrue(isSuccessful);
	}

	@Test
	public void slowClockIn() throws ExecutionException, InterruptedException, TimeoutException {
		Client client = new Client(new FakeServer(true));

		Boolean isSuccessful = clockIn(client, "Steve", "5:45");

		assertFalse(isSuccessful);
	}

	@Test
	public void timeOutClockIn() throws ExecutionException, InterruptedException, TimeoutException {
		Client client = new Client((__, ___) -> {
			while (true) {
			}
		});

		Boolean isSuccessful = clockIn(client, "Steve", "5:45");

		assertFalse(isSuccessful);
	}

	private Boolean clockIn(Client client, String userName, String timestamp) {
		CompletableFuture<Boolean> success = client.clockInAsync(userName, timestamp);
		try {
			return success.get(10, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			return false;
		}
	}
}
