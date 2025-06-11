module com.example.plantvszombie {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;
    requires java.desktop;


    opens com.example.plantvszombie to javafx.fxml;
    exports com.example.plantvszombie;
}