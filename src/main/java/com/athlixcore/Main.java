package com.athlixcore;

// import com.athlixcore.view.coach.CoachHomePage;
// import com.athlixcore.view.organizer.OrganizerHomePage;
import com.athlixcore.view.player.PlayerHomePage;
// import com.athlixcore.view.tmanager.TmanagerHomePage;

import javafx.application.Application;

public class Main {
    
    public static void main(String[] args) {
        System.out.println("HomePage Start");
        // Application.launch(CoachHomePage.class, args);
        // Application.launch(OrganizerHomePage.class, args);
        // Application.launch(PlayerHomePage.class, args);
        Application.launch(PlayerHomePage.class, args);


    }
}