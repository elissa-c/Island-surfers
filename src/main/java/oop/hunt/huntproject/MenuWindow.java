package oop.hunt.huntproject;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * MenuWindow creator class
 */
public class MenuWindow {

    /**
     * Creates a new MenuWindow
     * @param sheight the height of the simulation window
     * @param iheight the height of the info window
     * @param iX x coordinate of the info window
     * @param iY y coordinate of the info window
     * @param kill the kill button
     */
    public static void createMenu(double sheight, double iheight, double iX, double iY, Button kill) {
        Stage buttons = new Stage();
        buttons.setTitle("Menu");
        buttons.setX(iX);
        buttons.setY(iY+iheight);
        VBox mlayout = new VBox(10);

        Label menu = new Label("Create!");
        Button addPrey = new Button("Add Prey");
        addPrey.setOnAction(e -> {HuntProject.createPrey();});

        Button addPredator = new Button("Add Predator");
        addPrey.setMaxWidth(120);
        addPredator.setMaxWidth(120);
        kill.setMaxWidth(120);
        addPredator.setOnAction(e -> {HuntProject.createPredator();});


        mlayout.setAlignment(Pos.CENTER);
        mlayout.getChildren().addAll(menu, addPrey, addPredator, kill);
        mlayout.setPadding(new Insets(10));
        buttons.setScene(new Scene(mlayout, 300, sheight/2-40));
        buttons.setResizable(false);
        buttons.setOnCloseRequest(e -> System.exit(0));
        buttons.show();
    }
}
