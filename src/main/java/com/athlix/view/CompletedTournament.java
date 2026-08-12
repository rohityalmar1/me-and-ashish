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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.function.Consumer;

public class CompletedTournament {

    private Runnable onBackAction;
    private Runnable onViewAllAction;
    private Consumer<String> onViewTournamentDetailsAction;

    public CompletedTournament(Runnable onBackAction, Runnable onViewAllAction, Consumer<String> onViewTournamentDetailsAction) {
        this.onBackAction = onBackAction;
        this.onViewAllAction = onViewAllAction;
        this.onViewTournamentDetailsAction = onViewTournamentDetailsAction;
    }

    public Node getView() {
        VBox mainContainer = new VBox(25);
        mainContainer.setPadding(new Insets(30, 30, 80, 30));
        mainContainer.setStyle("-fx-background-color: #f8fafc;");

        // --- HEADER ---
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);

        Button backBtn = new Button("❮  Back to List");
        backBtn.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #10b981; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 8 18; -fx-background-radius: 20; -fx-border-color: #e2e8f0; -fx-border-radius: 20; -fx-cursor: hand;");
        backBtn.setOnAction(e -> {
            if (onBackAction != null) onBackAction.run();
        });

        Label titleText = new Label("🏁 Completed Tournaments");
        titleText.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        header.getChildren().addAll(backBtn, titleText);

        // --- 2x2 GRID ---
        GridPane grid = new GridPane();
        grid.setHgap(25);
        grid.setVgap(25);
        grid.setAlignment(Pos.CENTER);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col1, col2);

        grid.add(createCompletedCard("https://dummyimage.com/400x160/1e293b/38bdf8.png&text=Mumbai+Premier+League", "10 - 18 May 2026", "Mumbai Premier League", "Shivaji Park, Mumbai", "Mumbai Masters", "#3b82f6"), 0, 0);
        grid.add(createCompletedCard("https://dummyimage.com/400x160/1e293b/34d399.png&text=Pune+Super+Cup", "01 - 08 May 2026", "Pune Super Cricket Cup", "MCA Stadium, Pune", "Pune Strikers", "#10b981"), 1, 0);
        grid.add(createCompletedCard("https://dummyimage.com/400x160/1e293b/c084fc.png&text=Maharashtra+Championship", "20 - 28 Apr 2026", "Maharashtra State Championship", "NCA Grounds, Pune", "Royal Titans", "#8b5cf6"), 0, 1);
        grid.add(createCompletedCard("https://dummyimage.com/400x160/1e293b/fbbf24.png&text=Nashik+Trophy", "12 - 18 Apr 2026", "Nashik Open Trophy", "Local Grounds, Nashik", "Nashik Warriors", "#f59e0b"), 1, 1);

        // --- BOTTOM SECTION ---
        HBox bottomActionBox = new HBox();
        bottomActionBox.setAlignment(Pos.CENTER);
        bottomActionBox.setPadding(new Insets(20, 0, 0, 0));

        Button viewAllBtn = new Button("View All Completed Tournaments ➔");
        String defaultBtnStyle = "-fx-background-color: #ffffff; -fx-border-color: #10b981; -fx-border-radius: 8; -fx-background-radius: 8; -fx-text-fill: #10b981; -fx-font-size: 15px; -fx-font-weight: bold; -fx-padding: 12 30; -fx-cursor: hand;";
        String hoverBtnStyle = "-fx-background-color: #f1f5f9; -fx-border-color: #059669; -fx-border-radius: 8; -fx-background-radius: 8; -fx-text-fill: #059669; -fx-font-size: 15px; -fx-font-weight: bold; -fx-padding: 12 30; -fx-cursor: hand;";

        viewAllBtn.setStyle(defaultBtnStyle);
        viewAllBtn.setOnMouseEntered(e -> viewAllBtn.setStyle(hoverBtnStyle));
        viewAllBtn.setOnMouseExited(e -> viewAllBtn.setStyle(defaultBtnStyle));

        viewAllBtn.setOnAction(e -> {
            if (onViewAllAction != null) {
                onViewAllAction.run();
            }
        });

        bottomActionBox.getChildren().add(viewAllBtn);
        mainContainer.getChildren().addAll(header, grid, bottomActionBox);

        ScrollPane scrollPane = new ScrollPane(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f8fafc;");
        scrollPane.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");

        return scrollPane;
    }

    private VBox createCompletedCard(String imageSource, String dateRange, String title, String location, String winnerTeam, String accentColor) {
        VBox card = new VBox(14);
        card.setPadding(new Insets(18));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: " + accentColor + " #e2e8f0 #e2e8f0 #e2e8f0; -fx-border-width: 4 1 1 1; -fx-border-radius: 12;");
        HBox.setHgrow(card, Priority.ALWAYS);

        ImageView imageView = new ImageView();
        try {
            Image img = new Image(imageSource, true);
            imageView.setImage(img);
        } catch (Exception e) {}
        imageView.setFitWidth(380);
        imageView.setFitHeight(140);
        imageView.setPreserveRatio(false);

        Rectangle clip = new Rectangle(380, 140);
        clip.setArcWidth(10);
        clip.setArcHeight(10);
        imageView.setClip(clip);

        HBox topInfo = new HBox();
        topInfo.setAlignment(Pos.CENTER_LEFT);
        Label dateLbl = new Label("📅 " + dateRange);
        dateLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748b; -fx-font-weight: bold;");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Label statusBadge = new Label("COMPLETED");
        statusBadge.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #475569; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 3 8; -fx-background-radius: 6;");
        topInfo.getChildren().addAll(dateLbl, spacer, statusBadge);

        Label titleLbl = new Label(title);
        titleLbl.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        Label locLbl = new Label("📍 " + location);
        locLbl.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748b;");

        HBox winnerBox = new HBox(8);
        winnerBox.setAlignment(Pos.CENTER_LEFT);
        winnerBox.setPadding(new Insets(10, 14, 10, 14));
        winnerBox.setStyle("-fx-background-color: #fefce8; -fx-background-radius: 8; -fx-border-color: #fef08a; -fx-border-radius: 8;");
        Label trophyIcon = new Label("🏆");
        trophyIcon.setStyle("-fx-font-size: 16px;");
        Label winnerText = new Label("Winner: " + winnerTeam);
        winnerText.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #854d0e;");
        winnerBox.getChildren().addAll(trophyIcon, winnerText);

        Button viewTournamentBtn = new Button("View Tournament");
        viewTournamentBtn.setMaxWidth(Double.MAX_VALUE);
        viewTournamentBtn.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #0f172a; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 10; -fx-cursor: hand;");
        viewTournamentBtn.setOnMouseEntered(e -> viewTournamentBtn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 10; -fx-cursor: hand;"));
        viewTournamentBtn.setOnMouseExited(e -> viewTournamentBtn.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #0f172a; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 10; -fx-cursor: hand;"));

        // Trigger the detail screen navigation
        viewTournamentBtn.setOnAction(e -> {
            if (onViewTournamentDetailsAction != null) {
                onViewTournamentDetailsAction.accept(title);
            }
        });

        card.getChildren().addAll(imageView, topInfo, titleLbl, locLbl, winnerBox, viewTournamentBtn);

        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(200), card);
        scaleIn.setToX(1.02); scaleIn.setToY(1.02);
        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(200), card);
        scaleOut.setToX(1.0); scaleOut.setToY(1.0);

        card.setOnMouseEntered(e -> {
            scaleIn.playFromStart();
            card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: " + accentColor + "; -fx-border-width: 4 1 1 1; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.06), 10, 0, 0, 4);");
        });
        card.setOnMouseExited(e -> {
            scaleOut.playFromStart();
            card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: " + accentColor + " #e2e8f0 #e2e8f0 #e2e8f0; -fx-border-width: 4 1 1 1; -fx-border-radius: 12;");
        });

        return card;
    }
}