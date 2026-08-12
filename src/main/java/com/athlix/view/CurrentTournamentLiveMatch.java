// package com.athlix.view;

// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.Node;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.Region;
// import javafx.scene.layout.StackPane;
// import javafx.scene.layout.VBox;
// import javafx.scene.shape.Circle;

// public class CurrentTournamentLiveMatch {

//     private StackPane contentArea;
//     private Runnable onBackAction;
//     private Runnable onViewAllLiveMatchesAction; 

//     public CurrentTournamentLiveMatch(Runnable onBackAction, Runnable onViewAllLiveMatchesAction) {
//         this.onBackAction = onBackAction;
//         this.onViewAllLiveMatchesAction = onViewAllLiveMatchesAction;
//     }

//     public Node getView() {
//         VBox mainContainer = new VBox(20);
//         mainContainer.setPadding(new Insets(25, 25, 80, 25));
//         mainContainer.setStyle("-fx-background-color: #f8fafc;");

//         // --- HEADER SECTION ---
//         HBox header = new HBox(20);
//         header.setAlignment(Pos.CENTER_LEFT);

//         Button backBtn = new Button("❮  Back");
//         backBtn.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #10b981; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 8 18; -fx-background-radius: 20; -fx-border-color: #e2e8f0; -fx-border-radius: 20; -fx-cursor: hand;");
//         backBtn.setOnAction(e -> {
//             if (onBackAction != null) onBackAction.run();
//         });

//         Label headerTitle = new Label("🏆 Current Tournament");
//         headerTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
//         header.getChildren().addAll(backBtn, headerTitle);

//         // --- SUB-TABS NAVIGATION BAR ---
//         HBox subTabs = new HBox(25);
//         subTabs.setStyle("-fx-border-color: #e2e8f0; -fx-border-width: 0 0 1 0; -fx-padding: 0 0 10 0;");
        
//         Label liveMatchTab = createSubTab("Live Match", true);
//         Label pointsTab = createSubTab("Points Table", false);
//         Label teamsTab = createSubTab("Teams", false);
//         Label orangeCapTab = createSubTab("Orange Cap 🟠", false);
//         Label purpleCapTab = createSubTab("Purple Cap 🟣", false);

//         subTabs.getChildren().addAll(liveMatchTab, pointsTab, teamsTab, orangeCapTab, purpleCapTab);

//         contentArea = new StackPane();
//         contentArea.setAlignment(Pos.TOP_LEFT);

//         // --- COMPONENT VIEWS ---
        
//         // 1. Live Match View
//         VBox liveMatchView = buildLiveMatchContent();
        
//         // 2. Points Table View
//         CurrentTournamentPointTable pointTablePage = new CurrentTournamentPointTable();
//         Node pointsTableView = pointTablePage.getView();

//         // 3. Teams -> Squad -> Player Stats Routing
//         final Node[] teamsViewNode = new Node[1];
//         CurrentTournamentTeams teamsPage = new CurrentTournamentTeams(clickedTeamName -> {
//             final Node[] squadViewNode = new Node[1];
//             CurrentTournamentSquad squadPage = new CurrentTournamentSquad(clickedTeamName, 
//                 () -> {
//                     contentArea.getChildren().setAll(teamsViewNode[0]);
//                 },
//                 clickedPlayerName -> {
//                     CurrentTournamentStats statsPage = new CurrentTournamentStats(clickedPlayerName, () -> {
//                         contentArea.getChildren().setAll(squadViewNode[0]);
//                     });
//                     contentArea.getChildren().setAll(statsPage.getView());
//                 }
//             );
//             squadViewNode[0] = squadPage.getView();
//             contentArea.getChildren().setAll(squadViewNode[0]);
//         });
//         teamsViewNode[0] = teamsPage.getView();

//         // 4. Orange Cap View
//         CurrentTournamentOCap oCapPage = new CurrentTournamentOCap();
//         Node orangeCapView = oCapPage.getView();

//         // 5. Purple Cap View
//         CurrentTournamentPCap pCapPage = new CurrentTournamentPCap();
//         Node purpleCapView = pCapPage.getView();

//         // Set Default View
//         contentArea.getChildren().add(liveMatchView);

//         // --- TAB CLICK LISTENERS ---
//         liveMatchTab.setOnMouseClicked(e -> { 
//             setActiveSubTab(liveMatchTab, pointsTab, teamsTab, orangeCapTab, purpleCapTab); 
//             contentArea.getChildren().setAll(liveMatchView); 
//         });
//         pointsTab.setOnMouseClicked(e -> { 
//             setActiveSubTab(pointsTab, liveMatchTab, teamsTab, orangeCapTab, purpleCapTab); 
//             contentArea.getChildren().setAll(pointsTableView); 
//         });
//         teamsTab.setOnMouseClicked(e -> { 
//             setActiveSubTab(teamsTab, liveMatchTab, pointsTab, orangeCapTab, purpleCapTab); 
//             contentArea.getChildren().setAll(teamsViewNode[0]); 
//         });
//         orangeCapTab.setOnMouseClicked(e -> { 
//             setActiveSubTab(orangeCapTab, liveMatchTab, pointsTab, teamsTab, purpleCapTab); 
//             contentArea.getChildren().setAll(orangeCapView); 
//         });
//         purpleCapTab.setOnMouseClicked(e -> { 
//             setActiveSubTab(purpleCapTab, liveMatchTab, pointsTab, teamsTab, orangeCapTab); 
//             contentArea.getChildren().setAll(purpleCapView); 
//         });

//         mainContainer.getChildren().addAll(header, subTabs, contentArea);

//         ScrollPane scrollPane = new ScrollPane(mainContainer);
//         scrollPane.setFitToWidth(true);
//         scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f8fafc;");
//         scrollPane.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");

//         return scrollPane;
//     }

//     private VBox buildLiveMatchContent() {
//         VBox layout = new VBox(25);
//         VBox liveMatchesContainer = new VBox(15);
        
//         Label sectionTitle = new Label("🔴 Ongoing Live Matches");
//         sectionTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

//         liveMatchesContainer.getChildren().addAll(
//             sectionTitle,
//             createLiveMatchCard("Mumbai Indians (MI)", "165/6 (20.0 Ov)", "Chennai Super Kings (CSK)", "100/3 (16.0 Ov)", "2nd Innings • 16.0 Overs", "MI won toss & elected to bat.", "https://dummyimage.com/60x60/3b82f6/ffffff.png&text=MI", "https://dummyimage.com/60x60/eab308/ffffff.png&text=CSK"),
//             createLiveMatchCard("Royal Challengers (RCB)", "142/4 (15.2 Ov)", "Kolkata Knight Riders (KKR)", "139/8 (20.0 Ov)", "1st Innings • 15.2 Overs", "RCB powering through the middle overs.", "https://dummyimage.com/60x60/ef4444/ffffff.png&text=RCB", "https://dummyimage.com/60x60/8b5cf6/ffffff.png&text=KKR")
//         );

//         Button viewAllLiveMatchesBtn = new Button("View All Live Matches ➔");
//         viewAllLiveMatchesBtn.setMaxWidth(Double.MAX_VALUE);
//         String defaultStyle = "-fx-background-color: #ffffff; -fx-border-color: #10b981; -fx-border-radius: 8; -fx-background-radius: 8; -fx-text-fill: #10b981; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 12; -fx-cursor: hand;";
//         String hoverStyle = "-fx-background-color: #f1f5f9; -fx-border-color: #059669; -fx-border-radius: 8; -fx-background-radius: 8; -fx-text-fill: #059669; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 12; -fx-cursor: hand;";
//         viewAllLiveMatchesBtn.setStyle(defaultStyle);
        
//         viewAllLiveMatchesBtn.setOnMouseEntered(e -> viewAllLiveMatchesBtn.setStyle(hoverStyle));
//         viewAllLiveMatchesBtn.setOnMouseExited(e -> viewAllLiveMatchesBtn.setStyle(defaultStyle));

//         viewAllLiveMatchesBtn.setOnAction(e -> {
//             if (onViewAllLiveMatchesAction != null) {
//                 onViewAllLiveMatchesAction.run();
//             }
//         });

//         layout.getChildren().addAll(liveMatchesContainer, viewAllLiveMatchesBtn);
//         return layout;
//     }

//     private Label createSubTab(String text, boolean active) {
//         Label lbl = new Label(text);
//         if (active) {
//             lbl.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #10b981; -fx-border-color: #10b981; -fx-border-width: 0 0 3 0; -fx-padding: 0 0 8 0;");
//         } else {
//             lbl.setStyle("-fx-font-size: 15px; -fx-text-fill: #64748b; -fx-padding: 0 0 8 0; -fx-cursor: hand;");
//             lbl.setOnMouseEntered(e -> lbl.setStyle("-fx-font-size: 15px; -fx-text-fill: #0f172a; -fx-font-weight: bold; -fx-padding: 0 0 8 0; -fx-cursor: hand;"));
//             lbl.setOnMouseExited(e -> lbl.setStyle("-fx-font-size: 15px; -fx-text-fill: #64748b; -fx-font-weight: normal; -fx-padding: 0 0 8 0; -fx-cursor: hand;"));
//         }
//         return lbl;
//     }

//     private void setActiveSubTab(Label activeTab, Label... inactiveTabs) {
//         activeTab.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #10b981; -fx-border-color: #10b981; -fx-border-width: 0 0 3 0; -fx-padding: 0 0 8 0;");
//         for (Label tab : inactiveTabs) {
//             tab.setStyle("-fx-font-size: 15px; -fx-text-fill: #64748b; -fx-padding: 0 0 8 0; -fx-cursor: hand;");
//         }
//     }

//     private VBox createLiveMatchCard(String team1, String score1, String team2, String score2, String status, String commentary, String logoUrl1, String logoUrl2) {
//         VBox card = new VBox(15);
//         card.setPadding(new Insets(20));
//         card.setStyle("-fx-background-color: linear-gradient(to right, #0f172a, #1e3a8a); -fx-background-radius: 12;");
        
//         HBox liveHeader = new HBox();
//         Label liveBadge = new Label("🔴 LIVE MATCH");
//         liveBadge.setStyle("-fx-text-fill: #ef4444; -fx-font-size: 11px; -fx-font-weight: bold;");
//         Region liveSpacer = new Region();
//         HBox.setHgrow(liveSpacer, Priority.ALWAYS);
//         Label matchStatus = new Label(status);
//         matchStatus.setStyle("-fx-text-fill: #cbd5e1; -fx-font-size: 11px;");
//         liveHeader.getChildren().addAll(liveBadge, liveSpacer, matchStatus);

//         HBox scoreBox = new HBox(40);
//         scoreBox.setAlignment(Pos.CENTER);
        
//         VBox team1Box = createTeamScoreBlockWithImage(logoUrl1, team1, score1);
//         Label vsLabel = new Label("VS");
//         vsLabel.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 16px; -fx-font-weight: bold;");
//         VBox team2Box = createTeamScoreBlockWithImage(logoUrl2, team2, score2);
        
//         scoreBox.getChildren().addAll(team1Box, vsLabel, team2Box);

//         Label commentaryLbl = new Label("💬 " + commentary);
//         commentaryLbl.setStyle("-fx-text-fill: #34d399; -fx-font-size: 13px; -fx-font-weight: bold;");

//         Button viewLiveBtn = new Button("View Live Scorecard →");
//         viewLiveBtn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 8 16; -fx-cursor: hand;");

//         card.getChildren().addAll(liveHeader, scoreBox, commentaryLbl, viewLiveBtn);
//         return card;
//     }

//     private VBox createTeamScoreBlockWithImage(String logoUrl, String teamName, String score) {
//         VBox box = new VBox(6);
//         box.setAlignment(Pos.CENTER);
        
//         ImageView logoView = new ImageView();
//         try { Image img = new Image(logoUrl, true); logoView.setImage(img); } catch (Exception e) {}
//         logoView.setFitWidth(40);
//         logoView.setFitHeight(40);
//         logoView.setPreserveRatio(true);
//         Circle clip = new Circle(20, 20, 20);
//         logoView.setClip(clip);
        
//         Label t = new Label(teamName);
//         t.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
//         Label s = new Label(score);
//         s.setStyle("-fx-text-fill: #34d399; -fx-font-size: 20px; -fx-font-weight: bold;");
        
//         box.getChildren().addAll(logoView, t, s);
//         return box;
//     }
// }

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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class CurrentTournamentLiveMatch {

    private StackPane contentArea;
    private Runnable onBackAction;
    private Runnable onViewAllLiveMatchesAction; 

    public CurrentTournamentLiveMatch(Runnable onBackAction, Runnable onViewAllLiveMatchesAction) {
        this.onBackAction = onBackAction;
        this.onViewAllLiveMatchesAction = onViewAllLiveMatchesAction;
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

        Label headerTitle = new Label("🏆 Current Tournament");
        headerTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        header.getChildren().addAll(backBtn, headerTitle);

        // --- SUB-TABS NAVIGATION BAR (Live Match, Points Table, Teams) ---
        HBox subTabs = new HBox(25);
        subTabs.setStyle("-fx-border-color: #e2e8f0; -fx-border-width: 0 0 1 0; -fx-padding: 0 0 10 0;");
        
        Label liveMatchTab = createSubTab("Live Match", true);
        Label pointsTab = createSubTab("Points Table", false);
        Label teamsTab = createSubTab("Teams", false);

        subTabs.getChildren().addAll(liveMatchTab, pointsTab, teamsTab);

        contentArea = new StackPane();
        contentArea.setAlignment(Pos.TOP_LEFT);

        // --- COMPONENT VIEWS ---
        
        // 1. Live Match View
        VBox liveMatchView = buildLiveMatchContent();
        
        // 2. Points Table View
        CurrentTournamentPointTable pointTablePage = new CurrentTournamentPointTable();
        Node pointsTableView = pointTablePage.getView();

        // 3. Teams -> Squad -> Player Stats Routing
        final Node[] teamsViewNode = new Node[1];
        CurrentTournamentTeams teamsPage = new CurrentTournamentTeams(clickedTeamName -> {
            // Level 1: "View Squad" clicked
            final Node[] squadViewNode = new Node[1];
            CurrentTournamentSquad squadPage = new CurrentTournamentSquad(clickedTeamName, 
                () -> {
                    // Back from Squad -> Teams
                    contentArea.getChildren().setAll(teamsViewNode[0]);
                },
                clickedPlayerName -> {
                    // Level 2: "View Stats" clicked on player card
                    CurrentTournamentStats statsPage = new CurrentTournamentStats(clickedPlayerName, () -> {
                        // Back from Stats -> Squad
                        contentArea.getChildren().setAll(squadViewNode[0]);
                    });
                    contentArea.getChildren().setAll(statsPage.getView());
                }
            );
            squadViewNode[0] = squadPage.getView();
            contentArea.getChildren().setAll(squadViewNode[0]);
        });
        teamsViewNode[0] = teamsPage.getView();

        // Set Default View
        contentArea.getChildren().add(liveMatchView);

        // --- TAB CLICK LISTENERS ---
        liveMatchTab.setOnMouseClicked(e -> { 
            setActiveSubTab(liveMatchTab, pointsTab, teamsTab); 
            contentArea.getChildren().setAll(liveMatchView); 
        });
        pointsTab.setOnMouseClicked(e -> { 
            setActiveSubTab(pointsTab, liveMatchTab, teamsTab); 
            contentArea.getChildren().setAll(pointsTableView); 
        });
        teamsTab.setOnMouseClicked(e -> { 
            setActiveSubTab(teamsTab, liveMatchTab, pointsTab); 
            contentArea.getChildren().setAll(teamsViewNode[0]); 
        });

        mainContainer.getChildren().addAll(header, subTabs, contentArea);

        ScrollPane scrollPane = new ScrollPane(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f8fafc;");
        scrollPane.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");

        return scrollPane;
    }

    private VBox buildLiveMatchContent() {
        VBox layout = new VBox(25);
        VBox liveMatchesContainer = new VBox(15);
        
        Label sectionTitle = new Label("🔴 Ongoing Live Matches");
        sectionTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        liveMatchesContainer.getChildren().addAll(
            sectionTitle,
            createLiveMatchCard("Mumbai Indians (MI)", "165/6 (20.0 Ov)", "Chennai Super Kings (CSK)", "100/3 (16.0 Ov)", "2nd Innings • 16.0 Overs", "MI won toss & elected to bat.", "https://dummyimage.com/60x60/3b82f6/ffffff.png&text=MI", "https://dummyimage.com/60x60/eab308/ffffff.png&text=CSK"),
            createLiveMatchCard("Royal Challengers (RCB)", "142/4 (15.2 Ov)", "Kolkata Knight Riders (KKR)", "139/8 (20.0 Ov)", "1st Innings • 15.2 Overs", "RCB powering through the middle overs.", "https://dummyimage.com/60x60/ef4444/ffffff.png&text=RCB", "https://dummyimage.com/60x60/8b5cf6/ffffff.png&text=KKR")
        );

        Button viewAllLiveMatchesBtn = new Button("View All Live Matches ➔");
        viewAllLiveMatchesBtn.setMaxWidth(Double.MAX_VALUE);
        String defaultStyle = "-fx-background-color: #ffffff; -fx-border-color: #10b981; -fx-border-radius: 8; -fx-background-radius: 8; -fx-text-fill: #10b981; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 12; -fx-cursor: hand;";
        String hoverStyle = "-fx-background-color: #f1f5f9; -fx-border-color: #059669; -fx-border-radius: 8; -fx-background-radius: 8; -fx-text-fill: #059669; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 12; -fx-cursor: hand;";
        viewAllLiveMatchesBtn.setStyle(defaultStyle);
        
        viewAllLiveMatchesBtn.setOnMouseEntered(e -> viewAllLiveMatchesBtn.setStyle(hoverStyle));
        viewAllLiveMatchesBtn.setOnMouseExited(e -> viewAllLiveMatchesBtn.setStyle(defaultStyle));

        viewAllLiveMatchesBtn.setOnAction(e -> {
            if (onViewAllLiveMatchesAction != null) {
                onViewAllLiveMatchesAction.run();
            }
        });

        layout.getChildren().addAll(liveMatchesContainer, viewAllLiveMatchesBtn);
        return layout;
    }

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

    private VBox createLiveMatchCard(String team1, String score1, String team2, String score2, String status, String commentary, String logoUrl1, String logoUrl2) {
        VBox card = new VBox(15);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: linear-gradient(to right, #0f172a, #1e3a8a); -fx-background-radius: 12;");
        
        HBox liveHeader = new HBox();
        Label liveBadge = new Label("🔴 LIVE MATCH");
        liveBadge.setStyle("-fx-text-fill: #ef4444; -fx-font-size: 11px; -fx-font-weight: bold;");
        Region liveSpacer = new Region();
        HBox.setHgrow(liveSpacer, Priority.ALWAYS);
        Label matchStatus = new Label(status);
        matchStatus.setStyle("-fx-text-fill: #cbd5e1; -fx-font-size: 11px;");
        liveHeader.getChildren().addAll(liveBadge, liveSpacer, matchStatus);

        HBox scoreBox = new HBox(40);
        scoreBox.setAlignment(Pos.CENTER);
        
        VBox team1Box = createTeamScoreBlockWithImage(logoUrl1, team1, score1);
        Label vsLabel = new Label("VS");
        vsLabel.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 16px; -fx-font-weight: bold;");
        VBox team2Box = createTeamScoreBlockWithImage(logoUrl2, team2, score2);
        
        scoreBox.getChildren().addAll(team1Box, vsLabel, team2Box);

        Label commentaryLbl = new Label("💬 " + commentary);
        commentaryLbl.setStyle("-fx-text-fill: #34d399; -fx-font-size: 13px; -fx-font-weight: bold;");

        Button viewLiveBtn = new Button("View Live Scorecard →");
        viewLiveBtn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 8 16; -fx-cursor: hand;");

        card.getChildren().addAll(liveHeader, scoreBox, commentaryLbl, viewLiveBtn);
        return card;
    }

    private VBox createTeamScoreBlockWithImage(String logoUrl, String teamName, String score) {
        VBox box = new VBox(6);
        box.setAlignment(Pos.CENTER);
        
        ImageView logoView = new ImageView();
        try { Image img = new Image(logoUrl, true); logoView.setImage(img); } catch (Exception e) {}
        logoView.setFitWidth(40);
        logoView.setFitHeight(40);
        logoView.setPreserveRatio(true);
        Circle clip = new Circle(20, 20, 20);
        logoView.setClip(clip);
        
        Label t = new Label(teamName);
        t.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        Label s = new Label(score);
        s.setStyle("-fx-text-fill: #34d399; -fx-font-size: 20px; -fx-font-weight: bold;");
        
        box.getChildren().addAll(logoView, t, s);
        return box;
    }
}