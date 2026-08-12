package com.athlix.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CurrentTournamentPCap {

    public Node getView() {
        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(20, 0, 20, 0));

        // Section Title
        Label sectionTitle = new Label("🟣 Purple Cap Leaderboard (Most Wickets)");
        sectionTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        // 1. HERO CARD (Current Purple Cap Holder)
        HBox heroCard = createHeroCard("Jasprit Bumrah", "Mumbai Indians (MI)", "20", "14", "6.85", "5/21", "https://dummyimage.com/120x120/cbd5e1/0f172a.png&text=JB");

        // 2. LEADERBOARD TABLE
        VBox tableCard = new VBox();
        tableCard.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12;");
        
        // Table Header
        tableCard.getChildren().add(createHeaderRow());

        // Table Data Rows
        tableCard.getChildren().addAll(
            createPlayerRow(1, "Jasprit Bumrah", "MI", "#3b82f6", 14, 14, 20, "5/21", "16.50", "6.85", true),
            createPlayerRow(2, "Sunil Narine", "KKR", "#8b5cf6", 14, 14, 18, "3/22", "20.11", "7.15", false),
            createPlayerRow(3, "Yuzvendra Chahal", "RR", "#db2777", 15, 15, 17, "4/33", "22.45", "8.20", false),
            createPlayerRow(4, "Trent Boult", "RR", "#db2777", 14, 14, 16, "3/25", "24.10", "7.95", false),
            createPlayerRow(5, "Pat Cummins", "SRH", "#f97316", 14, 14, 15, "3/43", "28.33", "8.80", false),
            createPlayerRow(6, "Matheesha Pathirana", "CSK", "#eab308", 10, 10, 14, "4/28", "18.21", "7.55", false)
        );

        mainContainer.getChildren().addAll(sectionTitle, heroCard, tableCard);
        return mainContainer;
    }

    private HBox createHeroCard(String name, String team, String wickets, String matches, String econ, String bestBowling, String imageUrl) {
        HBox card = new HBox(20);
        card.setPadding(new Insets(25));
        // Purple gradient background for the cap holder
        card.setStyle("-fx-background-color: linear-gradient(to right, #faf5ff, #f3e8ff); -fx-background-radius: 12; -fx-border-color: #d8b4fe; -fx-border-radius: 12; -fx-border-width: 2;");
        card.setAlignment(Pos.CENTER_LEFT);

        // Player Profile Image
        ImageView profileImg = new ImageView();
        try { Image img = new Image(imageUrl, true); profileImg.setImage(img); } catch (Exception e) {}
        profileImg.setFitWidth(100);
        profileImg.setFitHeight(100);
        profileImg.setPreserveRatio(true);
        Circle clip = new Circle(50, 50, 50);
        profileImg.setClip(clip);

        // Player Details
        VBox detailsBox = new VBox(8);
        detailsBox.setAlignment(Pos.CENTER_LEFT);
        
        HBox badgeBox = new HBox(8);
        badgeBox.setAlignment(Pos.CENTER_LEFT);
        
        // Purple Cap Icon placeholder
        ImageView capIcon = new ImageView();
        try { Image img = new Image("https://dummyimage.com/30x30/9333ea/ffffff.png&text=CAP", true); capIcon.setImage(img); } catch (Exception e) {}
        capIcon.setFitWidth(24);
        capIcon.setFitHeight(24);
        Circle capClip = new Circle(12, 12, 12);
        capIcon.setClip(capClip);
        
        Label badgeLbl = new Label("CURRENT PURPLE CAP HOLDER");
        badgeLbl.setStyle("-fx-text-fill: #9333ea; -fx-font-size: 12px; -fx-font-weight: bold;");
        badgeBox.getChildren().addAll(capIcon, badgeLbl);

        Label nameLbl = new Label(name);
        nameLbl.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        Label teamLbl = new Label(team);
        teamLbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748b; -fx-font-weight: bold;");
        detailsBox.getChildren().addAll(badgeBox, nameLbl, teamLbl);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Stats Highlight Box
        HBox statsBox = new HBox(30);
        statsBox.setAlignment(Pos.CENTER);
        statsBox.setPadding(new Insets(15, 25, 15, 25));
        statsBox.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(147,51,234,0.15), 10, 0, 0, 5);");

        statsBox.getChildren().addAll(
            createHeroStat("WICKETS", wickets, "#9333ea", 28),
            createHeroStat("MATCHES", matches, "#0f172a", 20),
            createHeroStat("BBI", bestBowling, "#0f172a", 20),
            createHeroStat("ECON", econ, "#0f172a", 20)
        );

        card.getChildren().addAll(profileImg, detailsBox, spacer, statsBox);
        return card;
    }

    private VBox createHeroStat(String label, String value, String valueColor, int valueSize) {
        VBox box = new VBox(2);
        box.setAlignment(Pos.CENTER);
        Label lbl = new Label(label);
        lbl.setStyle("-fx-font-size: 11px; -fx-text-fill: #94a3b8; -fx-font-weight: bold;");
        Label val = new Label(value);
        val.setStyle("-fx-font-size: " + valueSize + "px; -fx-font-weight: bold; -fx-text-fill: " + valueColor + ";");
        box.getChildren().addAll(lbl, val);
        return box;
    }

    private HBox createHeaderRow() {
        HBox header = new HBox();
        header.setPadding(new Insets(15, 20, 15, 20));
        header.setStyle("-fx-background-color: #f8fafc; -fx-background-radius: 12 12 0 0; -fx-border-color: #e2e8f0; -fx-border-width: 0 0 1 0;");
        header.setAlignment(Pos.CENTER_LEFT);

        Label posLbl = createColLabel("POS", 40, Pos.CENTER);
        Label playerLbl = createColLabel("PLAYER", 240, Pos.CENTER_LEFT);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        header.getChildren().addAll(
            posLbl, playerLbl, spacer,
            createColLabel("M", 45, Pos.CENTER),
            createColLabel("I", 45, Pos.CENTER),
            createColLabel("WKT", 60, Pos.CENTER),
            createColLabel("BBI", 60, Pos.CENTER),
            createColLabel("AVG", 60, Pos.CENTER),
            createColLabel("ECON", 70, Pos.CENTER)
        );
        return header;
    }

    private HBox createPlayerRow(int rank, String playerName, String teamShortName, String teamColorHex, int m, int i, int wkt, String bbi, String avg, String econ, boolean isLeader) {
        HBox row = new HBox();
        row.setPadding(new Insets(12, 20, 12, 20));
        
        String borderAccent = isLeader ? "-fx-border-color: transparent transparent #e2e8f0 #9333ea; -fx-border-width: 0 0 1 4;" : "-fx-border-color: transparent transparent #e2e8f0 transparent; -fx-border-width: 0 0 1 0;";
        String bgColor = isLeader ? "#fcfaff" : "white";
        row.setStyle("-fx-background-color: " + bgColor + "; " + borderAccent);
        row.setAlignment(Pos.CENTER_LEFT);

        // Rank
        Label rankLbl = new Label(String.valueOf(rank));
        rankLbl.setPrefWidth(40);
        rankLbl.setMinWidth(40);
        rankLbl.setAlignment(Pos.CENTER);
        rankLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #475569; -fx-font-size: 14px;");

        // Player Info
        HBox playerBox = new HBox(12);
        playerBox.setAlignment(Pos.CENTER_LEFT);
        playerBox.setPrefWidth(240); 
        playerBox.setMinWidth(240);
        
        StackPane teamBadge = new StackPane();
        Circle teamCircle = new Circle(12, Color.web(teamColorHex));
        Label teamLbl = new Label(teamShortName);
        teamLbl.setStyle("-fx-text-fill: white; -fx-font-size: 9px; -fx-font-weight: bold;");
        teamBadge.getChildren().addAll(teamCircle, teamLbl);
        
        Label playerNameLbl = new Label(playerName);
        playerNameLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #0f172a; -fx-font-size: 14px;");
        
        playerBox.getChildren().addAll(teamBadge, playerNameLbl);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Stats Columns
        Label mLbl = createStatLabel(String.valueOf(m), 45);
        Label iLbl = createStatLabel(String.valueOf(i), 45);
        
        Label wktLbl = createStatLabel(String.valueOf(wkt), 60);
        wktLbl.setStyle("-fx-text-fill: " + (isLeader ? "#9333ea" : "#0f172a") + "; -fx-font-weight: bold; -fx-font-size: 15px;");
        
        Label bbiLbl = createStatLabel(bbi, 60);
        Label avgLbl = createStatLabel(avg, 60);
        Label econLbl = createStatLabel(econ, 70);

        row.getChildren().addAll(rankLbl, playerBox, spacer, mLbl, iLbl, wktLbl, bbiLbl, avgLbl, econLbl);

        // Hover Effect
        row.setOnMouseEntered(e -> row.setStyle("-fx-background-color: #f1f5f9; " + borderAccent));
        row.setOnMouseExited(e -> row.setStyle("-fx-background-color: " + bgColor + "; " + borderAccent));

        return row;
    }

    private Label createColLabel(String text, double width, Pos alignment) {
        Label lbl = new Label(text);
        lbl.setPrefWidth(width);
        lbl.setMinWidth(width);
        lbl.setAlignment(alignment);
        lbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #64748b; -fx-font-size: 12px;");
        return lbl;
    }

    private Label createStatLabel(String text, double width) {
        Label lbl = new Label(text);
        lbl.setPrefWidth(width);
        lbl.setMinWidth(width);
        lbl.setAlignment(Pos.CENTER);
        lbl.setStyle("-fx-text-fill: #475569; -fx-font-size: 14px;");
        return lbl;
    }
}