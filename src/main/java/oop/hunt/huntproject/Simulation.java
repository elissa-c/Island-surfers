package oop.hunt.huntproject;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;

import static java.lang.Math.floor;
import static oop.hunt.huntproject.HuntProject.predators;
import static oop.hunt.huntproject.HuntProject.preys;

import java.util.Iterator;
import java.util.Objects;


/**
 * JavaFX App
 * Class creating the user interface and painting the simulation
 */
public class Simulation extends Application {

    public static int GridSize = 30;
    public static int CellSize = 30;
    public Object clicked = null;
    public static String type = "";
    
    private Canvas world;
    private static GraphicsContext graphics;
    private AnimationTimer timer;
    public static VBox ilayout;


    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        launch(args);

    }

    /**
     * Method creating the interfaces and managing clicked objects
     * @param stage the stage to be displayed
     */
    @Override
    public void start(Stage stage) {

        world = new Canvas(GridSize * CellSize, GridSize * CellSize);
        graphics = world.getGraphicsContext2D();

        //simulation window
        BorderPane root = new BorderPane();
        root.setCenter(world);
        stage.setScene(new Scene(root));
        stage.setTitle("Island Surfers");
        stage.setOnCloseRequest(e -> System.exit(0));
        stage.setResizable(false);
        stage.show();

        //info window
        Stage info = new Stage();
        info.setTitle("Information");
        info.setX(stage.getX() + stage.getWidth());
        info.setY(stage.getY());

        ilayout = new VBox(10);
        Label information = new Label("Select an objet to see its information");
        ilayout.setAlignment(Pos.CENTER);
        ilayout.getChildren().add(information);
        ilayout.setPadding(new Insets(20));
        info.setScene(new Scene(ilayout, 300, stage.getHeight()/2-40));
        info.setResizable(false);
        info.setOnCloseRequest(e -> System.exit(0));
        info.show();

        Button kill = new Button("Kill animal");
        kill.setOnAction(e -> {
            if (clicked instanceof Prey) {
                ((Prey) clicked).living = false;
                ((Prey) clicked).die();
            } else if (clicked instanceof Predator) {
                ((Predator) clicked).living = false;
                ((Predator) clicked).die();
            }
        });

        MenuWindow.createMenu(stage.getHeight(), info.getHeight(), info.getX(), info.getY(), kill);

        HuntProject.startup();

        paintWorld();

        timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                setAnimals();
            }
        };
        timer.start();

        world.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int x = (int) floor(mouseEvent.getX()/CellSize);
                int y = (int) floor(mouseEvent.getY()/CellSize);
                System.out.println("CLICK" + x + y);
                Point click = new Point(x, y);
                clicked = null;

                for (Prey p : preys) {
                    if (p.coordinates.x == click.x && p.coordinates.y == click.y) {
                        clicked = p;
                        type = "Prey";
                        System.out.println("PREEEY " + type);
                        break;
                    }
                }
                for (Predator p : HuntProject.predators) {
                    if (p.coordinates.x == click.x && p.coordinates.y == click.y) {
                        clicked = p;
                        type = "Predator";
                        break;
                    }
                }
                if (clicked == null) {
                    clicked = HuntProject.findPlace(click);
                }
            }
        });

    }

    /**
     * Method painting the background and stationary elements of the simulation
     */
    public void paintWorld() {
        graphics.setFill(Color.CORNFLOWERBLUE);
        graphics.fillRect(0, 0, world.getWidth(), world.getHeight());


        for (int a = 0; a<GridSize; a++) {
            for (int b=0; b<GridSize; b++) {
                if (HuntProject.World[b][a] == "H") {
                    graphics.drawImage(new Image("Island.jpg"), b*CellSize, a*CellSize, CellSize, CellSize);
                }
                else if (HuntProject.World[b][a] == "W") {
                    graphics.drawImage(new Image("Wave.jpg"), b*CellSize, a*CellSize, CellSize, CellSize);
                }
                else if (HuntProject.World[b][a] == "V") {
                    graphics.drawImage(new Image("Shack.jpg"), b*CellSize, a*CellSize, CellSize, CellSize);
                }
                else if (HuntProject.World[b][a] == "P") {
                    graphics.setFill(Color.rgb(51, 125, 212));
                    graphics.fillRect(b*CellSize, a*CellSize, CellSize, CellSize);
                }
                else if (HuntProject.World[b][a] == "C") {
                    graphics.drawImage(new Image("Spin.jpg"), b*CellSize, a*CellSize, CellSize, CellSize);
                }
            }
        }

        graphics.setStroke(Color.DODGERBLUE);
        for (int i = 0; i<GridSize-1; i++) {
            graphics.strokeLine(0, (i+1)*CellSize, world.getWidth(), (i+1)*CellSize);
            graphics.strokeLine((i+1)*CellSize, 0, (i+1)*CellSize, world.getHeight());
        }

    }

    /**
     * Method painting the moving elements of the simulation updated on every animation timer and displaying information about the clicked object
     */
    private void setAnimals() {
        Iterator<Prey> pitter = preys.iterator();
        while (pitter.hasNext()) {
            Prey p = pitter.next();
            Point past = p.prevcoord;
            Point current = p.coordinates;
            if (past != null) {
                if (Objects.equals(HuntProject.World[past.x][past.y], "P")) {
                    graphics.setFill(Color.rgb(51, 125, 212));
                    graphics.fillRect(past.x * CellSize, past.y * CellSize, CellSize, CellSize);
                } else if (Objects.equals(HuntProject.World[past.x][past.y], "C")) {
                    graphics.drawImage(new Image("Spin.jpg"), past.x*CellSize, past.y*CellSize, CellSize, CellSize);
                } else if (Objects.equals(HuntProject.World[past.x][past.y], "H")) {
                    graphics.drawImage(new Image("Island.jpg"), past.x*CellSize, past.y*CellSize, CellSize, CellSize);
                }
            }
            if (Objects.equals(HuntProject.World[current.x][current.y], "o") || Objects.equals(HuntProject.World[current.x][current.y], "P")) {
                String desc = p.name;
                if (Objects.equals(desc, "Long haired surfer")) {
                    graphics.drawImage(new Image("Lady_Surfer.jpg"), current.x * CellSize, current.y * CellSize, CellSize, CellSize);
                }
                else {
                    graphics.drawImage(new Image("Mister_Surfer.jpg"), current.x * CellSize, current.y * CellSize, CellSize, CellSize);
                }
            }
        }
        Iterator<Predator> predit = predators.iterator();
        while (predit.hasNext()) {
            Predator p = predit.next();
            Point past = p.prevcoord;
            Point current = p.coordinates;
            if (past != null) {
                if (Objects.equals(HuntProject.World[past.x][past.y], "P")) {
                    graphics.setFill(Color.rgb(51, 125, 212));
                    graphics.fillRect(past.x * CellSize, past.y * CellSize, CellSize, CellSize);
                } else {
                    graphics.setFill(Color.CORNFLOWERBLUE);
                    graphics.fillRect(past.x * CellSize, past.y * CellSize, CellSize, CellSize);
                }
            }
            if (Objects.equals(HuntProject.World[current.x][current.y], "o") || Objects.equals(HuntProject.World[p.coordinates.x][p.coordinates.y], "P")) {
                graphics.drawImage(new Image("Shark.jpg"), current.x * CellSize, current.y * CellSize, CellSize, CellSize);
            }
        }

        graphics.setStroke(Color.DODGERBLUE);
        for (int i = 0; i<GridSize-1; i++) {
            graphics.strokeLine(0, (i+1)*CellSize, world.getWidth(), (i+1)*CellSize);
            graphics.strokeLine((i+1)*CellSize, 0, (i+1)*CellSize, world.getHeight());
        }

        if (clicked != null) {
            ilayout.getChildren().clear();
            if (Objects.equals(type, "Prey")) {
                Prey chosen = (Prey) clicked;
                if (!chosen.living) {
                    Label dead = new Label("Sorry it's dead :(");
                    ilayout.getChildren().add(dead);
                }
                else {
                    Label spec = new Label("You've got " + chosen.Name());
                    Label name = new Label("It is a type of: " + chosen.Species());
                    Label where = new Label("Where are they? Oh! Here " + chosen.coordinates.x + " " + chosen.coordinates.y);
                    Label fast = new Label("They are this fast: " + chosen.Speed());
                    Label strong = new Label("They are this strong: " + chosen.Strength());
                    Label water = new Label("They also have " + chosen.waterLevel + "% of water, ");
                    Label food = new Label("and this " + chosen.foodLevel + "% of food");
                    Label left = new Label("Health percentage: " + chosen.getHealth() + "%");


                    ilayout.getChildren().addAll(spec, name, where, fast, strong, water, food, left);
                }
            }
            else if (Objects.equals(type, "Predator")) {
                Predator chosen = (Predator) clicked;
                if (!chosen.living) {
                    Label dead = new Label("Sorry it's dead :(");
                    ilayout.getChildren().add(dead);
                } else {
                    Label spec = new Label("You've got " + chosen.Name());
                    Label name = new Label("It is a type of: " + chosen.Species());
                    Label where = new Label("Where are they? Oh! Here " + chosen.coordinates.x + " " + chosen.coordinates.y);
                    Label fast = new Label("They are this fast: " + chosen.Speed());
                    Label strong = new Label("They are this strong: " + chosen.Strength());
                    Label health = new Label("They also have " + (chosen.health * 20) + "% of health");
                    Label state = new Label("On the hunt? " + chosen.getState());


                    ilayout.getChildren().addAll(spec, name, where, fast, strong, health, state);
                }
            }
            else if (Objects.equals(type, "H")) {
                Hideout chosen = (Hideout) clicked;
                Label name = new Label("You're here: " + chosen.name);
                Label where = new Label("Where exactly? Here " + chosen.coordinates.x + " " + chosen.coordinates.y);
                Label cap = new Label("The capacity is " + chosen.capacity);
                Label who = new Label("Who is inside? " + chosen.getAnimals());

                ilayout.getChildren().addAll(name, where, cap, who);
            }
            else if (Objects.equals(type, "W")) {
                WaterSource chosen = (WaterSource) clicked;
                Label name = new Label("You're here: " + chosen.name);
                Label where = new Label("Where exactly? Here " + chosen.coordinates.x + " " + chosen.coordinates.y);
                Label cap = new Label("The capacity is " + chosen.capacity);
                Label who = new Label("Who is inside? " + chosen.getAnimals());

                ilayout.getChildren().addAll(name, where, cap, who);
            }
            else if (Objects.equals(type, "F")) {
                Plant chosen = (Plant) clicked;
                Label name = new Label("You're here: " + chosen.name);
                Label where = new Label("Where exactly? Here " + chosen.coordinates.x + " " + chosen.coordinates.y);
                Label cap = new Label("The capacity is " + chosen.capacity);
                Label who = new Label("Who is inside? " + chosen.getAnimals());

                ilayout.getChildren().addAll(name, where, cap, who);
            }
            else if (Objects.equals(type, "C")) {
                Crossroad chosen = (Crossroad) clicked;
                Label name = new Label("You're here: " + chosen.name);
                Label where = new Label("Where exactly? Here " + chosen.coordinates.x + " " + chosen.coordinates.y);
                Label cap = new Label("The capacity is " + chosen.capacity);
                Label who = new Label("Who is inside? " + chosen.getAnimals());

                ilayout.getChildren().addAll(name, where, cap, who);
            }
        }
    }

    /**
     * Method deleting the dead or removed objects from the simulation
     * @param taken point to be restored to original state
     */
    public static void cleanUp(Point taken) {
        System.out.println("cleaned " + taken.x + taken.y);
        if (Objects.equals(HuntProject.World[taken.x][taken.y], "P")) {
            graphics.setFill(Color.rgb(51, 125, 212));
            graphics.fillRect(taken.x * CellSize, taken.y * CellSize, CellSize, CellSize);
        } else if (Objects.equals(HuntProject.World[taken.x][taken.y], "o")) {
            graphics.setFill(Color.CORNFLOWERBLUE);
            graphics.fillRect(taken.x * CellSize, taken.y * CellSize, CellSize, CellSize);
        } else if (Objects.equals(HuntProject.World[taken.x][taken.y], "C")) {
            graphics.drawImage(new Image("Spin.jpg"), taken.x*CellSize, taken.y*CellSize, CellSize, CellSize);
        } else if (Objects.equals(HuntProject.World[taken.x][taken.y], "H")) {
            graphics.drawImage(new Image("Island.jpg"), taken.x*CellSize, taken.y*CellSize, CellSize, CellSize);
        }
    }

}