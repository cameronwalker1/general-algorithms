import java.util.Comparator;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Nick Liccini
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable).
     *
     * See the PDF for more info on this sort.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The input array or comparator"
                    + " is null, please use a valid input next time.");
        }
        for (int i = 1; i < arr.length; i++) {
            int j = i;
            while (j > 0 && comparator.compare(arr[j - 1], arr[j]) > 0) {
                swap(arr, j - 1, j);
                j--;
            }
        }
    }

    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     *
     * int pivotIndex = r.nextInt(b - a) + a;
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Note that there may be duplicates in the array.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not use the one we have taught you!
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null or k is not in the range of 1 to arr.length
     * @param <T> data type to sort
     * @param k the index + 1 (due to 0-indexing) to retrieve the data 
     * from as if the array were sorted; the 'k' in "kth select"
     * @param arr the array that should be modified after the method
     * is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @return the kth smallest element
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("The input array or comparator"
                    + " is null, please use a valid input next time.");
        } else if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("Index " + k + " is not within"
                    + " the range of the input array (" + 1 + "-" + arr.length
                    + ").");
        }
        return kthSelectHelper(k - 1, arr, 0,
                arr.length, comparator, rand);
    }

    /**
     * Private recursive helper method to perform kth select. Using the method
     * of quick select following the quick sort procedure
     *
     * @param k the index of the value being searched for
     * @param arr the array that should be modified after the method
     * is finished executing as needed
     * @param left the lower boundary of the portion of concern
     * @param right the upper boundary of the portion of concern
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @param <T> data type to sort
     * @return the kth smallest element
     */
    private static <T> T kthSelectHelper(int k, T[] arr, int left, int right,
                                    Comparator<T> comparator, Random rand) {
        int pivInd = rand.nextInt(right - left) + left;
        T pivot = arr[pivInd];
        swap(arr, pivInd, left);
        int i = left + 1;
        int j = right - 1;
        // Swap pivot with j only after j has crossed past i, not when they
        // are equal
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivot) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivot) >= 0) {
                j--;
            }
            if (i < j) {
                swap(arr, i, j);
                j--;
                i++;
            }
        }
        swap(arr, j, left);
        if (k > j) {
            return kthSelectHelper(k, arr, j + 1,
                    right, comparator, rand);
        } else if (k < j) {
            return kthSelectHelper(k, arr, left, j, comparator, rand);
        } else {
            return arr[j];
        }
    }

    /**
     * Private helper method to swap two items in an array
     *
     * @param arr the array containing the items
     * @param ind1 the index of the first item
     * @param ind2 the index of the second item
     * @param <T> data type to swap
     */
    private static <T> void swap(T[] arr, int ind1, int ind2) {
        T swap = arr[ind1];
        arr[ind1] = arr[ind2];
        arr[ind2] = swap;
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     * 
     * When necessary due to an odd number of elements, the 
     * excess element MUST go on the right side!
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The input array or comparator"
                    + " is null, please use a valid input next time.");
        }
        mergeSortHelper(arr, comparator);
    }

    /**
     * Private recursive helper method to sort the given array.
     *
     * If an array of length 1 is input, it is inherently sorted.
     * If an array of length 2 is input, it need only be swapped once max.
     *
     * Splits up array into two smaller arrays and merges them together sorted.
     *
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @param <T> data type to sort
     */
    private static <T> void mergeSortHelper(T[] arr,
                                            Comparator<T> comparator) {
        if (arr.length > 1) {
            int mid = arr.length / 2;
            T[] left = (T[]) new Object[mid];
            T[] right = (T[]) new Object[arr.length - mid];
            for (int i = 0; i < mid; i++) {
                left[i] = arr[i];
            }
            int j = 0;
            for (int i = mid; i < arr.length; i++) {
                right[j] = arr[i];
                j++;
            }
            mergeSortHelper(left, comparator);
            mergeSortHelper(right, comparator);
            merge(arr, left, right, comparator);
        }
    }

    /**
     * Helper method to merge the two smaller arrays back together in sorted
     * order
     *
     * @param arr the original array to be sorted
     * @param left the left half of the array
     * @param right the right half of the array
     * @param comparator the Comparator used to compare the data in arr
     * @param <T> data type to sort
     */
    private static <T> void merge(T[] arr, T[] left, T[] right,
                                 Comparator<T> comparator) {
        int leftPointer = 0;
        int rightPointer = 0;
        int arrPointer = 0;
        while (leftPointer < left.length && rightPointer < right.length) {
            if (comparator.compare(left[leftPointer],
                    right[rightPointer]) <= 0) {
                arr[arrPointer] = left[leftPointer];
                arrPointer++;
                leftPointer++;
            } else {
                arr[arrPointer] = right[rightPointer];
                arrPointer++;
                rightPointer++;
            }
        }
        while (leftPointer < left.length) {
            arr[arrPointer] = left[leftPointer];
            arrPointer++;
            leftPointer++;
        }
        while (rightPointer < right.length) {
            arr[arrPointer] = right[rightPointer];
            arrPointer++;
            rightPointer++;
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code!
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable)
     *
     * Do NOT use {@code Math.pow()} in your sort. Instead, if you need to, use
     * the provided {@code pow()} method below.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     * @return the sorted array
     */
    public static int[] lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Input array is null, please"
                    + " use a valid input next time.");
        }
        if (arr.length == 0) {
            return arr;
        }
        java.util.LinkedList<Integer>[] buckets =
                (java.util.LinkedList<Integer>[]) new java.util.LinkedList[19];
        // Determine the longest number in the array and find its number of
        // digits, k
        int longest = Math.abs(arr[0]);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > Integer.MIN_VALUE + 1 && Math.abs(arr[i]) > longest) {
                longest = Math.abs(arr[i]);
            }
        }
        int k = 1;
        while ((longest / 10) > 0) {
            longest /= 10;
            k++;
        }
        // Begin Radix Sort
        int bucket;
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < arr.length; j++) {
                bucket = arr[j] / pow(10, i) % 10 + 9;
                if (buckets[bucket] == null) {
                    buckets[bucket] = new java.util.LinkedList<>();
                }
                buckets[bucket].addLast(arr[j]);
            }
            int ind = 0;
            for (bucket = 0; bucket < 19; bucket++) {
                while (buckets[bucket] != null && !buckets[bucket].isEmpty()) {
                    arr[ind] = buckets[bucket].removeFirst();
                    ind++;
                }
            }
        }
        return arr;
    }

    /**
     * Calculate the result of a number raised to a power. Use this method in
     * your radix sorts instead of {@code Math.pow()}.
     *
     * DO NOT MODIFY THIS METHOD.
     *
     * @throws IllegalArgumentException if both {@code base} and {@code exp} are
     * 0
     * @throws IllegalArgumentException if {@code exp} is negative
     * @param base base of the number
     * @param exp power to raise the base to. Must be 0 or greater.
     * @return result of the base raised to that power
     */
    private static int pow(int base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Exponent cannot be negative.");
        } else if (base == 0 && exp == 0) {
            throw new IllegalArgumentException(
                    "Both base and exponent cannot be 0.");
        } else if (exp == 0) {
            return 1;
        } else if (exp == 1) {
            return base;
        }
        int halfPow = pow(base, exp / 2);
        if (exp % 2 == 0) {
            return halfPow * halfPow;
        } else {
            return halfPow * halfPow * base;
        }
    }
}