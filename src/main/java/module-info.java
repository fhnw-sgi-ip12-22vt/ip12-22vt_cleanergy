open module com.cleanergy {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.pi4j;
    requires j2mod;
    requires junit;
    requires java.logging;

    // Pi4J Modules
    requires com.pi4j.library.pigpio;
    requires com.pi4j.library.linuxfs;
    requires com.pi4j.plugin.pigpio;
    requires com.pi4j.plugin.raspberrypi;
    requires com.pi4j.plugin.mock;
    requires com.pi4j.plugin.linuxfs;
    uses com.pi4j.extension.Extension;
    uses com.pi4j.provider.Provider;


    // JavaFX
    requires javafx.base;

}