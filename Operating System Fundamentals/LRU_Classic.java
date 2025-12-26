import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LRU_Classic {
    private final int capacity;
    private final Map<Integer, Integer> cache;
    private final LinkedList<Integer> order;

    public LRU_Classic(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.order = new LinkedList<>();
    }

    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1; // Cache miss
        }
        // Move the accessed key to the front of the order list
        order.remove(key);
        order.addFirst(key);
        return cache.get(key);
    }

    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            // Update existing key
            cache.put(key, value);
            order.remove(key);
        } else {
            // Check capacity
            if (cache.size() >= capacity) {
                // Evict least recently used entry
                int lru = order.removeLast();
                cache.remove(lru);
            }
            cache.put(key, value);
        }
        order.addFirst(key);
    }
}
