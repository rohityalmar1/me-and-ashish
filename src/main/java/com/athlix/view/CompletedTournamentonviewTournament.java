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

public class CompletedTournamentonviewTournament {

    private String tournamentTitle;
    private Runnable onBackAction;
    private StackPane contentArea;

    public CompletedTournamentonviewTournament(String tournamentTitle, Runnable onBackAction) {
        this.tournamentTitle = tournamentTitle;
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

        Label headerTitle = new Label("🏆 " + tournamentTitle);
        headerTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        header.getChildren().addAll(backBtn, headerTitle);

        // --- SUB-TABS NAVIGATION BAR ---
        HBox subTabs = new HBox(25);
        subTabs.setStyle("-fx-border-color: #e2e8f0; -fx-border-width: 0 0 1 0; -fx-padding: 0 0 10 0;");

        Label completedMatchesTab = createSubTab("Completed Matches", true);
        Label orangeCapTab = createSubTab("Orange Cap 🟠", false);
        Label purpleCapTab = createSubTab("Purple Cap 🟣", false);

        subTabs.getChildren().addAll(completedMatchesTab, orangeCapTab, purpleCapTab);

        contentArea = new StackPane();
        contentArea.setAlignment(Pos.TOP_LEFT);

        // --- VIEW COMPONENTS ---
        VBox completedMatchesView = buildCompletedMatchesContent();

        CurrentTournamentOCap oCapPage = new CurrentTournamentOCap();
        Node orangeCapView = oCapPage.getView();

        CurrentTournamentPCap pCapPage = new CurrentTournamentPCap();
        Node purpleCapView = pCapPage.getView();

        contentArea.getChildren().add(completedMatchesView);

        // --- TAB LISTENERS ---
        completedMatchesTab.setOnMouseClicked(e -> {
            setActiveSubTab(completedMatchesTab, orangeCapTab, purpleCapTab);
            contentArea.getChildren().setAll(completedMatchesView);
        });

        orangeCapTab.setOnMouseClicked(e -> {
            setActiveSubTab(orangeCapTab, completedMatchesTab, purpleCapTab);
            contentArea.getChildren().setAll(orangeCapView);
        });

        purpleCapTab.setOnMouseClicked(e -> {
            setActiveSubTab(purpleCapTab, completedMatchesTab, orangeCapTab);
            contentArea.getChildren().setAll(purpleCapView);
        });

        mainContainer.getChildren().addAll(header, subTabs, contentArea);

        ScrollPane scrollPane = new ScrollPane(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f8fafc;");
        scrollPane.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");

        return scrollPane;
    }

    private VBox buildCompletedMatchesContent() {
        VBox layout = new VBox(20);

        Label sectionTitle = new Label("🏁 Completed Matches & Results");
        sectionTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        VBox matchesContainer = new VBox(15);
        matchesContainer.getChildren().addAll(
            createMatchCard(
                layout,
                "FINAL • 18 May 2026",
                "Mumbai Masters (MM)", "185/4 (20.0 Ov)",
                "Pune Strikers (PS)", "162/9 (20.0 Ov)",
                "Mumbai Masters won by 23 runs",
                "https://dummyimage.com/60x60/3b82f6/ffffff.png&text=MM",
                "https://dummyimage.com/60x60/10b981/ffffff.png&text=PS",
                "#3b82f6"
            ),
            createMatchCard(
                layout,
                "SEMI-FINAL 2 • 16 May 2026",
                "Pune Strikers (PS)", "174/6 (20.0 Ov)",
                "Royal Titans (RT)", "170/8 (20.0 Ov)",
                "Pune Strikers won by 4 runs",
                "https://dummyimage.com/60x60/10b981/ffffff.png&text=PS",
                "https://dummyimage.com/60x60/8b5cf6/ffffff.png&text=RT",
                "#10b981"
            ),
            createMatchCard(
                layout,
                "SEMI-FINAL 1 • 15 May 2026",
                "Mumbai Masters (MM)", "192/5 (20.0 Ov)",
                "Nashik Warriors (NW)", "145/10 (17.4 Ov)",
                "Mumbai Masters won by 47 runs",
                "https://dummyimage.com/60x60/3b82f6/ffffff.png&text=MM",
                "https://dummyimage.com/60x60/f59e0b/ffffff.png&text=NW",
                "#f59e0b"
            ),
            createMatchCard(
                layout,
                "LEAGUE MATCH 12 • 12 May 2026",
                "Royal Titans (RT)", "160/7 (20.0 Ov)",
                "Nashik Warriors (NW)", "158/8 (20.0 Ov)",
                "Royal Titans won by 2 runs",
                "https://dummyimage.com/60x60/8b5cf6/ffffff.png&text=RT",
                "https://dummyimage.com/60x60/f59e0b/ffffff.png&text=NW",
                "#8b5cf6"
            ),
            createMatchCard(
                layout,
                "LEAGUE MATCH 11 • 10 May 2026",
                "Mumbai Masters (MM)", "210/3 (20.0 Ov)",
                "Pune Strikers (PS)", "180/6 (20.0 Ov)",
                "Mumbai Masters won by 30 runs",
                "https://dummyimage.com/60x60/3b82f6/ffffff.png&text=MM",
                "https://dummyimage.com/60x60/10b981/ffffff.png&text=PS",
                "#0284c7"
            )
        );

        // --- BOTTOM ACTION BUTTON: "More Matches ➔" ---
        Button moreMatchesBtn = new Button("More Matches ➔");
        moreMatchesBtn.setMaxWidth(Double.MAX_VALUE);
        String defaultBtnStyle = "-fx-background-color: #ffffff; -fx-border-color: #10b981; -fx-border-radius: 8; -fx-background-radius: 8; -fx-text-fill: #10b981; -fx-font-size: 15px; -fx-font-weight: bold; -fx-padding: 12; -fx-cursor: hand;";
        String hoverBtnStyle = "-fx-background-color: #f1f5f9; -fx-border-color: #059669; -fx-border-radius: 8; -fx-background-radius: 8; -fx-text-fill: #059669; -fx-font-size: 15px; -fx-font-weight: bold; -fx-padding: 12; -fx-cursor: hand;";

        moreMatchesBtn.setStyle(defaultBtnStyle);
        moreMatchesBtn.setOnMouseEntered(e -> moreMatchesBtn.setStyle(hoverBtnStyle));
        moreMatchesBtn.setOnMouseExited(e -> moreMatchesBtn.setStyle(defaultBtnStyle));

        // Switch to the full matches page with support for clicking into match details
        moreMatchesBtn.setOnAction(e -> {
            final Node[] allMatchesNodeHolder = new Node[1];

            CompletedTournamentviewTournamentMoreMatchs allMatchesPage = new CompletedTournamentviewTournamentMoreMatchs(
                tournamentTitle,
                () -> contentArea.getChildren().setAll(layout), // Back returns to standard completed matches list
                clickedMatchTitle -> {
                    // Action when "View Match" inside the full list is clicked
                    CompletedTournamentviewTournamentCompletedMatchesViewMatchs matchDetailsView = 
                        new CompletedTournamentviewTournamentCompletedMatchesViewMatchs(
                            clickedMatchTitle,
                            () -> contentArea.getChildren().setAll(allMatchesNodeHolder[0]) // Back returns to full matches list
                        );
                    contentArea.getChildren().setAll(matchDetailsView.getView());
                }
            );

            allMatchesNodeHolder[0] = allMatchesPage.getView();
            contentArea.getChildren().setAll(allMatchesNodeHolder[0]);
        });

        layout.getChildren().addAll(sectionTitle, matchesContainer, moreMatchesBtn);
        return layout;
    }

    private VBox createMatchCard(VBox parentLayout, String matchStage, String team1, String score1, String team2, String score2, String resultSummary, String logoUrl1, String logoUrl2, String accentColor) {
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

        // Result Summary Banner
        HBox resultBox = new HBox();
        resultBox.setAlignment(Pos.CENTER_LEFT);
        resultBox.setPadding(new Insets(8, 14, 8, 14));
        resultBox.setStyle("-fx-background-color: #ecfdf5; -fx-background-radius: 8; -fx-border-color: #a7f3d0; -fx-border-radius: 8;");

        Label resultLbl = new Label("🎉 " + resultSummary);
        resultLbl.setStyle("-fx-text-fill: #065f46; -fx-font-size: 13px; -fx-font-weight: bold;");
        resultBox.getChildren().add(resultLbl);

        // View Scorecard Button
        Button viewMatchBtn = new Button("View Match →");
        viewMatchBtn.setStyle("-fx-background-color: #f8fafc; -fx-text-fill: #0f172a; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 6; -fx-border-color: #cbd5e1; -fx-border-radius: 6; -fx-padding: 7 14; -fx-cursor: hand;");
        viewMatchBtn.setOnMouseEntered(e -> viewMatchBtn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 6; -fx-border-color: #10b981; -fx-border-radius: 6; -fx-padding: 7 14; -fx-cursor: hand;"));
        viewMatchBtn.setOnMouseExited(e -> viewMatchBtn.setStyle("-fx-background-color: #f8fafc; -fx-text-fill: #0f172a; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 6; -fx-border-color: #cbd5e1; -fx-border-radius: 6; -fx-padding: 7 14; -fx-cursor: hand;"));

        // Route to Match Details Screen (Overview, Scorecard, Stats)
        viewMatchBtn.setOnAction(e -> {
            CompletedTournamentviewTournamentCompletedMatchesViewMatchs matchDetailsView = 
                new CompletedTournamentviewTournamentCompletedMatchesViewMatchs(
                    matchStage + " (" + team1 + " vs " + team2 + ")",
                    () -> contentArea.getChildren().setAll(parentLayout) // Back button restores completed matches list
                );
            contentArea.getChildren().setAll(matchDetailsView.getView());
        });

        card.getChildren().addAll(topHeader, scoreBox, resultBox, viewMatchBtn);

        // Hover Animation
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(150), card);
        scaleIn.setToX(1.01);
        scaleIn.setToY(1.01);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(150), card);
        scaleOut.setToX(1.0);
        scaleOut.setToY(1.0);

        card.setOnMouseEntered(e -> {
            scaleIn.playFromStart();
            card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12; -fx-border-color: " + accentColor + "; -fx-border-width: 3 1 1 1; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.06), 8, 0, 0, 3);");
        });
        card.setOnMouseExited(e -> {
            scaleOut.playFromStart();
            card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12; -fx-border-color: " + accentColor + " #e2e8f0 #e2e8f0 #e2e8f0; -fx-border-width: 3 1 1 1; -fx-border-radius: 12;");
        });

        return card;
    }

    private VBox createTeamScoreBlock(String logoUrl, String teamName, String score) {
        VBox box = new VBox(5);
        box.setAlignment(Pos.CENTER);

        ImageView logoView = new ImageView();
        try {
            Image img = new Image(logoUrl, true);
            logoView.setImage(img);
        } catch (Exception e) {}
        logoView.setFitWidth(42);
        logoView.setFitHeight(42);
        logoView.setPreserveRatio(true);
        Circle clip = new Circle(21, 21, 21);
        logoView.setClip(clip);

        Label t = new Label(teamName);
        t.setStyle("-fx-text-fill: #0f172a; -fx-font-size: 14px; -fx-font-weight: bold;");

        Label s = new Label(score);
        s.setStyle("-fx-text-fill: #059669; -fx-font-size: 18px; -fx-font-weight: bold;");

        box.getChildren().addAll(logoView, t, s);
        return box;
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
}