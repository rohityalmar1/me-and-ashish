

package com.athlix.view.Scorecard;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.LinkedHashMap;
import java.util.Map;

public class ScorecardMyMatchesEditButton {

    private String matchTitle, team1, team2;
    private Runnable onBackAction;

    private StackPane rootContainer;
    private ScrollPane mainScrollPane;

    // --- DUAL LIVE MATCH STATE ---
    private int activeInning = 0; // 0 = Team1 Batting, 1 = Team2 Batting
    private int[] runs = {0, 0};
    private int[] wickets = {0, 0};
    private int[] balls = {0, 0}; 

    // --- DYNAMIC PLAYER STATS (Key: Player Name, Value: Stats Array) ---
    // Batting Array: {runs, balls, 4s, 6s}
    private Map<String, int[]> t1Batting = new LinkedHashMap<>();
    private Map<String, int[]> t2Batting = new LinkedHashMap<>();
    
    // Bowling Array: {balls, maidens, runs, wickets}
    private Map<String, int[]> t1Bowling = new LinkedHashMap<>();
    private Map<String, int[]> t2Bowling = new LinkedHashMap<>();

    // Dismissals Map (Key: Batter Name, Value: Dismissal Text e.g. "b BowlerName")
    private Map<String, String> t1Dismissals = new LinkedHashMap<>();
    private Map<String, String> t2Dismissals = new LinkedHashMap<>();

    // UI ELEMENTS TO UPDATE DYNAMICALLY
    private Label t1ScoreLbl, t1OversLbl, t2ScoreLbl, t2OversLbl;
    private VBox t1BadgeBox, t2BadgeBox;
    private HBox timelineRow;
    private Label strikerStatsLbl, bowlerStatsLbl;
    private ComboBox<String> strikerCombo, bowlerCombo;

    public ScorecardMyMatchesEditButton(String matchTitle, String team1, String team2, Runnable onBackAction) {
        this.matchTitle = matchTitle;
        this.team1 = team1;
        this.team2 = team2;
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
        mainContainer.setPadding(new Insets(30, 30, 80, 30));
        mainContainer.setStyle("-fx-background-color: #f8fafc;");

        // --- 1. HEADER SECTION ---
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);

        Button backBtn = new Button("❮  Back");
        backBtn.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #3b82f6; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 8 18; -fx-background-radius: 20; -fx-border-color: #e2e8f0; -fx-border-radius: 20; -fx-cursor: hand;");
        backBtn.setOnAction(e -> {
            if (onBackAction != null) onBackAction.run();
        });

        VBox titleBox = new VBox(5);
        Label pageTitle = new Label("✏️ Live Match Editor");
        pageTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        Label subtitle = new Label("Updating: " + matchTitle);
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748b;");
        titleBox.getChildren().addAll(pageTitle, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Switch Innings Button
        Button switchInningsBtn = new Button("Switch Innings 🔄");
        switchInningsBtn.setStyle("-fx-background-color: #fef08a; -fx-text-fill: #854d0e; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8; -fx-padding: 10 20; -fx-cursor: hand;");
        addHoverEffect(switchInningsBtn, 1.02);
        switchInningsBtn.setOnAction(e -> {
            activeInning = 1 - activeInning; 
            timelineRow.getChildren().clear(); 
            populateDropdowns(); 
            updateScoreUI();
        });

        // View Scorecard button (Passes ALL data including dismissals)
        Button viewScorecardBtn = new Button("View Scorecard ➔");
        viewScorecardBtn.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #0f172a; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8; -fx-padding: 10 20; -fx-cursor: hand; -fx-border-color: #e2e8f0; -fx-border-radius: 8;");
        addHoverEffect(viewScorecardBtn, 1.02);
        
        viewScorecardBtn.setOnAction(e -> {
            ScorecardMyMatchesViewScorecard detailedScorecard = new ScorecardMyMatchesViewScorecard(
                matchTitle, team1, team2,
                runs, wickets, balls,
                t1Batting, t2Batting,
                t1Bowling, t2Bowling,
                t1Dismissals, t2Dismissals, // Pass the dismissal maps
                () -> {
                    rootContainer.getChildren().setAll(mainScrollPane);
                }
            );
            rootContainer.getChildren().setAll(detailedScorecard.getView());
        });

        header.getChildren().addAll(backBtn, titleBox, spacer, switchInningsBtn, viewScorecardBtn);

        // --- 2. LIVE SCORE OVERVIEW CARD ---
        HBox scoreOverview = new HBox(30);
        scoreOverview.setAlignment(Pos.CENTER);
        scoreOverview.setPadding(new Insets(25));
        scoreOverview.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #3b82f6; -fx-border-width: 2 0 0 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.04), 10, 0, 0, 4);");

        t1ScoreLbl = new Label("0/0"); t1OversLbl = new Label("(0.0 Ov)");
        t1BadgeBox = new VBox(createBattingBadge()); t1BadgeBox.setAlignment(Pos.CENTER);
        VBox t1Box = buildTeamHeaderBlock(team1, t1ScoreLbl, t1OversLbl, t1BadgeBox);

        Label vsLbl = new Label("VS"); vsLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #cbd5e1;");

        t2ScoreLbl = new Label("Yet to bat"); t2OversLbl = new Label("");
        t2BadgeBox = new VBox(); t2BadgeBox.setAlignment(Pos.CENTER);
        VBox t2Box = buildTeamHeaderBlock(team2, t2ScoreLbl, t2OversLbl, t2BadgeBox);

        scoreOverview.getChildren().addAll(t1Box, vsLbl, t2Box);

        // --- 3. EDITOR CONTROLS AREA ---
        HBox editorArea = new HBox(25);
        
        // LEFT COLUMN: Player Selection
        VBox playerSelectionBox = new VBox(20);
        playerSelectionBox.setPadding(new Insets(25));
        playerSelectionBox.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12;");
        HBox.setHgrow(playerSelectionBox, Priority.ALWAYS);

        Label selectLbl = new Label("Current Players on Field");
        selectLbl.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        // Striker
        VBox strikerBox = new VBox(8);
        Label sLbl = new Label("Striker (Batting)");
        sLbl.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #64748b;");
        strikerCombo = new ComboBox<>();
        strikerCombo.setMaxWidth(Double.MAX_VALUE);
        strikerCombo.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #cbd5e1; -fx-border-radius: 6; -fx-padding: 4; -fx-font-size: 14px;");
        strikerStatsLbl = new Label("Runs: 0 (0 balls)");
        strikerStatsLbl.setStyle("-fx-text-fill: #10b981; -fx-font-weight: bold; -fx-font-size: 12px;");
        strikerBox.getChildren().addAll(sLbl, strikerCombo, strikerStatsLbl);
        strikerCombo.setOnAction(e -> updatePlayerStatsUI()); 

        // Bowler
        VBox bowlerBox = new VBox(8);
        Label bLbl = new Label("Current Bowler");
        bLbl.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #64748b;");
        bowlerCombo = new ComboBox<>();
        bowlerCombo.setMaxWidth(Double.MAX_VALUE);
        bowlerCombo.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #cbd5e1; -fx-border-radius: 6; -fx-padding: 4; -fx-font-size: 14px;");
        bowlerStatsLbl = new Label("0.0 Overs | 0 Runs | 0 Wickets");
        bowlerStatsLbl.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold; -fx-font-size: 12px;");
        bowlerBox.getChildren().addAll(bLbl, bowlerCombo, bowlerStatsLbl);
        bowlerCombo.setOnAction(e -> updatePlayerStatsUI());

        playerSelectionBox.getChildren().addAll(selectLbl, strikerBox, bowlerBox);

        // RIGHT COLUMN: Keypad
        VBox keypadBox = new VBox(20);
        keypadBox.setPadding(new Insets(25));
        keypadBox.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #3b82f6; -fx-border-width: 2; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(59,130,246,0.1), 15, 0, 0, 5);");
        keypadBox.setPrefWidth(400);

        Label keypadLbl = new Label("Ball-by-Ball Update");
        keypadLbl.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        GridPane keypad = new GridPane();
        keypad.setHgap(15); keypad.setVgap(15); keypad.setAlignment(Pos.CENTER);
        keypad.add(createScoreBtn("0", 0, false, false, "#f1f5f9", "#475569"), 0, 0);
        keypad.add(createScoreBtn("1", 1, false, false, "#eff6ff", "#1d4ed8"), 1, 0);
        keypad.add(createScoreBtn("2", 2, false, false, "#eff6ff", "#1d4ed8"), 2, 0);
        keypad.add(createScoreBtn("3", 3, false, false, "#eff6ff", "#1d4ed8"), 0, 1);
        keypad.add(createScoreBtn("4", 4, false, false, "#dcfce7", "#15803d"), 1, 1);
        keypad.add(createScoreBtn("6", 6, false, false, "#dcfce7", "#15803d"), 2, 1);
        keypad.add(createScoreBtn("Wd", 1, true, false, "#fef9c3", "#a16207"), 0, 2);
        keypad.add(createScoreBtn("Nb", 1, true, false, "#fef9c3", "#a16207"), 1, 2);
        keypad.add(createScoreBtn("Lb", 1, false, false, "#f3e8ff", "#9333ea"), 2, 2); 
        
        Button wicketBtn = new Button("WICKET OUT");
        wicketBtn.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px; -fx-background-radius: 8; -fx-cursor: hand;");
        wicketBtn.setPrefSize(300, 50);
        GridPane.setColumnSpan(wicketBtn, 3);
        keypad.add(wicketBtn, 0, 3);
        addHoverEffect(wicketBtn, 1.02);
        
        // Trigger Wicket Logic
        wicketBtn.setOnAction(e -> handleBallEvent(0, false, true, "W", "#fee2e2", "#dc2626"));

        keypadBox.getChildren().addAll(keypadLbl, keypad);
        editorArea.getChildren().addAll(playerSelectionBox, keypadBox);

        // --- 4. TIMELINE ---
        VBox timelineBox = new VBox(15);
        timelineBox.setPadding(new Insets(20));
        timelineBox.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12;");

        timelineRow = new HBox(10);
        timelineRow.setAlignment(Pos.CENTER_LEFT);
        timelineRow.getChildren().add(createTimelineBall("?", "#f8fafc", "#cbd5e1"));
        timelineBox.getChildren().addAll(new Label("This Over Timeline"), timelineRow);

        mainContainer.getChildren().addAll(header, scoreOverview, editorArea, timelineBox);

        ScrollPane scrollPane = new ScrollPane(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f8fafc;");
        
        populateDropdowns(); 
        updateScoreUI();
        return scrollPane;
    }

    private void populateDropdowns() {
        strikerCombo.getItems().clear();
        bowlerCombo.getItems().clear();
        
        if (activeInning == 0) {
            strikerCombo.getItems().addAll("Aarav Sharma ("+team1+")", "Player 2 ("+team1+")", "Player 3 ("+team1+")");
            bowlerCombo.getItems().addAll("Jacob Bethell ("+team2+")", "Player 4 ("+team2+")", "Player 5 ("+team2+")");
        } else {
            strikerCombo.getItems().addAll("Jacob Bethell ("+team2+")", "Player 4 ("+team2+")", "Player 5 ("+team2+")");
            bowlerCombo.getItems().addAll("Aarav Sharma ("+team1+")", "Player 2 ("+team1+")", "Player 3 ("+team1+")");
        }
        
        strikerCombo.getSelectionModel().selectFirst();
        bowlerCombo.getSelectionModel().selectFirst();
        updatePlayerStatsUI();
    }

    // ==========================================
    // DYNAMIC DUAL SCORING LOGIC
    // ==========================================
    private void handleBallEvent(int runScored, boolean isExtra, boolean isWicket, String labelStr, String bgColor, String textColor) {
        String striker = strikerCombo.getValue();
        String bowler = bowlerCombo.getValue();
        if (striker == null || bowler == null) return;

        Map<String, int[]> currentBatMap = (activeInning == 0) ? t1Batting : t2Batting;
        Map<String, int[]> currentBowlMap = (activeInning == 0) ? t2Bowling : t1Bowling;
        Map<String, String> currentDisMap = (activeInning == 0) ? t1Dismissals : t2Dismissals;

        currentBatMap.putIfAbsent(striker, new int[]{0, 0, 0, 0}); 
        currentBowlMap.putIfAbsent(bowler, new int[]{0, 0, 0, 0}); 

        int[] sStats = currentBatMap.get(striker);
        int[] bStats = currentBowlMap.get(bowler);

        runs[activeInning] += runScored; 

        if (isWicket) {
            wickets[activeInning]++;
            bStats[3]++; // bowler wicket
            sStats[1]++; // striker ball
            bStats[0]++; // bowler ball
            balls[activeInning]++;
            
            // Mark the striker as out with the bowler's name
            currentDisMap.put(striker, "b " + bowler);

        } else if (!isExtra) {
            balls[activeInning]++;
            bStats[0]++;
            sStats[1]++;
            if (!labelStr.equals("Lb")) {
                sStats[0] += runScored;
                if (runScored == 4) sStats[2]++;
                if (runScored == 6) sStats[3]++;
            }
            bStats[2] += runScored; 
        } else {
            bStats[2] += runScored; 
        }

        updateScoreUI();
        updatePlayerStatsUI();
        
        // Update Timeline
        if (timelineRow.getChildren().size() >= 6) timelineRow.getChildren().remove(0);
        if (balls[activeInning] == 1 && !isExtra && timelineRow.getChildren().size() == 1) timelineRow.getChildren().clear();
        timelineRow.getChildren().add(createTimelineBall(labelStr, bgColor, textColor));
    }

    private void updatePlayerStatsUI() {
        String striker = strikerCombo.getValue();
        String bowler = bowlerCombo.getValue();
        if(striker == null || bowler == null) return;

        Map<String, int[]> currentBatMap = (activeInning == 0) ? t1Batting : t2Batting;
        Map<String, int[]> currentBowlMap = (activeInning == 0) ? t2Bowling : t1Bowling;

        int[] sStats = currentBatMap.getOrDefault(striker, new int[]{0,0,0,0});
        int[] bStats = currentBowlMap.getOrDefault(bowler, new int[]{0,0,0,0});

        strikerStatsLbl.setText("Runs: " + sStats[0] + " (" + sStats[1] + " balls)");
        bowlerStatsLbl.setText((bStats[0]/6) + "." + (bStats[0]%6) + " Overs | " + bStats[2] + " Runs | " + bStats[3] + " Wickets");
    }

    private void updateScoreUI() {
        if (activeInning == 0) {
            t1BadgeBox.getChildren().setAll(createBattingBadge()); t2BadgeBox.getChildren().clear();
            t1ScoreLbl.setText(runs[0] + "/" + wickets[0]); t1ScoreLbl.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
            t1OversLbl.setText("(" + (balls[0]/6) + "." + (balls[0]%6) + " Ov)");
            
            if (balls[1] == 0 && runs[1] == 0) {
                t2ScoreLbl.setText("Yet to bat"); t2ScoreLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #94a3b8;"); t2OversLbl.setText("");
            } else {
                t2ScoreLbl.setText(runs[1] + "/" + wickets[1]); t2ScoreLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #94a3b8;");
                t2OversLbl.setText("(" + (balls[1]/6) + "." + (balls[1]%6) + " Ov)");
            }
        } else {
            t2BadgeBox.getChildren().setAll(createBattingBadge()); t1BadgeBox.getChildren().clear();
            t2ScoreLbl.setText(runs[1] + "/" + wickets[1]); t2ScoreLbl.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
            t2OversLbl.setText("(" + (balls[1]/6) + "." + (balls[1]%6) + " Ov)");
            
            t1ScoreLbl.setText(runs[0] + "/" + wickets[0]); t1ScoreLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #94a3b8;");
            t1OversLbl.setText("(" + (balls[0]/6) + "." + (balls[0]%6) + " Ov)");
        }
    }

    // --- UI HELPERS ---
    private VBox buildTeamHeaderBlock(String teamName, Label scoreLbl, Label oversLbl, VBox badgeBox) {
        VBox box = new VBox(5); box.setAlignment(Pos.CENTER);
        Label tName = new Label(teamName); tName.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #475569;");
        scoreLbl.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #0f172a;"); oversLbl.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #64748b;");
        box.getChildren().addAll(badgeBox, tName, scoreLbl, oversLbl); return box;
    }

    private Label createBattingBadge() {
        Label b = new Label("▶ BATTING"); b.setStyle("-fx-background-color: #dbeafe; -fx-text-fill: #1d4ed8; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 3 8; -fx-background-radius: 10;"); return b;
    }

    private Button createScoreBtn(String text, int r, boolean ext, boolean wkt, String bg, String tc) {
        Button btn = new Button(text); btn.setPrefSize(90, 60);
        btn.setStyle("-fx-background-color: " + bg + "; -fx-text-fill: " + tc + "; -fx-font-size: 20px; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand; -fx-border-color: " + tc + "40; -fx-border-radius: 8;");
        addHoverEffect(btn, 1.05); btn.setOnAction(e -> handleBallEvent(r, ext, wkt, text, bg, tc)); return btn;
    }

    private StackPane createTimelineBall(String text, String bg, String tc) {
        StackPane ball = new StackPane(); Circle c = new Circle(18); c.setStyle("-fx-fill: " + bg + "; -fx-stroke: " + tc + "40; -fx-stroke-width: 1;");
        Label l = new Label(text); l.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: " + tc + ";");
        ball.getChildren().addAll(c, l); return ball;
    }

    private void addHoverEffect(Node node, double scale) {
        ScaleTransition in = new ScaleTransition(Duration.millis(100), node); in.setToX(scale); in.setToY(scale);
        ScaleTransition out = new ScaleTransition(Duration.millis(100), node); out.setToX(1.0); out.setToY(1.0);
        node.setOnMouseEntered(e -> in.playFromStart()); node.setOnMouseExited(e -> out.playFromStart());
    }
}