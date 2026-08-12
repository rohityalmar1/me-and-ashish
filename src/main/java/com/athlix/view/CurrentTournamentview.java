// package com.athlix.view;

// import javafx.animation.ScaleTransition;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.Node;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.VBox;
// import javafx.util.Duration;

// public class CurrentTournamentview {

//     private Runnable onBackAction;
//     private Runnable onViewLiveMatchAction;

//     public CurrentTournamentview(Runnable onBackAction, Runnable onViewLiveMatchAction) {
//         this.onBackAction = onBackAction;
//         this.onViewLiveMatchAction = onViewLiveMatchAction;
//     }

//     public Node getView() {
//         VBox mainContainer = new VBox(25);
//         mainContainer.setPadding(new Insets(30));
//         mainContainer.setStyle("-fx-background-color: #f8fafc;");

//         // --- HEADER SECTION: Back Button & Title ---
//         HBox header = new HBox(20);
//         header.setAlignment(Pos.CENTER_LEFT);

//         Button backBtn = new Button("❮  Back to Ongoing");
//         backBtn.setStyle(
//             "-fx-background-color: #ffffff; " +
//             "-fx-text-fill: #10b981; " +
//             "-fx-font-weight: bold; " +
//             "-fx-font-size: 14px; " +
//             "-fx-padding: 8 18; " +
//             "-fx-background-radius: 20; " +
//             "-fx-border-color: #e2e8f0; " +
//             "-fx-border-radius: 20; " +
//             "-fx-cursor: hand;"
//         );
//         backBtn.setOnAction(e -> {
//             if (onBackAction != null) onBackAction.run();
//         });

//         Label titleText = new Label("🔥 All Current & Ongoing Tournaments");
//         titleText.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

//         header.getChildren().addAll(backBtn, titleText);

//         // --- VERTICAL LIST OF MULTIPLE TOURNAMENTS ---
//         VBox tournamentsList = new VBox(25);
//         tournamentsList.setAlignment(Pos.CENTER);

//         tournamentsList.getChildren().addAll(
//             createTournamentCard("Corporate T20 Blast", "Shivaji Park, Mumbai", "₹1.2L Prize", "#3b82f6"),
//             createTournamentCard("Academy U-19 Championship", "NCA Grounds, Pune", "Selection Trial", "#10b981"),
//             createTournamentCard("Weekend Warriors Cup", "Local Ground, Nashik", "Trophy + Kit", "#8b5cf6"),
//             createTournamentCard("State Super League", "MCA Stadium, Pune", "₹5.0L Prize", "#f59e0b")
//         );

//         // --- BOTTOM SECTION: View More Tournaments Button (Styled as requested) ---
//         HBox bottomActionBox = new HBox();
//         bottomActionBox.setAlignment(Pos.CENTER);
//         bottomActionBox.setPadding(new Insets(20, 0, 10, 0));

//         Button viewMoreBtn = new Button("View More Tournaments ➔");
//         viewMoreBtn.setStyle(
//             "-fx-background-color: #ffffff; " +
//             "-fx-border-color: #10b981; " +
//             "-fx-border-radius: 8; " +
//             "-fx-background-radius: 8; " +
//             "-fx-text-fill: #10b981; " +
//             "-fx-font-size: 15px; " +
//             "-fx-font-weight: bold; " +
//             "-fx-padding: 12 30; " +
//             "-fx-cursor: hand;"
//         );
//         viewMoreBtn.setOnAction(e -> {
//             System.out.println("Loading additional tournaments...");
//         });

//         bottomActionBox.getChildren().add(viewMoreBtn);

//         mainContainer.getChildren().addAll(header, tournamentsList, bottomActionBox);

//         ScrollPane scrollPane = new ScrollPane(mainContainer);
//         scrollPane.setFitToWidth(true);
//         scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f8fafc;");
//         scrollPane.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");

//         return scrollPane;
//     }

//     private VBox createTournamentCard(String title, String loc, String prize, String accentColor) {
//         VBox card = new VBox(15);
//         card.setPadding(new Insets(25));
//         card.setMaxWidth(850);
//         card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: " + accentColor + " #e2e8f0 #e2e8f0 #e2e8f0; -fx-border-width: 4 1 1 1; -fx-border-radius: 12;");
//         HBox.setHgrow(card, Priority.ALWAYS);

//         // Clean header banner container
//         VBox bannerBox = new VBox();
//         bannerBox.setAlignment(Pos.CENTER);
//         bannerBox.setPadding(new Insets(30, 20, 30, 20));
//         bannerBox.setStyle("-fx-background-color: #f1f5f9; -fx-background-radius: 8; -fx-border-color: #cbd5e1; -fx-border-radius: 8;");
        
//         Label bannerTitle = new Label(title);
//         bannerTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
//         bannerBox.getChildren().add(bannerTitle);

//         Label l = new Label("📍 " + loc);
//         l.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748b;");

//         HBox details = new HBox();
//         Label p = new Label("🏆 " + prize);
//         p.setStyle("-fx-font-size: 14px; -fx-text-fill: #10b981; -fx-font-weight: bold;");
//         details.getChildren().add(p);

//         // Action Button to open live match view
//         Button currentTourneyBtn = new Button("View Current Tournament");
//         currentTourneyBtn.setMaxWidth(Double.MAX_VALUE);
//         currentTourneyBtn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 12; -fx-cursor: hand;");
        
//         currentTourneyBtn.setOnAction(e -> {
//             if (onViewLiveMatchAction != null) {
//                 onViewLiveMatchAction.run();
//             }
//         });

//         card.getChildren().addAll(bannerBox, l, details, currentTourneyBtn);

//         // Hover scale animation effect
//         ScaleTransition scaleIn = new ScaleTransition(Duration.millis(200), card);
//         scaleIn.setToX(1.01);
//         scaleIn.setToY(1.01);

//         ScaleTransition scaleOut = new ScaleTransition(Duration.millis(200), card);
//         scaleOut.setToX(1.0);
//         scaleOut.setToY(1.0);

//         card.setOnMouseEntered(e -> scaleIn.playFromStart());
//         card.setOnMouseExited(e -> scaleOut.playFromStart());

//         return card;
//     }
// }

package com.athlix.view;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class CurrentTournamentview {

    private Runnable onBackAction;
    private Runnable onViewLiveMatchAction;

    public CurrentTournamentview(Runnable onBackAction, Runnable onViewLiveMatchAction) {
        this.onBackAction = onBackAction;
        this.onViewLiveMatchAction = onViewLiveMatchAction;
    }

    public Node getView() {
        VBox mainContainer = new VBox(25);
        mainContainer.setPadding(new Insets(30, 30, 80, 30));
        mainContainer.setStyle("-fx-background-color: #f8fafc;");

        // --- HEADER SECTION: Back Button & Title ---
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);

        Button backBtn = new Button("❮  Back to Ongoing");
        backBtn.setStyle(
            "-fx-background-color: #ffffff; -fx-text-fill: #10b981; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 8 18; -fx-background-radius: 20; -fx-border-color: #e2e8f0; -fx-border-radius: 20; -fx-cursor: hand;"
        );
        backBtn.setOnAction(e -> {
            if (onBackAction != null) onBackAction.run();
        });

        Label titleText = new Label("🔥 All Current & Ongoing Tournaments");
        titleText.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        header.getChildren().addAll(backBtn, titleText);

        // --- VERTICAL LIST OF MULTIPLE TOURNAMENTS ---
        VBox tournamentsList = new VBox(25);
        tournamentsList.setAlignment(Pos.CENTER);

        tournamentsList.getChildren().addAll(
            createTournamentCard("Corporate T20 Blast", "Shivaji Park, Mumbai", "₹1.2L Prize", "#3b82f6"),
            createTournamentCard("Academy U-19 Championship", "NCA Grounds, Pune", "Selection Trial", "#10b981"),
            createTournamentCard("Weekend Warriors Cup", "Local Ground, Nashik", "Trophy + Kit", "#8b5cf6"),
            createTournamentCard("State Super League", "MCA Stadium, Pune", "₹5.0L Prize", "#f59e0b")
        );

        mainContainer.getChildren().addAll(header, tournamentsList);

        ScrollPane scrollPane = new ScrollPane(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f8fafc;");
        scrollPane.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");

        return scrollPane;
    }

    private VBox createTournamentCard(String title, String loc, String prize, String accentColor) {
        VBox card = new VBox(15);
        card.setPadding(new Insets(25));
        card.setMaxWidth(850);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: " + accentColor + " #e2e8f0 #e2e8f0 #e2e8f0; -fx-border-width: 4 1 1 1; -fx-border-radius: 12;");
        HBox.setHgrow(card, Priority.ALWAYS);

        VBox bannerBox = new VBox();
        bannerBox.setAlignment(Pos.CENTER);
        bannerBox.setPadding(new Insets(30, 20, 30, 20));
        bannerBox.setStyle("-fx-background-color: #f1f5f9; -fx-background-radius: 8; -fx-border-color: #cbd5e1; -fx-border-radius: 8;");
        
        Label bannerTitle = new Label(title);
        bannerTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        bannerBox.getChildren().add(bannerTitle);

        Label l = new Label("📍 " + loc);
        l.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748b;");

        HBox details = new HBox();
        Label p = new Label("🏆 " + prize);
        p.setStyle("-fx-font-size: 14px; -fx-text-fill: #10b981; -fx-font-weight: bold;");
        details.getChildren().add(p);

        // Action Button to open live match view
        Button currentTourneyBtn = new Button("View Current Tournament");
        currentTourneyBtn.setMaxWidth(Double.MAX_VALUE);
        currentTourneyBtn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 12; -fx-cursor: hand;");
        
        currentTourneyBtn.setOnAction(e -> {
            if (onViewLiveMatchAction != null) {
                onViewLiveMatchAction.run(); // THIS TRIGGERS THE SWITCH!
            }
        });

        card.getChildren().addAll(bannerBox, l, details, currentTourneyBtn);

        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(200), card);
        scaleIn.setToX(1.01);
        scaleIn.setToY(1.01);
        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(200), card);
        scaleOut.setToX(1.0);
        scaleOut.setToY(1.0);

        card.setOnMouseEntered(e -> scaleIn.playFromStart());
        card.setOnMouseExited(e -> scaleOut.playFromStart());

        return card;
    }
}