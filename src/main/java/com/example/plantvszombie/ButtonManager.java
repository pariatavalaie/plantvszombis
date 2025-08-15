package com.example.plantvszombie;

import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.*;

public class ButtonManager {
    private final Map<String, Button> buttons = new LinkedHashMap<>();
    private final VBox panel = new VBox(10);
    private final List<String> selectedList;
    private final Button shovelButton;
    private Text number;
    private TextFlow sunpointFlow;

    public ButtonManager(List<String> selectedList) {
        this.selectedList = selectedList;
        this.shovelButton = new Button();
        Text emoji = new Text("☀️");
        emoji.setFill(Color.GOLD);
        emoji.setStyle("-fx-font-size: 26px; -fx-font-family: 'Segoe UI Emoji';");
        number = new Text(String.valueOf(Sun.getCollectedpoint()));
        number.setFill(Color.BLACK);
        number.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-font-family: 'Segoe UI Emoji';");
        sunpointFlow = new TextFlow(emoji, number);
        sunpointFlow.setStyle(
                "-fx-background-color: #fffacd;" +
                        "-fx-padding: 4px 0px;" +
                        "-fx-background-radius: 8px;"
        );
        panel.setPadding(new Insets(10));
        initializeButtons();
    }

    private void initializeButtons() {
        panel.getChildren().add(sunpointFlow);
        for (String name : selectedList) {
            String imagePath = getImagePath(name);
            Button btn = new Button();
            btn.setGraphic(createImageView(imagePath));
            btn.setStyle("-fx-background-color: #fff");
            btn.setOnDragDetected(event -> {
                dragAction(btn, name);
                event.consume();
            });
            buttons.put(name, btn);
            panel.getChildren().add(btn);
        }
        setupShovelButton();
    }

    private void setupShovelButton() {
        ImageView shovelView = createImageView("/Shovel.jpg");
        shovelButton.setGraphic(shovelView);
        shovelButton.setStyle("-fx-background-color: #fff");
        shovelButton.setOnDragDetected(event -> {
            dragAction(shovelButton, "shovel");
            event.consume();
        });
    }

    private void dragAction(Button btn, String name) {
        Dragboard db = btn.startDragAndDrop(TransferMode.COPY);
        ClipboardContent content = new ClipboardContent();
        content.putString(name);
        db.setContent(content);

        String dragImagePath = getImagePathDrag(name);
        Image dragImage = new Image(getClass().getResource(dragImagePath).toExternalForm());
        ImageView preview = new ImageView(dragImage);
        preview.setFitWidth(60);
        preview.setFitHeight(40);
        preview.setPreserveRatio(true);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        WritableImage smallImage = preview.snapshot(params, null);

        db.setDragView(smallImage, dragImage.getWidth() / 2, dragImage.getHeight() / 2);
    }

    public void addTo(Pane root) {
        HBox box = new HBox(panel, shovelButton);
        root.getChildren().add(box);
    }

    public void update(Map<String, Boolean> canPlaceMap, Map<String, Integer> costMap, int sunPoints) {
        for (String name : buttons.keySet()) {
            Button btn = buttons.get(name);
            boolean canPlace = canPlaceMap.get(name);
            int cost = costMap.get(name);
            if (canPlace && sunPoints >= cost) {
                btn.setDisable(false);
                btn.setStyle("-fx-opacity: 1.0; -fx-background-color: #fff;");
            } else {
                btn.setDisable(true);
                btn.setStyle("-fx-opacity: 0.4; -fx-background-color: gray;");
            }
        }
        number.setText(String.valueOf(Sun.getCollectedpoint()));

    }


    public Button getButton(String name) {
        return buttons.get(name);
    }

    private ImageView createImageView(String path) {
        Image image = new Image(getClass().getResource(path).toExternalForm());
        ImageView iv = new ImageView(image);
        iv.setFitWidth(105);
        iv.setFitHeight(60);
        return iv;
    }

    private String getImagePath(String name) {
        switch (name) {
            case "Snow Pea":
                return "/SnowPea.png";
            case "Peashooter":
                return "/com/example/plantvszombie/peashooterCard.png";
            case "Repeater":
                return "/com/example/plantvszombie/repeaterCard.png";
            case "Tall-nut":
                return "/TallNut.png";
            case "Wall-nut":
                return "/com/example/plantvszombie/wallnutCard.png";
            case "Cherry Bomb":
                return "/com/example/plantvszombie/cherrybombCard.png";
            case "jalapeno":
                return "/com/example/plantvszombie/jalapenoCard.png";
            case "Sunflower":
                return "/com/example/plantvszombie/sunflowerCard.png";
            case "Hypno":
                return "/HypnoShroomSeed.png";
            case "Puff":
                return "/PuffShroomSeed.png";
            case "Scaredy":
                return "/ScaredyShroomSeed.png";
            case "Doom":
                return "/DoomShroomSeed.png";
            case "Ice":
                return "/IceShroomSeed.png";
            case "bean":
                return "/bean.png";
            case "plantern":
                return "/PlanternSeed.png";
            case "blover":
                return "/BloverSeed.png";
            case "Grave":
                return "/GraveBusterSeed.png";
            default:
                return "/bean.png";
        }
    }

    private String getImagePathDrag(String name) {
        switch (name) {
            case "Snow Pea":
                return "/SnowPea.gif";
            case "Peashooter":
                return "/peashooter.gif";
            case "Repeater":
                return "/repeater.gif";
            case "Tall-nut":
                return "/TallNut1.gif";
            case "Wall-nut":
                return "/walnut_full_life.gif";
            case "Cherry Bomb":
                return "/newCherryBomb.gif";
            case "jalapeno":
                return "/jalapeno.gif";
            case "Sunflower":
                return "/sunflower.gif";
            case "Hypno":
                return "/HypnoShroomSleep.gif";
            case "Puff":
                return "/PuffShroom1 (10).gif";
            case "Scaredy":
                return "/Scaredy-shroom.png";
            case "Doom":
                return "/DoomShroom3.gif";
            case "Ice":
                return "/IceShroom2.gif";
            case "bean":
                return "/CoffeeBean2.gif";
            case "plantern":
                return "/Animated_Plantern.gif";
            case "blover":
                return "/75f44f529822720e5a77af436ccb0a46f31fabd6.gif";
            case "Grave":
                return "/Transparent_grave_digger.gif";
            case "shovel":
                return "/Shovel.jpg";
            default:
                return "/bean.png";
        }
    }


}

