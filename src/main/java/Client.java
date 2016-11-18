import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Client {

    private final Server server;

    public Client(Server server) {
        this.server = server;
    }

    public String clockIn(String userName, String timestamp) {
        CompletableFuture<String> success = clockInAsync(userName, timestamp);

        // TODO we want to timeout a non responding server. 1st try - get with timeout
        // Put this into original future.
        try {
            return success.get(10, TimeUnit.MILLISECONDS);
        } catch (TimeoutException |
                InterruptedException |
                ExecutionException e) {

            // TODO we want to cancel the while loop on running future. cancel
            System.out.println("Canceled");
            success.cancel(true);

            return "Failure";
        }
    }

    private CompletableFuture<String> clockInAsync(String userName, String timestamp) {
        // making it async seems too easy - just wrap future around it?
        return CompletableFuture.supplyAsync(
                () -> server.recordTime(userName, timestamp)
        );
    }

    public CompletableFuture<String> clockInAsyncWithTimeout(String userName, String timestamp) {
        // TODO make the timeout into future
        return CompletableFuture.supplyAsync(() -> this.clockIn(userName, timestamp));

        // 1. What we'd like to have is setting timeout on the method like this:
        // e.g. return clockInAsync(userName, timestamp).setTimeout(10, "Failure");
        // 2. What do we need to implement to cancel the CompletableFuture?
    }
}
