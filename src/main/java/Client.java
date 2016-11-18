import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Client {

	private Server server;

	public Client(Server server) {
		this.server = server;
	}

	public CompletableFuture<String> clockInAsync(String userName, String timestamp) {
		return CompletableFuture.supplyAsync(
				() -> server.recordTime(userName, timestamp)
		);
	}

	public String clockIn(String userName, String timestamp) {
		CompletableFuture<String> success = clockInAsync(userName, timestamp);
		try {
			return success.get(10, TimeUnit.MILLISECONDS);
		} catch (TimeoutException |
				InterruptedException |
				ExecutionException e) {
			success.cancel(true);
			try {
				success.get();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			} catch (ExecutionException e1) {
				e1.printStackTrace();
			}
			System.out.println("Canceled");
			return "Failure";
		}
	}

	public CompletableFuture<String> clockInWithTimeout(String userName, String timestamp) {
		return CompletableFuture.supplyAsync(() -> this.clockIn(userName, timestamp));

		// 1. What we'd like to have is setting timeout on the method like this:
		// return clockInAsync(userName, timestamp).setTimeout(10, "Failure");
		// 2. What do we need to implement to cancel the CompletableFuture?
	}
}
