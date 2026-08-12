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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class MyTournamentPage {

    private StackPane rootContainer;
    private ScrollPane mainScrollPane;
    private Runnable onBackAction; 

    public MyTournamentPage(Runnable onBackAction) {
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

        Button backBtn = new Button("❮  Back");
        backBtn.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #10b981; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 8 18; -fx-background-radius: 20; -fx-border-color: #e2e8f0; -fx-border-radius: 20; -fx-cursor: hand;");
        backBtn.setOnAction(e -> {
            if (onBackAction != null) {
                onBackAction.run();
            }
        });

        Label headerTitle = new Label("👤 My Tournaments Dashboard");
        headerTitle.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        
        header.getChildren().addAll(backBtn, headerTitle);

        // --- 2. PERSONAL STATS ROW ---
        HBox statsRow = new HBox(20);
        statsRow.setAlignment(Pos.CENTER);
        
        VBox playedStat = createStatCard("Tournaments Played", "12", "🏆", "linear-gradient(to bottom right, #ffffff, #e0e7ff)", "#3b82f6");
        VBox currentStat = createStatCard("Currently Active", "1", "🔴", "linear-gradient(to bottom right, #ffffff, #fee2e2)", "#ef4444");
        VBox upcomingStat = createStatCard("Upcoming Registered", "2", "📅", "linear-gradient(to bottom right, #ffffff, #d1fae5)", "#10b981");
        
        statsRow.getChildren().addAll(playedStat, currentStat, upcomingStat);

        // --- 3. CURRENT ACTIVE TOURNAMENT (Only One) ---
        VBox currentSection = new VBox(15);
        Label currentTitle = new Label("🔴 Current Active Tournament");
        currentTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        
        VBox currentCard = createCurrentTournamentCard(
            "Mumbai Premier League", 
            "MUMBAI PREMIER LEAGUE", // Custom Banner Text
            "Mumbai Masters", 
            "Player / Top Order Batter",
            "Next Match: Tomorrow, 4:00 PM vs Pune Strikers",
            "https://dummyimage.com/60x60/3b82f6/ffffff.png&text=MM"
        );
        currentSection.getChildren().addAll(currentTitle, currentCard);

        // --- 4. UPCOMING TOURNAMENTS ---
        VBox upcomingSection = new VBox(15);
        Label upcomingTitle = new Label("📅 Upcoming Registrations");
        upcomingTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0f172a; -fx-padding: 10 0 0 0;");
        
        VBox upcomingList = new VBox(15);
        upcomingList.getChildren().addAll(
            createUpcomingCard("Corporate Shield 2026", "Starts: 15 Jun 2026", "Tech Innovators CC", "Approved", "#10b981"),
            createUpcomingCard("Monsoon T20 Invitational", "Starts: 02 Jul 2026", "Coastal Kings", "Pending Review", "#f59e0b")
        );
        upcomingSection.getChildren().addAll(upcomingTitle, upcomingList);

        // --- ASSEMBLE ---
        mainContainer.getChildren().addAll(header, statsRow, currentSection, upcomingSection);

        ScrollPane scrollPane = new ScrollPane(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f8fafc;");
        scrollPane.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");

        return scrollPane;
    }

    private VBox createStatCard(String title, String value, String icon, String gradientStyle, String iconColor) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background: " + gradientStyle + "; -fx-background-color: " + gradientStyle + "; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12;");
        HBox.setHgrow(box, Priority.ALWAYS);
        
        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER_LEFT);
        Label t = new Label(title);
        t.setStyle("-fx-text-fill: #475569; -fx-font-size: 14px; -fx-font-weight: bold;");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Label i = new Label(icon);
        i.setStyle("-fx-font-size: 18px; -fx-text-fill: " + iconColor + ";");
        topRow.getChildren().addAll(t, spacer, i);
        
        Label v = new Label(value);
        v.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        
        box.getChildren().addAll(topRow, v);

        // Hover effect
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(150), box);
        scaleIn.setToX(1.02); scaleIn.setToY(1.02);
        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(150), box);
        scaleOut.setToX(1.0); scaleOut.setToY(1.0);

        box.setOnMouseEntered(e -> {
            scaleIn.playFromStart();
            box.setStyle("-fx-background: " + gradientStyle + "; -fx-background-color: " + gradientStyle + "; -fx-background-radius: 12; -fx-border-color: " + iconColor + "; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 4);");
        });
        box.setOnMouseExited(e -> {
            scaleOut.playFromStart();
            box.setStyle("-fx-background: " + gradientStyle + "; -fx-background-color: " + gradientStyle + "; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12;");
        });

        return box;
    }

    private VBox createCurrentTournamentCard(String tournamentName, String bannerText, String teamName, String role, String nextMatchInfo, String logoUrl) {
        VBox card = new VBox(0);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #3b82f6 #e2e8f0 #e2e8f0 #e2e8f0; -fx-border-width: 4 1 1 1; -fx-border-radius: 12;");
        
        // 1. Custom CSS Styled Banner
        StackPane bannerPane = new StackPane();
        bannerPane.setPrefHeight(130);
        bannerPane.setMinHeight(130);
        bannerPane.setStyle("-fx-background-color: linear-gradient(to right, #0f172a, #1d4ed8, #0ea5e9); -fx-background-radius: 10 10 0 0;");
        
        Label bTextLabel = new Label(bannerText);
        bTextLabel.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: #facc15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 3);");
        
        bannerPane.getChildren().add(bTextLabel);

        // 2. Info Section
        VBox infoBox = new VBox(15);
        infoBox.setPadding(new Insets(20));

        HBox headerRow = new HBox(15);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        // Team Logo
        ImageView logo = new ImageView();
        try { Image img = new Image(logoUrl, true); logo.setImage(img); } catch (Exception e) {}
        logo.setFitWidth(50); logo.setFitHeight(50);
        logo.setClip(new Circle(25, 25, 25));

        VBox titleBox = new VBox(2);
        Label tName = new Label(tournamentName);
        tName.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        Label tmName = new Label("Playing for: " + teamName + " • " + role);
        tmName.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748b; -fx-font-weight: bold;");
        titleBox.getChildren().addAll(tName, tmName);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button enterHubBtn = new Button("Enter Tournament Hub ➔");
        enterHubBtn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 6; -fx-cursor: hand;");
        enterHubBtn.setOnMouseEntered(e -> enterHubBtn.setStyle("-fx-background-color: #059669; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 6; -fx-cursor: hand;"));
        enterHubBtn.setOnMouseExited(e -> enterHubBtn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 6; -fx-cursor: hand;"));

        // INTEGRATED ACTION: Route to the Tournament Hub
        enterHubBtn.setOnAction(e -> {
            MyTournamentHubButton hubPage = new MyTournamentHubButton(tournamentName, () -> {
                // Back Action: restore this main dashboard scroll pane
                rootContainer.getChildren().setAll(mainScrollPane);
            });
            // Set the view to the Hub
            rootContainer.getChildren().setAll(hubPage.getView());
        });

        headerRow.getChildren().addAll(logo, titleBox, spacer, enterHubBtn);

        // Next Match Alert Box
        HBox alertBox = new HBox(10);
        alertBox.setAlignment(Pos.CENTER_LEFT);
        alertBox.setPadding(new Insets(10, 15, 10, 15));
        alertBox.setStyle("-fx-background-color: #eff6ff; -fx-background-radius: 8; -fx-border-color: #bfdbfe; -fx-border-radius: 8;");
        
        Label alertIcon = new Label("⚡");
        Label alertText = new Label(nextMatchInfo);
        alertText.setStyle("-fx-text-fill: #1d4ed8; -fx-font-size: 14px; -fx-font-weight: bold;");
        alertBox.getChildren().addAll(alertIcon, alertText);

        infoBox.getChildren().addAll(headerRow, alertBox);
        card.getChildren().addAll(bannerPane, infoBox);

        return card;
    }

    private HBox createUpcomingCard(String title, String date, String team, String status, String statusColor) {
        HBox card = new HBox(15);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(15, 20, 15, 20));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12;");

        VBox infoBox = new VBox(5);
        Label tName = new Label(title);
        tName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        Label tmName = new Label("Registered as: " + team);
        tmName.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748b;");
        infoBox.getChildren().addAll(tName, tmName);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        VBox dateBox = new VBox(5);
        dateBox.setAlignment(Pos.CENTER_RIGHT);
        Label dName = new Label(date);
        dName.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #475569;");
        Label sName = new Label(status);
        
        // Status badge styling
        if(status.equals("Approved")) {
            sName.setStyle("-fx-background-color: #ecfdf5; -fx-text-fill: #059669; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 4 8; -fx-background-radius: 6;");
        } else {
            sName.setStyle("-fx-background-color: #fffbeb; -fx-text-fill: #d97706; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 4 8; -fx-background-radius: 6;");
        }
        
        dateBox.getChildren().addAll(dName, sName);

        card.getChildren().addAll(infoBox, spacer, dateBox);

        // Hover Effect
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(150), card);
        scaleIn.setToX(1.01); scaleIn.setToY(1.01);
        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(150), card);
        scaleOut.setToX(1.0); scaleOut.setToY(1.0);

        card.setOnMouseEntered(e -> {
            scaleIn.playFromStart();
            card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12; -fx-border-color: " + statusColor + "; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 8, 0, 0, 3);");
        });
        card.setOnMouseExited(e -> {
            scaleOut.playFromStart();
            card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12;");
        });

        return card;
    }
}