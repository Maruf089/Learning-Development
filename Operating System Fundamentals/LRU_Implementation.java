import java.util.*;
// Least Recently Used (LRU) Cache Implementation
// This implementation uses LinkedHashMap to maintain the order of access
// and evict the least recently used entry when the capacity is exceeded.
class LRUCache extends LinkedHashMap<Integer, Integer> {
    private final int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true); // true = access order
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;
    }
}
