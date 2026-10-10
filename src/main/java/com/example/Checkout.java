package com.example;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
/**
 * This class contains a final hash table of all possible darts checkout combinations
 * to allow the user to see if they can win on their current hand and what they need to 
 * throw in order to win
 * @author Josh Reynolds
 * @version 1.0
 * @since 25/02/2026
 */
public class Checkout{
    public static Map<Integer, String[]> findCheckout(){  
        final Map<Integer, String[]> checkoutMap = new HashMap<>();    
        checkoutMap.put(2, new String[]{"D1"});
        checkoutMap.put(3, new String[]{"S1", "D1"});
        checkoutMap.put(4, new String[]{"D2"});
        checkoutMap.put(5, new String[]{"S1", "D2"});
        checkoutMap.put(6, new String[]{"D3"});
        checkoutMap.put(7, new String[]{"S3", "D2"});
        checkoutMap.put(8, new String[]{"D4"});
        checkoutMap.put(9, new String[]{"S1", "D4"});
        checkoutMap.put(10, new String[]{"D5"});
        checkoutMap.put(11, new String[]{"S3", "D4"});
        checkoutMap.put(12, new String[]{"D6"});
        checkoutMap.put(13, new String[]{"S5", "D4"});
        checkoutMap.put(14, new String[]{"D7"});
        checkoutMap.put(15, new String[]{"S7", "D4"});
        checkoutMap.put(16, new String[]{"D8"});
        checkoutMap.put(17, new String[]{"S9", "D4"});
        checkoutMap.put(18, new String[]{"D9"});
        checkoutMap.put(19, new String[]{"S7", "D6"});
        checkoutMap.put(20, new String[]{"D10"});
        checkoutMap.put(21, new String[]{"S1", "D10"});
        checkoutMap.put(22, new String[]{"D11"});
        checkoutMap.put(23, new String[]{"S3", "D10"});
        checkoutMap.put(24, new String[]{"D12"});
        checkoutMap.put(25, new String[]{"S5", "D10"});
        checkoutMap.put(26, new String[]{"D13"});
        checkoutMap.put(27, new String[]{"S7", "D10"});
        checkoutMap.put(28, new String[]{"D14"});
        checkoutMap.put(29, new String[]{"S9", "D10"});
        checkoutMap.put(30, new String[]{"D15"});
        checkoutMap.put(31, new String[]{"S11", "D10"});
        checkoutMap.put(32, new String[]{"D16"});
        checkoutMap.put(33, new String[]{"S3", "D10"});
        checkoutMap.put(34, new String[]{"D17"});
        checkoutMap.put(35, new String[]{"S15", "D10"});
        checkoutMap.put(36, new String[]{"D18"});
        checkoutMap.put(37, new String[]{"S17", "D10"});
        checkoutMap.put(38, new String[]{"D19"});
        checkoutMap.put(39, new String[]{"S9", "D10"});
        checkoutMap.put(40, new String[]{"D20"});
        checkoutMap.put(41, new String[]{"S1", "D20"});
        checkoutMap.put(42, new String[]{"S2", "D20"});
        checkoutMap.put(43, new String[]{"S3", "D20"});
        checkoutMap.put(44, new String[]{"S4", "D20"});
        checkoutMap.put(45, new String[]{"S5", "D20"});
        checkoutMap.put(46, new String[]{"S6", "D20"});
        checkoutMap.put(47, new String[]{"S7", "D20"});
        checkoutMap.put(48, new String[]{"S8", "D20"});
        checkoutMap.put(49, new String[]{"S9", "D20"});
        checkoutMap.put(50, new String[]{"Bull"});
        checkoutMap.put(51, new String[]{"S11", "D20"});
        checkoutMap.put(52, new String[]{"S12", "D20"});
        checkoutMap.put(53, new String[]{"S13", "D20"});
        checkoutMap.put(54, new String[]{"S14", "D20"});
        checkoutMap.put(55, new String[]{"S15", "D20"});
        checkoutMap.put(56, new String[]{"S16", "D20"});
        checkoutMap.put(57, new String[]{"S17", "D20"});
        checkoutMap.put(58, new String[]{"S18", "D20"});
        checkoutMap.put(59, new String[]{"S19", "D20"});
        checkoutMap.put(60, new String[]{"S20", "D20"});
        checkoutMap.put(61, new String[]{"T15", "D8"});
        checkoutMap.put(62, new String[]{"T10", "D16"});
        checkoutMap.put(63, new String[]{"T13", "D12"});
        checkoutMap.put(64, new String[]{"T16", "D8"});
        checkoutMap.put(65, new String[]{"T19", "D4"});
        checkoutMap.put(66, new String[]{"T10", "D18"});
        checkoutMap.put(67, new String[]{"T17", "D8"});
        checkoutMap.put(68, new String[]{"T20", "D4"});
        checkoutMap.put(69, new String[]{"T19", "D6"});
        checkoutMap.put(70, new String[]{"T18", "D8"});
        checkoutMap.put(71, new String[]{"T13", "D16"});
        checkoutMap.put(72, new String[]{"T16", "D12"});
        checkoutMap.put(73, new String[]{"T19", "D8"});
        checkoutMap.put(74, new String[]{"T14", "D16"});
        checkoutMap.put(75, new String[]{"T17", "D12"});
        checkoutMap.put(76, new String[]{"T20", "D8"});
        checkoutMap.put(77, new String[]{"T19", "D10"});
        checkoutMap.put(78, new String[]{"T18", "D12"});
        checkoutMap.put(79, new String[]{"T13", "D20"});
        checkoutMap.put(80, new String[]{"T20", "D10"});
        checkoutMap.put(81, new String[]{"T19", "D12"});
        checkoutMap.put(82, new String[]{"T14", "D20"});
        checkoutMap.put(83, new String[]{"T17", "D16"});
        checkoutMap.put(84, new String[]{"T20", "D12"});
        checkoutMap.put(85, new String[]{"T15", "D20"});
        checkoutMap.put(86, new String[]{"T18", "D16"});
        checkoutMap.put(87, new String[]{"T17", "D18"});
        checkoutMap.put(88, new String[]{"T16", "D20"});
        checkoutMap.put(89, new String[]{"T19", "D16"});
        checkoutMap.put(90, new String[]{"T18", "D18"});
        checkoutMap.put(91, new String[]{"T17", "D20"});
        checkoutMap.put(92, new String[]{"T20", "D16"});
        checkoutMap.put(93, new String[]{"T19", "D18"});
        checkoutMap.put(94, new String[]{"T18", "D20"});
        checkoutMap.put(95, new String[]{"T19", "D19"});
        checkoutMap.put(96, new String[]{"T20", "D18"});
        checkoutMap.put(97, new String[]{"T19", "D20"});
        checkoutMap.put(98, new String[]{"T20", "D19"}); 
        checkoutMap.put(99,  new String[]{"T19", "S10", "D16"});  
        checkoutMap.put(100, new String[]{"T20", "D20"});
        checkoutMap.put(101, new String[]{"T20", "S9", "D16"});
        checkoutMap.put(102, new String[]{"T20", "S10", "D16"});
        checkoutMap.put(103, new String[]{"T20", "S11", "D16"});
        checkoutMap.put(104, new String[]{"T18", "S10", "D20"});
        checkoutMap.put(105, new String[]{"T20", "S5", "D20"});
        checkoutMap.put(106, new String[]{"T20", "S6", "D20"});
        checkoutMap.put(107, new String[]{"T20", "S15", "D16"});
        checkoutMap.put(108, new String[]{"T19", "S19", "D16"});
        checkoutMap.put(109, new String[]{"T19", "S12", "D20"});
        checkoutMap.put(110, new String[]{"T20", "S10", "D20"});
        checkoutMap.put(111, new String[]{"T20", "S11", "D20"});
        checkoutMap.put(112, new String[]{"T20", "S12", "D20"});
        checkoutMap.put(113, new String[]{"T20", "S13", "D20"});
        checkoutMap.put(114, new String[]{"T20", "S14", "D20"});
        checkoutMap.put(115, new String[]{"T20", "S15", "D20"});
        checkoutMap.put(116, new String[]{"T20", "S16", "D20"});
        checkoutMap.put(117, new String[]{"T20", "S17", "D20"});
        checkoutMap.put(118, new String[]{"T20", "S18", "D20"});
        checkoutMap.put(119, new String[]{"T19", "S12", "D16"});
        checkoutMap.put(120, new String[]{"T20", "S20", "D20"});
        checkoutMap.put(121, new String[]{"T20", "T11", "D4"});
        checkoutMap.put(122, new String[]{"T18", "T18", "D7"});
        checkoutMap.put(123, new String[]{"T19", "T16", "D9"});
        checkoutMap.put(124, new String[]{"T20", "T16", "D8"});
        checkoutMap.put(125, new String[]{"T20", "T15", "D10"});
        checkoutMap.put(126, new String[]{"T19", "T19", "D6"});
        checkoutMap.put(127, new String[]{"T20", "T17", "D8"});
        checkoutMap.put(128, new String[]{"T18", "T14", "D16"});
        checkoutMap.put(129, new String[]{"T19", "T16", "D12"});
        checkoutMap.put(130, new String[]{"T20", "T18", "D8"});
        checkoutMap.put(131, new String[]{"T20", "T13", "D16"});
        checkoutMap.put(132, new String[]{"T20", "T16", "D12"});
        checkoutMap.put(133, new String[]{"T20", "T19", "D8"});
        checkoutMap.put(134, new String[]{"T20", "T14", "D16"});
        checkoutMap.put(135, new String[]{"T20", "T17", "D12"});
        checkoutMap.put(136, new String[]{"T20", "T20", "D8"});
        checkoutMap.put(137, new String[]{"T20", "T19", "D10"});
        checkoutMap.put(138, new String[]{"T20", "T18", "D12"});
        checkoutMap.put(139, new String[]{"T20", "T13", "D20"});
        checkoutMap.put(140, new String[]{"T20", "T20", "D10"});
        checkoutMap.put(141, new String[]{"T20", "T19", "D12"});
        checkoutMap.put(142, new String[]{"T20", "T14", "D20"});
        checkoutMap.put(143, new String[]{"T20", "T17", "D16"});
        checkoutMap.put(144, new String[]{"T20", "T20", "D12"});
        checkoutMap.put(145, new String[]{"T20", "T19", "D14"});
        checkoutMap.put(146, new String[]{"T20", "T18", "D16"});
        checkoutMap.put(147, new String[]{"T20", "T17", "D18"});
        checkoutMap.put(148, new String[]{"T20", "T20", "D14"});
        checkoutMap.put(149, new String[]{"T20", "T19", "D16"});
        checkoutMap.put(150, new String[]{"T20", "T18", "D18"});
        checkoutMap.put(151, new String[]{"T20", "T17", "D20"});
        checkoutMap.put(152, new String[]{"T20", "T20", "D16"});
        checkoutMap.put(153, new String[]{"T20", "T19", "D18"});
        checkoutMap.put(154, new String[]{"T20", "T18", "D20"});
        checkoutMap.put(155, new String[]{"T20", "T19", "D19"});
        checkoutMap.put(156, new String[]{"T20", "T20", "D18"});
        checkoutMap.put(157, new String[]{"T20", "T19", "D20"});
        checkoutMap.put(158, new String[]{"T20", "T20", "D19"});
        checkoutMap.put(160, new String[]{"T20", "T20", "D20"});
        checkoutMap.put(161, new String[]{"T20", "T17", "Bull"});
        checkoutMap.put(164, new String[]{"T20", "T18", "Bull"});
        checkoutMap.put(167, new String[]{"T20", "T19", "Bull"});
        checkoutMap.put(170, new String[]{"T20", "T20", "Bull"});
        return checkoutMap;
    }
    /**
     * Gets the checkout options for a score of 170 or below.
     * @param score the player's current score
     * @return the checkout options, or {@code null} if the score has no checkout
     */
    public static String[] getCheckoutOptions(int score){
        if(score > 170){
            return null;
        }
        return Checkout.findCheckout().get(score);
    }
    /**
     * this method will find the checkout options for the player's current score if they are 170 or below,
     * @param score
     */
    public static void findCheckout(int score){
        String[] checkout = getCheckoutOptions(score);
        if(checkout != null){
            System.out.println("Checkout options for " + score + ": " + Arrays.toString(checkout));
        }
    }
}