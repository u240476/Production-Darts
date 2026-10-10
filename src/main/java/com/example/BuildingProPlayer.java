package com.example;
public class BuildingProPlayer{

      public static double[] ChoosingPlayer(String player){
          switch (player) {
              case "Beginner" ->
              {
                  double TriplePercentage = 3.0;
                  double NumberPercentage = 25.0;
                  double leftPercentage = 36.0;
                  double rightPercentage = 36.0;
                  double checkoutPercentage = 12.0;
                  double checkoutNumberPercentage = 37.0;
                  double outPercentage = 50.0;
                  double twentyChance = 50.0;
                  double nineteenChance = 25.0;
                  double eighteenChance = 25.0;
                  return new double[]{TriplePercentage, NumberPercentage, leftPercentage, rightPercentage, checkoutPercentage, checkoutNumberPercentage, outPercentage, twentyChance, nineteenChance, eighteenChance};
              }
              case "Casual" -> {
                  double TriplePercentage = 6.0; 
                  double NumberPercentage = 34.0; 
                  double leftPercentage = 30.0; 
                  double rightPercentage = 30.0; 
                  double checkoutPercentage = 18.0; 
                  double checkoutNumberPercentage = 40.0; 
                  double outPercentage = 42.0; 
                  double twentyChance = 60.0; 
                  double nineteenChance = 25.0; 
                  double eighteenChance = 15.0;
                  return new double[]{TriplePercentage, NumberPercentage, leftPercentage, rightPercentage, checkoutPercentage, checkoutNumberPercentage, outPercentage, twentyChance, nineteenChance, eighteenChance};
              }
              case "Amateur" -> {
                  double TriplePercentage = 14.0; 
                  double NumberPercentage = 42.0; 
                  double leftPercentage = 22.0; 
                  double rightPercentage = 22.0; 
                  double checkoutPercentage = 20.0; 
                  double checkoutNumberPercentage = 47.0; 
                  double outPercentage = 33.0; 
                  double twentyChance = 70.0; 
                  double nineteenChance = 20.0; 
                  double eighteenChance = 10.0;
                  return new double[]{TriplePercentage, NumberPercentage, leftPercentage, rightPercentage, checkoutPercentage, checkoutNumberPercentage, outPercentage, twentyChance, nineteenChance, eighteenChance};
              }
              case "Intermediate" -> {
                  double TriplePercentage = 24.0; 
                  double NumberPercentage = 44.0; 
                  double leftPercentage = 16.0; 
                  double rightPercentage = 16.0; 
                  double checkoutPercentage = 28.0; 
                  double checkoutNumberPercentage = 45.0; 
                  double outPercentage = 27.0; 
                  double twentyChance = 75.0; 
                  double nineteenChance = 17.0; 
                  double eighteenChance = 8.0;
                  return new double[]{TriplePercentage, NumberPercentage, leftPercentage, rightPercentage, checkoutPercentage, checkoutNumberPercentage, outPercentage, twentyChance, nineteenChance, eighteenChance};
              }
              case "Semi-Pro" -> {
                  double TriplePercentage = 33.0; 
                  double NumberPercentage = 49.0; 
                  double leftPercentage = 9.0; 
                  double rightPercentage = 9.0; 
                  double checkoutPercentage = 35.0; 
                  double checkoutNumberPercentage = 43.0; 
                  double outPercentage = 22.0; 
                  double twentyChance = 80.0; 
                  double nineteenChance = 15.0; 
                  double eighteenChance = 5.0;
                  return new double[]{TriplePercentage, NumberPercentage, leftPercentage, rightPercentage, checkoutPercentage, checkoutNumberPercentage, outPercentage, twentyChance, nineteenChance, eighteenChance};
              }
              case "Luke Littler" ->
              {
                  double TriplePercentage = 47.6;
                  double NumberPercentage = 45.4;
                  double leftPercentage = 3.5;
                  double rightPercentage = 3.5;
                  double checkoutPercentage = 64.2;
                  double checkoutNumberPercentage = 17.9;
                  double outPercentage = 17.9;
                  double twentyChance = 80.0;
                  double nineteenChance = 15.0;
                  double eighteenChance = 5.0;
                  return new double[]{TriplePercentage, NumberPercentage, leftPercentage, rightPercentage, checkoutPercentage, checkoutNumberPercentage, outPercentage, twentyChance, nineteenChance, eighteenChance};
              }
              case "Luke Humphries" ->
              {
                  double TriplePercentage = 44.0;
                  double NumberPercentage = 48.6;
                  double leftPercentage = 3.7;
                  double rightPercentage = 3.7;
                  double checkoutPercentage = 67.0;
                  double checkoutNumberPercentage = 16.5;
                  double outPercentage = 16.5;
                  double twentyChance = 80.0;
                  double nineteenChance = 15.0;
                  double eighteenChance = 5.0;
                  return new double[]{TriplePercentage, NumberPercentage, leftPercentage, rightPercentage, checkoutPercentage, checkoutNumberPercentage, outPercentage, twentyChance, nineteenChance, eighteenChance};
              }     case "Michael van Gerwen" ->
              {
                  double TriplePercentage = 48.0;
                  double NumberPercentage = 42.0;
                  double leftPercentage = 5.0;
                  double rightPercentage = 5.0;
                  double checkoutPercentage = 39.0;
                  double checkoutNumberPercentage = 30.5;
                  double outPercentage = 30.5;
                  double twentyChance = 80.0;
                  double nineteenChance = 15.0;
                  double eighteenChance = 5.0;
                 return new double[]{TriplePercentage, NumberPercentage, leftPercentage, rightPercentage, checkoutPercentage, checkoutNumberPercentage, outPercentage, twentyChance, nineteenChance, eighteenChance};
              }     default -> throw new IllegalArgumentException("Invalid player name");
          }
        }
}
