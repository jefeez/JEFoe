module org.jefeez.efoe {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens org.jefeez.efoe to javafx.fxml;
    exports org.jefeez.efoe;
}