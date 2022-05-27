import java.util.LinkedList;

public class QueueWithWaitAndNotify {
    // A linked list is used to hold the queue.
    LinkedList q = new LinkedList();

    // Add an element to the end of the line
    public synchronized void addAtEnd(Object o) {
        q.addLast(o);
        this.notify();
    }

    // Remove the first in line
    public synchronized Object removeFromFront() {
        while(q.size() == 0) {
            try { this.wait(); }
            catch (InterruptedException e) { /* ignore this exception */ }
        }
        return q.removeFirst();
    }
}
