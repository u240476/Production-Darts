package com.example;
/** This class will check if the dart thrown is a valid double, it will be used in the Finish class to check if the player has won by throwing a valid double. 
 * A valid double is any even number between 2 and 40, or 50 (the bullseye). 
 * If the player throws a valid double that brings their score to exactly 0, they win the game. 
 * If the player throws a valid double that brings their score below 0, it is considered a bust and their score resets to what it was at the start of their turn. 
 * If the player throws an invalid double, it is also considered a bust and their score resets to what it was at the start of their turn. 
 * @author Josh Reynolds
 * @version 1.0
 * @since 25/02/2026
 */  

public class Doubles{
    /**
     * this method checks if the dart thrown is a valid double
     * @param dart
     * @return boolean
     */
    public static boolean checkingValidDoubles(int dart){
        final int[] validDoubles = {2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36,38,40,50}; 
        for(int i: validDoubles){
            if(dart == i){
                return true;
            }
        }
        return false;
    }
}