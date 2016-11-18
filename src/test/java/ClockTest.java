import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;

public class ClockTest {

    @Test
    public void syncServerAccepts() throws ExecutionException, InterruptedException {
        Client client = new Client((__, ___) -> "OK");

        String isSuccessful = client.clockIn("Steve", "5:45");

        assertEquals("OK", isSuccessful);
    }

    @Test
    public void syncServerRejects() throws ExecutionException, InterruptedException, TimeoutException {
        Client client = new Client((__, ___) -> "Failure");

        String isSuccessful = client.clockIn("Steve", "5:45");

        assertEquals("Failure", isSuccessful);
    }

    @Test
    public void syncServerThrowsException() throws ExecutionException, InterruptedException, TimeoutException {
        Client client = new Client((__, ___) -> {
            throw new IllegalArgumentException("server crashed");
        });

        String isSuccessful = client.clockIn("Steve", "5:45");

        assertEquals("Failure", isSuccessful);
    }

    @Test
    public void syncServerDoesNotAnswer() throws ExecutionException, InterruptedException, TimeoutException {
        Client client = new Client((__, ___) -> {
            while (true) {
            }
        });

        String isSuccessful = client.clockIn("Steve", "5:45");

        assertEquals("Failure", isSuccessful);
    }

    @Test @Ignore
    public void timeOutAsyncWrapperNoBlocking() throws ExecutionException, InterruptedException, TimeoutException {
        Client client = new Client((__, ___) -> {
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Call: Interrupted");
                }
                System.out.println(".");
            }
        });

        CompletableFuture<String> successful = client.clockInAsyncWithTimeout("Steve", "5:45");

        System.out.println("Doing more stuff");

        // do not check with GET, check on complete?
        //        successful.whenComplete((s, throwable) -> {
        //            try {
        //                System.out.println("Done");
        //            } catch (Exception e) {
        //                e.printStackTrace();
        //            }
        //        });

        Thread.sleep(10000);

        assertEquals("Failure", successful.get());
    }
}
