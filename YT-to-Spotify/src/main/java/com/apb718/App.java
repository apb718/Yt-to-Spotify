package com.apb718;

import java.io.FileWriter;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;


class Main {
    private static String spotifyLocation;
    public static boolean keep_going = true;
    public static void main(String[] args) {
        clearScreen();
        // load saved config
        loadSpotifyLocation();
        // check if there is a saved location
        checkIfSpotifyLocationExists();
        spotifyLocation = spotifyLocation.trim();
        while(keep_going) {
            printOptions();
            chooseOption();
        }
        
        
    
        
        


        
    }

    public static void chooseOption() {
        System.out.print("Please choose an option: ");
        int input;
        try{
            input = Integer.parseInt(getInput());
        }   catch (Exception e) {
            System.out.println("Error: " + e);
            return;
        }
        
        switch(input) {
            case 1:
                //Choose video
                // System.out.println("You chose 1");
                chooseVideo();
                break;
            case 2:
                //View Spotify Save Location
                // System.out.println("You chose 2");
                clearScreen();
                System.out.println("Spotify Location: \"" + getSpotifyLocation() + "\\OUTPUT_FILE.mp3");
                System.out.println();
                break;
            case 3:
                //Set New Spotify Location
                // System.out.println("You chose 3");
                checkIfSpotifyLocationExists();
                clearScreen();
                System.out.println("New Location is: " + getSpotifyLocation());
                break;
            case 4:
                //Close Program
                // System.out.println("You chose 4");
                endProgram();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }
    
    public static void chooseVideo() {
        clearScreen();
        System.out.print("Enter Youtube Video URL:");
        String url = getInput().trim();
        String path = getSpotifyLocation();

        ProcessBuilder pb = new ProcessBuilder("YT-to-Spotify\\src\\youtube-dl.exe", "--extract-audio","--audio-format","mp3","-o",path + "\\%(title)s.mp3",url); 
        try {
            Process p = pb.start();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
            
        

    }

    public static void printOptions() {
        print_bar();
        System.out.println("Options:");
        System.out.println("\t1. Choose Video to download");
        System.out.println("\t2. View Spotify Save Location");
        System.out.println("\t3. Set New Spotify Location");
        System.out.println("\t4. Close Program");
        print_bar();
    }

    public static void checkIfSpotifyLocationExists() {
       
        // check if there is already a location saved
        if(spotifyLocation == null){
            assignSpotifyLocation();
        } 
        else {
            System.out.println("You already have a spotify location set.");
            System.out.print("Would you like to set a new Spotify location?(y/n)");
            String input = getInput();
            if(input.equals("y")){
                assignSpotifyLocation();
            }
        }
    }

    public static void assignSpotifyLocation() {

        System.out.print("Please enter the location of your Spotify installation(i.e \"C:\\~\\~\"): ");
        String input = getInput();
        setSpotifyLocation(input);
        
        System.out.println("Spotify location: " + spotifyLocation);
        
        
        saveSpotifyLocation();
    
    }

    public static String getInput() {
        
        Scanner input = new Scanner(System.in);
        System.out.println("");

        String userInput = input.nextLine();
        return userInput;
        
    }

    public static void saveSpotifyLocation() {
        
        try { 
            
            BufferedWriter writer = new BufferedWriter(new FileWriter("spotifyLocation.txt"));
            //if file is empty
            writer.write(spotifyLocation);
            writer.close();
            return;
            
            
        }  catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }

    public static void loadSpotifyLocation() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("spotifyLocation.txt"));
            String line;
            
            while((line = reader.readLine()) != null) {
                setSpotifyLocation(line);
            }
            
            reader.close();

        } catch(Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public static String getSpotifyLocation() {
        return spotifyLocation;
    }

    public static void setSpotifyLocation(String spotifyLocation) {
        Main.spotifyLocation = spotifyLocation;
    }
    
    public static void print_bar() {
        System.out.println("--------------------------------------------------");
    }

    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  

    public static void endProgram() {
        
        clearScreen();
        
        System.out.println("See you next time!");
        System.out.println();
        keep_going = false;
        
    }
}
