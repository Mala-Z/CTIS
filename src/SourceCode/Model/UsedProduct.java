package SourceCode.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.format.DateTimeFormatter;

/**
 * Created by Paula on 26/03/16.
 */
public class UsedProduct {
    private final IntegerProperty id;
    private final IntegerProperty employeeBarcode;
    private final IntegerProperty itemBarcode;
    private final ObjectProperty<DateTimeFormatter> timeTaken;
    private final IntegerProperty quantity;

    //DEFAULT CONSTRUCTOR
    public UsedProduct() {
        this(0, 0, 0, null, 0);
    }

    //CONSTRUCTOR
    public UsedProduct(int id, int employeeBarcode, int itemBarcode, String timeTaken, int quantity) {
        this.id = new SimpleIntegerProperty(id);
        this.employeeBarcode = new SimpleIntegerProperty(employeeBarcode);
        this.itemBarcode = new SimpleIntegerProperty(itemBarcode);
        this.timeTaken = new SimpleObjectProperty(timeTaken);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    //GET METHODS
    public final int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public IntegerProperty employeeBarcodeProperty() {
        return employeeBarcode;
    }

    public IntegerProperty itemBarcodeProperty() { return itemBarcode; }

    public ObjectProperty timeTakenProperty() {
        return timeTaken;
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }
}
