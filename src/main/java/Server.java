public class Server {

	private boolean serverOverwhelmed;

	public Server(boolean serverOverwhelmed) {
		this.serverOverwhelmed = serverOverwhelmed;
	}

	public boolean recordTime(String userName, String timestamp) {
		boolean canRecordTime = !serverOverwhelmed;
		return canRecordTime;
	}
}
