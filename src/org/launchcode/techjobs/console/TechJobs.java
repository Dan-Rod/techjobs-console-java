package org.launchcode.techjobs.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    private static Scanner in = new Scanner(System.in);

    public static void main (String[] args) {

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("name", "Name");
        columnChoices.put("all", "All");

        // Top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Welcome to LaunchCode's TechJobs App!");

        //Top level search string

        // Allow the user to search until they manually quit
        while (true) {

            String actionChoice = getUserSelection("View jobs by:", actionChoices);

            if (actionChoice.equals("list")) {

                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) {
                    System.out.println(printJobs(JobData.findAll()));
                } else {

                    ArrayList<String> results = JobData.findAll(columnChoice);

                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");

                    // Print list of skills, employers, etc
                    for (String item : results) {
                        System.out.println(item);
                    }
                }

            } else { // choice is "search"

                // How does the user want to search (e.g. by skill or employer)
                String columnChoice = getUserSelection("Search by:",columnChoices);
                String filteredSearch = getUserSearch("\n" + "What would you like to search for?");

                if (columnChoice.equals("all")) {
                    // What is their search term?
                        System.out.println(printJobs(JobData.findByValue(columnChoices,filteredSearch)));

                }else {
                        System.out.println(printJobs(JobData.findByColumnAndValue(columnChoice,filteredSearch)));
                }
            }
        }
    }


    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {

        Integer choiceIdx;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        Integer i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {

            System.out.println("\n" + menuHeader);

            // Print available choices
            for (Integer j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

            choiceIdx = in.nextInt();
            in.nextLine();

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            } else {
                validChoice = true;
            }

        } while(!validChoice);

        return choiceKeys[choiceIdx];
    }

    // Returns the string from user's search term
    private static String getUserSearch(String menuHeader){
        System.out.println("\n" + menuHeader);
        String searchTerm= in.next();
        return searchTerm;

    }

    // Print a list of jobs
    private static StringBuilder printJobs(ArrayList<HashMap<String, String>> someJobs) {
        StringBuilder jobsList = new StringBuilder();
        for (HashMap job:someJobs){
            jobsList.append("\n****\n");
            for (Object line:job.keySet()){
                 jobsList.append(line +": "+ job.get(line) + "\n");
             }jobsList.append("****\n");
        }if (jobsList.length()==0){
            return jobsList.append("\n****\n No jobs found that match your request \n****\n");
        }
        return jobsList;
    }

}
