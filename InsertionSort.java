public class InsertionSort {
    /**
    * General implementation of Insertion Sort algorithm using objects
    * @version 1.0
    * @author Nick Liccini
    */
    public class Sorting {
        /**
        * A general case Insertion Sort implementation for comparable objects
        * @param arr An array of comparable TenXers
        */
        public static void insertionSort(Comparable[] arr) {
            // Your Implementation of Insertion Sort
            for (int i = 2; i < arr.length; i++) {
                Comparable key = arr[i];
                int j = i - 1;
                while (j >= 0 && (arr[j].compareTo(key) > 0)) {
                    arr[j + 1] = arr[j];
                    j--;
                }
                arr[j + 1] = key;
            }
        }
    }
}
