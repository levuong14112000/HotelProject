module com.example.hotelproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires itextpdf;
    requires java.desktop;


    opens com.example.hotelproject to javafx.fxml;
    exports com.example.hotelproject;
}