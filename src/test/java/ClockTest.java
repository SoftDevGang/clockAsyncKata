import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ClockTest {

	private boolean serverOverhelmed;

	@Test
	public void workingClockIn() throws ExecutionException, InterruptedException {
		CompletableFuture<Boolean> success = clockInAsync("Steve", "5:45");

		assertTrue(success.get());
	}

	@Test
	public void slowClockIn() throws ExecutionException, InterruptedException {
		setServerOverhelmed(true);

		CompletableFuture<Boolean> success = clockInAsync("Steve", "5:45");

		assertFalse(success.get());
	}


	private CompletableFuture<Boolean> clockInAsync(String userName, String timestamp) {
		return CompletableFuture.supplyAsync(() -> this.recordTime(userName, timestamp));
	}

	private boolean recordTime(String userName, String timestamp) {
		boolean canRecordTime = !serverOverhelmed;
		return canRecordTime;
	}

	public void setServerOverhelmed(boolean serverOverhelmed) {
		this.serverOverhelmed = serverOverhelmed;
	}

	public boolean isServerOverhelmed() {
		return serverOverhelmed;
	}
}
