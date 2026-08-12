package com.athlix.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class TournamentSchedulePage {

    private String tournamentTitle;

    // Constructor to receive the specific tournament name
    public TournamentSchedulePage(String tournamentTitle) {
        this.tournamentTitle = tournamentTitle;
    }

    public Node getView() {
        VBox scheduleBox = new VBox(25);
        scheduleBox.setPadding(new Insets(30));
        scheduleBox.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12;");

        // Header Title with tournament-specific symbol
        Label title = new Label("📅 Schedule for " + tournamentTitle);
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        VBox matchesList = new VBox(15);

        // Dynamically load different schedules based on the tournament title
        if (tournamentTitle != null && tournamentTitle.contains("Corporate T20")) {
            matchesList.getChildren().addAll(
                createMatchCard("Match 1 • League Stage", "15 Sept 2026, 09:00 AM", "Reliance XI", "Tata Titans", "Shivaji Park, Mumbai", "⚡ Upcoming"),
                createMatchCard("Match 2 • League Stage", "16 Sept 2026, 10:30 AM", "Infosys Strikers", "Wipro Warriors", "Shivaji Park, Mumbai", "⚡ Upcoming"),
                createMatchCard("Match 3 • Qualifier 1", "18 Sept 2026, 02:00 PM", "Top 1 Seed", "Top 2 Seed", "Shivaji Park, Mumbai", "🔒 Locked"),
                createMatchCard("Match 4 • Championship Final", "20 Sept 2026, 05:00 PM", "Winner Q1", "Winner Eliminator", "Shivaji Park, Mumbai", "🏆 Grand Finale")
            );
        } else if (tournamentTitle != null && tournamentTitle.contains("U-19 Trials")) {
            matchesList.getChildren().addAll(
                createMatchCard("Session 1 • Batting Drills", "15 Sept 2026, 08:00 AM", "Batch A (Open)", "Batch B (Open)", "NCA Grounds, Pune", "🏏 Active"),
                createMatchCard("Session 2 • Bowling Trials", "16 Sept 2026, 10:00 AM", "Fast Bowlers", "Spinners", "NCA Grounds, Pune", "⚡ Upcoming"),
                createMatchCard("Session 3 • Practice Match", "18 Sept 2026, 01:00 PM", "Team Red", "Team Blue", "NCA Grounds, Pune", "🔒 Locked")
            );
        } else {
            // Default schedule for Weekend Warriors Cup or others
            matchesList.getChildren().addAll(
                createMatchCard("Match 1 • Group Stage", "15 Sept 2026, 09:00 AM", "Mumbai Strikers", "Pune Panthers", "Local Ground, Nashik", "⚡ Upcoming"),
                createMatchCard("Match 2 • Group Stage", "16 Sept 2026, 10:30 AM", "Nashik Warriors", "Thane Challengers", "Local Ground, Nashik", "⚡ Upcoming"),
                createMatchCard("Match 3 • Semi-Final", "18 Sept 2026, 02:00 PM", "Group A Winner", "Group B Winner", "Local Ground, Nashik", "🔒 Scheduled"),
                createMatchCard("Match 4 • Final Match", "20 Sept 2026, 05:00 PM", "Winner SF 1", "Winner SF 2", "Local Ground, Nashik", "🏆 Finale")
            );
        }

        scheduleBox.getChildren().addAll(title, matchesList);

        ScrollPane scrollPane = new ScrollPane(scheduleBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f8fafc;");
        scrollPane.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");

        return scrollPane;
    }

    private VBox createMatchCard(String matchNumber, String dateTime, String team1, String team2, String venue, String status) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #f8fafc; -fx-background-radius: 10; -fx-border-color: #e2e8f0; -fx-border-radius: 10;");

        // Top Row: Match Number & Status Tag
        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label lblMatchNum = new Label(matchNumber);
        lblMatchNum.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #64748b;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label lblStatus = new Label(status);
        lblStatus.setStyle("-fx-background-color: #d1fae5; -fx-text-fill: #065f46; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 4 10; -fx-background-radius: 12;");

        topRow.getChildren().addAll(lblMatchNum, spacer, lblStatus);

        // Middle Row: Teams battling vs Date/Time
        HBox middleRow = new HBox(15);
        middleRow.setAlignment(Pos.CENTER_LEFT);

        VBox teamsBox = new VBox(4);
        Label lblTeams = new Label(team1 + "  ⚔️  " + team2);
        lblTeams.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        
        Label lblVenue = new Label("📍 " + venue + "   🕒 " + dateTime);
        lblVenue.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748b;");
        
        teamsBox.getChildren().addAll(lblTeams, lblVenue);

        middleRow.getChildren().add(teamsBox);
        card.getChildren().addAll(topRow, middleRow);

        return card;
    }
}