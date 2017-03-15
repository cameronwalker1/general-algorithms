public class MergeSort {
    /**
    * General implementation of Merge Sort algorithm using objects
    * @version 1.0
    * @author Nick Liccini
    */
    public class Sorting {
        /**
        * A general case Merge Sort implementation for comparable objects
        * @param arr An array of comparable objects
        */
        public static void mergeSort(Comparable[] arr) {
            // Public Implementation of Merge Sort
            Comparable[] temp = new Comparable[arr.length];
            mergeSort(arr, temp, 0, arr.length - 1);
        }

        /**
        * A private recursive helper method for Merge Sort
        * @param arr An array of comparable objects
        * @param temp An array of comparable objects for storing temp values
        * @param low An index of the starting point
        * @param high An index of the ending point
        */
        private static void mergeSort(Comparable[] arr, Comparable[] temp,
                int low, int high) {
            // Recursive helper method for Merge Sort
            if (low < high) {
                int mid = low + (high - low) / 2;
                mergeSort(arr, temp, low, mid);
                mergeSort(arr, temp, mid + 1, high);
                merge(arr, temp, low, mid, high);
            }
        }

        /**
        * A private helper method to merge the sorted sub arrays
        * @param arr An array of comparable objects
        * @param temp An array of comparable objects for storing temp values
        * @param low An index of the starting point
        * @param mid An index of the middle point
        * @param high An index of the ending point
        */
        private static void merge(Comparable[] arr, Comparable[] temp,
                int low, int mid, int high) {
            // Helper method to merge sorted sub arrays
            for (int i = low; i <= high; i++) {
                temp[i] = arr[i];
            }
            int lo = low;
            int hi = mid + 1;
            for (int i = low; i <= high; i++) {
                if (lo > mid) {
                    arr[i] = temp[hi++];
                } else if (hi > high) {
                    arr[i] = temp[lo++];
                } else if (temp[lo].compareTo(temp[hi]) <= 0) {
                    arr[i] = temp[lo++];
                } else {
                    arr[i] = temp[hi++];
                }
            }
        }
    }
}
