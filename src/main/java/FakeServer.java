public class FakeServer implements Server {

	private boolean serverOverwhelmed;

	public FakeServer() {
		this.serverOverwhelmed = false;
	}

	public FakeServer(boolean serverOverwhelmed) {
		this.serverOverwhelmed = serverOverwhelmed;
	}

	public boolean recordTime(String userName, String timestamp) {
		boolean canRecordTime = !serverOverwhelmed;
		return canRecordTime;
	}
}
