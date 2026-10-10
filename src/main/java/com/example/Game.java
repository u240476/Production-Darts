package com.example;
import java.util.Scanner;

/**
 * @author Josh Reynolds
 * @version 1.0
 * @since 2024-06-01
 */
public class Game{
     
    /**
     * @param num the starting score
     * @return true if num is 301, 401 or 501
     */
    public static boolean isValidGame(int num){
        return num == 301 || num == 401 || num == 501;
    }

    /**
      * This method will get the game number from the user, it must be 301, 401, or 501. 
      * If the user inputs an invalid number, it will throw an IllegalArgumentException.
      * @return the game number
      */

    public static int gettingGame(){
        Scanner sc1 = new Scanner(System.in);
        System.out.println("please input which game you are playing");
        int num = sc1.nextInt();
        if(!isValidGame(num)){
            throw new IllegalArgumentException("Game must be 301,401 or 501");
        }
        return num;

    }
}