package com.example;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
  
public class Stats{
    static ArrayList<Integer> dartsStats1 = new ArrayList<>();
    static ArrayList<Integer> dartsStats2 = new ArrayList<>();

    public static void Statistics(List <Integer> dartsStats){
        dartsStats1.clear();
        dartsStats2.clear();   
        int j = 0;
        for(int i = 0; i < dartsStats.size(); i++){
            if (j < 3){
                dartsStats1.add(dartsStats.get(i));
                j++;
            }
            else if (j >= 3 &&  j< 6){
                dartsStats2.add(dartsStats.get(i));
                j++;
            }
            else if(j == 6){
                dartsStats1.add(dartsStats.get(i));
                j = 1;
            }
        }
        StatisticsHelper(dartsStats1, "Player 1");
        StatisticsHelper(dartsStats2, "Player 2");
    }
        
    /** One player's figures, as calculated by {@link #calculate(List)}. */
    public record Summary(double threeDartAverage, int bullseyes, int tripleTwenties){}

    /**
     * Calculates a player's three dart average, bullseye count and triple twenty count.
     * Same maths StatisticsHelper has always used, returned instead of printed so
     * the web API can use it.
     * @param dartsStats the darts one player has thrown (0 for a bust)
     * @return the three figures
     */
    public static Summary calculate(List<Integer> dartsStats){
        double threeDartAverage = 0;
        for(int i: dartsStats){
            threeDartAverage += i;
        }
        if(dartsStats.size() == 0){
            threeDartAverage = 0;
        }else{
            threeDartAverage /= (dartsStats.size());
            threeDartAverage = Math.round(threeDartAverage*3);
        }
        int TripleTwentyCount = Collections.frequency(dartsStats, 60);
        int bullseyeCount = Collections.frequency(dartsStats, 50);
        return new Summary(threeDartAverage, bullseyeCount, TripleTwentyCount);
    }

    public static void StatisticsHelper(ArrayList<Integer> dartsStats, String playerName){
        Summary summary = calculate(dartsStats);
        System.out.println(playerName + "'s Three dart average: " + summary.threeDartAverage());
        System.out.println(playerName + " hit: " + summary.bullseyes() + " bullseyes");
        System.out.println(playerName + " hit: " + summary.tripleTwenties() + " triple twenties");
    }
}
