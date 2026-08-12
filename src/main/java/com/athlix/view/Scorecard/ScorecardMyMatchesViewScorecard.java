

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
import java.util.Map;

public class ScorecardMyMatchesViewScorecard {

    private String matchTitle, team1, team2;
    private int[] runs, wickets, balls;
    private Map<String, int[]> t1Batting, t2Batting, t1Bowling, t2Bowling;
    private Map<String, String> t1Dismissals, t2Dismissals;
    private Runnable onBackAction;

    public ScorecardMyMatchesViewScorecard(
            String matchTitle, String team1, String team2,
            int[] runs, int[] wickets, int[] balls,
            Map<String, int[]> t1Batting, Map<String, int[]> t2Batting,
            Map<String, int[]> t1Bowling, Map<String, int[]> t2Bowling,
            Map<String, String> t1Dismissals, Map<String, String> t2Dismissals,
            Runnable onBackAction) {
        
        this.matchTitle = matchTitle;
        this.team1 = team1;
        this.team2 = team2;
        this.runs = runs;
        this.wickets = wickets;
        this.balls = balls;
        this.t1Batting = t1Batting;
        this.t2Batting = t2Batting;
        this.t1Bowling = t1Bowling;
        this.t2Bowling = t2Bowling;
        this.t1Dismissals = t1Dismissals;
        this.t2Dismissals = t2Dismissals;
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

        Button backBtn = new Button("❮  Back");
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

        HBox teamTabs = new HBox();
        teamTabs.setStyle("-fx-background-color: #f8fafc; -fx-background-radius: 8 8 0 0; -fx-border-color: #e2e8f0; -fx-border-width: 0 0 1 0;");
        
        VBox tabTeam1 = createTeamTab(team1, true);
        VBox tabTeam2 = createTeamTab(team2, false);
        teamTabs.getChildren().addAll(tabTeam1, tabTeam2);

        // --- 3. DYNAMIC RESULT BANNER ---
        HBox resultBanner = createMatchResultBanner();
        VBox.setMargin(resultBanner, new Insets(20, 20, 0, 20));

        // --- 4. DYNAMIC CONTENT AREA ---
        StackPane scorecardContentArea = new StackPane();
        scorecardContentArea.setPadding(new Insets(20));

        VBox team1InningsView = buildInningsView(0, t1Batting, t2Bowling, t1Dismissals); 
        VBox team2InningsView = buildInningsView(1, t2Batting, t1Bowling, t2Dismissals); 

        scorecardContentArea.getChildren().add(team1InningsView);

        tabTeam1.setOnMouseClicked(e -> {
            setActiveTeamTab(tabTeam1, tabTeam2);
            scorecardContentArea.getChildren().setAll(team1InningsView);
        });

        tabTeam2.setOnMouseClicked(e -> {
            setActiveTeamTab(tabTeam2, tabTeam1);
            scorecardContentArea.getChildren().setAll(team2InningsView);
        });

        scorecardCard.getChildren().addAll(teamTabs, resultBanner, scorecardContentArea);
        mainContainer.getChildren().addAll(header, scorecardCard);

        ScrollPane scrollPane = new ScrollPane(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f8fafc;");
        return scrollPane;
    }

    // --- LOGIC HELPER: MATCH RESULT PREDICTOR ---
    private HBox createMatchResultBanner() {
        HBox banner = new HBox(10);
        banner.setAlignment(Pos.CENTER);
        banner.setPadding(new Insets(12, 15, 12, 15));

        String resultText = "";
        String boxStyle = "";
        String textColor = "";

        // Default to 20 overs if unable to parse from title
        int maxBalls = 120; 
        try {
            if (matchTitle.contains("Overs")) {
                String[] parts = matchTitle.split(" ");
                for (int i = 0; i < parts.length; i++) {
                    if (parts[i].equalsIgnoreCase("Overs")) {
                        maxBalls = Integer.parseInt(parts[i - 1]) * 6;
                        break;
                    }
                }
            }
        } catch (Exception ignored) {}

        int target = runs[0] + 1;

        if (balls[0] == 0 && balls[1] == 0) {
            resultText = "⏳ Match is about to start.";
            boxStyle = "-fx-background-color: #f8fafc; -fx-background-radius: 8; -fx-border-color: #e2e8f0; -fx-border-radius: 8;";
            textColor = "#475569";
        } else if (balls[1] == 0 && wickets[0] < 10 && balls[0] < maxBalls) {
            resultText = "⚡ 1st Innings in progress: " + team1 + " is batting.";
            boxStyle = "-fx-background-color: #fffbeb; -fx-background-radius: 8; -fx-border-color: #fde68a; -fx-border-radius: 8;";
            textColor = "#b45309";
        } else if (balls[1] == 0 && (wickets[0] == 10 || balls[0] >= maxBalls)) {
            resultText = "🎯 Innings Break. " + team2 + " needs " + target + " runs to win.";
            boxStyle = "-fx-background-color: #fffbeb; -fx-background-radius: 8; -fx-border-color: #fde68a; -fx-border-radius: 8;";
            textColor = "#b45309";
        } else {
            // Team 2 is Batting (Chasing)
            if (runs[1] >= target) {
                int ballsRemaining = maxBalls - balls[1];
                resultText = "🏆 " + team2 + " won by " + (10 - wickets[1]) + " wickets" + (ballsRemaining > 0 ? " (with " + ballsRemaining + " balls remaining)." : ".");
                boxStyle = "-fx-background-color: #ecfdf5; -fx-background-radius: 8; -fx-border-color: #a7f3d0; -fx-border-radius: 8;";
                textColor = "#065f46";
            } else if (wickets[1] == 10 || balls[1] >= maxBalls) {
                if (runs[0] == runs[1]) {
                    resultText = "🤝 Match Tied!";
                    boxStyle = "-fx-background-color: #eff6ff; -fx-background-radius: 8; -fx-border-color: #bfdbfe; -fx-border-radius: 8;";
                    textColor = "#1e3a8a";
                } else {
                    resultText = "🏆 " + team1 + " won by " + (runs[0] - runs[1]) + " runs.";
                    boxStyle = "-fx-background-color: #ecfdf5; -fx-background-radius: 8; -fx-border-color: #a7f3d0; -fx-border-radius: 8;";
                    textColor = "#065f46";
                }
            } else {
                int runsNeeded = target - runs[1];
                int ballsRemaining = maxBalls - balls[1];
                resultText = "⚡ " + team2 + " needs " + runsNeeded + " runs to win from " + ballsRemaining + " balls.";
                boxStyle = "-fx-background-color: #fffbeb; -fx-background-radius: 8; -fx-border-color: #fde68a; -fx-border-radius: 8;";
                textColor = "#b45309";
            }
        }

        banner.setStyle(boxStyle);
        Label lbl = new Label(resultText);
        lbl.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: " + textColor + ";");
        banner.getChildren().add(lbl);

        return banner;
    }

    // --- UI BUILDER HELPERS ---

    private VBox buildInningsView(int index, Map<String, int[]> batMap, Map<String, int[]> bowlMap, Map<String, String> disMap) {
        VBox layout = new VBox(20);

        int tRuns = runs[index];
        int tWickets = wickets[index];
        int tBalls = balls[index];

        String matchOvers = (tBalls / 6) + "." + (tBalls % 6);

        // A. Batting Table
        VBox battingSection = new VBox();
        battingSection.getChildren().add(createTableHeader("Batting", "R", "B", "4s", "6s", "S/R"));
        
        if (batMap.isEmpty() && tRuns == 0) {
            battingSection.getChildren().add(createBattingRow("Yet to bat", "not out", "0", "0", "-", "-", "0.00", ""));
        } else {
            for (Map.Entry<String, int[]> entry : batMap.entrySet()) {
                String pName = entry.getKey();
                int[] st = entry.getValue(); 
                
                if (st[1] > 0 || st[0] > 0 || disMap.containsKey(pName)) { 
                    String sr = st[1] > 0 ? String.format("%.2f", (st[0] * 100.0) / st[1]) : "0.00";
                    String dismissal = disMap.getOrDefault(pName, "not out");
                    String tag = dismissal.equals("not out") ? "*" : "";
                    
                    battingSection.getChildren().add(createBattingRow(pName, dismissal, String.valueOf(st[0]), String.valueOf(st[1]), String.valueOf(st[2]), String.valueOf(st[3]), sr, tag));
                }
            }
        }

        VBox extrasTotalsBox = new VBox();
        extrasTotalsBox.getChildren().add(createTotalRow("Total runs", tRuns + " (" + tWickets + " wkts, " + matchOvers + " ov)"));

        // B. Bowling Table
        VBox bowlingSection = new VBox();
        bowlingSection.getChildren().add(createTableHeader("Bowling", "O", "M", "R", "W", "Econ"));
        
        if (bowlMap.isEmpty() && tRuns == 0) {
            bowlingSection.getChildren().add(createBowlingRow("Yet to bowl", "0.0", "0", "0", "0", "0.00"));
        } else {
            for (Map.Entry<String, int[]> entry : bowlMap.entrySet()) {
                String pName = entry.getKey();
                int[] st = entry.getValue(); 
                if (st[0] > 0 || st[2] > 0) { 
                    String overs = (st[0] / 6) + "." + (st[0] % 6);
                    String econ = st[0] > 0 ? String.format("%.2f", (st[2] * 6.0) / st[0]) : "0.00";
                    bowlingSection.getChildren().add(createBowlingRow(pName, overs, String.valueOf(st[1]), String.valueOf(st[2]), String.valueOf(st[3]), econ));
                }
            }
        }

        layout.getChildren().addAll(battingSection, extrasTotalsBox, bowlingSection);
        return layout;
    }

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
        columns.getChildren().addAll(createColLabel(c1, 35), createColLabel(c2, 35), createColLabel(c3, 35), createColLabel(c4, 35), createColLabel(c5, 50));
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
        if (dismissal.equals("not out")) {
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
        columns.getChildren().addAll(createDataLabel(r, 35, true), createDataLabel(b, 35, false), createDataLabel(fours, 35, false), createDataLabel(sixes, 35, false), createDataLabel(sr, 50, false));
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
        columns.getChildren().addAll(createDataLabel(o, 35, false), createDataLabel(m, 35, false), createDataLabel(r, 35, false), createDataLabel(w, 35, true), createDataLabel(econ, 50, false));
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
        Label l = new Label(text); l.setPrefWidth(width); l.setAlignment(Pos.CENTER_RIGHT);
        l.setStyle("-fx-text-fill: #64748b; -fx-font-size: 13px;"); return l;
    }

    private Label createDataLabel(String text, double width, boolean isBold) {
        Label l = new Label(text); l.setPrefWidth(width); l.setAlignment(Pos.CENTER_RIGHT);
        l.setStyle("-fx-text-fill: #0f172a; -fx-font-size: 14px; " + (isBold ? "-fx-font-weight: bold;" : "")); return l;
    }
}