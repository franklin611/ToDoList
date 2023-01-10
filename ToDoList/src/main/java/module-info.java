module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    opens com.example.demo;
    opens com.example.demo.datamodel;


    exports com.example.demo;

}