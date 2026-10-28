/**
 * Prime sums - AP CS A, October 27, 2026.
 * Complete the two methods below. 
 */
public class PrimeSum {
    /**
     * Returns the sum of all prime integers from 1 through n, inclusive.
     * Returns 0 when n < 2. Required inputs do not exceed 100000.
     */
    public static int sumPrimes(int n) {
        int sum = 0;

        for (int i = 2; i <= n; i++) {
            if (isPrime(i)) {
                sum += i;
            }
        }
        return sum;
    }

    /**
     * Returns true exactly when number is prime.
     * A prime integer is greater than 1 and has exactly two positive divisors.
     */
    public static boolean isPrime(int number) {
        if (number < 2) {
            return false;
        }
        for (int divisor = 2; divisor <= number / divisor; divisor++) {
            if (number % divisor == 0) {
                return false;
            }
        }
        return true;
    }
    
    
    // Call sumPrimes to test it out!
    public static void main(String[] args) {
        System.out.println(sumPrimes(1000)); // Expected output: 17 (2 + 3 + 5 + 7)
    }
}