package comp3350.ptw.application;


import comp3350.ptw.persistence.DataAccess;
import comp3350.ptw.persistence.DataAccessObject;

public class DataServices {

    private static DataAccess dataAccessService = null;

    public static DataAccess createDataAccess(String dbName) {

        if (dataAccessService == null) {
            dataAccessService = new DataAccessObject(dbName);
            dataAccessService.open(Main.getDBPath());
        }

        return dataAccessService;
    }

    public static DataAccess createDataAccess(DataAccess alternateDataAccessService) {

        if (dataAccessService == null) {
            dataAccessService = alternateDataAccessService;
            dataAccessService.open(Main.getDBPath());
        }

        return dataAccessService;
    }

    public static DataAccess getDataAccess() {

        if (dataAccessService == null) {
            System.out.println("No DataAccess found.");
            System.exit(1);
        }

        return dataAccessService;
    }

    public static void closeDataAccess() {

        if (dataAccessService != null) {
            dataAccessService.close();
        }

        dataAccessService = null;
    }


}
