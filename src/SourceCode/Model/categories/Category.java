package SourceCode.Model.categories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public abstract class Category {
    public static ArrayList<String> getCategories(){
        ArrayList<String> categoryList = new ArrayList<>();
        categoryList.add("Apartment cleaning");
        categoryList.add("Clothes");
        categoryList.add("Green areas");
        categoryList.add("Key");
        categoryList.add("Snow");

        return categoryList;
    }
}
