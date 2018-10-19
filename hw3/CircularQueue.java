import java.util.*;

public class CircularQueue<T> implements Iterable<T> {
    private LinkedList<T> queue;
    private int peopleNum;
    private int intervalNum;

    public CircularQueue() { 
       //construction
       queue = new LinkedList<T>();
    }

    public boolean isEmpty() { 
        if (queue.size() == 0)
            return true;
        else
            return false;
    }

    public int size() { 
        return queue.size();
    }

    public void enqueue(T item) { 
        // Add item to the end of the list 
        queue.add(item);
    }

    public T removeFirst() { 
        // Remove item from the beginning of the list
        return queue.removeFirst();
    }
    
    public Iterator iterator() {
        return new circularIterator();
    }
    private class circularIterator<T> implements Iterator<T> {
        private int index = 0;

        public boolean hasNext() {
            if (queue.size() <= 0) {
                return false;
            } else {
                return true;
            }
        }
        public T next() {
            T result = (T) queue.get(index);
	    index = (index + 1) % queue.size();
            return result;
        }
    }

    public void setNum(int people, int interval) {
        peopleNum = people;
        intervalNum = interval;
    }

    public void printJosephus() {
        int[] line = new int[peopleNum];
        boolean[] survive = new boolean[peopleNum];
        int round = 1;
        int iter = 1;
        int lookingIndex = 0;

        for (int i = 0; i < peopleNum; i++) {
            line[i] = i;
            survive[i] = true;
        }

        while (round <= peopleNum) {
            while (iter <= intervalNum) {
                if (lookingIndex >= peopleNum) {
                    lookingIndex = lookingIndex - peopleNum;
                }

                if (survive[lookingIndex]) {
                    if (iter == intervalNum) {
                        System.out.print(lookingIndex);
                        survive[lookingIndex] = false;
                        lookingIndex++;
                        iter++;
                    } else {
                        lookingIndex++;
                        iter++;
                    }
                } else {
                    lookingIndex++;
                }
            }
            iter = 1;

            if (round == peopleNum) {
                System.out.println("");
            } else {
                System.out.print(" ");
            }

        round++;
        }
    }

    public static void main(String[] args) { 
        CircularQueue<Integer> circularQueue = new CircularQueue();
        circularQueue.setNum(Integer.valueOf(args[0]), Integer.valueOf(args[1]));
        circularQueue.printJosephus();
    }
}
