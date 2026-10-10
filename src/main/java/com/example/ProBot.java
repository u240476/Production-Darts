
package com.example;

import java.util.Map;

public class ProBot {
     static int[] boardList = {20,1,18,4,13,6,10,15,2,17,3,19,7,16,8,11,14,9,12,5}; 
      static double TriplePercentage = 0.0; 
     static double NumberPercentage = 0.0;
     static double leftPercentage = 0.0;
     static double rightPercentage = 0.0;
     static double checkoutPercentage = 0.0;
     static double checkoutNumberPercentage = 0.0;
     static double outPercentage = 0.0;
     static double twentyChance = 0.0;
     static double nineteenChance = 0.0;
     static double eighteenChance = 0.0;

     public static void AssigningStats(
        double[] playerStatistics
     ){
        ProBot.TriplePercentage = playerStatistics[0];
        ProBot.NumberPercentage = playerStatistics[1];
        ProBot.leftPercentage = playerStatistics[2];
        ProBot.rightPercentage = playerStatistics[3];
        ProBot.checkoutPercentage = playerStatistics[4];
        ProBot.checkoutNumberPercentage = playerStatistics[5];  
        ProBot.outPercentage = playerStatistics[6];
        ProBot.twentyChance = playerStatistics[7];
        ProBot.nineteenChance = playerStatistics[8];
        ProBot.eighteenChance = playerStatistics[9];
     }
    public static int ProPlayer(int score){
        
        int dart = 0;
        int checkoutIndex = 0;
            double numberChanceValue = Math.random() * 100;
            String[] checkoutOptions = Checkout.getCheckoutOptions(score);
            if(score <= 170 && checkoutOptions != null){
              int[] checkoutOptionsInt = new int[checkoutOptions.length];
              for (int i = 0; i < checkoutOptions.length; i++) {
                checkoutOptionsInt[i] = checkoutToInt.get(checkoutOptions[i]);
              }
              if(numberChanceValue < outPercentage){
                dart = 0;
                return dart;
              }else if(numberChanceValue < outPercentage + checkoutNumberPercentage){
                if(checkoutOptions[checkoutIndex].charAt(0) == 'T'){
                    dart =  checkoutOptionsInt[checkoutIndex]/3;
                    return dart;
                }else{
                    dart = checkoutOptionsInt[checkoutIndex]/2;
                    return dart;
                }
              }else{
                dart = checkoutOptionsInt[checkoutIndex];
                return dart;
              }
            }
            if(numberChanceValue < eighteenChance){
                int eighteenIndex = 0;
                for(int i = 0; i<boardList.length; i++){
                    if(boardList[i] == 18){
                        eighteenIndex = i;
                        break;
                    }
                }
                    double eighteenpercentageValue = Math.random() * 100;
                    if(eighteenpercentageValue < leftPercentage){
                        eighteenIndex = (eighteenIndex - 1 + boardList.length) % boardList.length;
                        dart = boardList[eighteenIndex];
                        return dart;
                    }
                    else if(eighteenpercentageValue < leftPercentage + rightPercentage){
                        eighteenIndex = (eighteenIndex + 1) % boardList.length;
                        dart = boardList[eighteenIndex];
                        return dart;
                    }
                    else if(eighteenpercentageValue < leftPercentage + rightPercentage + NumberPercentage){
                        dart = 18;
                        return dart;
                    }else{
                        dart = 54;
                        return dart;
                    }
                
            }
            else if(numberChanceValue < eighteenChance + nineteenChance){
                int nineteenIndex = 0;
                for(int i = 0; i<boardList.length; i++){
                    if(boardList[i] == 19){
                        nineteenIndex = i;
                        break;
                    }
                }
                    double nineteenpercentageValue = Math.random() * 100;
                    if(nineteenpercentageValue < leftPercentage){
                        nineteenIndex = (nineteenIndex - 1 + boardList.length) % boardList.length;
                        dart = boardList[nineteenIndex];
                        return dart;
                    }
                    else if(nineteenpercentageValue < leftPercentage + rightPercentage){
                        nineteenIndex = (nineteenIndex + 1) % boardList.length;
                        dart = boardList[nineteenIndex];
                        return dart;
                    }
                    else if(nineteenpercentageValue < leftPercentage + rightPercentage + NumberPercentage){
                        dart = 19;
                        return dart;
                    }else{
                        dart = 57;
                        return dart;
                    }
                
            }else{
                int twentyIndex = 0;
                for(int i = 0; i<boardList.length; i++){
                    if(boardList[i] == 20){
                        twentyIndex = i;
                        break;
                    }
                }
                    double twentiepercentageValue = Math.random() * 100;
                    if(twentiepercentageValue < leftPercentage){
                        twentyIndex = (twentyIndex - 1 + boardList.length) % boardList.length;
                        dart = boardList[twentyIndex];
                        return dart;
                    }
                    else if(twentiepercentageValue < leftPercentage + rightPercentage){
                        twentyIndex = (twentyIndex + 1) % boardList.length;
                        dart = boardList[twentyIndex];
                        return dart;
                    }
                    else if(twentiepercentageValue < leftPercentage + rightPercentage + NumberPercentage){
                        dart = 20;
                        return dart;
                    }else{
                        dart = 60;
                        return dart;
                    }
               
            }
        
    }
    
    final static Map<String, Integer> checkoutToInt = Map.ofEntries(
        Map.entry("S1", 1),
        Map.entry("S2", 2),
        Map.entry("S3", 3),
        Map.entry("S4", 4),
        Map.entry("S5", 5),
        Map.entry("S6", 6),
        Map.entry("S7", 7),
        Map.entry("S8", 8),
        Map.entry("S9", 9),
        Map.entry("S10", 10),
        Map.entry("S11", 11),     
        Map.entry("S12", 12),
        Map.entry("S13", 13),
        Map.entry("S14", 14),
        Map.entry("S15", 15),
        Map.entry("S16", 16),
        Map.entry("S17", 17),
        Map.entry("S18", 18),
        Map.entry("S19", 19),
        Map.entry("S20", 20),
        Map.entry("D1", 2),
        Map.entry("D2", 4),
        Map.entry("D3", 6),
        Map.entry("D4", 8),
        Map.entry("D5", 10),
        Map.entry("D6", 12),
        Map.entry("D7", 14),
        Map.entry("D8", 16),
        Map.entry("D9", 18),
        Map.entry("D10", 20),
        Map.entry("D11", 22),
        Map.entry("D12", 24),
        Map.entry("D13", 26),
        Map.entry("D14", 28),
        Map.entry("D15", 30),
        Map.entry("D16", 32),
        Map.entry("D17", 34),
        Map.entry("D18", 36),
        Map.entry("D19", 38),
        Map.entry("D20", 40),
        Map.entry("T1", 3),
        Map.entry("T2", 6),
        Map.entry("T3", 9),
        Map.entry("T4", 12),
        Map.entry("T5", 15),
        Map.entry("T6", 18),
        Map.entry("T7", 21),
        Map.entry("T8", 24),
        Map.entry("T9", 27),
        Map.entry("T10", 30),
        Map.entry("T11", 33),
        Map.entry("T12", 36),
        Map.entry("T13", 39),
        Map.entry("T14", 42),
        Map.entry("T15", 45),
        Map.entry("T16", 48),
        Map.entry("T17", 51),
        Map.entry("T18", 54),
        Map.entry("T19", 57),
        Map.entry("T20", 60),
        Map.entry("Bull", 50),
        Map.entry("Outer Bull", 25)
);
}
