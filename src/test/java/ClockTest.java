import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ClockTest {

	@Test
	public void workingClockIn() throws ExecutionException, InterruptedException {
		Client client = new Client(new Server(false));

		CompletableFuture<Boolean> success = client.clockInAsync("Steve", "5:45");

		assertTrue(success.get());
	}

	@Test
	public void slowClockIn() throws ExecutionException, InterruptedException {
		Client client = new Client(new Server(true));

		CompletableFuture<Boolean> success = client.clockInAsync("Steve", "5:45");

		assertFalse(success.get());
	}
}
