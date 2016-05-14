package SourceCode.BusinessLogic;

public class Factory {
    public static ConnectDB connectDB = null;

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
}
