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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class MyTournamentHubButtonViewSquad {

    private String teamName;
    private Runnable onBackAction;

    public MyTournamentHubButtonViewSquad(String teamName, Runnable onBackAction) {
        this.teamName = teamName;
        this.onBackAction = onBackAction;
    }

    public Node getView() {
        VBox mainContainer = new VBox(25);
        mainContainer.setPadding(new Insets(25, 25, 80, 25));
        mainContainer.setStyle("-fx-background-color: #f8fafc;");

        // --- 1. HEADER SECTION ---
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);

        Button backBtn = new Button("❮  Back to Hub");
        backBtn.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #10b981; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 8 18; -fx-background-radius: 20; -fx-border-color: #e2e8f0; -fx-border-radius: 20; -fx-cursor: hand;");
        backBtn.setOnAction(e -> {
            if (onBackAction != null) onBackAction.run();
        });

        Label headerTitle = new Label("🛡️ " + teamName + " - Full Squad");
        headerTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        header.getChildren().addAll(backBtn, headerTitle);

        // --- 2. SQUAD GRID ---
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col1, col2);

        // Add Players to Grid
        grid.add(createPlayerCard("Aarav Sharma", "Captain / Top Order Batter", "C", "https://dummyimage.com/100x100/f59e0b/ffffff.png&text=AS", "#f59e0b"), 0, 0);
        grid.add(createPlayerCard("Vikram Deshmukh", "Vice Captain / All-Rounder", "VC", "https://dummyimage.com/100x100/3b82f6/ffffff.png&text=VD", "#3b82f6"), 1, 0);
        
        grid.add(createPlayerCard("Sameer Khan", "Wicket Keeper / Batter", "WK", "https://dummyimage.com/100x100/10b981/ffffff.png&text=SK", "#10b981"), 0, 1);
        grid.add(createPlayerCard("Rahul Joshi", "Middle Order Batter", "", "https://dummyimage.com/100x100/64748b/ffffff.png&text=RJ", "#e2e8f0"), 1, 1);
        
        grid.add(createPlayerCard("Kunal Verma", "Top Order Batter", "", "https://dummyimage.com/100x100/64748b/ffffff.png&text=KV", "#e2e8f0"), 0, 2);
        grid.add(createPlayerCard("Aditya Shinde", "Spin Bowler", "", "https://dummyimage.com/100x100/64748b/ffffff.png&text=AS", "#e2e8f0"), 1, 2);
        
        grid.add(createPlayerCard("Rohan Mehta", "Fast Bowler", "", "https://dummyimage.com/100x100/64748b/ffffff.png&text=RM", "#e2e8f0"), 0, 3);
        grid.add(createPlayerCard("Nikhil Rao", "Fast Bowler", "", "https://dummyimage.com/100x100/64748b/ffffff.png&text=NR", "#e2e8f0"), 1, 3);
        
        grid.add(createPlayerCard("Ishaan Patil", "All-Rounder", "", "https://dummyimage.com/100x100/64748b/ffffff.png&text=IP", "#e2e8f0"), 0, 4);
        grid.add(createPlayerCard("Dev Kumar", "Bench / Reserve", "", "https://dummyimage.com/100x100/cbd5e1/ffffff.png&text=DK", "#e2e8f0"), 1, 4);

        mainContainer.getChildren().addAll(header, grid);

        ScrollPane scrollPane = new ScrollPane(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f8fafc;");
        scrollPane.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");

        return scrollPane;
    }

    private HBox createPlayerCard(String name, String role, String tag, String avatarUrl, String accentColor) {
        HBox card = new HBox(15);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(15, 20, 15, 20));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: " + accentColor + " #e2e8f0 #e2e8f0 #e2e8f0; -fx-border-width: 0 0 0 4; -fx-border-radius: 12;");
        GridPane.setHgrow(card, Priority.ALWAYS);

        // Player Avatar
        ImageView avatar = new ImageView();
        try { Image img = new Image(avatarUrl, true); avatar.setImage(img); } catch (Exception e) {}
        avatar.setFitWidth(56);
        avatar.setFitHeight(56);
        avatar.setPreserveRatio(true);
        avatar.setClip(new Circle(28, 28, 28));

        // Info Box
        VBox infoBox = new VBox(4);
        infoBox.setAlignment(Pos.CENTER_LEFT);

        HBox nameRow = new HBox(8);
        nameRow.setAlignment(Pos.CENTER_LEFT);
        Label nameLbl = new Label(name);
        nameLbl.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        nameRow.getChildren().add(nameLbl);

        // Add Tag (C, VC, WK) if present
        if (tag != null && !tag.isEmpty()) {
            Label tagLbl = new Label(tag);
            tagLbl.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #475569; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 3 6; -fx-background-radius: 4;");
            nameRow.getChildren().add(tagLbl);
        }

        Label roleLbl = new Label(role);
        roleLbl.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748b;");

        infoBox.getChildren().addAll(nameRow, roleLbl);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Quick Stats Button (Decorative)
        Button statsBtn = new Button("Stats");
        statsBtn.setStyle("-fx-background-color: #f8fafc; -fx-text-fill: #10b981; -fx-font-weight: bold; -fx-font-size: 12px; -fx-background-radius: 6; -fx-border-color: #e2e8f0; -fx-border-radius: 6; -fx-cursor: hand;");

        card.getChildren().addAll(avatar, infoBox, spacer, statsBtn);

        // Hover Effect
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(150), card);
        scaleIn.setToX(1.02); scaleIn.setToY(1.02);
        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(150), card);
        scaleOut.setToX(1.0); scaleOut.setToY(1.0);

        card.setOnMouseEntered(e -> {
            scaleIn.playFromStart();
            card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: " + accentColor + " #e2e8f0 #e2e8f0 #e2e8f0; -fx-border-width: 0 0 0 4; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.06), 8, 0, 0, 4);");
            statsBtn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-background-radius: 6; -fx-border-color: #10b981; -fx-border-radius: 6; -fx-cursor: hand;");
        });
        card.setOnMouseExited(e -> {
            scaleOut.playFromStart();
            card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: " + accentColor + " #e2e8f0 #e2e8f0 #e2e8f0; -fx-border-width: 0 0 0 4; -fx-border-radius: 12;");
            statsBtn.setStyle("-fx-background-color: #f8fafc; -fx-text-fill: #10b981; -fx-font-weight: bold; -fx-font-size: 12px; -fx-background-radius: 6; -fx-border-color: #e2e8f0; -fx-border-radius: 6; -fx-cursor: hand;");
        });

        return card;
    }
}