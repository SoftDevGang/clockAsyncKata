import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Client {

	private Server server;

	public Client(Server server) {
		this.server = server;
	}

	public CompletableFuture<Boolean> clockInAsync(String userName, String timestamp) {
		return CompletableFuture.supplyAsync(
				() -> server.recordTime(userName, timestamp)
		);
	}

	public Boolean clockIn(String userName, String timestamp) {
		CompletableFuture<Boolean> success = clockInAsync(userName, timestamp);
		try {
			return success.get(10, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			return false;
		}
	}
}
