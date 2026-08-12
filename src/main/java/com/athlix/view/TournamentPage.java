package com.athlix.view;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class TournamentPage {

    private StackPane rootContainer;
    private ScrollPane tournamentListScrollPane;

    public Node getView() {
        rootContainer = new StackPane();
        tournamentListScrollPane = buildTournamentListView();
        rootContainer.getChildren().add(tournamentListScrollPane);
        return rootContainer;
    }

    private ScrollPane buildTournamentListView() {
        VBox mainLayout = new VBox(25);
        mainLayout.setPadding(new Insets(25));
        mainLayout.setStyle("-fx-background-color: #f8fafc;");

        // --- 1. HERO BANNER ---
        VBox heroBanner = new VBox(15);
        heroBanner.setPadding(new Insets(30));
        heroBanner.setStyle("-fx-background-color: linear-gradient(to right, #0f172a, #1e3a8a); -fx-background-radius: 16;");
        heroBanner.setMinHeight(240);
        
        Label featuredBadge = new Label("● FEATURED TOURNAMENT");
        featuredBadge.setStyle("-fx-text-fill: #34d399; -fx-font-size: 12px; -fx-font-weight: bold;");
        
        Label heroTitle = new Label("Mumbai Premier League");
        heroTitle.setStyle("-fx-text-fill: white; -fx-font-size: 36px; -fx-font-weight: bold;");
        
        Label heroDesc = new Label("Experience the pinnacle of corporate cricket. Battle with the best teams for\nthe ultimate championship title.");
        heroDesc.setStyle("-fx-text-fill: #cbd5e1; -fx-font-size: 14px;");

        HBox heroBottom = new HBox(20);
        heroBottom.setAlignment(Pos.CENTER_LEFT);
        
        VBox regEndsBox = createHeroStatBox("REGISTRATION ENDS IN", "03 : 12 : 45");
        VBox prizeBox = createHeroStatBox("PRIZE POOL", "₹5,00,000");
        Button registerHeroBtn = new Button("Register Now ⚡");
        registerHeroBtn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 12 24; -fx-background-radius: 8; -fx-cursor: hand;");
        
        heroBottom.getChildren().addAll(regEndsBox, prizeBox, registerHeroBtn);
        heroBanner.getChildren().addAll(featuredBadge, heroTitle, heroDesc, heroBottom);

        // --- 2. MIDDLE SECTION: Stats Blocks & Refine Search Neighbor ---
        HBox middleSection = new HBox(25);
        middleSection.setAlignment(Pos.TOP_CENTER);

        // 2x2 Stats Grid
        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(20);
        statsGrid.setVgap(20);
        
        ColumnConstraints statCol1 = new ColumnConstraints();
        statCol1.setPercentWidth(50);
        ColumnConstraints statCol2 = new ColumnConstraints();
        statCol2.setPercentWidth(50);
        statsGrid.getColumnConstraints().addAll(statCol1, statCol2);

        statsGrid.add(createStatCard("Total Tournaments", "42", "🏆", "linear-gradient(to bottom right, #ffffff, #e0e7ff)"), 0, 0);
        statsGrid.add(createStatCard("My Registrations", "3", "📋", "linear-gradient(to bottom right, #ffffff, #d1fae5)"), 1, 0);
        statsGrid.add(createStatCard("Ongoing Events", "8", "▶", "linear-gradient(to bottom right, #ffffff, #ede9fe)"), 0, 1);
        statsGrid.add(createStatCard("Completed", "156", "⏱", "linear-gradient(to bottom right, #ffffff, #fef3c7)"), 1, 1);

        HBox.setHgrow(statsGrid, Priority.ALWAYS);

        // Refine Search Sidebar
        VBox refineBox = new VBox(15);
        refineBox.setPadding(new Insets(20));
        refineBox.setPrefWidth(320);
        refineBox.setMinWidth(320);
        refineBox.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12;");
        
        Label refineTitle = new Label("🔍 Refine Search");
        refineTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        
        Label formatLbl = new Label("FORMAT");
        formatLbl.setStyle("-fx-font-size: 11px; -fx-text-fill: #64748b; -fx-font-weight: bold;");
        ComboBox<String> formatCombo = new ComboBox<>();
        formatCombo.getItems().add("Leather Ball T20");
        formatCombo.getSelectionModel().selectFirst();
        formatCombo.setMaxWidth(Double.MAX_VALUE);
        formatCombo.setStyle("-fx-background-color: #f1f5f9; -fx-background-radius: 6;");

        Label cityLbl = new Label("CITY");
        cityLbl.setStyle("-fx-font-size: 11px; -fx-text-fill: #64748b; -fx-font-weight: bold;");
        
        HBox cityTags = new HBox(8);
        ToggleGroup cityGroup = new ToggleGroup();
        ToggleButton btnMumbai = createCityToggle("Mumbai", true); btnMumbai.setToggleGroup(cityGroup);
        ToggleButton btnPune = createCityToggle("Pune", false); btnPune.setToggleGroup(cityGroup);
        ToggleButton btnNashik = createCityToggle("Nashik", false); btnNashik.setToggleGroup(cityGroup);
        cityTags.getChildren().addAll(btnMumbai, btnPune, btnNashik);

        // Fee Filter with Dynamic Label
        HBox feeHeader = new HBox();
        feeHeader.setAlignment(Pos.CENTER_LEFT);
        
        Label feeLbl = new Label("REGISTRATION FEE RANGE");
        feeLbl.setStyle("-fx-font-size: 11px; -fx-text-fill: #64748b; -fx-font-weight: bold;");
        Region feeSpacer = new Region();
        HBox.setHgrow(feeSpacer, Priority.ALWAYS);
        Label feeValueLbl = new Label("₹2,500");
        feeValueLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #10b981; -fx-font-weight: bold;");
        feeHeader.getChildren().addAll(feeLbl, feeSpacer, feeValueLbl);

        Slider feeSlider = new Slider(0, 10000, 2500);
        feeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            feeValueLbl.setText("₹" + String.format("%,d", newVal.intValue()));
        });

        // Apply Filters Button
        Button searchBtn = new Button("Apply Filters");
        searchBtn.setMaxWidth(Double.MAX_VALUE);
        searchBtn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 10; -fx-cursor: hand;");
        searchBtn.setOnMouseEntered(e -> searchBtn.setStyle("-fx-background-color: #059669; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 10; -fx-cursor: hand;"));
        searchBtn.setOnMouseExited(e -> searchBtn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 10; -fx-cursor: hand;"));

        refineBox.getChildren().addAll(refineTitle, formatLbl, formatCombo, cityLbl, cityTags, feeHeader, feeSlider, searchBtn);
        middleSection.getChildren().addAll(statsGrid, refineBox);

        // --- 3. TAB NAVIGATION ---
        HBox tabs = new HBox(20);
        tabs.setStyle("-fx-border-color: #e2e8f0; -fx-border-width: 0 0 1 0; -fx-padding: 0 0 10 0;");
        
        ToggleGroup tabGroup = new ToggleGroup();
        ToggleButton tabUpcoming = createTabToggle("Upcoming", true, tabGroup);
        ToggleButton tabCurrent = createTabToggle("Current", false, tabGroup);
        ToggleButton tabCompleted = createTabToggle("Completed", false, tabGroup);
        ToggleButton tabMyTournaments = createTabToggle("My Tournaments", false, tabGroup);

        tabs.getChildren().addAll(tabUpcoming, tabCurrent, tabCompleted, tabMyTournaments);

        // --- 4. TOURNAMENT CARDS ROW ---
        HBox cardsRow = new HBox(20);
        String imgPath1 = "https://dummyimage.com/280x130/e2e8f0/64748b.png&text=Corporate+T20"; 
        String imgPath2 = "https://dummyimage.com/280x140/e2e8f0/64748b.png&text=U-19+Trials";
        String imgPath3 = "https://dummyimage.com/280x140/e2e8f0/64748b.png&text=Weekend+Cup";

        cardsRow.getChildren().addAll(
            createTournamentCard(imgPath1, "Corporate T20 Blast", "Allied Sports Mgmt", "Shivaji Park, Mumbai", "₹2,500 / Team", "₹1.2L Prize", "Register Team", "#3b82f6"),
            createTournamentCard(imgPath2, "Academy U-19 Trials", "Star Sports Academy", "NCA Grounds, Pune", "FREE Entry", "Selection Trial", "Apply Now", "#10b981"),
            createTournamentCard(imgPath3, "Weekend Warriors Cup", "Athlix Community", "Local Ground, Nashik", "₹1,500 / Team", "Trophy + Kit", "Register Team", "#8b5cf6")
        );

        mainLayout.getChildren().addAll(heroBanner, middleSection, tabs, cardsRow);

        // --- TAB SWITCHING & PAGE ROUTING LOGIC ---
        
        // 1. Current Tournament Page Instance
        final CurrentTournament[] currentTournamentPageHolder = new CurrentTournament[1];
        currentTournamentPageHolder[0] = new CurrentTournament(
            () -> {
                tabUpcoming.setSelected(true);
                rootContainer.getChildren().setAll(tournamentListScrollPane);
            },
            () -> {
                final CurrentTournamentLiveMatch[] liveMatchPageHolder = new CurrentTournamentLiveMatch[1];
                liveMatchPageHolder[0] = new CurrentTournamentLiveMatch(
                    () -> rootContainer.getChildren().setAll(currentTournamentPageHolder[0].getView()),
                    () -> {
                        CurrentTournamentLiveMatchview allLiveView = new CurrentTournamentLiveMatchview(() -> {
                            rootContainer.getChildren().setAll(liveMatchPageHolder[0].getView());
                        });
                        rootContainer.getChildren().setAll(allLiveView.getView());
                    }
                );
                rootContainer.getChildren().setAll(liveMatchPageHolder[0].getView());
            },
            () -> {
                final CurrentTournamentview[] moreViewHolder = new CurrentTournamentview[1];
                moreViewHolder[0] = new CurrentTournamentview(
                    () -> rootContainer.getChildren().setAll(currentTournamentPageHolder[0].getView()), 
                    () -> {
                        final CurrentTournamentLiveMatch[] liveMatchPageHolder = new CurrentTournamentLiveMatch[1];
                        liveMatchPageHolder[0] = new CurrentTournamentLiveMatch(
                            () -> rootContainer.getChildren().setAll(moreViewHolder[0].getView()),
                            () -> {
                                CurrentTournamentLiveMatchview allLiveView = new CurrentTournamentLiveMatchview(() -> {
                                    rootContainer.getChildren().setAll(liveMatchPageHolder[0].getView());
                                });
                                rootContainer.getChildren().setAll(allLiveView.getView());
                            }
                        );
                        rootContainer.getChildren().setAll(liveMatchPageHolder[0].getView());
                    }
                );
                rootContainer.getChildren().setAll(moreViewHolder[0].getView());
            }
        );

        // 2. Completed Tournament Page & CompletedTournamentView Routing
        final CompletedTournament[] completedTournamentPageHolder = new CompletedTournament[1];
        final CompletedTournamentView[] allCompletedViewHolder = new CompletedTournamentView[1];

        allCompletedViewHolder[0] = new CompletedTournamentView(
            () -> {
                // Back from full archive view returns to Completed summary
                rootContainer.getChildren().setAll(completedTournamentPageHolder[0].getView());
            },
            clickedTournamentTitle -> {
                // Action when "View Tournament" button inside CompletedTournamentView is clicked
                CompletedTournamentonviewTournament detailsView = new CompletedTournamentonviewTournament(
                    clickedTournamentTitle,
                    () -> rootContainer.getChildren().setAll(allCompletedViewHolder[0].getView()) // Back returns to archive
                );
                rootContainer.getChildren().setAll(detailsView.getView());
            }
        );

        completedTournamentPageHolder[0] = new CompletedTournament(
            () -> {
                // Back button restores Upcoming tab view
                tabUpcoming.setSelected(true);
                rootContainer.getChildren().setAll(tournamentListScrollPane);
            },
            () -> {
                // Action when "View All Completed Tournaments ➔" is clicked
                rootContainer.getChildren().setAll(allCompletedViewHolder[0].getView());
            },
            clickedTournamentTitle -> {
                // Action when "View Tournament" card button in summary page is clicked
                CompletedTournamentonviewTournament detailsView = new CompletedTournamentonviewTournament(
                    clickedTournamentTitle,
                    () -> rootContainer.getChildren().setAll(completedTournamentPageHolder[0].getView()) // Back returns to summary
                );
                rootContainer.getChildren().setAll(detailsView.getView());
            }
        );
        CompletedTournament completedTournamentPage = completedTournamentPageHolder[0];

        // 3. My Tournaments Page Instance (UPDATED TO PASS BACK ACTION)
        MyTournamentPage myTournamentsPage = new MyTournamentPage(() -> {
            // Re-select the "Upcoming" tab when they click back on the dashboard
            tabUpcoming.setSelected(true);
            rootContainer.getChildren().setAll(tournamentListScrollPane);
        });

        // --- TAB SELECTION LISTENERS ---
        tabCurrent.selectedProperty().addListener((obs, oldVal, isCurrentSelected) -> {
            if (isCurrentSelected) {
                rootContainer.getChildren().setAll(currentTournamentPageHolder[0].getView());
            }
        });

        tabCompleted.selectedProperty().addListener((obs, oldVal, isCompletedSelected) -> {
            if (isCompletedSelected) {
                rootContainer.getChildren().setAll(completedTournamentPage.getView());
            }
        });

        tabUpcoming.selectedProperty().addListener((obs, oldVal, isUpcomingSelected) -> {
            if (isUpcomingSelected) {
                rootContainer.getChildren().setAll(tournamentListScrollPane);
            }
        });

        // Add Listener for My Tournaments Tab
        tabMyTournaments.selectedProperty().addListener((obs, oldVal, isMyTournamentsSelected) -> {
            if (isMyTournamentsSelected) {
                rootContainer.getChildren().setAll(myTournamentsPage.getView());
            }
        });

        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f8fafc;");
        scrollPane.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");

        return scrollPane;
    }

    private VBox createHeroStatBox(String title, String value) {
        VBox box = new VBox(5);
        box.setPadding(new Insets(10, 20, 10, 20));
        box.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-background-radius: 8;");
        Label t = new Label(title);
        t.setStyle("-fx-text-fill: #cbd5e1; -fx-font-size: 10px;");
        Label v = new Label(value);
        v.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");
        box.getChildren().addAll(t, v);
        return box;
    }

    private VBox createStatCard(String title, String value, String icon, String gradientStyle) {
        VBox box = new VBox(8);
        box.setPadding(new Insets(18));
        box.setStyle("-fx-background: " + gradientStyle + "; -fx-background-color: " + gradientStyle + "; -fx-background-radius: 12; -fx-border-color: #cbd5e1; -fx-border-radius: 12;");
        HBox.setHgrow(box, Priority.ALWAYS);
        
        Label t = new Label(title);
        t.setStyle("-fx-text-fill: #475569; -fx-font-size: 12px; -fx-font-weight: bold;");
        
        HBox valBox = new HBox(10);
        valBox.setAlignment(Pos.CENTER_LEFT);
        Label v = new Label(value);
        v.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Label i = new Label(icon);
        i.setStyle("-fx-font-size: 18px; -fx-text-fill: #0f172a;");
        
        valBox.getChildren().addAll(v, spacer, i);
        box.getChildren().addAll(t, valBox);

        addHoverAnimation(box);
        return box;
    }

    private ToggleButton createTabToggle(String text, boolean selected, ToggleGroup group) {
        ToggleButton btn = new ToggleButton(text);
        btn.setSelected(selected);
        btn.setToggleGroup(group);
        btn.setCursor(javafx.scene.Cursor.HAND);
        
        Runnable updateStyle = () -> {
            if (btn.isSelected()) {
                btn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 8 18;");
            } else {
                btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #64748b; -fx-font-size: 15px; -fx-font-weight: normal; -fx-background-radius: 8; -fx-padding: 8 18;");
            }
        };

        updateStyle.run();
        btn.selectedProperty().addListener((obs, oldVal, newVal) -> updateStyle.run());

        btn.setOnMouseEntered(e -> {
            if (!btn.isSelected()) {
                btn.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #0f172a; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 8 18; -fx-cursor: hand;");
            }
        });
        btn.setOnMouseExited(e -> {
            if (!btn.isSelected()) {
                updateStyle.run();
            }
        });

        return btn;
    }

    private VBox createTournamentCard(String imageSource, String title, String org, String loc, String fee, String prize, String btnText, String accentColor) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: " + accentColor + " #e2e8f0 #e2e8f0 #e2e8f0; -fx-border-width: 4 1 1 1; -fx-border-radius: 12;");
        HBox.setHgrow(card, Priority.ALWAYS);

        ImageView imageView = new ImageView();
        try {
            Image img = new Image(imageSource, true);
            imageView.setImage(img);
        } catch (Exception e) {
            System.err.println("Could not load image: " + imageSource);
        }
        
        imageView.setFitWidth(260);
        imageView.setFitHeight(120);
        imageView.setPreserveRatio(false); 
        
        Rectangle clip = new Rectangle(260, 120);
        clip.setArcWidth(10);
        clip.setArcHeight(10);
        imageView.setClip(clip);

        Label t = new Label(title);
        t.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        
        Label o = new Label("By " + org);
        o.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748b;");
        
        Label l = new Label("📍 " + loc);
        l.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748b;");

        HBox details = new HBox(15);
        Label f = new Label("💳 " + fee);
        f.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        Label p = new Label("🏆 " + prize);
        p.setStyle("-fx-font-size: 12px; -fx-text-fill: #10b981; -fx-font-weight: bold;");
        details.getChildren().addAll(f, p);

        HBox buttonBox = new HBox(8);
        
        Button viewDetailsBtn = new Button("View Details");
        viewDetailsBtn.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(viewDetailsBtn, Priority.ALWAYS);
        viewDetailsBtn.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 8; -fx-cursor: hand;");
        
        viewDetailsBtn.setOnAction(e -> {
            TournamentDetailsPage detailsPage = new TournamentDetailsPage(
                () -> rootContainer.getChildren().setAll(tournamentListScrollPane),
                title, loc, fee, prize
            );
            rootContainer.getChildren().setAll(detailsPage.getView()); 
        });

        Button primaryBtn = new Button(btnText);
        primaryBtn.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(primaryBtn, Priority.ALWAYS);
        primaryBtn.setStyle("-fx-background-color: linear-gradient(to right, #10b981, #059669); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8; -fx-background-radius: 6; -fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(16,185,129,0.3), 5, 0, 0, 2);");

        ScaleTransition btnScaleIn = new ScaleTransition(Duration.millis(150), primaryBtn);
        btnScaleIn.setToX(1.04);
        btnScaleIn.setToY(1.04);

        ScaleTransition btnScaleOut = new ScaleTransition(Duration.millis(150), primaryBtn);
        btnScaleOut.setToX(1.0);
        btnScaleOut.setToY(1.0);

        primaryBtn.setOnMouseEntered(e -> btnScaleIn.playFromStart());
        primaryBtn.setOnMouseExited(e -> btnScaleOut.playFromStart());

        buttonBox.getChildren().addAll(viewDetailsBtn, primaryBtn);
        card.getChildren().addAll(imageView, t, o, l, details, buttonBox);

        addHoverAnimation(card);
        return card;
    }

    private ToggleButton createCityToggle(String text, boolean selected) {
        ToggleButton btn = new ToggleButton(text);
        btn.setSelected(selected);
        btn.setCursor(javafx.scene.Cursor.HAND);
        
        Runnable updateToggleStyle = () -> {
            if (btn.isSelected()) {
                btn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 4 12;");
            } else {
                btn.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #64748b; -fx-background-radius: 20; -fx-padding: 4 12;");
            }
        };

        updateToggleStyle.run();
        btn.selectedProperty().addListener((obs, oldVal, newVal) -> updateToggleStyle.run());

        return btn;
    }

    private void addHoverAnimation(Node node) {
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(200), node);
        scaleIn.setToX(1.02);
        scaleIn.setToY(1.02);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(200), node);
        scaleOut.setToX(1.0);
        scaleOut.setToY(1.0);

        node.setOnMouseEntered(e -> scaleIn.playFromStart());
        node.setOnMouseExited(e -> scaleOut.playFromStart());
    }
}