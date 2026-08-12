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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class MyTournamentHubButton {

    private String tournamentName;
    private Runnable onBackAction;
    private StackPane rootContainer;
    private ScrollPane mainScrollPane;

    public MyTournamentHubButton(String tournamentName, Runnable onBackAction) {
        this.tournamentName = tournamentName;
        this.onBackAction = onBackAction;
    }

    public Node getView() {
        if (rootContainer == null) {
            rootContainer = new StackPane();
            mainScrollPane = buildMainContent();
            rootContainer.getChildren().add(mainScrollPane);
        }
        return rootContainer;
    }

    private ScrollPane buildMainContent() {
        VBox mainContainer = new VBox(25);
        mainContainer.setPadding(new Insets(25, 25, 80, 25));
        mainContainer.setStyle("-fx-background-color: #f8fafc;");

        // --- 1. HEADER SECTION ---
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);

        Button backBtn = new Button("❮  Back to Dashboard");
        backBtn.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #10b981; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 8 18; -fx-background-radius: 20; -fx-border-color: #e2e8f0; -fx-border-radius: 20; -fx-cursor: hand;");
        backBtn.setOnAction(e -> {
            if (onBackAction != null) onBackAction.run();
        });

        Label headerTitle = new Label("🏟️ Tournament Hub: " + tournamentName);
        headerTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        header.getChildren().addAll(backBtn, headerTitle);

        // --- 2. MY TEAM HERO CARD ---
        VBox teamHeroCard = createTeamHeroCard(
            "Mumbai Masters", 
            "Player / Top Order Batter", 
            "https://dummyimage.com/100x100/3b82f6/ffffff.png&text=MM",
            "#1e3a8a", "#3b82f6" // Dark blue to light blue gradient
        );

        // --- 3. HUB GRID (Next Match & Team Stats) ---
        GridPane hubGrid = new GridPane();
        hubGrid.setHgap(20);
        hubGrid.setVgap(20);

        // Next Match Card (Left)
        VBox nextMatchCard = createNextMatchCard(
            "Pune Strikers", 
            "Tomorrow, 4:00 PM", 
            "Shivaji Park, Mumbai",
            "https://dummyimage.com/60x60/10b981/ffffff.png&text=PS"
        );
        GridPane.setHgrow(nextMatchCard, Priority.ALWAYS);
        hubGrid.add(nextMatchCard, 0, 0);

        // Proper Team Standings (Right)
        VBox statsCard = createTeamStatsCard("1st", "10", "+1.245", "6", "5", "1");
        GridPane.setHgrow(statsCard, Priority.ALWAYS);
        hubGrid.add(statsCard, 1, 0);

        // --- 4. MULTIPLE MATCHES PLAYED SECTION ---
        VBox matchesSection = new VBox(15);
        Label matchesTitle = new Label("🏏 My Team's Recent Matches");
        matchesTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0f172a; -fx-padding: 10 0 0 0;");
        
        VBox matchesList = new VBox(15);
        matchesList.getChildren().addAll(
            createMatchCard(
                "LEAGUE MATCH 11 • 10 May 2026",
                "Mumbai Masters (MM)", "210/3 (20.0 Ov)",
                "Pune Strikers (PS)", "180/6 (20.0 Ov)",
                "Mumbai Masters won by 30 runs",
                "https://dummyimage.com/60x60/3b82f6/ffffff.png&text=MM",
                "https://dummyimage.com/60x60/10b981/ffffff.png&text=PS",
                "#3b82f6", true
            ),
            createMatchCard(
                "LEAGUE MATCH 8 • 04 May 2026",
                "Mumbai Masters (MM)", "195/6 (20.0 Ov)",
                "Royal Titans (RT)", "172/8 (20.0 Ov)",
                "Mumbai Masters won by 23 runs",
                "https://dummyimage.com/60x60/3b82f6/ffffff.png&text=MM",
                "https://dummyimage.com/60x60/8b5cf6/ffffff.png&text=RT",
                "#3b82f6", true
            ),
            createMatchCard(
                "LEAGUE MATCH 5 • 28 Apr 2026",
                "Mumbai Masters (MM)", "160/8 (20.0 Ov)",
                "Coastal Kings (CK)", "164/4 (18.2 Ov)",
                "Coastal Kings won by 6 wickets",
                "https://dummyimage.com/60x60/3b82f6/ffffff.png&text=MM",
                "https://dummyimage.com/60x60/06b6d4/ffffff.png&text=CK",
                "#06b6d4", false
            )
        );
        matchesSection.getChildren().addAll(matchesTitle, matchesList);

        // --- 5. ASSEMBLE ---
        mainContainer.getChildren().addAll(header, teamHeroCard, hubGrid, matchesSection);

        ScrollPane scrollPane = new ScrollPane(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f8fafc;");
        scrollPane.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");

        return scrollPane;
    }

    private VBox createTeamHeroCard(String teamName, String role, String logoUrl, String colorStart, String colorEnd) {
        VBox card = new VBox(15);
        card.setPadding(new Insets(30));
        card.setStyle("-fx-background-color: linear-gradient(to right, " + colorStart + ", " + colorEnd + "); -fx-background-radius: 16; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        HBox contentBox = new HBox(25);
        contentBox.setAlignment(Pos.CENTER_LEFT);

        ImageView logo = new ImageView();
        try { Image img = new Image(logoUrl, true); logo.setImage(img); } catch (Exception e) {}
        logo.setFitWidth(90); 
        logo.setFitHeight(90);
        logo.setClip(new Circle(45, 45, 45));
        logo.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 2);");

        VBox textBox = new VBox(5);
        Label playingForLbl = new Label("YOUR TEAM");
        playingForLbl.setStyle("-fx-text-fill: #93c5fd; -fx-font-size: 13px; -fx-font-weight: bold; -fx-letter-spacing: 1px;");
        
        Label tName = new Label(teamName);
        tName.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        Label roleLbl = new Label("👤 Role: " + role);
        roleLbl.setStyle("-fx-font-size: 15px; -fx-text-fill: #e0f2fe; -fx-font-weight: bold; -fx-background-color: rgba(255,255,255,0.15); -fx-padding: 4 10; -fx-background-radius: 6;");
        
        textBox.getChildren().addAll(playingForLbl, tName, roleLbl);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button viewSquadBtn = new Button("View Full Squad ➔");
        viewSquadBtn.setStyle("-fx-background-color: white; -fx-text-fill: #1e40af; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 8; -fx-cursor: hand;");
        viewSquadBtn.setOnMouseEntered(e -> viewSquadBtn.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #1e3a8a; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 8; -fx-cursor: hand;"));
        viewSquadBtn.setOnMouseExited(e -> viewSquadBtn.setStyle("-fx-background-color: white; -fx-text-fill: #1e40af; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 8; -fx-cursor: hand;"));

        // ACTION: ROUTE TO SQUAD VIEW
        viewSquadBtn.setOnAction(e -> {
            MyTournamentHubButtonViewSquad squadPage = new MyTournamentHubButtonViewSquad(teamName, () -> {
                // Back action restores the Hub layout
                rootContainer.getChildren().setAll(mainScrollPane);
            });
            rootContainer.getChildren().setAll(squadPage.getView());
        });

        contentBox.getChildren().addAll(logo, textBox, spacer, viewSquadBtn);
        card.getChildren().add(contentBox);

        return card;
    }

    private VBox createNextMatchCard(String oppTeam, String time, String venue, String oppLogoUrl) {
        VBox card = new VBox(15);
        card.setPadding(new Insets(25));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12;");

        Label title = new Label("⚡ Your Next Match");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        HBox matchDetails = new HBox(15);
        matchDetails.setAlignment(Pos.CENTER_LEFT);

        ImageView oppLogo = new ImageView();
        try { Image img = new Image(oppLogoUrl, true); oppLogo.setImage(img); } catch (Exception e) {}
        oppLogo.setFitWidth(50); oppLogo.setFitHeight(50);
        oppLogo.setClip(new Circle(25, 25, 25));

        VBox textDetails = new VBox(3);
        Label vsLbl = new Label("vs " + oppTeam);
        vsLbl.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #ef4444;");
        Label timeLbl = new Label("🕒 " + time);
        timeLbl.setStyle("-fx-font-size: 13px; -fx-text-fill: #475569; -fx-font-weight: bold;");
        Label venueLbl = new Label("📍 " + venue);
        venueLbl.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748b;");
        
        textDetails.getChildren().addAll(vsLbl, timeLbl, venueLbl);
        matchDetails.getChildren().addAll(oppLogo, textDetails);

        Button matchCenterBtn = new Button("Match Center");
        matchCenterBtn.setMaxWidth(Double.MAX_VALUE);
        matchCenterBtn.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #0f172a; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 10; -fx-cursor: hand;");
        
        card.getChildren().addAll(title, matchDetails, matchCenterBtn);
        addHoverAnimation(card);
        return card;
    }

    private VBox createTeamStatsCard(String pos, String points, String nrr, String played, String won, String lost) {
        VBox card = new VBox(20);
        card.setPadding(new Insets(25));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12;");

        Label title = new Label("📊 Team Standings");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        // Top Row: Position, Points, NRR
        HBox statsRow1 = new HBox(15);
        statsRow1.getChildren().addAll(
            createMiniStat("Position", "#" + pos, "#3b82f6"),
            createMiniStat("Points", points, "#10b981"),
            createMiniStat("NRR", nrr, "#8b5cf6")
        );

        // Bottom Row: Played, Won, Lost
        HBox statsRow2 = new HBox(15);
        statsRow2.getChildren().addAll(
            createMiniStat("Played", played, "#475569"),
            createMiniStat("Won", won, "#059669"),
            createMiniStat("Lost", lost, "#ef4444")
        );

        card.getChildren().addAll(title, statsRow1, statsRow2);
        addHoverAnimation(card);
        return card;
    }

    private VBox createMiniStat(String label, String value, String valueColor) {
        VBox box = new VBox(2);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(12));
        box.setStyle("-fx-background-color: #f8fafc; -fx-background-radius: 8; -fx-border-color: #e2e8f0; -fx-border-radius: 8;");
        HBox.setHgrow(box, Priority.ALWAYS);

        Label l = new Label(label);
        l.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748b; -fx-font-weight: bold;");
        Label v = new Label(value);
        v.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: " + valueColor + ";");

        box.getChildren().addAll(l, v);
        return box;
    }

    private VBox createMatchCard(String matchStage, String team1, String score1, String team2, String score2, String resultSummary, String logoUrl1, String logoUrl2, String accentColor, boolean isWin) {
        VBox card = new VBox(14);
        card.setPadding(new Insets(18, 22, 18, 22));
        card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12; -fx-border-color: " + accentColor + " #e2e8f0 #e2e8f0 #e2e8f0; -fx-border-width: 3 1 1 1; -fx-border-radius: 12;");

        // Top Header
        HBox topHeader = new HBox();
        topHeader.setAlignment(Pos.CENTER_LEFT);

        Label stageBadge = new Label("🏆 " + matchStage);
        stageBadge.setStyle("-fx-text-fill: #0284c7; -fx-font-size: 12px; -fx-font-weight: bold;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label statusBadge = new Label("RESULT");
        statusBadge.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #475569; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 3 8; -fx-background-radius: 6;");

        topHeader.getChildren().addAll(stageBadge, spacer, statusBadge);

        // Scores Box
        HBox scoreBox = new HBox(40);
        scoreBox.setAlignment(Pos.CENTER);

        VBox team1Box = createTeamScoreBlock(logoUrl1, team1, score1);
        Label vsLabel = new Label("VS");
        vsLabel.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 14px; -fx-font-weight: bold;");
        VBox team2Box = createTeamScoreBlock(logoUrl2, team2, score2);

        scoreBox.getChildren().addAll(team1Box, vsLabel, team2Box);

        // Result Summary Banner (Green for Win, Red for Loss)
        HBox resultBox = new HBox();
        resultBox.setAlignment(Pos.CENTER_LEFT);
        resultBox.setPadding(new Insets(8, 14, 8, 14));
        if (isWin) {
            resultBox.setStyle("-fx-background-color: #ecfdf5; -fx-background-radius: 8; -fx-border-color: #a7f3d0; -fx-border-radius: 8;");
        } else {
            resultBox.setStyle("-fx-background-color: #fef2f2; -fx-background-radius: 8; -fx-border-color: #fca5a5; -fx-border-radius: 8;");
        }

        Label resultLbl = new Label((isWin ? "🎉 " : "❌ ") + resultSummary);
        resultLbl.setStyle("-fx-text-fill: " + (isWin ? "#065f46" : "#991b1b") + "; -fx-font-size: 13px; -fx-font-weight: bold;");
        resultBox.getChildren().add(resultLbl);

        // View Scorecard Button
        Button viewMatchBtn = new Button("View Match Scorecard →");
        viewMatchBtn.setStyle("-fx-background-color: #f8fafc; -fx-text-fill: #0f172a; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 6; -fx-border-color: #cbd5e1; -fx-border-radius: 6; -fx-padding: 7 14; -fx-cursor: hand;");
        viewMatchBtn.setOnMouseEntered(e -> viewMatchBtn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 6; -fx-border-color: #10b981; -fx-border-radius: 6; -fx-padding: 7 14; -fx-cursor: hand;"));
        viewMatchBtn.setOnMouseExited(e -> viewMatchBtn.setStyle("-fx-background-color: #f8fafc; -fx-text-fill: #0f172a; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 6; -fx-border-color: #cbd5e1; -fx-border-radius: 6; -fx-padding: 7 14; -fx-cursor: hand;"));

        card.getChildren().addAll(topHeader, scoreBox, resultBox, viewMatchBtn);

        // Hover Effect
        addHoverAnimation(card);
        return card;
    }

    private VBox createTeamScoreBlock(String logoUrl, String teamName, String score) {
        VBox box = new VBox(5);
        box.setAlignment(Pos.CENTER);

        ImageView logoView = new ImageView();
        try { Image img = new Image(logoUrl, true); logoView.setImage(img); } catch (Exception e) {}
        logoView.setFitWidth(42); logoView.setFitHeight(42);
        logoView.setPreserveRatio(true);
        logoView.setClip(new Circle(21, 21, 21));

        Label t = new Label(teamName);
        t.setStyle("-fx-text-fill: #0f172a; -fx-font-size: 14px; -fx-font-weight: bold;");

        Label s = new Label(score);
        s.setStyle("-fx-text-fill: #059669; -fx-font-size: 18px; -fx-font-weight: bold;");

        box.getChildren().addAll(logoView, t, s);
        return box;
    }

    private void addHoverAnimation(Node node) {
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(150), node);
        scaleIn.setToX(1.02); scaleIn.setToY(1.02);
        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(150), node);
        scaleOut.setToX(1.0); scaleOut.setToY(1.0);

        node.setOnMouseEntered(e -> scaleIn.playFromStart());
        node.setOnMouseExited(e -> scaleOut.playFromStart());
    }
}