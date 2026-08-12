

package com.athlix.view.Scorecard;

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

public class ScorecardPageViewScorecard {

    private String matchTitle;
    private String team1;
    private String team2;
    private Runnable onBackAction;

    public ScorecardPageViewScorecard(String matchTitle, String team1, String team2, Runnable onBackAction) {
        this.matchTitle = matchTitle;
        this.team1 = team1;
        this.team2 = team2;
        this.onBackAction = onBackAction;
    }

    public Node getView() {
        VBox mainContainer = new VBox();
        mainContainer.setStyle("-fx-background-color: #f8fafc;");
        mainContainer.setPadding(new Insets(25, 25, 80, 25));
        mainContainer.setSpacing(20);

        // --- 1. HEADER (Back Button & Title) ---
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);

        Button backBtn = new Button("❮  Back to Dashboard");
        backBtn.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #3b82f6; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 8 18; -fx-background-radius: 20; -fx-border-color: #e2e8f0; -fx-border-radius: 20; -fx-cursor: hand;");
        backBtn.setOnAction(e -> {
            if (onBackAction != null) onBackAction.run();
        });

        Label headerTitle = new Label(matchTitle != null ? matchTitle : "Detailed Scorecard");
        headerTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        header.getChildren().addAll(backBtn, headerTitle);

        // --- 2. SCORECARD CONTENT CARD ---
        VBox scorecardCard = new VBox();
        scorecardCard.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 8; -fx-border-color: #e2e8f0; -fx-border-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 4);");

        // Team Toggle Tabs
        HBox teamTabs = new HBox();
        teamTabs.setStyle("-fx-background-color: #f8fafc; -fx-background-radius: 8 8 0 0; -fx-border-color: #e2e8f0; -fx-border-width: 0 0 1 0;");
        
        VBox tabTeam1 = createTeamTab(team1, true);
        VBox tabTeam2 = createTeamTab(team2, false);
        teamTabs.getChildren().addAll(tabTeam1, tabTeam2);

        // --- 3. DYNAMIC CONTENT AREA ---
        StackPane scorecardContentArea = new StackPane();
        scorecardContentArea.setPadding(new Insets(20));

        // Create the mock data views for Team 1 and Team 2
        VBox team1InningsView = buildTeam1View(); 
        VBox team2InningsView = buildTeam2View(); 

        // Set default view to Team 1
        scorecardContentArea.getChildren().add(team1InningsView);

        // Toggle Actions
        tabTeam1.setOnMouseClicked(e -> {
            setActiveTeamTab(tabTeam1, tabTeam2);
            scorecardContentArea.getChildren().setAll(team1InningsView);
        });

        tabTeam2.setOnMouseClicked(e -> {
            setActiveTeamTab(tabTeam2, tabTeam1);
            scorecardContentArea.getChildren().setAll(team2InningsView);
        });

        // Assemble Scorecard Body
        scorecardCard.getChildren().addAll(teamTabs, scorecardContentArea);
        mainContainer.getChildren().addAll(header, scorecardCard);

        ScrollPane scrollPane = new ScrollPane(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f8fafc;");
        scrollPane.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");

        return scrollPane;
    }

    // --- VIEW BUILDERS FOR TEAMS WITH MOCK DATA ---

    private VBox buildTeam1View() {
        VBox layout = new VBox(20);

        // Batting Table
        VBox battingSection = new VBox();
        battingSection.getChildren().add(createTableHeader("Batting", "R", "B", "4s", "6s", "S/R"));
        battingSection.getChildren().addAll(
            createBattingRow("Player 1 (" + team1 + ")", "c & b Player A", "45", "32", "4", "2", "140.62", ""),
            createBattingRow("Player 2 (" + team1 + ")", "not out", "68", "40", "7", "3", "170.00", "*"),
            createBattingRow("Player 3 (" + team1 + ")", "run out", "12", "8", "1", "0", "150.00", ""),
            createBattingRow("Player 4 (" + team1 + ")", "not out", "22", "10", "2", "1", "220.00", "*")
        );

        // Extras & Totals
        VBox extrasTotalsBox = new VBox();
        extrasTotalsBox.getChildren().addAll(
            createTotalRow("Extras", "11 (NB 1, W 6, LB 4)"),
            createTotalRow("Total runs", "158 (2 wkts, 15.0 ov)")
        );

        // Bowling Table
        VBox bowlingSection = new VBox();
        bowlingSection.getChildren().add(createTableHeader("Bowling", "O", "M", "R", "W", "Econ"));
        bowlingSection.getChildren().addAll(
            createBowlingRow("Player A (" + team2 + ")", "4.0", "0", "28", "1", "7.00"),
            createBowlingRow("Player B (" + team2 + ")", "4.0", "0", "35", "0", "8.75"),
            createBowlingRow("Player C (" + team2 + ")", "4.0", "0", "42", "0", "10.50"),
            createBowlingRow("Player D (" + team2 + ")", "3.0", "0", "42", "0", "14.00")
        );

        layout.getChildren().addAll(battingSection, extrasTotalsBox, bowlingSection);
        return layout;
    }

    private VBox buildTeam2View() {
        VBox layout = new VBox(20);

        // Batting Table 
        VBox battingSection = new VBox();
        battingSection.getChildren().add(createTableHeader("Batting", "R", "B", "4s", "6s", "S/R"));
        battingSection.getChildren().addAll(
            createBattingRow("Player A (" + team2 + ")", "b Player 1", "55", "35", "6", "2", "157.14", ""),
            createBattingRow("Player B (" + team2 + ")", "c Player 2 b Player 3", "18", "15", "2", "0", "120.00", ""),
            createBattingRow("Player C (" + team2 + ")", "not out", "70", "38", "8", "4", "184.21", "*"),
            createBattingRow("Player D (" + team2 + ")", "not out", "5", "2", "1", "0", "250.00", "*")
        );

        // Extras & Totals
        VBox extrasTotalsBox = new VBox();
        extrasTotalsBox.getChildren().addAll(
            createTotalRow("Extras", "12 (NB 2, W 8, LB 2)"),
            createTotalRow("Total runs", "160 (2 wkts, 15.0 ov)")
        );

        // Bowling Table 
        VBox bowlingSection = new VBox();
        bowlingSection.getChildren().add(createTableHeader("Bowling", "O", "M", "R", "W", "Econ"));
        bowlingSection.getChildren().addAll(
            createBowlingRow("Player 1 (" + team1 + ")", "4.0", "0", "30", "1", "7.50"),
            createBowlingRow("Player 2 (" + team1 + ")", "4.0", "0", "45", "0", "11.25"),
            createBowlingRow("Player 3 (" + team1 + ")", "4.0", "0", "38", "1", "9.50"),
            createBowlingRow("Player 4 (" + team1 + ")", "3.0", "0", "35", "0", "11.66")
        );

        layout.getChildren().addAll(battingSection, extrasTotalsBox, bowlingSection);
        return layout;
    }

    // --- UI BUILDER HELPERS ---

    private VBox createTeamTab(String text, boolean active) {
        VBox tab = new VBox();
        tab.setAlignment(Pos.CENTER);
        tab.setPadding(new Insets(15, 0, 15, 0));
        HBox.setHgrow(tab, Priority.ALWAYS);
        Label lbl = new Label(text);
        lbl.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; " + (active ? "-fx-text-fill: #0f172a;" : "-fx-text-fill: #94a3b8;"));
        if (active) {
            tab.setStyle("-fx-border-color: #0f172a; -fx-border-width: 0 0 2 0;");
        } else {
            tab.setStyle("-fx-cursor: hand; -fx-border-color: transparent; -fx-border-width: 0 0 2 0;");
        }
        tab.getChildren().add(lbl);
        return tab;
    }

    private void setActiveTeamTab(VBox activeTab, VBox inactiveTab) {
        activeTab.setStyle("-fx-border-color: #0f172a; -fx-border-width: 0 0 2 0;");
        ((Label) activeTab.getChildren().get(0)).setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        
        inactiveTab.setStyle("-fx-cursor: hand; -fx-border-color: transparent; -fx-border-width: 0 0 2 0;");
        ((Label) inactiveTab.getChildren().get(0)).setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #94a3b8;");
    }

    private HBox createTableHeader(String title, String c1, String c2, String c3, String c4, String c5) {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(10, 0, 10, 0));
        header.setStyle("-fx-border-color: #e2e8f0; -fx-border-width: 0 0 1 0;");

        Label titleLbl = new Label(title);
        titleLbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748b;");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox columns = new HBox(15);
        columns.setAlignment(Pos.CENTER_RIGHT);
        columns.getChildren().addAll(
            createColLabel(c1, 35), createColLabel(c2, 35), 
            createColLabel(c3, 35), createColLabel(c4, 35), createColLabel(c5, 50)
        );

        header.getChildren().addAll(titleLbl, spacer, columns);
        return header;
    }

    private HBox createBattingRow(String name, String dismissal, String r, String b, String fours, String sixes, String sr, String tag) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(12, 0, 12, 0));
        row.setStyle("-fx-border-color: #f1f5f9; -fx-border-width: 0 0 1 0;");

        HBox playerInfo = new HBox(12);
        playerInfo.setAlignment(Pos.CENTER_LEFT);

        ImageView avatar = new ImageView();
        try { Image img = new Image("https://dummyimage.com/40x40/cbd5e1/ffffff.png&text=" + name.charAt(0), true); avatar.setImage(img); } catch (Exception e) {}
        avatar.setFitWidth(36); avatar.setFitHeight(36);
        avatar.setClip(new Circle(18, 18, 18));

        VBox nameBox = new VBox(2);
        HBox nRow = new HBox(5);
        Label nLbl = new Label(name);
        nLbl.setStyle("-fx-font-size: 15px; -fx-text-fill: #0f172a;");
        nRow.getChildren().add(nLbl);
        
        if (!tag.isEmpty()) {
            Label tagLbl = new Label(tag);
            tagLbl.setStyle("-fx-font-size: 13px; -fx-text-fill: #10b981; -fx-font-weight: bold;");
            nRow.getChildren().add(tagLbl);
        }

        Label dLbl = new Label(dismissal);
        if (dismissal.equals("not out") || dismissal.isEmpty()) {
            dLbl.setStyle("-fx-font-size: 13px; -fx-text-fill: #10b981;");
        } else {
            dLbl.setStyle("-fx-font-size: 13px; -fx-text-fill: #ef4444; -fx-font-weight: bold;");
        }
        
        nameBox.getChildren().addAll(nRow, dLbl);

        playerInfo.getChildren().addAll(avatar, nameBox);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox columns = new HBox(15);
        columns.setAlignment(Pos.CENTER_RIGHT);
        columns.getChildren().addAll(
            createDataLabel(r, 35, true), createDataLabel(b, 35, false), 
            createDataLabel(fours, 35, false), createDataLabel(sixes, 35, false), createDataLabel(sr, 50, false)
        );

        row.getChildren().addAll(playerInfo, spacer, columns);
        return row;
    }

    private HBox createBowlingRow(String name, String o, String m, String r, String w, String econ) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(12, 0, 12, 0));
        row.setStyle("-fx-border-color: #f1f5f9; -fx-border-width: 0 0 1 0;");

        HBox playerInfo = new HBox(12);
        playerInfo.setAlignment(Pos.CENTER_LEFT);

        ImageView avatar = new ImageView();
        try { Image img = new Image("https://dummyimage.com/40x40/cbd5e1/ffffff.png&text=" + name.charAt(0), true); avatar.setImage(img); } catch (Exception e) {}
        avatar.setFitWidth(36); avatar.setFitHeight(36);
        avatar.setClip(new Circle(18, 18, 18));

        Label nLbl = new Label(name);
        nLbl.setStyle("-fx-font-size: 15px; -fx-text-fill: #0f172a;");
        playerInfo.getChildren().addAll(avatar, nLbl);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox columns = new HBox(15);
        columns.setAlignment(Pos.CENTER_RIGHT);
        columns.getChildren().addAll(
            createDataLabel(o, 35, false), createDataLabel(m, 35, false), 
            createDataLabel(r, 35, false), createDataLabel(w, 35, true), createDataLabel(econ, 50, false)
        );

        row.getChildren().addAll(playerInfo, spacer, columns);
        return row;
    }

    private HBox createTotalRow(String title, String value) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(12, 0, 12, 0));
        row.setStyle("-fx-border-color: #f1f5f9; -fx-border-width: 0 0 1 0;");

        Label tLbl = new Label(title);
        tLbl.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label vLbl = new Label(value);
        vLbl.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        row.getChildren().addAll(tLbl, spacer, vLbl);
        return row;
    }

    private Label createColLabel(String text, double width) {
        Label l = new Label(text);
        l.setPrefWidth(width);
        l.setAlignment(Pos.CENTER_RIGHT);
        l.setStyle("-fx-text-fill: #64748b; -fx-font-size: 13px;");
        return l;
    }

    private Label createDataLabel(String text, double width, boolean isBold) {
        Label l = new Label(text);
        l.setPrefWidth(width);
        l.setAlignment(Pos.CENTER_RIGHT);
        l.setStyle("-fx-text-fill: #0f172a; -fx-font-size: 14px; " + (isBold ? "-fx-font-weight: bold;" : ""));
        return l;
    }
}