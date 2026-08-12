

package com.athlix.view.Scorecard;

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

import java.util.LinkedHashMap;
import java.util.Map;

public class ScorecardPage {

    private StackPane rootContainer;
    private StackPane contentArea;
    private ScrollPane mainScrollPane;
    
    // Layouts for different tabs
    private VBox currentMatchesLayout;
    private VBox myMatchesLayout;

    public Node getView() {
        if (rootContainer == null) {
            rootContainer = new StackPane();
            mainScrollPane = buildMainContent();
            rootContainer.getChildren().add(mainScrollPane);
        }
        return rootContainer;
    }

    private ScrollPane buildMainContent() {
        VBox mainLayout = new VBox(25);
        mainLayout.setPadding(new Insets(30, 30, 80, 30));
        mainLayout.setStyle("-fx-background-color: #f8fafc;");

        // --- HEADER SECTION ---
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(5);
        Label pageTitle = new Label("📊 Scorecard Dashboard");
        pageTitle.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        Label pageSubtitle = new Label("Manage and track all your tournament matches from one place.");
        pageSubtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748b;");
        titleBox.getChildren().addAll(pageTitle, pageSubtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button createMatchBtn = new Button("➕ Create Match");
        createMatchBtn.setStyle(
            "-fx-background-color: linear-gradient(to right, #3b82f6, #2563eb); " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 15px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 12 24; " +
            "-fx-background-radius: 8; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(59,130,246,0.4), 8, 0, 0, 4);"
        );

        ScaleTransition btnScaleIn = new ScaleTransition(Duration.millis(150), createMatchBtn);
        btnScaleIn.setToX(1.05); btnScaleIn.setToY(1.05);
        ScaleTransition btnScaleOut = new ScaleTransition(Duration.millis(150), createMatchBtn);
        btnScaleOut.setToX(1.0); btnScaleOut.setToY(1.0);
        
        createMatchBtn.setOnMouseEntered(e -> btnScaleIn.playFromStart());
        createMatchBtn.setOnMouseExited(e -> btnScaleOut.playFromStart());

        // --- TAB NAVIGATION ---
        HBox tabsContainer = new HBox(30);
        tabsContainer.setStyle("-fx-border-color: #e2e8f0; -fx-border-width: 0 0 2 0; -fx-padding: 0 0 10 0;");

        Label currentTab = createTab("🔴 Current Matches", true);
        Label upcomingTab = createTab("📅 Upcoming Matches", false);
        Label completedTab = createTab("✅ Completed Matches", false);
        Label myMatchesTab = createTab("👤 My Matches", false);

        tabsContainer.getChildren().addAll(currentTab, upcomingTab, completedTab, myMatchesTab);

        // --- CONTENT AREAS ---
        contentArea = new StackPane();
        contentArea.setAlignment(Pos.TOP_LEFT);
        
        currentMatchesLayout = buildCurrentMatchesView();
        VBox upcomingView = buildUpcomingMatchesView();
        VBox completedView = buildCompletedMatchesView();
        
        myMatchesLayout = new VBox(20);
        myMatchesLayout.setPadding(new Insets(15, 0, 0, 0));

        // --- CREATION ROUTING ---
        createMatchBtn.setOnAction(e -> {
            ScorecardCreateButton createPage = new ScorecardCreateButton(
                () -> {
                    rootContainer.getChildren().setAll(mainScrollPane);
                },
                (team1Name, team2Name, overs, tossResultText) -> {
                    String t1Initial = team1Name.isEmpty() ? "T" : String.valueOf(team1Name.charAt(0)).toUpperCase();
                    String t2Initial = team2Name.isEmpty() ? "T" : String.valueOf(team2Name.charAt(0)).toUpperCase();
                    
                    VBox newMatchCard = createMyMatchCard(
                        "NEW MATCH • " + overs + " Overs", "LIVE", 
                        team1Name, "0/0 (0.0 Ov)", "https://dummyimage.com/60x60/3b82f6/ffffff.png&text=" + t1Initial,
                        team2Name, "Yet to bat", "https://dummyimage.com/60x60/10b981/ffffff.png&text=" + t2Initial, 
                        tossResultText, "#3b82f6"
                    );
                    
                    myMatchesLayout.getChildren().add(0, newMatchCard);
                    setActiveTab(myMatchesTab, currentTab, upcomingTab, completedTab);
                    contentArea.getChildren().setAll(myMatchesLayout);
                }
            );
            rootContainer.getChildren().setAll(createPage.getView());
        });

        header.getChildren().addAll(titleBox, spacer, createMatchBtn);
        contentArea.getChildren().add(currentMatchesLayout);

        // Tab Switching Logic
        currentTab.setOnMouseClicked(e -> {
            setActiveTab(currentTab, upcomingTab, completedTab, myMatchesTab);
            contentArea.getChildren().setAll(currentMatchesLayout);
        });

        upcomingTab.setOnMouseClicked(e -> {
            setActiveTab(upcomingTab, currentTab, completedTab, myMatchesTab);
            contentArea.getChildren().setAll(upcomingView);
        });

        completedTab.setOnMouseClicked(e -> {
            setActiveTab(completedTab, currentTab, upcomingTab, myMatchesTab);
            contentArea.getChildren().setAll(completedView);
        });
        
        myMatchesTab.setOnMouseClicked(e -> {
            setActiveTab(myMatchesTab, currentTab, upcomingTab, completedTab);
            contentArea.getChildren().setAll(myMatchesLayout);
        });

        mainLayout.getChildren().addAll(header, tabsContainer, contentArea);

        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f8fafc;");
        scrollPane.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");

        return scrollPane;
    }

    private VBox buildCurrentMatchesView() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(15, 0, 0, 0));

        layout.getChildren().addAll(
            createMatchCard("LEAGUE MATCH 14 • Shivaji Park", "LIVE", 
                "Mumbai Masters", "124/3 (14.2 Ov)", "https://dummyimage.com/60x60/3b82f6/ffffff.png&text=MM",
                "Pune Strikers", "Yet to bat", "https://dummyimage.com/60x60/10b981/ffffff.png&text=PS", 
                "⚡ Mumbai Masters chose to bat", "#ef4444"),
            
            createMatchCard("QUARTER FINAL • NCA Grounds", "LIVE", 
                "Royal Titans", "180/6 (20.0 Ov)", "https://dummyimage.com/60x60/8b5cf6/ffffff.png&text=RT",
                "Nashik Warriors", "45/2 (5.1 Ov)", "https://dummyimage.com/60x60/f59e0b/ffffff.png&text=NW", 
                "⚡ Nashik Warriors need 136 runs in 89 balls", "#ef4444")
        );
        return layout;
    }

    private VBox buildUpcomingMatchesView() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(15, 0, 0, 0));

        layout.getChildren().addAll(
            createMatchCard("SEMI FINAL 1 • Wankhede Stadium", "UPCOMING", 
                "Tech Innovators CC", "Scheduled", "https://dummyimage.com/60x60/f43f5e/ffffff.png&text=TI",
                "Coastal Kings", "14 Aug, 10:00 AM", "https://dummyimage.com/60x60/06b6d4/ffffff.png&text=CK", 
                "Match starts in 2 days", "#3b82f6")
        );
        return layout;
    }

    private VBox buildCompletedMatchesView() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(15, 0, 0, 0));

        layout.getChildren().addAll(
            createMatchCard("LEAGUE MATCH 12 • Local Grounds", "COMPLETED", 
                "Nashik Warriors", "158/8 (20.0 Ov)", "https://dummyimage.com/60x60/f59e0b/ffffff.png&text=NW",
                "Royal Titans", "160/7 (19.4 Ov)", "https://dummyimage.com/60x60/8b5cf6/ffffff.png&text=RT", 
                "🎉 Royal Titans won by 3 wickets", "#10b981")
        );
        return layout;
    }

    // Generic Cards (Calls Static File)
    private VBox createMatchCard(String matchStage, String status, 
                                 String team1, String score1, String logoUrl1, 
                                 String team2, String score2, String logoUrl2, 
                                 String footerText, String accentColor) {
        return buildBaseMatchCard(matchStage, status, team1, score1, logoUrl1, team2, score2, logoUrl2, footerText, accentColor, false);
    }

    // My Matches Cards (Calls Dynamic File)
    private VBox createMyMatchCard(String matchStage, String status, 
                                 String team1, String score1, String logoUrl1, 
                                 String team2, String score2, String logoUrl2, 
                                 String footerText, String accentColor) {
        return buildBaseMatchCard(matchStage, status, team1, score1, logoUrl1, team2, score2, logoUrl2, footerText, accentColor, true);
    }

    // Centralized Card Builder
    private VBox buildBaseMatchCard(String matchStage, String status, 
                                 String team1, String score1, String logoUrl1, 
                                 String team2, String score2, String logoUrl2, 
                                 String footerText, String accentColor, boolean isMyMatch) {

        VBox card = new VBox(15);
        card.setPadding(new Insets(20, 25, 20, 25));
        card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12; -fx-border-color: " + accentColor + " #e2e8f0 #e2e8f0 #e2e8f0; -fx-border-width: 4 1 1 1; -fx-border-radius: 12;");

        HBox topHeader = new HBox();
        topHeader.setAlignment(Pos.CENTER_LEFT);

        Label stageBadge = new Label("🏆 " + matchStage);
        stageBadge.setStyle("-fx-text-fill: #475569; -fx-font-size: 13px; -fx-font-weight: bold;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label statusBadge = new Label(status);
        if (status.equals("LIVE")) {
            statusBadge.setStyle("-fx-background-color: #fee2e2; -fx-text-fill: #ef4444; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 4 10; -fx-background-radius: 6;");
        } else if (status.equals("COMPLETED")) {
            statusBadge.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #64748b; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 4 10; -fx-background-radius: 6;");
        } else {
            statusBadge.setStyle("-fx-background-color: #e0f2fe; -fx-text-fill: #0284c7; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 4 10; -fx-background-radius: 6;");
        }
        topHeader.getChildren().addAll(stageBadge, spacer, statusBadge);

        HBox scoreBox = new HBox(50);
        scoreBox.setAlignment(Pos.CENTER);
        VBox team1Box = createTeamScoreBlock(logoUrl1, team1, score1);
        Label vsLabel = new Label("VS");
        vsLabel.setStyle("-fx-text-fill: #cbd5e1; -fx-font-size: 16px; -fx-font-weight: bold;");
        VBox team2Box = createTeamScoreBlock(logoUrl2, team2, score2);
        scoreBox.getChildren().addAll(team1Box, vsLabel, team2Box);

        HBox footerBox = new HBox();
        footerBox.setAlignment(Pos.CENTER_LEFT);
        footerBox.setPadding(new Insets(10, 15, 10, 15));
        
        if (status.equals("LIVE")) {
            footerBox.setStyle("-fx-background-color: #fffbeb; -fx-background-radius: 8; -fx-border-color: #fde68a; -fx-border-radius: 8;");
            Label footerLbl = new Label(footerText);
            footerLbl.setStyle("-fx-text-fill: #b45309; -fx-font-size: 13px; -fx-font-weight: bold;");
            footerBox.getChildren().add(footerLbl);
        } else if (status.equals("COMPLETED")) {
            footerBox.setStyle("-fx-background-color: #ecfdf5; -fx-background-radius: 8; -fx-border-color: #a7f3d0; -fx-border-radius: 8;");
            Label footerLbl = new Label(footerText);
            footerLbl.setStyle("-fx-text-fill: #065f46; -fx-font-size: 13px; -fx-font-weight: bold;");
            footerBox.getChildren().add(footerLbl);
        } else {
            footerBox.setStyle("-fx-background-color: #f8fafc; -fx-background-radius: 8; -fx-border-color: #e2e8f0; -fx-border-radius: 8;");
            Label footerLbl = new Label(footerText);
            footerLbl.setStyle("-fx-text-fill: #64748b; -fx-font-size: 13px; -fx-font-weight: bold;");
            footerBox.getChildren().add(footerLbl);
        }

        HBox actionBox = new HBox(10);
        actionBox.setAlignment(Pos.CENTER_RIGHT);

        // --- THE EDIT BUTTON (Only for My Matches) ---
        if (isMyMatch) {
            Button editBtn = new Button("Edit ✏️");
            editBtn.setStyle("-fx-background-color: #fef08a; -fx-text-fill: #854d0e; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 6; -fx-padding: 8 16; -fx-cursor: hand;");
            editBtn.setOnMouseEntered(e -> editBtn.setStyle("-fx-background-color: #fde047; -fx-text-fill: #854d0e; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 6; -fx-padding: 8 16; -fx-cursor: hand;"));
            editBtn.setOnMouseExited(e -> editBtn.setStyle("-fx-background-color: #fef08a; -fx-text-fill: #854d0e; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 6; -fx-padding: 8 16; -fx-cursor: hand;"));
            
            editBtn.setOnAction(e -> {
                String fullMatchTitle = matchStage + " (" + team1 + " vs " + team2 + ")";
                ScorecardMyMatchesEditButton editorPage = new ScorecardMyMatchesEditButton(fullMatchTitle, team1, team2, () -> {
                    rootContainer.getChildren().setAll(mainScrollPane);
                });
                rootContainer.getChildren().setAll(editorPage.getView());
            });
            actionBox.getChildren().add(editBtn);
        }

        // --- VIEW SCORECARD BUTTON (Routes differently based on the tab) ---
        Button viewScorecardBtn = new Button("View Scorecard ➔");
        viewScorecardBtn.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #0f172a; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 6; -fx-padding: 8 16; -fx-cursor: hand;");
        viewScorecardBtn.setOnMouseEntered(e -> viewScorecardBtn.setStyle("-fx-background-color: #e2e8f0; -fx-text-fill: #0f172a; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 6; -fx-padding: 8 16; -fx-cursor: hand;"));
        viewScorecardBtn.setOnMouseExited(e -> viewScorecardBtn.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #0f172a; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 6; -fx-padding: 8 16; -fx-cursor: hand;"));

        viewScorecardBtn.setOnAction(e -> {
            String fullMatchTitle = matchStage + " (" + team1 + " vs " + team2 + ")";
            
            if (isMyMatch) {
                // Route to Dynamic File (My Matches)
                int[] emptyRuns = {0, 0}; int[] emptyWickets = {0, 0}; int[] emptyBalls = {0, 0};
                Map<String, int[]> emptyBatting = new LinkedHashMap<>();
                Map<String, int[]> emptyBowling = new LinkedHashMap<>();
                Map<String, String> emptyDismissals = new LinkedHashMap<>();

                ScorecardMyMatchesViewScorecard detailedScorecard = new ScorecardMyMatchesViewScorecard(
                    fullMatchTitle, team1, team2,
                    emptyRuns, emptyWickets, emptyBalls,
                    emptyBatting, emptyBatting, emptyBowling, emptyBowling, emptyDismissals, emptyDismissals,
                    () -> rootContainer.getChildren().setAll(mainScrollPane)
                );
                rootContainer.getChildren().setAll(detailedScorecard.getView());
            } else {
                // Route to Static File (Current/Upcoming/Completed Matches) -> Passes team names!
                ScorecardPageViewScorecard standardScorecard = new ScorecardPageViewScorecard(
                    fullMatchTitle, team1, team2,
                    () -> rootContainer.getChildren().setAll(mainScrollPane)
                );
                rootContainer.getChildren().setAll(standardScorecard.getView());
            }
        });

        actionBox.getChildren().add(viewScorecardBtn);
        card.getChildren().addAll(topHeader, scoreBox, footerBox, actionBox);

        // Hover Effect
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(150), card);
        scaleIn.setToX(1.015); scaleIn.setToY(1.015);
        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(150), card);
        scaleOut.setToX(1.0); scaleOut.setToY(1.0);

        card.setOnMouseEntered(e -> {
            scaleIn.playFromStart();
            card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12; -fx-border-color: " + accentColor + "; -fx-border-width: 4 1 1 1; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 4);");
        });
        card.setOnMouseExited(e -> {
            scaleOut.playFromStart();
            card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12; -fx-border-color: " + accentColor + " #e2e8f0 #e2e8f0 #e2e8f0; -fx-border-width: 4 1 1 1; -fx-border-radius: 12;");
        });

        return card;
    }

    private VBox createTeamScoreBlock(String logoUrl, String teamName, String score) {
        VBox box = new VBox(8);
        box.setAlignment(Pos.CENTER);

        ImageView logoView = new ImageView();
        try { Image img = new Image(logoUrl, true); logoView.setImage(img); } catch (Exception e) {}
        logoView.setFitWidth(46); logoView.setFitHeight(46);
        logoView.setClip(new Circle(23, 23, 23));

        Label t = new Label(teamName);
        t.setStyle("-fx-text-fill: #475569; -fx-font-size: 14px; -fx-font-weight: bold;");

        Label s = new Label(score);
        if(score.contains("Ov") || score.contains("/")) {
            s.setStyle("-fx-text-fill: #0f172a; -fx-font-size: 20px; -fx-font-weight: bold;");
        } else {
            s.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 14px; -fx-font-weight: bold;");
        }

        box.getChildren().addAll(logoView, t, s);
        return box;
    }

    private Label createTab(String text, boolean active) {
        Label lbl = new Label(text);
        if (active) {
            lbl.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #3b82f6; -fx-border-color: #3b82f6; -fx-border-width: 0 0 3 0; -fx-padding: 0 0 8 0;");
        } else {
            lbl.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #94a3b8; -fx-padding: 0 0 8 0; -fx-cursor: hand;");
            lbl.setOnMouseEntered(e -> lbl.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #0f172a; -fx-padding: 0 0 8 0; -fx-cursor: hand;"));
            lbl.setOnMouseExited(e -> lbl.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #94a3b8; -fx-padding: 0 0 8 0; -fx-cursor: hand;"));
        }
        return lbl;
    }

    private void setActiveTab(Label activeTab, Label... inactiveTabs) {
        activeTab.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #3b82f6; -fx-border-color: #3b82f6; -fx-border-width: 0 0 3 0; -fx-padding: 0 0 8 0;");
        for (Label tab : inactiveTabs) {
            tab.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #94a3b8; -fx-padding: 0 0 8 0; -fx-cursor: hand;");
            tab.setOnMouseEntered(e -> tab.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #0f172a; -fx-padding: 0 0 8 0; -fx-cursor: hand;"));
            tab.setOnMouseExited(e -> tab.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #94a3b8; -fx-padding: 0 0 8 0; -fx-cursor: hand;"));
        }
        activeTab.setOnMouseEntered(null);
        activeTab.setOnMouseExited(null);
    }
}