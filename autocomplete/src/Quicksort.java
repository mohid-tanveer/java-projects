import java.util.ArrayList;
import java.util.Comparator;

public class Quicksort {

    public static <E> void quicksort(ArrayList<E> arrayList, Comparator<E> comparator) {
        quicksort(arrayList, comparator, 0, arrayList.size() - 1);
    }

    private static <E> void quicksort(ArrayList<E> arrayList, Comparator<E> comparator, int first, int last) {
        if (first < last) {
            int pivIndex = partition(arrayList, comparator, first, last);
            quicksort(arrayList, comparator, first, pivIndex - 1);
            quicksort(arrayList, comparator, pivIndex + 1, last);
        }
    }

    private static <E> int partition(ArrayList<E> arrayList, Comparator<E> comparator, int first, int last) {
        // Select the first item as the pivot value.
        E pivot = arrayList.get(first);

        int up = first;
        int down = last;

        do {
            while ((up < last) && ((comparator.compare(pivot, arrayList.get(up)) >= 0))) {
                up++;
            }
            // After loop ends: up equals last or arrayList.get(up) > pivot.

            while (comparator.compare(pivot, arrayList.get(down)) < 0) {
                down--;
            }
            // After loop ends: down equals first or arrayList.get(down) <= pivot.

            if (up < down) { // If up is to the left of down.
                // Exchange arrayList.get(up) and arrayList.get(down).
                E temp = arrayList.get(up);
                arrayList.set(up, arrayList.get(down));
                arrayList.set(down, temp);
            }
        } while (up < down); // Repeat while up is left of down.

        // Exchange arrayList.get(first) and arrayList.get(down) thus
        // putting the pivot value where it belongs.
        E temp = arrayList.get(first);
        arrayList.set(first, arrayList.get(down));
        arrayList.set(down, temp);

        // Return the index of the pivot value.
        return down;
    }
}
