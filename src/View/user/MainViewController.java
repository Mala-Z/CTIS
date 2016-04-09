package View.user;

import View.RunView;
import javafx.fxml.FXML;

import java.io.IOException;

/**
 * Created by St_Muerte on 3/24/16.
 */
public class MainViewController {
    private RunView runView;


    @FXML
    private void goHome() throws IOException {
        runView.showMainView();
    }

    @FXML
    private void goTakeItem() throws IOException{
        runView.showTakeItem();
    }
    @FXML
    private void goReturnItem() throws IOException{
        runView.showReturnItemView();
    }
    @FXML
    private void goSearch() throws IOException{
        runView.showSearch();

    }
    @FXML
    private void goAdminView() throws IOException{
        runView.showAdminView();
    }


}
