package com.athlix.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class TournamentTeamPage {

    private String tournamentTitle;

    public TournamentTeamPage(String tournamentTitle) {
        this.tournamentTitle = tournamentTitle;
    }

    public Node getView() {
        VBox teamsBox = new VBox(30);
        teamsBox.setPadding(new Insets(35));
        teamsBox.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12;");

        Label title = new Label("👥 Registered Teams - " + tournamentTitle);
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        // GridPane for spacious multi-team layout
        GridPane teamsGrid = new GridPane();
        teamsGrid.setHgap(35);
        teamsGrid.setVgap(25);

        // Make columns expand equally to use the available page width
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        teamsGrid.getColumnConstraints().addAll(col1, col2);

        // --- DYNAMIC TOURNAMENT-WISE TEAMS LIST ---
        if (tournamentTitle != null && tournamentTitle.contains("Corporate T20")) {
            teamsGrid.add(createTeamCard("Reliance XI", "Captain: Rohit S.", "16 Players", "⚡ Verified"), 0, 0);
            teamsGrid.add(createTeamCard("Tata Titans", "Captain: Virat K.", "15 Players", "⚡ Verified"), 1, 0);
            teamsGrid.add(createTeamCard("Infosys Strikers", "Captain: MS Dhoni", "16 Players", "⚡ Verified"), 0, 1);
            teamsGrid.add(createTeamCard("Wipro Warriors", "Captain: Hardik P.", "14 Players", "⏳ Pending"), 1, 1);
            teamsGrid.add(createTeamCard("TCS Chargers", "Captain: Jasprit B.", "15 Players", "⚡ Verified"), 0, 2);
            teamsGrid.add(createTeamCard("Adani Blasters", "Captain: Surya K.", "16 Players", "⚡ Verified"), 1, 2);
        } else if (tournamentTitle != null && tournamentTitle.contains("U-19 Trials")) {
            teamsGrid.add(createTeamCard("Mumbai Under-19s", "Captain: Ayush M.", "16 Players", "⚡ Verified"), 0, 0);
            teamsGrid.add(createTeamCard("Pune Colts", "Captain: Omkar K.", "15 Players", "⚡ Verified"), 1, 0);
            teamsGrid.add(createTeamCard("Vidarbha Youngsters", "Captain: Faiz F.", "16 Players", "⚡ Verified"), 0, 1);
            teamsGrid.add(createTeamCard("Kolhapur Tigers", "Captain: Ruturaj G.", "14 Players", "⏳ Pending"), 1, 1);
            teamsGrid.add(createTeamCard("Nagpur Rising Stars", "Captain: Yash Rathod", "15 Players", "⚡ Verified"), 0, 2);
            teamsGrid.add(createTeamCard("Thane Prospects", "Captain: Divyansh S.", "16 Players", "⚡ Verified"), 1, 2);
        } else {
            // Default Teams for Weekend Warriors Cup or others
            teamsGrid.add(createTeamCard("Mumbai Strikers", "Captain: Shreyas I.", "16 Players", "⚡ Verified"), 0, 0);
            teamsGrid.add(createTeamCard("Pune Panthers", "Captain: Rahul T.", "15 Players", "⚡ Verified"), 1, 0);
            teamsGrid.add(createTeamCard("Nashik Warriors", "Captain: Ajinkya R.", "16 Players", "⚡ Verified"), 0, 1);
            teamsGrid.add(createTeamCard("Thane Challengers", "Captain: Prithvi S.", "14 Players", "⏳ Pending"), 1, 1);
            teamsGrid.add(createTeamCard("Nagpur Blasters", "Captain: Karan S.", "15 Players", "⚡ Verified"), 0, 2);
            teamsGrid.add(createTeamCard("Kolhapur Royals", "Captain: Ankit B.", "16 Players", "⚡ Verified"), 1, 2);
        }

        teamsBox.getChildren().addAll(title, teamsGrid);

        ScrollPane scrollPane = new ScrollPane(teamsBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f8fafc;");
        scrollPane.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");

        return scrollPane;
    }

    private VBox createTeamCard(String teamName, String captain, String squadSize, String status) {
        VBox card = new VBox(15);
        card.setPadding(new Insets(24));
        
        // --- UPDATED: Increased width to fill page space evenly ---
        card.setMaxWidth(Double.MAX_VALUE);
        card.setPrefWidth(520); 

        card.setStyle("-fx-background-color: #f8fafc; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12;");

        // Top Row: Team Name & Status
        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label lblName = new Label(teamName);
        lblName.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label lblStatus = new Label(status);
        lblStatus.setStyle("-fx-background-color: #d1fae5; -fx-text-fill: #065f46; -fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 5 12; -fx-background-radius: 12;");

        topRow.getChildren().addAll(lblName, spacer, lblStatus);

        // Bottom Row: Captain & Squad Count details
        VBox detailsBox = new VBox(6);
        Label lblCaptain = new Label("👤 " + captain);
        lblCaptain.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748b;");

        Label lblSquad = new Label("🛡️ Squad: " + squadSize);
        lblSquad.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748b;");

        detailsBox.getChildren().addAll(lblCaptain, lblSquad);

        card.getChildren().addAll(topRow, detailsBox);
        return card;
    }
}