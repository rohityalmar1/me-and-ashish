package com.athlix.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class TournamentLiveMatch {

    private Runnable onBackAction;

    public TournamentLiveMatch(Runnable onBackAction) {
        this.onBackAction = onBackAction;
    }

    public Node getView() {
        VBox mainContainer = new VBox(25);
        mainContainer.setPadding(new Insets(30));
        mainContainer.setStyle("-fx-background-color: #f8fafc;");

        // Header Section with Back Button and Title
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);

        Button backBtn = new Button("❮  Back");
        backBtn.setStyle(
            "-fx-background-color: #ffffff; " +
            "-fx-text-fill: #10b981; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 14px; " +
            "-fx-padding: 8 18; " +
            "-fx-background-radius: 20; " +
            "-fx-border-color: #e2e8f0; " +
            "-fx-border-radius: 20; " +
            "-fx-cursor: hand;"
        );
        backBtn.setOnAction(e -> {
            if (onBackAction != null) onBackAction.run();
        });

        Label titleText = new Label("🔴 All Live Matches & Ongoing Games");
        titleText.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        header.getChildren().addAll(backBtn, titleText);

        // Matches List Container
        VBox matchesList = new VBox(20);
        matchesList.getChildren().addAll(
            createDetailedLiveMatchCard(
                "Mumbai Indians (MI)", "165/6 (20.0 Ov)", 
                "Chennai Super Kings (CSK)", "100/3 (16.0 Ov)", 
                "2nd Innings • 16.0 Overs", 
                "MI won toss & elected to bat. CSK needs 66 runs in 24 balls.", 
                "https://dummyimage.com/60x60/3b82f6/ffffff.png&text=MI", 
                "https://dummyimage.com/60x60/eab308/ffffff.png&text=CSK",
                "Wankhede Stadium, Mumbai"
            ),
            createDetailedLiveMatchCard(
                "Royal Challengers (RCB)", "142/4 (15.2 Ov)", 
                "Kolkata Knight Riders (KKR)", "139/8 (20.0 Ov)", 
                "1st Innings • 15.2 Overs", 
                "RCB powering through the middle overs with explosive boundaries.", 
                "https://dummyimage.com/60x60/ef4444/ffffff.png&text=RCB", 
                "https://dummyimage.com/60x60/8b5cf6/ffffff.png&text=KKR",
                "M. Chinnaswamy Stadium, Bengaluru"
            ),
            createDetailedLiveMatchCard(
                "Delhi Capitals (DC)", "88/2 (9.1 Ov)", 
                "Rajasthan Royals (RR)", "--/-- (0.0 Ov)", 
                "1st Innings • 9.1 Overs", 
                "DC steady in the Powerplay. Strategic timeout called.", 
                "https://dummyimage.com/60x60/0284c7/ffffff.png&text=DC", 
                "https://dummyimage.com/60x60/db2777/ffffff.png&text=RR",
                "Arun Jaitley Stadium, Delhi"
            )
        );

        mainContainer.getChildren().addAll(header, matchesList);

        ScrollPane scrollPane = new ScrollPane(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f8fafc;");
        scrollPane.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");

        return scrollPane;
    }

    private VBox createDetailedLiveMatchCard(String team1, String score1, String team2, String score2, String status, String commentary, String logoUrl1, String logoUrl2, String venue) {
        VBox card = new VBox(15);
        card.setPadding(new Insets(24));
        card.setStyle("-fx-background-color: linear-gradient(to right, #0f172a, #1e3a8a); -fx-background-radius: 12;");
        
        HBox liveHeader = new HBox(15);
        liveHeader.setAlignment(Pos.CENTER_LEFT);
        
        Label liveBadge = new Label("🔴 LIVE MATCH");
        liveBadge.setStyle("-fx-text-fill: #ef4444; -fx-font-size: 12px; -fx-font-weight: bold;");
        
        Label venueLbl = new Label("📍 " + venue);
        venueLbl.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 12px;");

        Region liveSpacer = new Region();
        HBox.setHgrow(liveSpacer, Priority.ALWAYS);
        
        Label matchStatus = new Label(status);
        matchStatus.setStyle("-fx-text-fill: #cbd5e1; -fx-font-size: 12px; -fx-font-weight: bold;");
        
        liveHeader.getChildren().addAll(liveBadge, venueLbl, liveSpacer, matchStatus);

        HBox scoreBox = new HBox(50);
        scoreBox.setAlignment(Pos.CENTER);
        
        VBox team1Box = createTeamScoreBlockWithImage(logoUrl1, team1, score1);
        Label vsLabel = new Label("VS");
        vsLabel.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 18px; -fx-font-weight: bold;");
        VBox team2Box = createTeamScoreBlockWithImage(logoUrl2, team2, score2);
        
        scoreBox.getChildren().addAll(team1Box, vsLabel, team2Box);

        Label commentaryLbl = new Label("💬 " + commentary);
        commentaryLbl.setStyle("-fx-text-fill: #34d399; -fx-font-size: 13px; -fx-font-weight: bold;");

        Button viewLiveBtn = new Button("View Complete Scorecard →");
        viewLiveBtn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 10 20; -fx-cursor: hand;");

        card.getChildren().addAll(liveHeader, scoreBox, commentaryLbl, viewLiveBtn);
        return card;
    }

    private VBox createTeamScoreBlockWithImage(String logoUrl, String teamName, String score) {
        VBox box = new VBox(8);
        box.setAlignment(Pos.CENTER);
        
        ImageView logoView = new ImageView();
        try {
            Image img = new Image(logoUrl, true);
            logoView.setImage(img);
        } catch (Exception e) {
            System.err.println("Could not load team logo.");
        }
        logoView.setFitWidth(50);
        logoView.setFitHeight(50);
        logoView.setPreserveRatio(true);
        
        Circle clip = new Circle(25, 25, 25);
        logoView.setClip(clip);
        
        Label t = new Label(teamName);
        t.setStyle("-fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold;");
        
        Label s = new Label(score);
        s.setStyle("-fx-text-fill: #34d399; -fx-font-size: 22px; -fx-font-weight: bold;");
        
        box.getChildren().addAll(logoView, t, s);
        return box;
    }
}