package SourceCode.BusinessLogic;

import SourceCode.Controller.admin.CreateEmployeeController;

public class Factory {
    public static ConnectDB connectDB = null;
    public static CreateEmployeeController createEmployeeController = null;

    /* CONSTRUCTOR */
    public Factory() throws Exception{
        getInstance();
    }

    /* RETURN AN INSTANCE OF THE FACTORY */
    public static ConnectDB getInstance() throws Exception{
        if (connectDB == null) {
            connectDB = new ConnectDB();
            return connectDB;
        }
        return connectDB;
    }
    public static CreateEmployeeController getEmployeeInstance() throws Exception{
        if (createEmployeeController == null){
            createEmployeeController = new CreateEmployeeController();
            return createEmployeeController;
        }
        return createEmployeeController;
    }
}
