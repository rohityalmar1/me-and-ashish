package com.athlix.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CurrentTournamentPointTable {

    public Node getView() {
        VBox mainContainer = new VBox(15);
        mainContainer.setPadding(new Insets(20, 0, 20, 0));

        // Section Title
        Label sectionTitle = new Label("📊 Tournament Point Table");
        sectionTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0f172a; -fx-padding: 0 0 10 0;");

        // Table Card Container
        VBox tableCard = new VBox();
        tableCard.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12;");
        
        // 1. Table Header
        HBox headerRow = createHeaderRow();
        tableCard.getChildren().add(headerRow);

        // 2. Table Data Rows (Mock Data)
        // format: rank, teamName, logoText, logoColorHex, played, won, lost, tied, nrr, points, isTop4
        tableCard.getChildren().addAll(
            createTeamRow(1, "Chennai Super Kings (CSK)", "CSK", "#eab308", 10, 8, 2, 0, "+1.450", 16, true),
            createTeamRow(2, "Mumbai Indians (MI)", "MI", "#3b82f6", 10, 7, 3, 0, "+1.120", 14, true),
            createTeamRow(3, "Kolkata Knight Riders (KKR)", "KKR", "#8b5cf6", 11, 6, 5, 0, "+0.850", 12, true),
            createTeamRow(4, "Royal Challengers (RCB)", "RCB", "#ef4444", 11, 6, 5, 0, "+0.340", 12, true),
            createTeamRow(5, "Sunrisers Hyderabad (SRH)", "SRH", "#f97316", 10, 5, 5, 0, "-0.120", 10, false),
            createTeamRow(6, "Delhi Capitals (DC)", "DC", "#0284c7", 10, 4, 6, 0, "-0.450", 8, false),
            createTeamRow(7, "Rajasthan Royals (RR)", "RR", "#db2777", 10, 3, 7, 0, "-0.890", 6, false),
            createTeamRow(8, "Punjab Kings (PBKS)", "PBK", "#dc2626", 10, 2, 8, 0, "-1.250", 4, false)
        );

        mainContainer.getChildren().addAll(sectionTitle, tableCard);
        return mainContainer;
    }

    private HBox createHeaderRow() {
        HBox header = new HBox();
        header.setPadding(new Insets(15, 20, 15, 20));
        header.setStyle("-fx-background-color: #f8fafc; -fx-background-radius: 12 12 0 0; -fx-border-color: #e2e8f0; -fx-border-width: 0 0 1 0;");
        header.setAlignment(Pos.CENTER_LEFT);

        // Fixed width columns for left side
        Label posLbl = createColLabel("POS", 40, Pos.CENTER);
        Label teamLbl = createColLabel("TEAM", 260, Pos.CENTER_LEFT);
        
        // The dynamic spacer pushes everything else to the far right flawlessly
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Fixed width columns for stats side
        header.getChildren().addAll(
            posLbl, teamLbl, spacer,
            createColLabel("P", 45, Pos.CENTER),
            createColLabel("W", 45, Pos.CENTER),
            createColLabel("L", 45, Pos.CENTER),
            createColLabel("T", 45, Pos.CENTER),
            createColLabel("NRR", 70, Pos.CENTER),
            createColLabel("PTS", 50, Pos.CENTER)
        );

        return header;
    }

    private HBox createTeamRow(int rank, String teamName, String logoText, String colorHex, int p, int w, int l, int t, String nrr, int pts, boolean isTop4) {
        HBox row = new HBox();
        row.setPadding(new Insets(12, 20, 12, 20));
        
        // Add a left border accent for the top 4 qualifying teams
        String borderAccent = isTop4 ? "-fx-border-color: transparent transparent #e2e8f0 #10b981; -fx-border-width: 0 0 1 4;" : "-fx-border-color: transparent transparent #e2e8f0 transparent; -fx-border-width: 0 0 1 0;";
        row.setStyle("-fx-background-color: white; " + borderAccent);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setCursor(javafx.scene.Cursor.HAND);

        // Rank (Fixed Width)
        Label rankLbl = new Label(String.valueOf(rank));
        rankLbl.setPrefWidth(40);
        rankLbl.setMinWidth(40);
        rankLbl.setMaxWidth(40);
        rankLbl.setAlignment(Pos.CENTER);
        rankLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #475569; -fx-font-size: 14px;");

        // Team Info (Fixed Width)
        HBox teamBox = new HBox(12);
        teamBox.setAlignment(Pos.CENTER_LEFT);
        teamBox.setPrefWidth(260); // Must exactly match the TEAM header width
        teamBox.setMinWidth(260);
        teamBox.setMaxWidth(260);
        
        StackPane logoPane = new StackPane();
        Circle logoCircle = new Circle(14, Color.web(colorHex));
        Label logoLbl = new Label(logoText);
        logoLbl.setStyle("-fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: bold;");
        logoPane.getChildren().addAll(logoCircle, logoLbl);
        
        Label teamNameLbl = new Label(teamName);
        teamNameLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #0f172a; -fx-font-size: 14px;");
        
        teamBox.getChildren().addAll(logoPane, teamNameLbl);

        // The dynamic spacer ensures alignment perfectly tracks the header
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Stats Columns (Fixed Widths mirroring header exactly)
        Label pLbl = createStatLabel(String.valueOf(p), 45);
        Label wLbl = createStatLabel(String.valueOf(w), 45);
        Label lLbl = createStatLabel(String.valueOf(l), 45);
        Label tLbl = createStatLabel(String.valueOf(t), 45);
        
        Label nrrLbl = createStatLabel(nrr, 70);
        if (nrr.startsWith("+")) {
            nrrLbl.setStyle("-fx-text-fill: #10b981; -fx-font-weight: bold; -fx-font-size: 13px;");
        } else {
            nrrLbl.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold; -fx-font-size: 13px;");
        }

        Label ptsLbl = createStatLabel(String.valueOf(pts), 50);
        ptsLbl.setStyle("-fx-text-fill: #0f172a; -fx-font-weight: bold; -fx-font-size: 15px;");

        row.getChildren().addAll(rankLbl, teamBox, spacer, pLbl, wLbl, lLbl, tLbl, nrrLbl, ptsLbl);

        // Hover Effect
        row.setOnMouseEntered(e -> row.setStyle("-fx-background-color: #f8fafc; " + borderAccent));
        row.setOnMouseExited(e -> row.setStyle("-fx-background-color: white; " + borderAccent));

        return row;
    }

    // Helper: Creates strictly sized Header labels
    private Label createColLabel(String text, double width, Pos alignment) {
        Label lbl = new Label(text);
        lbl.setPrefWidth(width);
        lbl.setMinWidth(width);
        lbl.setMaxWidth(width);
        lbl.setAlignment(alignment);
        lbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #64748b; -fx-font-size: 12px;");
        return lbl;
    }

    // Helper: Creates strictly sized Stat labels
    private Label createStatLabel(String text, double width) {
        Label lbl = new Label(text);
        lbl.setPrefWidth(width);
        lbl.setMinWidth(width);
        lbl.setMaxWidth(width);
        lbl.setAlignment(Pos.CENTER);
        lbl.setStyle("-fx-text-fill: #475569; -fx-font-size: 14px;");
        return lbl;
    }
}