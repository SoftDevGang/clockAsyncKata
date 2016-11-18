import java.util.concurrent.CompletableFuture;

public class Client {

	private Server server;

	public Client(Server server) {
		this.server = server;
	}

	public CompletableFuture<Boolean> clockInAsync(String userName, String timestamp) {
		return CompletableFuture.supplyAsync(() -> server.recordTime(userName, timestamp));
	}
}
