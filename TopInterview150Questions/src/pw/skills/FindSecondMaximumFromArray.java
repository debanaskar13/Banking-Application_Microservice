package pw.skills;

import java.util.HashMap;
import java.util.Map;

public class FindSecondMaximumFromArray {

    public static void main(String[] args) {

        int[] arr = {74,5,8,94,62,147,75,42,158,685,24,15,356,95};

        int max = Integer.MIN_VALUE;
        int secondMax = Integer.MIN_VALUE;

        for(int e:arr){
            max = e > max ? e : max;
            secondMax = e > secondMax && e != max ? e : secondMax;
        }

        System.out.println("The maximum number in the array is : " + secondMax);

    }

}
