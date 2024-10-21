module disneyapi {
    requires javafx.controls;
    requires javafx.graphics;
    requires transitive javafx.fxml;
    requires javafx.base;
    requires json.simple;
    requires org.json;
    requires org.jdom2;
    requires java.xml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires java.base;
    requires java.desktop;
    requires com.google.gson;

    opens disneyapi to javafx.fxml, com.fasterxml.jackson.databind, com.google.gson;

    exports disneyapi;
}
