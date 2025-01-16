package pw.skills;

public class FindMaximumFromArray {

    public static void main(String[] args) {

        int[] arr = {74,5,8,94,62,147,75,42,158,685,24,15,356,95};

        int max = Integer.MIN_VALUE;

        for(int e:arr){
            max = e > max ? e : max;
        }

        System.out.println("The maximum number in the array is : " + max);

    }

}
