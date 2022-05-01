package com.example.sfgrecipe.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ProfileHelper {

    private final String[] LOWER_ENVS = {"dev", "qa"};

    private boolean verboseEnabled = false;

    @Value("${spring.profiles.active}")
    private String activeProfiles;

    public ProfileHelper(Environment env) {
        if (env == null) return;
        String[] profiles = env.getActiveProfiles();
        for ( int i = 0; profiles != null && i < profiles.length && !verboseEnabled; i++ ) {
            for ( int j = 0; j < LOWER_ENVS.length && !verboseEnabled; j++ ) {
                verboseEnabled |= profiles[i].equalsIgnoreCase(LOWER_ENVS[j]);
            }
        }

    }

    public boolean isThisProduction() {
        return ! verboseEnabled;
    }

    @Override
    public String toString() {
        return "ProfileHelper{" +
                "verboseEnabled=" + verboseEnabled +
                ", activeProfiles='" + activeProfiles + '\'' +
                '}';
    }
}
