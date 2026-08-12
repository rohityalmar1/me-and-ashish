package com.athlix.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CompletedTournamentviewTournamentCompletedMatchesViewMatchs {

    private String matchTitle;
    private Runnable onBackAction;
    private StackPane contentArea;

    public CompletedTournamentviewTournamentCompletedMatchesViewMatchs(String matchTitle, Runnable onBackAction) {
        this.matchTitle = matchTitle;
        this.onBackAction = onBackAction;
    }

    public Node getView() {
        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(25, 25, 80, 25));
        mainContainer.setStyle("-fx-background-color: #f8fafc;");

        // --- HEADER SECTION ---
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);

        Button backBtn = new Button("❮  Back");
        backBtn.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #10b981; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 8 18; -fx-background-radius: 20; -fx-border-color: #e2e8f0; -fx-border-radius: 20; -fx-cursor: hand;");
        backBtn.setOnAction(e -> {
            if (onBackAction != null) onBackAction.run();
        });

        Label headerTitle = new Label("🏏 " + matchTitle);
        headerTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        header.getChildren().addAll(backBtn, headerTitle);

        // --- MATCH HERO SCORECARD CARD ---
        VBox heroScoreCard = createHeroScoreCard();

        // --- SUB-TABS: Overview, Scorecard, Stats ---
        HBox subTabs = new HBox(25);
        subTabs.setStyle("-fx-border-color: #e2e8f0; -fx-border-width: 0 0 1 0; -fx-padding: 0 0 10 0;");

        Label overviewTab = createSubTab("Overview", true);
        Label scorecardTab = createSubTab("Scorecard", false);
        Label statsTab = createSubTab("Stats", false);

        subTabs.getChildren().addAll(overviewTab, scorecardTab, statsTab);

        contentArea = new StackPane();
        contentArea.setAlignment(Pos.TOP_LEFT);

        // View Contents
        VBox overviewView = buildOverviewContent();
        VBox scorecardView = buildScorecardContent();
        VBox statsView = buildStatsContent();

        contentArea.getChildren().add(overviewView);

        // Tab Listeners
        overviewTab.setOnMouseClicked(e -> {
            setActiveSubTab(overviewTab, scorecardTab, statsTab);
            contentArea.getChildren().setAll(overviewView);
        });

        scorecardTab.setOnMouseClicked(e -> {
            setActiveSubTab(scorecardTab, overviewTab, statsTab);
            contentArea.getChildren().setAll(scorecardView);
        });

        statsTab.setOnMouseClicked(e -> {
            setActiveSubTab(statsTab, overviewTab, scorecardTab);
            contentArea.getChildren().setAll(statsView);
        });

        mainContainer.getChildren().addAll(header, heroScoreCard, subTabs, contentArea);

        ScrollPane scrollPane = new ScrollPane(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f8fafc;");
        scrollPane.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");

        return scrollPane;
    }

    private VBox createHeroScoreCard() {
        VBox card = new VBox(15);
        card.setPadding(new Insets(20, 25, 20, 25));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #3b82f6 #e2e8f0 #e2e8f0 #e2e8f0; -fx-border-width: 3 1 1 1; -fx-border-radius: 12;");

        // Top info
        HBox topInfo = new HBox();
        Label stageLbl = new Label("🏆 FINAL MATCH • 18 May 2026 • Shivaji Park, Mumbai");
        stageLbl.setStyle("-fx-text-fill: #64748b; -fx-font-size: 12px; -fx-font-weight: bold;");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Label statusBadge = new Label("COMPLETED");
        statusBadge.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #475569; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 3 8; -fx-background-radius: 6;");
        topInfo.getChildren().addAll(stageLbl, spacer, statusBadge);

        // Scores Box
        HBox scoreBox = new HBox(40);
        scoreBox.setAlignment(Pos.CENTER);

        VBox team1 = createTeamHeroBlock("https://dummyimage.com/60x60/3b82f6/ffffff.png&text=MM", "Mumbai Masters (MM)", "185/4 (20.0 Ov)");
        Label vs = new Label("VS");
        vs.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 16px; -fx-font-weight: bold;");
        VBox team2 = createTeamHeroBlock("https://dummyimage.com/60x60/10b981/ffffff.png&text=PS", "Pune Strikers (PS)", "162/9 (20.0 Ov)");

        scoreBox.getChildren().addAll(team1, vs, team2);

        // Result Highlight
        HBox resultBox = new HBox();
        resultBox.setAlignment(Pos.CENTER);
        resultBox.setPadding(new Insets(8, 14, 8, 14));
        resultBox.setStyle("-fx-background-color: #ecfdf5; -fx-background-radius: 8; -fx-border-color: #a7f3d0; -fx-border-radius: 8;");
        Label resLbl = new Label("🎉 Mumbai Masters won by 23 runs");
        resLbl.setStyle("-fx-text-fill: #065f46; -fx-font-size: 14px; -fx-font-weight: bold;");
        resultBox.getChildren().add(resLbl);

        card.getChildren().addAll(topInfo, scoreBox, resultBox);
        return card;
    }

    private VBox createTeamHeroBlock(String logoUrl, String name, String score) {
        VBox box = new VBox(6);
        box.setAlignment(Pos.CENTER);

        ImageView logo = new ImageView();
        try { Image img = new Image(logoUrl, true); logo.setImage(img); } catch (Exception e) {}
        logo.setFitWidth(45);
        logo.setFitHeight(45);
        logo.setPreserveRatio(true);
        logo.setClip(new Circle(22.5, 22.5, 22.5));

        Label nameLbl = new Label(name);
        nameLbl.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        Label scoreLbl = new Label(score);
        scoreLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #059669;");

        box.getChildren().addAll(logo, nameLbl, scoreLbl);
        return box;
    }

    // --- 1. OVERVIEW CONTENT ---
    private VBox buildOverviewContent() {
        VBox box = new VBox(20);
        box.setPadding(new Insets(10, 0, 10, 0));

        // Player of the Match Card
        HBox potmCard = new HBox(20);
        potmCard.setAlignment(Pos.CENTER_LEFT);
        potmCard.setPadding(new Insets(20));
        potmCard.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #fef08a; -fx-border-width: 2; -fx-border-radius: 12;");

        ImageView potmImg = new ImageView();
        try { Image img = new Image("https://dummyimage.com/80x80/cbd5e1/0f172a.png&text=POTM", true); potmImg.setImage(img); } catch (Exception e) {}
        potmImg.setFitWidth(65);
        potmImg.setFitHeight(65);
        potmImg.setClip(new Circle(32.5, 32.5, 32.5));

        VBox potmInfo = new VBox(4);
        Label potmBadge = new Label("⭐ PLAYER OF THE MATCH");
        potmBadge.setStyle("-fx-text-fill: #ca8a04; -fx-font-size: 11px; -fx-font-weight: bold;");
        Label potmName = new Label("Aarav Sharma (Mumbai Masters)");
        potmName.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        Label potmStats = new Label("🏏 78 runs (42 balls, 8 fours, 4 sixes) & 1/18 (3.0 ov)");
        potmStats.setStyle("-fx-font-size: 13px; -fx-text-fill: #475569;");
        potmInfo.getChildren().addAll(potmBadge, potmName, potmStats);

        potmCard.getChildren().addAll(potmImg, potmInfo);

        // Match Key Highlights Card
        VBox highlightsCard = new VBox(12);
        highlightsCard.setPadding(new Insets(20));
        highlightsCard.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12;");

        Label hlTitle = new Label("📌 Match Key Highlights");
        hlTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        highlightsCard.getChildren().addAll(
            hlTitle,
            createHighlightBullet("Toss: Mumbai Masters won the toss & elected to bat first."),
            createHighlightBullet("1st Innings: Mumbai Masters posted 185/4 powered by Aarav Sharma's 78 off 42 balls."),
            createHighlightBullet("Key Bowling: Rohan Mehta took 3/28 in 4 overs for Pune Strikers."),
            createHighlightBullet("2nd Innings: Pune Strikers restricted to 162/9 despite a valiant 54 by Kunal Verma.")
        );

        box.getChildren().addAll(potmCard, highlightsCard);
        return box;
    }

    private HBox createHighlightBullet(String text) {
        HBox row = new HBox(8);
        row.setAlignment(Pos.CENTER_LEFT);
        Label bullet = new Label("•");
        bullet.setStyle("-fx-text-fill: #10b981; -fx-font-size: 16px; -fx-font-weight: bold;");
        Label lbl = new Label(text);
        lbl.setStyle("-fx-font-size: 13px; -fx-text-fill: #475569;");
        row.getChildren().addAll(bullet, lbl);
        return row;
    }

    // --- 2. SCORECARD CONTENT ---
    private VBox buildScorecardContent() {
        VBox box = new VBox(20);
        box.setPadding(new Insets(10, 0, 10, 0));

        // 1st Innings Batting Table
        VBox innings1 = createInningsSection(
            "Mumbai Masters Innings - 185/4 (20.0 Ov)",
            new String[][]{
                {"Aarav Sharma", "c Verma b Mehta", "78", "42", "8", "4", "185.71"},
                {"Sameer Khan", "b Patil", "34", "26", "4", "1", "130.77"},
                {"Vikram Deshmukh", "not out", "45", "30", "3", "2", "150.00"},
                {"Rahul Joshi", "run out", "12", "11", "1", "0", "109.09"}
            }
        );

        // 2nd Innings Batting Table
        VBox innings2 = createInningsSection(
            "Pune Strikers Innings - 162/9 (20.0 Ov)",
            new String[][]{
                {"Kunal Verma", "c Deshmukh b Sharma", "54", "38", "5", "2", "142.11"},
                {"Aditya Shinde", "lbw b Joshi", "22", "17", "2", "1", "129.41"},
                {"Rohan Mehta", "c Khan b Joshi", "18", "14", "1", "1", "128.57"},
                {"Nikhil Rao", "b Sharma", "8", "9", "0", "0", "88.89"}
            }
        );

        box.getChildren().addAll(innings1, innings2);
        return box;
    }

    private VBox createInningsSection(String headerText, String[][] batters) {
        VBox section = new VBox(10);
        section.setPadding(new Insets(15));
        section.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12;");

        Label header = new Label(headerText);
        header.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        // Table Header
        HBox th = new HBox();
        th.setPadding(new Insets(8, 12, 8, 12));
        th.setStyle("-fx-background-color: #f8fafc; -fx-background-radius: 6;");
        th.getChildren().addAll(
            createHeaderCell("Batter", 180, Pos.CENTER_LEFT),
            createHeaderCell("Dismissal", 160, Pos.CENTER_LEFT),
            createHeaderCell("R", 45, Pos.CENTER),
            createHeaderCell("B", 45, Pos.CENTER),
            createHeaderCell("4s", 45, Pos.CENTER),
            createHeaderCell("6s", 45, Pos.CENTER),
            createHeaderCell("SR", 65, Pos.CENTER)
        );
        HBox.setHgrow(th.getChildren().get(0), Priority.ALWAYS);

        section.getChildren().addAll(header, th);

        for (String[] b : batters) {
            HBox row = new HBox();
            row.setPadding(new Insets(8, 12, 8, 12));
            row.setStyle("-fx-border-color: #f1f5f9; -fx-border-width: 0 0 1 0;");
            row.getChildren().addAll(
                createDataCell(b[0], 180, Pos.CENTER_LEFT, true),
                createDataCell(b[1], 160, Pos.CENTER_LEFT, false),
                createDataCell(b[2], 45, Pos.CENTER, true),
                createDataCell(b[3], 45, Pos.CENTER, false),
                createDataCell(b[4], 45, Pos.CENTER, false),
                createDataCell(b[5], 45, Pos.CENTER, false),
                createDataCell(b[6], 65, Pos.CENTER, false)
            );
            HBox.setHgrow(row.getChildren().get(0), Priority.ALWAYS);
            section.getChildren().add(row);
        }

        return section;
    }

    private Label createHeaderCell(String text, double width, Pos align) {
        Label l = new Label(text);
        l.setPrefWidth(width);
        l.setAlignment(align);
        l.setStyle("-fx-font-weight: bold; -fx-text-fill: #64748b; -fx-font-size: 12px;");
        return l;
    }

    private Label createDataCell(String text, double width, Pos align, boolean bold) {
        Label l = new Label(text);
        l.setPrefWidth(width);
        l.setAlignment(align);
        l.setStyle("-fx-text-fill: #0f172a; -fx-font-size: 13px;" + (bold ? " -fx-font-weight: bold;" : ""));
        return l;
    }

    // --- 3. STATS CONTENT (Scoring Comparison Worm Graph) ---
    private VBox buildStatsContent() {
        VBox box = new VBox(20);
        box.setPadding(new Insets(10, 0, 10, 0));

        VBox chartCard = new VBox(15);
        chartCard.setPadding(new Insets(20));
        chartCard.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12;");

        // Section Title
        Label title = new Label("📈 Scoring comparison");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        // Legend: MM (Green) & PS (Slate Gray)
        HBox legendBox = new HBox(25);
        legendBox.setAlignment(Pos.CENTER_LEFT);
        legendBox.setPadding(new Insets(0, 0, 10, 10));

        HBox team1Legend = createLegendItem("MM", "#10b981", true);
        HBox team2Legend = createLegendItem("PS", "#94a3b8", false);
        legendBox.getChildren().addAll(team1Legend, team2Legend);

        // Chart Axes
        NumberAxis xAxis = new NumberAxis(0, 20, 5);
        xAxis.setLabel("Overs");
        xAxis.setStyle("-fx-tick-label-fill: #64748b;");

        NumberAxis yAxis = new NumberAxis(0, 220, 50);
        yAxis.setLabel("Runs");
        yAxis.setStyle("-fx-tick-label-fill: #64748b;");

        // Create LineChart
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(null);
        lineChart.setLegendVisible(false);
        lineChart.setAnimated(false);
        lineChart.setPrefHeight(380);
        lineChart.setStyle("-fx-background-color: transparent;");

        // Series 1: Mumbai Masters (MM) - Green line
        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        series1.setName("MM");
        series1.getData().add(new XYChart.Data<>(0, 0));
        series1.getData().add(new XYChart.Data<>(2, 18));
        series1.getData().add(new XYChart.Data<>(4, 38));
        series1.getData().add(new XYChart.Data<>(6, 52));
        series1.getData().add(new XYChart.Data<>(8, 72));
        series1.getData().add(new XYChart.Data<>(10, 88));
        series1.getData().add(new XYChart.Data<>(12, 108));
        series1.getData().add(new XYChart.Data<>(14, 126));
        series1.getData().add(new XYChart.Data<>(16, 146));
        series1.getData().add(new XYChart.Data<>(18, 168));
        series1.getData().add(new XYChart.Data<>(20, 185));

        // Series 2: Pune Strikers (PS) - Slate/Gray line
        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        series2.setName("PS");
        series2.getData().add(new XYChart.Data<>(0, 0));
        series2.getData().add(new XYChart.Data<>(2, 14));
        series2.getData().add(new XYChart.Data<>(4, 30));
        series2.getData().add(new XYChart.Data<>(6, 44));
        series2.getData().add(new XYChart.Data<>(8, 62));
        series2.getData().add(new XYChart.Data<>(10, 78));
        series2.getData().add(new XYChart.Data<>(12, 94));
        series2.getData().add(new XYChart.Data<>(14, 114));
        series2.getData().add(new XYChart.Data<>(16, 132));
        series2.getData().add(new XYChart.Data<>(18, 148));
        series2.getData().add(new XYChart.Data<>(20, 162));

        lineChart.getData().addAll(series1, series2);

        // Apply Custom Colors & CSS
        String chartCss = "data:text/css," +
            ".default-color0.chart-series-line { -fx-stroke: #10b981; -fx-stroke-width: 3px; }" +
            ".default-color0.chart-line-symbol { -fx-background-color: #10b981, white; -fx-background-radius: 4px; -fx-padding: 4px; }" +
            ".default-color1.chart-series-line { -fx-stroke: #94a3b8; -fx-stroke-width: 3px; }" +
            ".default-color1.chart-line-symbol { -fx-background-color: #94a3b8, white; -fx-background-radius: 4px; -fx-padding: 4px; }" +
            ".chart-plot-background { -fx-background-color: #f8fafc; -fx-background-radius: 8px; }" +
            ".chart-vertical-grid-lines { -fx-stroke: #e2e8f0; }" +
            ".chart-horizontal-grid-lines { -fx-stroke: #e2e8f0; }";

        lineChart.getStylesheets().add(chartCss);

        chartCard.getChildren().addAll(title, legendBox, lineChart);
        box.getChildren().add(chartCard);
        return box;
    }

    private HBox createLegendItem(String labelText, String hexColor, boolean isCircle) {
        HBox item = new HBox(8);
        item.setAlignment(Pos.CENTER_LEFT);

        Region dot;
        if (isCircle) {
            Circle circle = new Circle(6, Color.web(hexColor));
            item.getChildren().add(circle);
        } else {
            dot = new Region();
            dot.setPrefSize(12, 12);
            dot.setStyle("-fx-background-color: " + hexColor + "; -fx-background-radius: 2px;");
            item.getChildren().add(dot);
        }

        Label lbl = new Label(labelText);
        lbl.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #475569;");
        item.getChildren().add(lbl);

        return item;
    }

    // --- SUB-TAB HELPERS ---
    private Label createSubTab(String text, boolean active) {
        Label lbl = new Label(text);
        if (active) {
            lbl.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #10b981; -fx-border-color: #10b981; -fx-border-width: 0 0 3 0; -fx-padding: 0 0 8 0;");
        } else {
            lbl.setStyle("-fx-font-size: 15px; -fx-text-fill: #64748b; -fx-padding: 0 0 8 0; -fx-cursor: hand;");
            lbl.setOnMouseEntered(e -> lbl.setStyle("-fx-font-size: 15px; -fx-text-fill: #0f172a; -fx-font-weight: bold; -fx-padding: 0 0 8 0; -fx-cursor: hand;"));
            lbl.setOnMouseExited(e -> lbl.setStyle("-fx-font-size: 15px; -fx-text-fill: #64748b; -fx-font-weight: normal; -fx-padding: 0 0 8 0; -fx-cursor: hand;"));
        }
        return lbl;
    }

    private void setActiveSubTab(Label activeTab, Label... inactiveTabs) {
        activeTab.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #10b981; -fx-border-color: #10b981; -fx-border-width: 0 0 3 0; -fx-padding: 0 0 8 0;");
        for (Label tab : inactiveTabs) {
            tab.setStyle("-fx-font-size: 15px; -fx-text-fill: #64748b; -fx-padding: 0 0 8 0; -fx-cursor: hand;");
        }
    }
}