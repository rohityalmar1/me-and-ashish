package com.athlix.view;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.util.function.Consumer;

public class CurrentTournamentSquad {

    private String teamName;
    private Runnable onBackAction;
    private Consumer<String> onViewPlayerStatsAction;

    // Updated constructor to support View Stats routing
    public CurrentTournamentSquad(String teamName, Runnable onBackAction, Consumer<String> onViewPlayerStatsAction) {
        this.teamName = teamName;
        this.onBackAction = onBackAction;
        this.onViewPlayerStatsAction = onViewPlayerStatsAction;
    }

    public Node getView() {
        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(20, 0, 20, 0));

        // --- HEADER: Back Button & Team Name ---
        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);

        Button backBtn = new Button("❮ Back to Teams");
        backBtn.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #64748b; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 6 12; -fx-cursor: hand;");
        backBtn.setOnAction(e -> {
            if (onBackAction != null) onBackAction.run();
        });
        backBtn.setOnMouseEntered(e -> backBtn.setStyle("-fx-background-color: #e2e8f0; -fx-text-fill: #0f172a; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 6 12; -fx-cursor: hand;"));
        backBtn.setOnMouseExited(e -> backBtn.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #64748b; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 6 12; -fx-cursor: hand;"));

        Label titleText = new Label("🛡️ " + teamName + " Squad");
        titleText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        header.getChildren().addAll(backBtn, titleText);

        // --- PLAYERS GRID ---
        FlowPane playersGrid = new FlowPane();
        playersGrid.setHgap(20);
        playersGrid.setVgap(20);
        playersGrid.setAlignment(Pos.TOP_LEFT);

        // Add Mock Players
        playersGrid.getChildren().addAll(
            createPlayerCard("https://dummyimage.com/100x100/cbd5e1/0f172a.png&text=P1", "Player One", "👑 Captain / Batter"),
            createPlayerCard("https://dummyimage.com/100x100/cbd5e1/0f172a.png&text=P2", "Player Two", "🏏 Top Order Batter"),
            createPlayerCard("https://dummyimage.com/100x100/cbd5e1/0f172a.png&text=P3", "Player Three", "🧤 Wicket Keeper"),
            createPlayerCard("https://dummyimage.com/100x100/cbd5e1/0f172a.png&text=P4", "Player Four", "⚔️ All-Rounder"),
            createPlayerCard("https://dummyimage.com/100x100/cbd5e1/0f172a.png&text=P5", "Player Five", "⚾ Fast Bowler"),
            createPlayerCard("https://dummyimage.com/100x100/cbd5e1/0f172a.png&text=P6", "Player Six", "🌀 Spin Bowler")
        );

        mainContainer.getChildren().addAll(header, playersGrid);
        
        ScrollPane scrollPane = new ScrollPane(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        scrollPane.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");

        return scrollPane;
    }

    private VBox createPlayerCard(String imageUrl, String playerName, String role) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20));
        card.setPrefWidth(180);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12;");

        // Player Image (Circular)
        ImageView profileImg = new ImageView();
        try {
            Image img = new Image(imageUrl, true);
            profileImg.setImage(img);
        } catch (Exception e) {}
        profileImg.setFitWidth(80);
        profileImg.setFitHeight(80);
        profileImg.setPreserveRatio(true);
        Circle clip = new Circle(40, 40, 40);
        profileImg.setClip(clip);

        // Player Info
        Label nameLbl = new Label(playerName);
        nameLbl.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        
        Label roleLbl = new Label(role);
        roleLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748b;");

        // New "View Stats" Button
        Button viewStatsBtn = new Button("View Stats");
        viewStatsBtn.setMaxWidth(Double.MAX_VALUE);
        viewStatsBtn.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #10b981; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 6 12; -fx-cursor: hand; -fx-margin-top: 10px;");
        viewStatsBtn.setOnMouseEntered(e -> viewStatsBtn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 6 12; -fx-cursor: hand;"));
        viewStatsBtn.setOnMouseExited(e -> viewStatsBtn.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #10b981; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 6 12; -fx-cursor: hand;"));
        
        // Wire up the stat routing
        viewStatsBtn.setOnAction(e -> {
            if (onViewPlayerStatsAction != null) {
                onViewPlayerStatsAction.accept(playerName);
            }
        });

        card.getChildren().addAll(profileImg, nameLbl, roleLbl, viewStatsBtn);

        // Hover Animation
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(200), card);
        scaleIn.setToX(1.04);
        scaleIn.setToY(1.04);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(200), card);
        scaleOut.setToX(1.0);
        scaleOut.setToY(1.0);

        card.setOnMouseEntered(e -> {
            scaleIn.playFromStart();
            card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #10b981; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(16,185,129,0.2), 10, 0, 0, 4);");
        });
        card.setOnMouseExited(e -> {
            scaleOut.playFromStart();
            card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12;");
        });

        return card;
    }
}