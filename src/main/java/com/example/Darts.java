package com.example;
public class Darts{
    /**
     * this method checks if the dart thrown is a valid double
     * @param dart
     * @return boolean
     */
    public static boolean checkingValidDarts(int dart){
        final int[] validDarts = {
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
        11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
        21, 22, 24, 25, 26, 27, 28, 30, 32, 33, 34,
        36, 38, 39, 40, 42, 45, 48, 50, 51, 54, 57, 60}; 
        for(int i: validDarts){
            if(dart == i){
                return true;
            }
        }
        return false;
    }
}