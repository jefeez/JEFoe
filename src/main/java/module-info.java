module org.jefeez.efoe {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.github.kwhat.jnativehook;
    requires opencv;


    opens org.jefeez.efoe to javafx.fxml;
    exports org.jefeez.efoe;
}