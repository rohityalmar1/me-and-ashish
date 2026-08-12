package com.athlixcore.view.player.Leaderboard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class Leaderboard_Page extends VBox {

    private BorderPane mainLayout;
    
    private VBox podiumContainer;
    private HBox tabsContainer;
    private VBox tableRowsContainer;
    private ScrollPane mainScrollPane;

    public Leaderboard_Page(BorderPane mainLayout) {
        this.mainLayout = mainLayout;
        
        this.setSpacing(25);
        this.setPadding(new Insets(25, 40, 25, 40));
        this.setStyle("-fx-background-color: #f8fafc;");

        showLeaderboardView("Overall");
    }

    private void showLeaderboardView(String initialCategory) {
        this.getChildren().clear();

        HBox pageHeader = new HBox();
        pageHeader.setAlignment(Pos.CENTER_LEFT);
        
        VBox titleBox = new VBox(5);
        Label mainTitle = new Label("Leaderboard");
        mainTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #111827;");
        Label subTitle = new Label("Top performers across all formats and categories");
        subTitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #6b7280;");
        titleBox.getChildren().addAll(mainTitle, subTitle);
        
        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);
        
        ComboBox<String> seasonCombo = new ComboBox<>();
        seasonCombo.getItems().addAll("This Season", "Last Season", "All Time");
        seasonCombo.setValue("📅 This Season");
        seasonCombo.setStyle("-fx-background-color: white; -fx-border-color: #e5e7eb; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 4 10; -fx-font-weight: bold; -fx-text-fill: #374151;");
        
        pageHeader.getChildren().addAll(titleBox, headerSpacer, seasonCombo);

        HBox mainSplit = new HBox(30);
        VBox.setVgrow(mainSplit, Priority.ALWAYS);

        VBox leftContent = new VBox(25);
        HBox.setHgrow(leftContent, Priority.ALWAYS);

        tabsContainer = new HBox(10);
        tabsContainer.setAlignment(Pos.CENTER_LEFT);

        podiumContainer = new VBox();
        podiumContainer.setAlignment(Pos.CENTER);

        VBox rankingTable = buildRankingTableContainer();

        leftContent.getChildren().addAll(tabsContainer, podiumContainer, rankingTable);

        VBox rightSidebar = new VBox(20);
        rightSidebar.setPrefWidth(280);
        rightSidebar.setMinWidth(280);

        rightSidebar.getChildren().addAll(
            buildCategoriesWidget(),
            buildTopTeamsWidget(),
            buildAboutWidget()
        );

        mainSplit.getChildren().addAll(leftContent, rightSidebar);

        VBox contentBox = new VBox(20, pageHeader, mainSplit);
        mainScrollPane = new ScrollPane(contentBox);
        mainScrollPane.setFitToWidth(true);
        mainScrollPane.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
        mainScrollPane.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");
        VBox.setVgrow(mainScrollPane, Priority.ALWAYS);

        this.getChildren().add(mainScrollPane);

        loadCategoryData(initialCategory);
    }

    private void loadCategoryData(String category) {
        tabsContainer.getChildren().clear();
        tabsContainer.getChildren().addAll(
            createCategoryTab("🎯 Overall", "Overall", category.equals("Overall")),
            createCategoryTab("🏏 Batsman", "Batsman", category.equals("Batsman")),
            createCategoryTab("⚾ Bowler", "Bowler", category.equals("Bowler")),
            createCategoryTab("⚡ All Rounder", "All Rounder", category.equals("All Rounder")),
            createCategoryTab("🥊 Fielders", "Fielders", category.equals("Fielders")),
            createCategoryTab("🛡️ Teams", "Teams", category.equals("Teams"))
        );

        podiumContainer.getChildren().clear();
        buildLightPodium(category);

        tableRowsContainer.getChildren().clear();
        loadTableRowsForCategory(category);
    }

    private Button createCategoryTab(String displayText, String categoryKey, boolean isActive) {
        Button btn = new Button(displayText);
        btn.setCursor(Cursor.HAND);
        if (isActive) {
            btn.setStyle("-fx-background-color: #16a34a; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 8; -fx-padding: 8 16;");
        } else {
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 8 16;");
            btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #f3f4f6; -fx-text-fill: #111827; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 8; -fx-padding: 8 16;"));
            btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 8 16;"));
        }
        btn.setOnAction(e -> {
            if (categoryKey.equals("Teams")) {
                showTeamRankingsView();
            } else {
                loadCategoryData(categoryKey);
            }
        });
        return btn;
    }

    private Node getPlayerAvatar(String name, String imgPath, double size, String fallbackBgColor, String fallbackTextColor) {
        StackPane container = new StackPane();
        container.setPrefSize(size, size);
        container.setMaxSize(size, size);
        
        boolean imageLoaded = false;
        
        if (imgPath != null && !imgPath.isEmpty()) {
            try {
                String finalPath = null;
                java.net.URL res = getClass().getResource(imgPath);
                if (res != null) {
                    finalPath = res.toExternalForm();
                } else {
                    java.io.File file = new java.io.File("src/main/resources" + imgPath);
                    if (file.exists()) {
                        finalPath = file.toURI().toString();
                    }
                }
                
                if (finalPath != null) {
                    Image img = new Image(finalPath, size, size, false, true);
                    if (!img.isError()) {
                        ImageView imgView = new ImageView(img);
                        imgView.setFitWidth(size);
                        imgView.setFitHeight(size);
                        
                        Circle clip = new Circle(size / 2, size / 2, size / 2);
                        imgView.setClip(clip);
                        
                        container.getChildren().add(imgView);
                        imageLoaded = true;
                    }
                }
            } catch (Exception e) {}
        }
        
        if (!imageLoaded) {
            Label initial = new Label(name.substring(0, 1).toUpperCase());
            initial.setPrefSize(size, size);
            initial.setAlignment(Pos.CENTER);
            initial.setStyle("-fx-background-color: " + fallbackBgColor + "; -fx-text-fill: " + fallbackTextColor + "; -fx-background-radius: " + (size/2) + "; -fx-font-weight: bold; -fx-font-size: " + (size/2.5) + "px;");
            container.getChildren().add(initial);
        }
        
        return container;
    }

    private void buildLightPodium(String category) {
        HBox podiumRow = new HBox(20);
        podiumRow.setAlignment(Pos.BOTTOM_CENTER);
        
        VBox rank1, rank2, rank3;
        
        if (category.equals("Batsman")) {
            rank2 = buildPodiumCard("Surya Kumar Yadav", "2,450", "82.4", "#ffffff", 220, "2", "#94a3b8", "#3b82f6", "/assests/images/Virat.jpg", true);
            rank1 = buildPodiumCard("Virat Kohli", "3,125", "89.7", "#fffbeb", 250, "1", "#f59e0b", "#f59e0b", "/assests/images/Virat.jpg", true);
            rank3 = buildPodiumCard("Rohit Sharma", "2,210", "80.2", "#ffffff", 220, "3", "#b45309", "#d97706", "/assests/images/Virat.jpg", false);
        } else {
            rank2 = buildPodiumCard("Surya Kumar Yadav", "2,450", "82.4", "#ffffff", 220, "2", "#94a3b8", "#3b82f6", "/assests/images/surya.jpg", false);
            rank1 = buildPodiumCard("Virat Kohli", "3,125", "89.7", "#fffbeb", 250, "1", "#f59e0b", "#f59e0b", "/assests/images/Virat.jpg", true);
            rank3 = buildPodiumCard("Rohit Sharma", "2,210", "80.2", "#fff5f5", 220, "3", "#b45309", "#d97706", "/assests/images/rohit.jpg", true);
        }
        
        podiumRow.getChildren().addAll(rank2, rank1, rank3);
        podiumContainer.getChildren().add(podiumRow);
    }

    private VBox buildPodiumCard(String name, String points, String rating, String bgColor, double height, String rank, String medalColor, String badgeColor, String imagePath, boolean isOnline) {
        VBox card = new VBox(15);
        card.setAlignment(Pos.CENTER);
        card.setPrefHeight(height);
        HBox.setHgrow(card, Priority.ALWAYS);
        card.setMaxWidth(Double.MAX_VALUE);
        card.setCursor(Cursor.HAND);
        card.setStyle("-fx-background-color: " + bgColor + "; -fx-background-radius: 12; -fx-border-color: #f1f5f9; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 2);");

        Label medal = new Label(rank);
        medal.setPrefSize(30, 30);
        medal.setAlignment(Pos.CENTER);
        medal.setStyle("-fx-background-color: " + medalColor + "; -fx-text-fill: white; -fx-background-radius: 15; -fx-font-weight: bold; -fx-font-size: 14px;");

        Node avatarNode = getPlayerAvatar(name, imagePath, 70, "#1e3a8a", "white");
        
        Circle statusBadge = new Circle(8);
        statusBadge.setFill(isOnline ? Color.valueOf("#10b981") : Color.valueOf("#9ca3af"));
        statusBadge.setStroke(Color.WHITE);
        statusBadge.setStrokeWidth(2);

        StackPane avatarPane = new StackPane(avatarNode, statusBadge);
        StackPane.setAlignment(statusBadge, Pos.BOTTOM_RIGHT);

        VBox textStack = new VBox(5);
        textStack.setAlignment(Pos.CENTER);
        Label nameLbl = new Label(name);
        nameLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: #111827;");
        Label pointsLbl = new Label(points + " Points");
        pointsLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #6b7280;");
        textStack.getChildren().addAll(nameLbl, pointsLbl);

        Label ratingBadge = new Label(rating);
        ratingBadge.setPrefSize(45, 30);
        ratingBadge.setAlignment(Pos.CENTER);
        ratingBadge.setStyle("-fx-background-color: " + badgeColor + "; -fx-text-fill: white; -fx-background-radius: 8; -fx-font-weight: bold; -fx-font-size: 12px;");

        card.getChildren().addAll(medal, avatarPane, textStack, ratingBadge);
        
        // --- PASS THE BACK ACTION: Return to Overall Dashboard ---
        card.setOnMouseClicked(e -> showPlayerRankingsView(name, imagePath, () -> showLeaderboardView("Overall")));
        
        return card;
    }

    private VBox buildRankingTableContainer() {
        VBox tableContainer = new VBox(15);
        
        Label title = new Label("Top 10 Players");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #111827;");

        VBox table = new VBox(0);
        table.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 8, 0, 0, 2);");
        
        HBox header = new HBox(10);
        header.setPadding(new Insets(15, 20, 15, 20));
        header.setStyle("-fx-border-color: #e5e7eb; -fx-border-width: 0 0 1 0;");
        header.setAlignment(Pos.CENTER_LEFT);

        header.getChildren().addAll(
            createHeaderLabel("Rank", 40), 
            createHeaderLabel("Player", 180), 
            createHeaderLabel("Matches", 70), 
            createHeaderLabel("Runs", 60), 
            createHeaderLabel("Wickets", 60),
            createHeaderLabel("Points", 70),
            createHeaderLabel("Rating", 50)
        );
        
        tableRowsContainer = new VBox();

        Button viewFullBtn = new Button("View Full Leaderboard →");
        viewFullBtn.setCursor(Cursor.HAND);
        viewFullBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #2563eb; -fx-font-weight: bold; -fx-padding: 15;");
        viewFullBtn.setMaxWidth(Double.MAX_VALUE);
        viewFullBtn.setOnAction(e -> showFullLeaderboardGrid());

        table.getChildren().addAll(header, tableRowsContainer, viewFullBtn);
        tableContainer.getChildren().addAll(title, table);
        
        return tableContainer;
    }

    private Label createHeaderLabel(String text, double width) {
        Label lbl = new Label(text);
        lbl.setPrefWidth(width);
        lbl.setAlignment(Pos.CENTER);
        if(text.equals("Player") || text.equals("Rank")) lbl.setAlignment(Pos.CENTER_LEFT);
        lbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #6b7280; -fx-font-size: 12px;");
        return lbl;
    }

    private void loadTableRowsForCategory(String category) {
        tableRowsContainer.getChildren().addAll(
            buildTableRow("4", "Hardik Pandya", "All Rounder", "18", "512", "15", "2,050", "76.3", "/assests/images/hardik.jpg"),
            buildTableRow("5", "Ravindra Jadeja", "All Rounder", "16", "420", "21", "1,950", "75.2", "/assests/images/jadeja.jpg"),
            buildTableRow("6", "Jasprit Bumrah", "Bowler", "14", "120", "28", "1,880", "74.5", "/assests/images/bumrah.jpg"),
            buildTableRow("7", "KL Rahul", "Batsman", "17", "610", "0", "1,750", "72.1", "/assests/images/rahul.jpg"),
            buildTableRow("8", "Shubman Gill", "Batsman", "16", "580", "0", "1,680", "70.8", "/assests/images/gill.jpg"),
            buildTableRow("9", "Yuzvendra Chahal", "Bowler", "18", "80", "24", "1,640", "69.7", "/assests/images/chahal.jpg"),
            buildTableRow("10", "Mohammed Siraj", "Bowler", "15", "60", "20", "1,520", "68.2", "/assests/images/siraj.jpg")
        );
    }

    private HBox buildTableRow(String rank, String name, String role, String matches, String runs, String wickets, String points, String rating, String imagePath) {
        HBox row = new HBox(10);
        row.setPadding(new Insets(12, 20, 12, 20));
        row.setAlignment(Pos.CENTER_LEFT);
        row.setCursor(Cursor.HAND);
        row.setStyle("-fx-background-color: white; -fx-border-color: #f3f4f6; -fx-border-width: 0 0 1 0;");

        Label rankLbl = new Label(rank);
        rankLbl.setPrefWidth(40);
        rankLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #111827; -fx-font-size: 14px;");

        HBox playerBox = new HBox(10);
        playerBox.setPrefWidth(180);
        playerBox.setAlignment(Pos.CENTER_LEFT);
        
        Node avatarNode = getPlayerAvatar(name, imagePath, 35, "#e2e8f0", "#475569");
        
        VBox nameDetails = new VBox(2);
        Label nameLbl = new Label(name);
        nameLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #111827; -fx-font-size: 13px;");
        Label roleLbl = new Label(role);
        roleLbl.setStyle("-fx-text-fill: #9ca3af; -fx-font-size: 11px;");
        nameDetails.getChildren().addAll(nameLbl, roleLbl);
        playerBox.getChildren().addAll(avatarNode, nameDetails);

        Label matLbl = createDataLabel(matches, 70);
        Label runLbl = createDataLabel(runs, 60);
        Label wktLbl = createDataLabel(wickets, 60);
        
        Label ptsLbl = new Label(points);
        ptsLbl.setPrefWidth(70);
        ptsLbl.setAlignment(Pos.CENTER);
        ptsLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #16a34a; -fx-font-size: 13px;");
        
        Label rtgLbl = new Label(rating);
        rtgLbl.setPrefWidth(50);
        rtgLbl.setAlignment(Pos.CENTER);
        rtgLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #16a34a; -fx-font-size: 13px;");

        row.getChildren().addAll(rankLbl, playerBox, matLbl, runLbl, wktLbl, ptsLbl, rtgLbl);
        
        // --- PASS THE BACK ACTION: Return to Overall Dashboard ---
        row.setOnMouseClicked(e -> showPlayerRankingsView(name, imagePath, () -> showLeaderboardView("Overall")));
        
        return row;
    }

    private Label createDataLabel(String text, double width) {
        Label lbl = new Label(text);
        lbl.setPrefWidth(width);
        lbl.setAlignment(Pos.CENTER);
        lbl.setStyle("-fx-text-fill: #4b5563; -fx-font-size: 13px;");
        return lbl;
    }

    private VBox buildCategoriesWidget() {
        VBox widget = new VBox(10);
        widget.setPadding(new Insets(20));
        widget.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 8, 0, 0, 2);");

        Label title = new Label("Categories");
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #111827; -fx-font-size: 15px; -fx-padding: 0 0 5 0;");

        widget.getChildren().addAll(title,
            createSidebarCat("🎯 Overall", "Overall", true),
            createSidebarCat("🏏 Batsman", "Batsman", false),
            createSidebarCat("⚾ Bowler", "Bowler", false),
            createSidebarCat("⚡ All Rounder", "All Rounder", false),
            createSidebarCat("🥊 Fielders", "Fielders", false),
            createSidebarCat("🛡️ Teams", "Teams", false)
        );
        return widget;
    }

    private Label createSidebarCat(String text, String categoryKey, boolean isActive) {
        Label lbl = new Label(text);
        lbl.setPrefWidth(Double.MAX_VALUE);
        lbl.setPadding(new Insets(8, 12, 8, 12));
        if (isActive) {
            lbl.setStyle("-fx-background-color: #f0fdf4; -fx-text-fill: #16a34a; -fx-font-weight: bold; -fx-background-radius: 6;");
        } else {
            lbl.setStyle("-fx-text-fill: #6b7280; -fx-font-weight: bold;");
            lbl.setOnMouseEntered(e -> lbl.setStyle("-fx-background-color: #f9fafb; -fx-text-fill: #374151; -fx-font-weight: bold; -fx-background-radius: 6;"));
            lbl.setOnMouseExited(e -> lbl.setStyle("-fx-text-fill: #6b7280; -fx-font-weight: bold;"));
        }
        lbl.setCursor(Cursor.HAND);
        lbl.setOnMouseClicked(e -> {
            if (categoryKey.equals("Teams")) {
                showTeamRankingsView();
            } else {
                loadCategoryData(categoryKey);
            }
        });
        return lbl;
    }

    private VBox buildTopTeamsWidget() {
        VBox widget = new VBox(15);
        widget.setPadding(new Insets(20));
        widget.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 8, 0, 0, 2);");

        Label title = new Label("Top Teams");
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #111827; -fx-font-size: 15px;");

        widget.getChildren().addAll(
            title,
            buildTeamRow("1", "Mumbai Warriors", "3,250 PTS"),
            buildTeamRow("2", "Pune Strikers", "2,880 PTS"),
            buildTeamRow("3", "Delhi Daredevils", "2,760 PTS")
        );
        return widget;
    }

    private HBox buildTeamRow(String rank, String name, String points) {
        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setCursor(Cursor.HAND);
        
        Label rLbl = new Label(rank);
        rLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #16a34a; -fx-font-size: 14px;");

        Label logo = new Label("🛡️");
        logo.setStyle("-fx-font-size: 16px;");

        VBox text = new VBox(2);
        Label nLbl = new Label(name);
        nLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #111827; -fx-font-size: 12px;");
        Label pLbl = new Label(points);
        pLbl.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 11px;");
        text.getChildren().addAll(nLbl, pLbl);

        row.getChildren().addAll(rLbl, logo, text);
        row.setOnMouseClicked(e -> showTeamRankingsView()); 
        return row;
    }

    private VBox buildAboutWidget() {
        VBox widget = new VBox(10);
        widget.setPadding(new Insets(20));
        widget.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 8, 0, 0, 2);");

        Label title = new Label("About Leaderboard");
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #111827; -fx-font-size: 15px;");
        
        Label desc = new Label("Rankings are based on performance in matches, tournaments and overall contributions.");
        desc.setWrapText(true);
        desc.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 12px; -fx-line-spacing: 4px;");

        widget.getChildren().addAll(title, desc);
        return widget;
    }

    private void showFullLeaderboardGrid() {
        this.getChildren().clear();

        Button backBtn = new Button("← Back to Leaderboard Home");
        backBtn.setCursor(Cursor.HAND);
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #2563eb; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 0;");
        backBtn.setOnAction(e -> showLeaderboardView("Overall"));

        VBox headerTitleBox = new VBox(5);
        Label mainTitle = new Label("Full Player Leaderboard");
        mainTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #111827;");
        Label subTitle = new Label("Browse the complete ranking of all athletes in the league.");
        subTitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #6b7280;");
        headerTitleBox.getChildren().addAll(mainTitle, subTitle);

        FlowPane grid = new FlowPane(25, 25);
        
        grid.getChildren().addAll(
            buildFullPlayerCard("1", "Virat Kohli", "Batsman", "3,125", "/assests/images/Virat.jpg"),
            buildFullPlayerCard("2", "Surya Kumar Yadav", "Batsman", "2,450", "/assests/images/surya.jpg"),
            buildFullPlayerCard("3", "Rohit Sharma", "Batsman", "2,210", "/assests/images/rohit.jpg"),
            buildFullPlayerCard("4", "Hardik Pandya", "All Rounder", "2,050", "/assests/images/hardik.jpg"),
            buildFullPlayerCard("5", "Ravindra Jadeja", "All Rounder", "1,950", "/assests/images/jadeja.jpg"),
            buildFullPlayerCard("6", "Jasprit Bumrah", "Bowler", "1,880", "/assests/images/bumrah.jpg"),
            buildFullPlayerCard("7", "KL Rahul", "Batsman", "1,750", "/assests/images/rahul.jpg"),
            buildFullPlayerCard("8", "Shubman Gill", "Batsman", "1,680", "/assests/images/gill.jpg"),
            buildFullPlayerCard("9", "Yuzvendra Chahal", "Bowler", "1,640", "/assests/images/chahal.jpg"),
            buildFullPlayerCard("10", "Mohammed Siraj", "Bowler", "1,520", "/assests/images/siraj.jpg")
        );

        VBox layout = new VBox(25, backBtn, headerTitleBox, grid);
        
        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
        scrollPane.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        this.getChildren().add(scrollPane);
    }

    private VBox buildFullPlayerCard(String rank, String name, String role, String points, String imgPath) {
        VBox card = new VBox();
        card.setPrefSize(220, 260); 
        card.setCursor(Cursor.HAND);
        
        Rectangle cardClip = new Rectangle(220, 260);
        cardClip.setArcWidth(24);
        cardClip.setArcHeight(24);
        card.setClip(cardClip);
        
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 12, 0, 0, 4);");

        StackPane topHalf = new StackPane();
        topHalf.setPrefSize(220, 150);
        topHalf.setStyle("-fx-background-color: #e2e8f0;");
        
        boolean imageLoaded = false;
        if (imgPath != null && !imgPath.isEmpty()) {
            try {
                String finalPath = null;
                java.net.URL res = getClass().getResource(imgPath);
                if (res != null) {
                    finalPath = res.toExternalForm();
                } else {
                    java.io.File file = new java.io.File("src/main/resources" + imgPath);
                    if (file.exists()) {
                        finalPath = file.toURI().toString();
                    }
                }
                
                if (finalPath != null) {
                    Image img = new Image(finalPath, 220, 150, false, true);
                    if (!img.isError()) {
                        ImageView imgView = new ImageView(img);
                        imgView.setFitWidth(220);
                        imgView.setFitHeight(150);
                        topHalf.getChildren().add(imgView);
                        imageLoaded = true;
                    }
                }
            } catch (Exception e) {}
        }
        
        if (!imageLoaded) {
            Label initial = new Label(name.substring(0, 1).toUpperCase());
            initial.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: #475569;");
            topHalf.getChildren().add(initial);
        }

        Label rankBadge = new Label("#" + rank);
        rankBadge.setStyle("-fx-background-color: #16a34a; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 4 10; -fx-background-radius: 8;");
        StackPane.setAlignment(rankBadge, Pos.TOP_LEFT);
        StackPane.setMargin(rankBadge, new Insets(10));
        topHalf.getChildren().add(rankBadge);

        VBox bottomHalf = new VBox(5);
        bottomHalf.setPadding(new Insets(15));
        bottomHalf.setAlignment(Pos.CENTER);

        Label nameLbl = new Label(name);
        nameLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #111827;");

        Label roleLbl = new Label(role);
        roleLbl.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 13px;");

        Label ptsLbl = new Label(points + " PTS");
        ptsLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #2563eb; -fx-font-size: 14px; -fx-padding: 5 0 0 0;");

        bottomHalf.getChildren().addAll(nameLbl, roleLbl, ptsLbl);
        card.getChildren().addAll(topHalf, bottomHalf);

        // --- PASS THE BACK ACTION: Return to the Full Grid View ---
        card.setOnMouseClicked(e -> showPlayerRankingsView(name, imgPath, () -> showFullLeaderboardGrid()));

        return card;
    }

    private void showTeamRankingsView() {
        this.getChildren().clear();

        Button backBtn = new Button("← Back to Leaderboard");
        backBtn.setCursor(Cursor.HAND);
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #2563eb; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 0;");
        backBtn.setOnAction(e -> showLeaderboardView("Overall"));

        VBox headerTitleBox = new VBox(5);
        Label mainTitle = new Label("Team Rankings & Standings");
        mainTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #111827;");
        Label subTitle = new Label("Comprehensive performance, win rates, and points table across all teams");
        subTitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #6b7280;");
        headerTitleBox.getChildren().addAll(mainTitle, subTitle);

        HBox topTeamsCards = new HBox(20);
        topTeamsCards.getChildren().addAll(
            createTeamCard("1", "Mumbai Warriors", "3,250 PTS", "18 Matches", "14 Won", "77.8% Win Rate", "#16a34a"),
            createTeamCard("2", "Pune Strikers", "2,880 PTS", "18 Matches", "12 Won", "66.7% Win Rate", "#2563eb"),
            createTeamCard("3", "Delhi Daredevils", "2,760 PTS", "18 Matches", "11 Won", "61.1% Win Rate", "#d97706")
        );

        VBox tableBox = new VBox(0);
        tableBox.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 8, 0, 0, 2);");
        
        HBox tableHeader = new HBox(10);
        tableHeader.setPadding(new Insets(15, 20, 15, 20));
        tableHeader.setStyle("-fx-border-color: #e5e7eb; -fx-border-width: 0 0 1 0;");
        tableHeader.setAlignment(Pos.CENTER_LEFT);

        tableHeader.getChildren().addAll(
            createHeaderLabel("Rank", 50),
            createHeaderLabel("Team", 180),
            createHeaderLabel("Matches", 80),
            createHeaderLabel("Won", 70),
            createHeaderLabel("Lost", 70),
            createHeaderLabel("Win %", 80),
            createHeaderLabel("Points", 80)
        );

        VBox rows = new VBox();
        rows.getChildren().addAll(
            buildTeamTableRow("1", "Mumbai Warriors", "18", "14", "4", "77.8%", "3,250"),
            buildTeamTableRow("2", "Pune Strikers", "18", "12", "6", "66.7%", "2,880"),
            buildTeamTableRow("3", "Delhi Daredevils", "18", "11", "7", "61.1%", "2,760"),
            buildTeamTableRow("4", "Bangalore Smashers", "18", "10", "8", "55.6%", "2,450"),
            buildTeamTableRow("5", "Chennai Kings", "18", "9", "9", "50.0%", "2,210"),
            buildTeamTableRow("6", "Kolkata Knights", "18", "7", "11", "38.9%", "1,890"),
            buildTeamTableRow("7", "Gujarat Lions", "18", "5", "13", "27.8%", "1,420")
        );

        tableBox.getChildren().addAll(tableHeader, rows);

        VBox pageLayout = new VBox(20, backBtn, headerTitleBox, topTeamsCards, tableBox);
        ScrollPane scrollPane = new ScrollPane(pageLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        this.getChildren().add(scrollPane);
    }

    private VBox createTeamCard(String rank, String teamName, String pts, String matches, String won, String winRate, String accentColor) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 8, 0, 0, 2);");
        HBox.setHgrow(card, Priority.ALWAYS);

        HBox topRow = new HBox();
        Label rLbl = new Label("#" + rank);
        rLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: " + accentColor + ";");
        Region sp = new Region(); HBox.setHgrow(sp, Priority.ALWAYS);
        Label pLbl = new Label(pts);
        pLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #111827;");
        topRow.getChildren().addAll(rLbl, sp, pLbl);

        Label nameLbl = new Label(teamName);
        nameLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #111827;");

        Label statsLbl = new Label(matches + "  •  " + won + "  •  " + winRate);
        statsLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b7280;");

        card.getChildren().addAll(topRow, nameLbl, statsLbl);
        return card;
    }

    private HBox buildTeamTableRow(String rank, String name, String matches, String won, String lost, String winPct, String pts) {
        HBox row = new HBox(10);
        row.setPadding(new Insets(12, 20, 12, 20));
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: white; -fx-border-color: #f3f4f6; -fx-border-width: 0 0 1 0;");

        Label rankLbl = new Label(rank); rankLbl.setPrefWidth(50); rankLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #111827;");
        
        HBox teamBox = new HBox(8);
        teamBox.setPrefWidth(180);
        teamBox.setAlignment(Pos.CENTER_LEFT);
        Label logo = new Label("🛡️"); logo.setStyle("-fx-font-size: 16px;");
        Label nameLbl = new Label(name); nameLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #111827;");
        teamBox.getChildren().addAll(logo, nameLbl);

        Label matLbl = createDataLabel(matches, 80);
        Label wonLbl = createDataLabel(won, 70);
        Label lostLbl = createDataLabel(lost, 70);
        Label winPctLbl = createDataLabel(winPct, 80);
        
        Label ptsLbl = new Label(pts);
        ptsLbl.setPrefWidth(80);
        ptsLbl.setAlignment(Pos.CENTER);
        ptsLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #16a34a; -fx-font-size: 13px;");

        row.getChildren().addAll(rankLbl, teamBox, matLbl, wonLbl, lostLbl, winPctLbl, ptsLbl);
        return row;
    }

    // --- NEW SIGNATURE: Added the onBack Runnable so the profile knows exactly where to go back to! ---
    private void showPlayerRankingsView(String playerName, String imgPath, Runnable onBack) {
        this.getChildren().clear();

        Button backBtn = new Button("← Back");
        backBtn.setCursor(Cursor.HAND);
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #2563eb; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 0;");
        backBtn.setOnAction(e -> {
            if (onBack != null) onBack.run();
        });

        VBox headerTitleBox = new VBox(5);
        Label mainTitle = new Label("Player Rankings");
        mainTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #111827;");
        Label subTitle = new Label("Detailed rankings and performance analysis");
        subTitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #6b7280;");
        headerTitleBox.getChildren().addAll(mainTitle, subTitle);

        HBox playerHeaderCard = new HBox(25);
        playerHeaderCard.setPadding(new Insets(20, 30, 20, 30));
        playerHeaderCard.setAlignment(Pos.CENTER_LEFT);
        playerHeaderCard.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 8, 0, 0, 2);");

        Node avatar = getPlayerAvatar(playerName, imgPath, 70, "#1e3a8a", "white");

        VBox nameDetails = new VBox(6);
        Label pName = new Label(playerName + " ✔");
        pName.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #111827;");
        Label pRole = new Label("Right Hand Batsman  •  🇮🇳 India");
        pRole.setStyle("-fx-font-size: 13px; -fx-text-fill: #6b7280;");
        nameDetails.getChildren().addAll(pName, pRole);

        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);

        HBox statsRow = new HBox(25);
        statsRow.getChildren().addAll(
            createHeaderStat("Matches", "128"),
            createHeaderStat("Runs", "6,523"),
            createHeaderStat("Average", "58.42"),
            createHeaderStat("Strike Rate", "92.1")
        );

        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        VBox pointsBox = new VBox(2);
        pointsBox.setAlignment(Pos.CENTER);
        Label ptLabel = new Label("Ranking Points");
        ptLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #6b7280;");
        Label ptVal = new Label("3,125");
        ptVal.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #111827;");
        Label rankLbl = new Label("Overall Rank: 1");
        rankLbl.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #16a34a;");
        pointsBox.getChildren().addAll(ptLabel, ptVal, rankLbl);

        playerHeaderCard.getChildren().addAll(avatar, nameDetails, spacer1, statsRow, spacer2, pointsBox);

        HBox subTabs = new HBox(20);
        subTabs.getChildren().addAll(
            createSubTab("Overview", true),
            createSubTab("Batting", false),
            createSubTab("Bowling", false),
            createSubTab("Fielding", false),
            createSubTab("All Formats", false),
            createSubTab("Recent Form", false),
            createSubTab("Awards", false)
        );

        HBox detailSplit = new HBox(25);
        VBox leftDetail = new VBox(20);
        HBox.setHgrow(leftDetail, Priority.ALWAYS);

        leftDetail.getChildren().addAll(
            buildRadarChartWidget(),
            buildRecentPerformanceWidget()
        );

        VBox rightDetail = new VBox(20);
        rightDetail.setPrefWidth(300);
        rightDetail.setMinWidth(300);

        rightDetail.getChildren().addAll(
            buildCategoryRankingsWidget(),
            buildComparePlayersWidget(),
            buildAchievementsWidget()
        );

        detailSplit.getChildren().addAll(leftDetail, rightDetail);

        VBox pageLayout = new VBox(20, backBtn, headerTitleBox, playerHeaderCard, subTabs, detailSplit);
        ScrollPane detailScroll = new ScrollPane(pageLayout);
        detailScroll.setFitToWidth(true);
        detailScroll.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
        VBox.setVgrow(detailScroll, Priority.ALWAYS);

        this.getChildren().add(detailScroll);
    }

    private VBox createHeaderStat(String title, String val) {
        VBox b = new VBox(2);
        b.setAlignment(Pos.CENTER);
        Label t = new Label(title);
        t.setStyle("-fx-font-size: 11px; -fx-text-fill: #6b7280;");
        Label v = new Label(val);
        v.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #111827;");
        b.getChildren().addAll(t, v);
        return b;
    }

    private Label createSubTab(String text, boolean active) {
        Label l = new Label(text);
        l.setCursor(Cursor.HAND);
        if (active) {
            l.setStyle("-fx-text-fill: #16a34a; -fx-font-weight: bold; -fx-border-color: #16a34a; -fx-border-width: 0 0 2 0; -fx-padding: 5 10;");
        } else {
            l.setStyle("-fx-text-fill: #6b7280; -fx-font-weight: bold; -fx-padding: 5 10;");
        }
        return l;
    }

    private VBox buildRadarChartWidget() {
        VBox widget = new VBox(15);
        widget.setPadding(new Insets(20));
        widget.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12;");

        Label title = new Label("Ranking Summary");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: #111827;");

        StackPane chartPane = new StackPane();
        chartPane.setPrefSize(350, 220);

        Polygon pentagon = new Polygon();
        pentagon.getPoints().addAll(new Double[]{
            175.0, 30.0,
            270.0, 100.0,
            230.0, 190.0,
            120.0, 190.0,
            80.0, 100.0
        });
        pentagon.setStyle("-fx-fill: rgba(34, 197, 94, 0.25); -fx-stroke: #16a34a; -fx-stroke-width: 2;");

        Label bat = new Label("Batting\n  89");
        bat.setStyle("-fx-font-size: 11px; -fx-font-weight: bold;");
        StackPane.setAlignment(bat, Pos.TOP_CENTER);

        Label bowl = new Label("Bowling\n  45");
        bowl.setStyle("-fx-font-size: 11px; -fx-font-weight: bold;");
        StackPane.setAlignment(bowl, Pos.CENTER_RIGHT);

        Label fld = new Label("Fielding\n  78");
        fld.setStyle("-fx-font-size: 11px; -fx-font-weight: bold;");
        StackPane.setAlignment(fld, Pos.BOTTOM_RIGHT);

        Label con = new Label("Consistency\n   88");
        con.setStyle("-fx-font-size: 11px; -fx-font-weight: bold;");
        StackPane.setAlignment(con, Pos.BOTTOM_LEFT);

        Label imp = new Label("Impact\n  90");
        imp.setStyle("-fx-font-size: 11px; -fx-font-weight: bold;");
        StackPane.setAlignment(imp, Pos.CENTER_LEFT);

        chartPane.getChildren().addAll(pentagon, bat, bowl, fld, con, imp);
        widget.getChildren().addAll(title, chartPane);
        return widget;
    }

    private VBox buildRecentPerformanceWidget() {
        VBox widget = new VBox(15);
        widget.setPadding(new Insets(20));
        widget.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12;");

        Label title = new Label("Recent Performance (Last 5 Matches)");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: #111827;");

        VBox table = new VBox(0);
        HBox header = new HBox(10);
        header.setPadding(new Insets(10, 0, 10, 0));
        header.setStyle("-fx-border-color: #e5e7eb; -fx-border-width: 0 0 1 0;");
        
        header.getChildren().addAll(
            createHeaderLabel("Match", 110),
            createHeaderLabel("Format", 60),
            createHeaderLabel("Opponent", 100),
            createHeaderLabel("Runs", 50),
            createHeaderLabel("Balls", 50),
            createHeaderLabel("SR", 60),
            createHeaderLabel("Result", 60),
            createHeaderLabel("Points", 60)
        );

        VBox rows = new VBox();
        rows.getChildren().addAll(
            buildMatchRow("vs Pune Strikers", "T20", "Pune Strikers", "85", "52", "163.46", "Won", "125"),
            buildMatchRow("vs Delhi Daredevils", "T20", "Delhi Daredevils", "76", "45", "168.88", "Won", "115"),
            buildMatchRow("vs Bangalore Smashers", "T20", "Bangalore Smashers", "12", "18", "66.67", "Lost", "20"),
            buildMatchRow("vs Chennai Kings", "ODI", "Chennai Kings", "103", "89", "115.73", "Won", "140")
        );

        table.getChildren().addAll(header, rows);
        widget.getChildren().addAll(title, table);
        return widget;
    }

    private HBox buildMatchRow(String m, String fmt, String opp, String r, String b, String sr, String res, String pts) {
        HBox row = new HBox(10);
        row.setPadding(new Insets(10, 0, 10, 0));
        row.setStyle("-fx-border-color: #f3f4f6; -fx-border-width: 0 0 1 0;");

        Label mLbl = new Label(m); mLbl.setPrefWidth(110); mLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        Label fLbl = new Label(fmt); fLbl.setPrefWidth(60); fLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b7280;");
        Label oLbl = new Label(opp); oLbl.setPrefWidth(100); oLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b7280;");
        Label rLbl = new Label(r); rLbl.setPrefWidth(50); rLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        Label bLbl = new Label(b); bLbl.setPrefWidth(50); bLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b7280;");
        Label sLbl = new Label(sr); sLbl.setPrefWidth(60); sLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b7280;");
        
        Label resLbl = new Label(res); resLbl.setPrefWidth(60); 
        if (res.equals("Won")) resLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #16a34a; -fx-font-size: 12px;");
        else resLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #dc2626; -fx-font-size: 12px;");

        Label pLbl = new Label(pts); pLbl.setPrefWidth(60); pLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #16a34a; -fx-font-size: 12px;");

        row.getChildren().addAll(mLbl, fLbl, oLbl, rLbl, bLbl, sLbl, resLbl, pLbl);
        return row;
    }

    private VBox buildCategoryRankingsWidget() {
        VBox widget = new VBox(12);
        widget.setPadding(new Insets(20));
        widget.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12;");

        Label title = new Label("Rankings in Categories");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: #111827;");

        widget.getChildren().addAll(title,
            createCatRankRow("🎯 Overall Ranking", "1"),
            createCatRankRow("🏏 ODI Ranking", "1"),
            createCatRankRow("⚾ T20 Ranking", "2"),
            createCatRankRow("📜 Test Ranking", "1"),
            createCatRankRow("⚡ Batsman Ranking", "1"),
            createCatRankRow("🛡️ All Rounder Ranking", "2")
        );
        return widget;
    }

    private HBox createCatRankRow(String label, String rank) {
        HBox r = new HBox();
        Label l = new Label(label); l.setStyle("-fx-font-size: 12px; -fx-text-fill: #374151;");
        Region sp = new Region(); HBox.setHgrow(sp, Priority.ALWAYS);
        Label val = new Label(rank); val.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #111827;");
        r.getChildren().addAll(l, sp, val);
        return r;
    }

    private VBox buildComparePlayersWidget() {
        VBox widget = new VBox(12);
        widget.setPadding(new Insets(20));
        widget.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12;");

        Label title = new Label("Compare Players");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: #111827;");

        ComboBox<String> select = new ComboBox<>();
        select.setPromptText("Select Player");
        select.setStyle("-fx-background-color: white; -fx-border-color: #e5e7eb; -fx-border-radius: 6;");
        select.setMaxWidth(Double.MAX_VALUE);

        VBox list = new VBox(10);
        list.getChildren().addAll(
            createMiniPlayerRow("Virat Kohli", "3,125 PTS"),
            createMiniPlayerRow("Rohit Sharma", "2,210 PTS"),
            createMiniPlayerRow("Surya Kumar Yadav", "2,450 PTS")
        );

        Button cmpBtn = new Button("Compare");
        cmpBtn.setMaxWidth(Double.MAX_VALUE);
        cmpBtn.setCursor(Cursor.HAND);
        cmpBtn.setStyle("-fx-background-color: #16a34a; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 8;");

        widget.getChildren().addAll(title, select, list, cmpBtn);
        return widget;
    }

    private HBox createMiniPlayerRow(String name, String pts) {
        HBox r = new HBox(10);
        r.setAlignment(Pos.CENTER_LEFT);
        
        Label av = new Label(name.substring(0, 1));
        av.setPrefSize(25, 25);
        av.setAlignment(Pos.CENTER);
        av.setStyle("-fx-background-color: #e2e8f0; -fx-text-fill: #475569; -fx-background-radius: 12.5; -fx-font-size: 11px; -fx-font-weight: bold;");
        
        Label n = new Label(name); n.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        Region sp = new Region(); HBox.setHgrow(sp, Priority.ALWAYS);
        Label p = new Label(pts); p.setStyle("-fx-text-fill: #16a34a; -fx-font-weight: bold; -fx-font-size: 11px;");
        r.getChildren().addAll(av, n, sp, p);
        return r;
    }

    private VBox buildAchievementsWidget() {
        VBox widget = new VBox(12);
        widget.setPadding(new Insets(20));
        widget.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12;");

        Label title = new Label("Achievements");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: #111827;");

        widget.getChildren().addAll(title,
            createAchievementRow("🏆", "Player of the Series", "5 Times"),
            createAchievementRow("🥇", "Player of the Match", "24 Times"),
            createAchievementRow("🏏", "Most Runs in T20", "2 Times"),
            createAchievementRow("🎗️", "ICC ODI Player Rank", "#1")
        );
        return widget;
    }

    private HBox createAchievementRow(String icon, String title, String sub) {
        HBox r = new HBox(10);
        r.setAlignment(Pos.CENTER_LEFT);
        Label ic = new Label(icon); ic.setStyle("-fx-font-size: 18px;");
        VBox b = new VBox(2);
        Label t = new Label(title); t.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: #111827;");
        Label s = new Label(sub); s.setStyle("-fx-font-size: 11px; -fx-text-fill: #6b7280;");
        b.getChildren().addAll(t, s);
        r.getChildren().addAll(ic, b);
        return r;
    }
}