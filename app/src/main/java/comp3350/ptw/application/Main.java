package comp3350.ptw.application;

public class Main
{
	public static final String dbName = "PTWDB";
	private static String dbPath = "hsqldb";

	public static void main(String[] args)
	{
		startUp();
		
		shutDown();
		System.out.println("All done");
	}

	public static void startUp()
	{
		DataServices.createDataAccess(dbName);
	}

	public static void shutDown()
	{
		DataServices.closeDataAccess();
	}

	public static String getDBPath() {
		if (dbPath == null)
			return dbName;
		else
			return dbPath;
	}

	public static void setDBPath(String pathName) {
		System.out.println("Setting DB path to: " + pathName);
		dbPath = pathName;
	}
}
