package com.athlix.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class CurrentTournamentStats {

    private String playerName;
    private Runnable onBackAction;

    public CurrentTournamentStats(String playerName, Runnable onBackAction) {
        this.playerName = playerName;
        this.onBackAction = onBackAction;
    }

    public Node getView() {
        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(20, 0, 20, 0));

        // --- HEADER: Back Button & Title ---
        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);

        Button backBtn = new Button("❮ Back to Squad");
        backBtn.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #64748b; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 6 12; -fx-cursor: hand;");
        backBtn.setOnAction(e -> {
            if (onBackAction != null) onBackAction.run();
        });
        backBtn.setOnMouseEntered(e -> backBtn.setStyle("-fx-background-color: #e2e8f0; -fx-text-fill: #0f172a; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 6 12; -fx-cursor: hand;"));
        backBtn.setOnMouseExited(e -> backBtn.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #64748b; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 6 12; -fx-cursor: hand;"));

        Label titleText = new Label("📈 Player Statistics");
        titleText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        header.getChildren().addAll(backBtn, titleText);

        // --- PLAYER PROFILE CARD ---
        HBox profileCard = new HBox(30);
        profileCard.setPadding(new Insets(30));
        profileCard.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12;");
        profileCard.setAlignment(Pos.CENTER_LEFT);

        // Player Image
        ImageView profileImg = new ImageView();
        try { Image img = new Image("https://dummyimage.com/150x150/cbd5e1/0f172a.png&text=" + playerName.substring(0, 2).toUpperCase(), true); profileImg.setImage(img); } catch (Exception e) {}
        profileImg.setFitWidth(120);
        profileImg.setFitHeight(120);
        profileImg.setPreserveRatio(true);
        Circle clip = new Circle(60, 60, 60);
        profileImg.setClip(clip);

        // Player Info
        VBox infoBox = new VBox(8);
        infoBox.setAlignment(Pos.CENTER_LEFT);
        Label nameLbl = new Label(playerName);
        nameLbl.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        Label roleLbl = new Label("🏏 Top Order Batter  •  Right-Handed");
        roleLbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748b;");
        infoBox.getChildren().addAll(nameLbl, roleLbl);
        HBox.setHgrow(infoBox, Priority.ALWAYS);

        profileCard.getChildren().addAll(profileImg, infoBox);

        // --- STATS GRIDS ---
        HBox statsContainer = new HBox(20);
        
        // Batting Stats
        VBox battingStats = createStatsSection("Batting Statistics", new String[]{"Matches", "Innings", "Runs", "Highest Score", "Average", "Strike Rate", "100s", "50s"}, new String[]{"12", "11", "452", "112*", "45.20", "142.50", "1", "3"});
        HBox.setHgrow(battingStats, Priority.ALWAYS);

        // Bowling Stats
        VBox bowlingStats = createStatsSection("Bowling Statistics", new String[]{"Matches", "Innings", "Overs", "Wickets", "Economy", "Best Bowling", "Average", "5W"}, new String[]{"12", "5", "18.0", "6", "8.45", "3/22", "25.33", "0"});
        HBox.setHgrow(bowlingStats, Priority.ALWAYS);

        statsContainer.getChildren().addAll(battingStats, bowlingStats);

        mainContainer.getChildren().addAll(header, profileCard, statsContainer);
        
        ScrollPane scrollPane = new ScrollPane(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        scrollPane.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");

        return scrollPane;
    }

    private VBox createStatsSection(String title, String[] labels, String[] values) {
        VBox section = new VBox(15);
        section.setPadding(new Insets(25));
        section.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12;");

        Label titleLbl = new Label(title);
        titleLbl.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #0f172a; -fx-border-color: #e2e8f0; -fx-border-width: 0 0 1 0; -fx-padding: 0 0 10 0;");
        titleLbl.setMaxWidth(Double.MAX_VALUE);

        GridPane grid = new GridPane();
        grid.setHgap(30);
        grid.setVgap(15);

        for (int i = 0; i < labels.length; i++) {
            VBox statBox = new VBox(5);
            Label l = new Label(labels[i]);
            l.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748b;");
            Label v = new Label(values[i]);
            v.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #10b981;");
            statBox.getChildren().addAll(l, v);
            grid.add(statBox, i % 2, i / 2); // 2 columns
        }

        section.getChildren().addAll(titleLbl, grid);
        return section;
    }
}