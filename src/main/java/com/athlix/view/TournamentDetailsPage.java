package com.athlix.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TournamentDetailsPage {

    private Runnable onBackAction;
    private String titleTextData;
    private String venueData;
    private String feeData;
    private String prizePoolData;

    // Content containers for tab switching
    private HBox overviewContent;
    private Node rulesContent;
    private Node scheduleContent;
    private Node teamsContent;

    public TournamentDetailsPage(Runnable onBackAction, String titleTextData, String venueData, String feeData, String prizePoolData) {
        this.onBackAction = onBackAction;
        this.titleTextData = titleTextData;
        this.venueData = venueData;
        this.feeData = feeData;
        this.prizePoolData = prizePoolData;
    }

    public Node getView() {
        VBox mainContainer = new VBox(25);
        mainContainer.setPadding(new Insets(30));
        mainContainer.setStyle("-fx-background-color: #f8fafc;");

        // 1. Header Section
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);

        Button backBtn = new Button("❮  Back to List");
        backBtn.setStyle(
            "-fx-background-color: #ffffff; " +
            "-fx-text-fill: #10b981; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 14px; " +
            "-fx-padding: 8 18; " +
            "-fx-background-radius: 20; " +
            "-fx-border-color: #e2e8f0; " +
            "-fx-border-radius: 20; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 0, 2);"
        );
        backBtn.setOnAction(e -> {
            if (onBackAction != null) onBackAction.run();
        });

        HBox titleBox = new HBox(10);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        
        Label titleSymbol = new Label("🏆");
        titleSymbol.setStyle("-fx-font-size: 32px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 4, 0, 0, 2);");
        
        Label titleText = new Label(titleTextData);
        titleText.setStyle("-fx-font-size: 30px; -fx-font-weight: 900; -fx-text-fill: #0f172a;");
        
        titleBox.getChildren().addAll(titleSymbol, titleText);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button shareBtn = new Button("Share");
        shareBtn.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-radius: 6; -fx-text-fill: #475569; -fx-padding: 8 16; -fx-cursor: hand; -fx-font-weight: bold;");
        
        header.getChildren().addAll(backBtn, titleBox, spacer, shareBtn);

        // 2. Tabs Navigation
        HBox tabs = new HBox(30);
        tabs.setStyle("-fx-border-color: #e2e8f0; -fx-border-width: 0 0 1 0;");
        
        Label overviewTab = createTabLabel("Overview", true);
        Label rulesTab = createTabLabel("Rules", false);
        Label scheduleTab = createTabLabel("Schedule", false);
        Label teamsTab = createTabLabel("Teams", false);
        
        tabs.getChildren().addAll(overviewTab, rulesTab, scheduleTab, teamsTab);

        // 3. Content Area (StackPane to handle dynamic tab toggling)
        StackPane contentArea = new StackPane();
        contentArea.setAlignment(Pos.TOP_LEFT);
        
        overviewContent = buildOverviewContent();
        
        TournamentRulesPage rulesPage = new TournamentRulesPage();
        rulesContent = rulesPage.getView();
        rulesContent.setVisible(false); rulesContent.setManaged(false);

        TournamentSchedulePage schedulePage = new TournamentSchedulePage(titleTextData);
        scheduleContent = schedulePage.getView();
        scheduleContent.setVisible(false); scheduleContent.setManaged(false);

        TournamentTeamPage teamsPage = new TournamentTeamPage(titleTextData);
        teamsContent = teamsPage.getView();
        teamsContent.setVisible(false); teamsContent.setManaged(false);

        contentArea.getChildren().addAll(overviewContent, rulesContent, scheduleContent, teamsContent);

        // 4. Tab Click Event Listeners
        overviewTab.setOnMouseClicked(e -> {
            setActiveTabStyle(overviewTab, rulesTab, scheduleTab, teamsTab);
            overviewContent.setVisible(true); overviewContent.setManaged(true);
            rulesContent.setVisible(false); rulesContent.setManaged(false);
            scheduleContent.setVisible(false); scheduleContent.setManaged(false);
            teamsContent.setVisible(false); teamsContent.setManaged(false);
        });

        rulesTab.setOnMouseClicked(e -> {
            setActiveTabStyle(rulesTab, overviewTab, scheduleTab, teamsTab);
            overviewContent.setVisible(false); overviewContent.setManaged(false);
            rulesContent.setVisible(true); rulesContent.setManaged(true);
            scheduleContent.setVisible(false); scheduleContent.setManaged(false);
            teamsContent.setVisible(false); teamsContent.setManaged(false);
        });

        scheduleTab.setOnMouseClicked(e -> {
            setActiveTabStyle(scheduleTab, overviewTab, rulesTab, teamsTab);
            overviewContent.setVisible(false); overviewContent.setManaged(false);
            rulesContent.setVisible(false); rulesContent.setManaged(false);
            scheduleContent.setVisible(true); scheduleContent.setManaged(true);
            teamsContent.setVisible(false); teamsContent.setManaged(false);
        });

        // Fixed click event for Teams tab
        teamsTab.setOnMouseClicked(e -> {
            setActiveTabStyle(teamsTab, overviewTab, rulesTab, scheduleTab);
            overviewContent.setVisible(false); overviewContent.setManaged(false);
            rulesContent.setVisible(false); rulesContent.setManaged(false);
            scheduleContent.setVisible(false); scheduleContent.setManaged(false);
            teamsContent.setVisible(true); teamsContent.setManaged(true);
        });

        mainContainer.getChildren().addAll(header, tabs, contentArea);

        ScrollPane scrollPane = new ScrollPane(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f8fafc;");
        scrollPane.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");

        return scrollPane;
    }

    private HBox buildOverviewContent() {
        HBox layout = new HBox(30);
        
        VBox leftColumn = new VBox(25);
        HBox.setHgrow(leftColumn, Priority.ALWAYS);
        leftColumn.setPadding(new Insets(25));
        leftColumn.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12;");

        Label infoTitle = new Label("Tournament Information");
        infoTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        VBox detailsList = new VBox(15);
        detailsList.getChildren().addAll(
            createDetailRow("Venue", venueData),
            createDetailRow("Match Type", "Tennis Ball"),
            createDetailRow("Overs", "20 Overs"),
            createDetailRow("Players", "16 (11 Playing)"),
            createDetailRow("R.F (Entry Fee)", feeData),
            createDetailRow("Prize Pool", prizePoolData),
            createDetailRow("Start Date", "15 Sept 2026")
        );

        Button registerBtn = new Button("Register Team");
        registerBtn.setMaxWidth(Double.MAX_VALUE);
        registerBtn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 12 24; -fx-background-radius: 8; -fx-cursor: hand; -fx-margin-top: 20px;");

        leftColumn.getChildren().addAll(infoTitle, detailsList, registerBtn);

        VBox rightColumn = new VBox(20);
        rightColumn.setPrefWidth(400);
        rightColumn.setMinWidth(400);
        rightColumn.setPadding(new Insets(25));
        rightColumn.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12;");

        Label prizeTitle = new Label("Prize Distribution 🏆");
        prizeTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        VBox prizesList = new VBox(15);
        prizesList.getChildren().addAll(
            createPrizeRow("Winner", "₹ 30,000", true),
            createPrizeRow("Runner-up", "₹ 15,000", false),
            createPrizeRow("Man of the Match", "₹ 3,000", false),
            createPrizeRow("Best Batsman", "₹ 1,000", false),
            createPrizeRow("Best Bowler", "₹ 1,000", false)
        );

        rightColumn.getChildren().addAll(prizeTitle, prizesList);
        layout.getChildren().addAll(leftColumn, rightColumn);
        return layout;
    }

    private Label createTabLabel(String text, boolean active) {
        Label lbl = new Label(text);
        if (active) {
            lbl.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #10b981; -fx-border-color: #10b981; -fx-border-width: 0 0 3 0; -fx-padding: 0 0 10 0; -fx-cursor: hand;");
        } else {
            lbl.setStyle("-fx-font-size: 16px; -fx-text-fill: #64748b; -fx-padding: 0 0 10 0; -fx-cursor: hand;");
        }
        return lbl;
    }

    private void setActiveTabStyle(Label activeTab, Label... inactiveTabs) {
        activeTab.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #10b981; -fx-border-color: #10b981; -fx-border-width: 0 0 3 0; -fx-padding: 0 0 10 0; -fx-cursor: hand;");
        for (Label tab : inactiveTabs) {
            tab.setStyle("-fx-font-size: 16px; -fx-text-fill: #64748b; -fx-padding: 0 0 10 0; -fx-cursor: hand;");
        }
    }

    private HBox createDetailRow(String label, String value) {
        HBox row = new HBox();
        Label lblName = new Label(label);
        lblName.setStyle("-fx-text-fill: #64748b; -fx-font-size: 14px;");
        lblName.setPrefWidth(150); 
        Label lblValue = new Label(value);
        lblValue.setStyle("-fx-text-fill: #0f172a; -fx-font-size: 14px; -fx-font-weight: bold;");
        row.getChildren().addAll(lblName, lblValue);
        return row;
    }

    private HBox createPrizeRow(String title, String amount, boolean isHighlight) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-border-color: #e2e8f0; -fx-border-width: 0 0 1 0; -fx-padding: 10 0;");
        Label lblTitle = new Label(title);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Label lblAmount = new Label(amount);
        
        if (isHighlight) {
            lblTitle.setStyle("-fx-text-fill: #10b981; -fx-font-size: 16px; -fx-font-weight: bold;");
            lblAmount.setStyle("-fx-text-fill: #10b981; -fx-font-size: 18px; -fx-font-weight: bold;");
        } else {
            lblTitle.setStyle("-fx-text-fill: #475569; -fx-font-size: 14px;");
            lblAmount.setStyle("-fx-text-fill: #0f172a; -fx-font-size: 14px; -fx-font-weight: bold;");
        }
        row.getChildren().addAll(lblTitle, spacer, lblAmount);
        return row;
    }
}