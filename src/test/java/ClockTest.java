import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

public class ClockTest {

	@Test
	public void workingClockIn() throws ExecutionException, InterruptedException {
		CompletableFuture<Boolean> success = clockInAsync("Steve", "5:45");

		assertTrue(success.get());
	}

	private CompletableFuture<Boolean> clockInAsync(String userName, String timestamp) {
		return CompletableFuture.supplyAsync(() -> this.recordTime(userName, timestamp));
	}

	private boolean recordTime(String userName, String timestamp) {
		return true;
	}

}
