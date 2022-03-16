/*
 2/9/22 , This program takes an array and finds a subarray that produces the largest sum using a divide and conquer approach.
Along with the test and base case a randomly generated price array is printed with their low/high indexes and the max profit.
 */

import java.util.Arrays;
import java.util.Random;

public class Test {

    public static void main(String[] args) {

//Test a base case
        int dayBaseCase[] = new int [] {0, 1};
        int priceBaseCase[] = new int[] {60, 61};
        int changeBaseCase[] = new int[] {1};

        System.out.println("");
        System.out.println("Base case array values (only 1 element): ");
        System.out.println("");
        System.out.println( "| Days: \t" + Arrays.toString(dayBaseCase) );
        System.out.println(" ");
        System.out.println( "| Price:\t" + Arrays.toString(priceBaseCase) );
        System.out.println(" ");
        System.out.println( "| Change:\t" + Arrays.toString(changeBaseCase) );
        System.out.println(" ");

        int b = changeBaseCase.length;
        int baseMaxSum = maxSubarray(changeBaseCase, 0, b-1 );
        System.out.println("Sum of largest subarray is " + baseMaxSum + " (max profit)");
        indexValues(changeBaseCase);
        System.out.println("");

//Test case from lecture

        int days[] = new int [] {0, 1, 2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
        int price[] = new int[] {100,113,110,85,105,102,86,63,81,101,94,106,101,79,94,90,97};
        int change[] = new int[] {0, 13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7};

        System.out.println("");
        System.out.println("Test case array values: ");
        System.out.println("");
        System.out.println( "| Days: \t" + Arrays.toString(days) );
        System.out.println(" ");
        System.out.println( "| Price:\t" + Arrays.toString(price) );
        System.out.println(" ");
        System.out.println( "| Change:\t" + Arrays.toString(change) );
        System.out.println(" ");

        int t = change.length;
        int testMaxSum = maxSubarray(change, 0, t-1 );
        System.out.println("Sum of largest subarray is: " + testMaxSum + " (max profit)");
        indexValues(change);
        System.out.println("");

//Random values case
    int randomDays[] = new int[101];
    int randomPrices[] = new int[101];
    int randomChange[] = new int[100];

    System.out.println("");
    System.out.println("Randomly generated values case: ");
    System.out.println("");

    Random rand = new Random();
    // print days 1-100
    for (int i = 0; i<randomDays.length; i++){
        randomDays[i] = i;
    }
        System.out.println("| Days: \t" + Arrays.toString(randomDays));

        System.out.println(" ");

    //get random values for prices and print them (0-100 prices from 50 to 120)
    for (int i = 0; i<randomPrices.length; i++){
        randomPrices[i] = 50 + rand.nextInt(71);

    }
        System.out.println("| Price:\t" + Arrays.toString(randomPrices));

        System.out.println(" ");

    //get change in price and print
     for (int i = 0; i <randomChange.length; i++) {
     randomChange[i] = randomPrices[i + 1] - randomPrices[i];

    }
        System.out.println("| Change:\t" + Arrays.toString(randomChange));

        System.out.println(" ");

        int r = randomChange.length;
        int randomMaxSum = maxSubarray(randomChange, 0, r-1 );
        System.out.println("Sum of largest subarray is: " + randomMaxSum + " (max profit)");
        indexValues(randomChange);

    }

     // get index values of max subarray
    public static void indexValues(int arr[]){
        int maxLowIndex=0;
        int maxHighIndex=0;
        //int maxSum = Integer.MIN_VALUE;
        int maxSum = 0;

        int cumulativeSum= 0;
        int maxStartIndexUntilNow=0;

        //iterate through array to find start and end index of max sub array
        for (int currentIndex = 0; currentIndex < arr.length; currentIndex++) {

            int eachArrayItem = arr[currentIndex];

            cumulativeSum+=eachArrayItem;

            if(cumulativeSum>maxSum){
                maxSum = cumulativeSum;
                maxLowIndex=maxStartIndexUntilNow;
                maxHighIndex = currentIndex;
            }
            //base case
            if (cumulativeSum<0){
                maxStartIndexUntilNow=currentIndex+1;
                cumulativeSum=0;
            }
        }
        System.out.println("Max subarray low index is: " + maxLowIndex);
        System.out.println("Max subarray high index is: " + maxHighIndex);
    }

   // divide and conquer
   public static int maxSubarray(int arr[], int low, int high) {
       //base case: only 1 element in array
       if (high == low)
           return Math.max(low, Math.max(high,arr[0]));
       else {
           int mid = (low + high) / 2; // find the mid point
           int leftLow = maxSubarray(arr, low, mid), leftHigh = maxSubarray(arr, low, mid), leftSum = maxSubarray(arr, low, mid); //left array
           int rightLow = maxSubarray(arr, mid + 1, high), rightHigh = maxSubarray(arr, mid + 1, high), rightSum = maxSubarray(arr, mid + 1, high); // right array
           int crossLow = maxCrossSubarray(arr, low, mid, high), crossHigh = maxCrossSubarray(arr, low, mid, high), crossSum = maxCrossSubarray(arr, low, mid, high); //covers the mid of array

           //return values of left array if its the max sub array
           if (leftSum >= rightSum && leftSum >= crossSum)
               return Math.max(leftLow, Math.max(leftHigh, leftSum));

           //return values of right array if its the max sub array
           else if (rightSum >= leftSum && rightSum >= crossSum)
               return Math.max(rightLow, Math.max(rightHigh, rightSum));

               //else return mid array
           else

               return Math.max(crossLow, Math.max(crossHigh, crossSum));

       }
   }
      //covers mid array
   public static int maxCrossSubarray(int arr[], int low, int mid, int high) {

       // int leftSum = Integer.MIN_VALUE;
       int leftSum = 0;
        int sum = 0;
        int maxLeft =0;
         //scan left array towards the left <-
        for(int i = mid; i > low; i--) {
            sum += arr[i];
            if (sum > leftSum) {
                leftSum = sum;
                 maxLeft = i;
            }
        }

        //int rightSum = Integer.MIN_VALUE;
        int rightSum = 0;
         sum= 0;
         int maxRight=0;
        // scan right array towards the right ->
        for(int j = mid + 1; j < high; j++) {
            sum += arr[j];
            if (sum> rightSum) {
                rightSum = sum;
                maxRight = j;
            }
        }
        return Math.max(leftSum + rightSum, Math.max(maxLeft, maxRight)); //return sum of elements on left and right of the mid
    }

}
