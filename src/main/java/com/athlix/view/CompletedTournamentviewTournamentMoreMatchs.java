package com.athlix.view;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.util.function.Consumer;

public class CompletedTournamentviewTournamentMoreMatchs {

    private String tournamentTitle;
    private Runnable onBackAction;
    private Consumer<String> onViewMatchAction;

    public CompletedTournamentviewTournamentMoreMatchs(String tournamentTitle, Runnable onBackAction, Consumer<String> onViewMatchAction) {
        this.tournamentTitle = tournamentTitle;
        this.onBackAction = onBackAction;
        this.onViewMatchAction = onViewMatchAction;
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

        Label headerTitle = new Label("🏁 " + tournamentTitle + " - All Completed Matches");
        headerTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        header.getChildren().addAll(backBtn, headerTitle);

        // --- MATCHES CONTAINER ---
        VBox matchesContainer = new VBox(15);
        matchesContainer.getChildren().addAll(
            createMatchCard(
                "FINAL • 18 May 2026",
                "Mumbai Masters (MM)", "185/4 (20.0 Ov)",
                "Pune Strikers (PS)", "162/9 (20.0 Ov)",
                "Mumbai Masters won by 23 runs",
                "https://dummyimage.com/60x60/3b82f6/ffffff.png&text=MM",
                "https://dummyimage.com/60x60/10b981/ffffff.png&text=PS",
                "#3b82f6"
            ),
            createMatchCard(
                "SEMI-FINAL 2 • 16 May 2026",
                "Pune Strikers (PS)", "174/6 (20.0 Ov)",
                "Royal Titans (RT)", "170/8 (20.0 Ov)",
                "Pune Strikers won by 4 runs",
                "https://dummyimage.com/60x60/10b981/ffffff.png&text=PS",
                "https://dummyimage.com/60x60/8b5cf6/ffffff.png&text=RT",
                "#10b981"
            ),
            createMatchCard(
                "SEMI-FINAL 1 • 15 May 2026",
                "Mumbai Masters (MM)", "192/5 (20.0 Ov)",
                "Nashik Warriors (NW)", "145/10 (17.4 Ov)",
                "Mumbai Masters won by 47 runs",
                "https://dummyimage.com/60x60/3b82f6/ffffff.png&text=MM",
                "https://dummyimage.com/60x60/f59e0b/ffffff.png&text=NW",
                "#f59e0b"
            ),
            createMatchCard(
                "LEAGUE MATCH 12 • 12 May 2026",
                "Royal Titans (RT)", "160/7 (20.0 Ov)",
                "Nashik Warriors (NW)", "158/8 (20.0 Ov)",
                "Royal Titans won by 2 runs",
                "https://dummyimage.com/60x60/8b5cf6/ffffff.png&text=RT",
                "https://dummyimage.com/60x60/f59e0b/ffffff.png&text=NW",
                "#8b5cf6"
            ),
            createMatchCard(
                "LEAGUE MATCH 11 • 10 May 2026",
                "Mumbai Masters (MM)", "210/3 (20.0 Ov)",
                "Pune Strikers (PS)", "180/6 (20.0 Ov)",
                "Mumbai Masters won by 30 runs",
                "https://dummyimage.com/60x60/3b82f6/ffffff.png&text=MM",
                "https://dummyimage.com/60x60/10b981/ffffff.png&text=PS",
                "#0284c7"
            ),
            createMatchCard(
                "LEAGUE MATCH 10 • 08 May 2026",
                "Coastal Kings (CK)", "148/9 (20.0 Ov)",
                "Tech Innovators CC (TI)", "150/4 (18.2 Ov)",
                "Tech Innovators CC won by 6 wickets",
                "https://dummyimage.com/60x60/06b6d4/ffffff.png&text=CK",
                "https://dummyimage.com/60x60/f43f5e/ffffff.png&text=TI",
                "#06b6d4"
            ),
            createMatchCard(
                "LEAGUE MATCH 9 • 06 May 2026",
                "Pune Strikers (PS)", "166/5 (20.0 Ov)",
                "Nashik Warriors (NW)", "160/7 (20.0 Ov)",
                "Pune Strikers won by 6 runs",
                "https://dummyimage.com/60x60/10b981/ffffff.png&text=PS",
                "https://dummyimage.com/60x60/f59e0b/ffffff.png&text=NW",
                "#10b981"
            ),
            createMatchCard(
                "LEAGUE MATCH 8 • 04 May 2026",
                "Mumbai Masters (MM)", "195/6 (20.0 Ov)",
                "Royal Titans (RT)", "172/8 (20.0 Ov)",
                "Mumbai Masters won by 23 runs",
                "https://dummyimage.com/60x60/3b82f6/ffffff.png&text=MM",
                "https://dummyimage.com/60x60/8b5cf6/ffffff.png&text=RT",
                "#3b82f6"
            )
        );

        mainContainer.getChildren().addAll(header, matchesContainer);
        return mainContainer;
    }

    private VBox createMatchCard(String matchStage, String team1, String score1, String team2, String score2, String resultSummary, String logoUrl1, String logoUrl2, String accentColor) {
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

        // View Match Button
        Button viewMatchBtn = new Button("View Match →");
        viewMatchBtn.setStyle("-fx-background-color: #f8fafc; -fx-text-fill: #0f172a; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 6; -fx-border-color: #cbd5e1; -fx-border-radius: 6; -fx-padding: 7 14; -fx-cursor: hand;");
        viewMatchBtn.setOnMouseEntered(e -> viewMatchBtn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 6; -fx-border-color: #10b981; -fx-border-radius: 6; -fx-padding: 7 14; -fx-cursor: hand;"));
        viewMatchBtn.setOnMouseExited(e -> viewMatchBtn.setStyle("-fx-background-color: #f8fafc; -fx-text-fill: #0f172a; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 6; -fx-border-color: #cbd5e1; -fx-border-radius: 6; -fx-padding: 7 14; -fx-cursor: hand;"));

        // Trigger Match Details View
        viewMatchBtn.setOnAction(e -> {
            if (onViewMatchAction != null) {
                onViewMatchAction.accept(matchStage + " (" + team1 + " vs " + team2 + ")");
            }
        });

        card.getChildren().addAll(topHeader, scoreBox, resultBox, viewMatchBtn);

        // Hover Effect
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
}