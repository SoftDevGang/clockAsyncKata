import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

public class ClockTest {

	/*
	Given:
Server is in good condition

When:
Steve clocks in at 5:45

Then:
Receives acknowledgment he was clocked in at 5:45
	*/

//	@Test
//	public void something() {
//		boolean success = clockIn("Steve", "5:45");
//
//		assertTrue(success);
//	}

	@Test
	public void somethingElse() throws ExecutionException, InterruptedException {
		CompletableFuture<Boolean> success = clockInAsync("Steve", "5:45");

		assertTrue(success.get());
	}

	private CompletableFuture<Boolean> clockInAsync(String userName, String timestamp) {
		return CompletableFuture.supplyAsync(() -> this.recordTime(userName, timestamp));
	}

	private boolean recordTime(String userName, String timestamp) {
		return true;
	}

	private boolean clockIn(String userName, String timestamp) {

		return false;
	}
}
