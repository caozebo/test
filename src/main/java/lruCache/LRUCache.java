package lruCache;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LRUCache {

    private final int capacity;
    private Map<String, String> cache = new ConcurrentHashMap<>();
    private Queue<String> keysQueue = new ConcurrentLinkedQueue<>();

    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock readLock = lock.readLock();
    private Lock writeLock = lock.writeLock();

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public String get(String key) {
        readLock.lock();
        try {
            if (cache.containsKey(key)) {
                keysQueue.remove(key);
                keysQueue.offer(key);
                return cache.get(key);
            }
            return null;
        } finally {
            readLock.unlock();
        }
    }

    public String put(String key, String value) {
        writeLock.lock();
        try {
            if (cache.containsKey(key)) {
                cache.put(key, value);
                keysQueue.remove(key);
                keysQueue.add(key);
                return value;
            } else {
                // 容量已满
                if (cache.size() >= capacity) {
                    String oldestKey = keysQueue.poll();
                    if (oldestKey != null) {
                        cache.remove(oldestKey);
                    }
                }
                cache.put(key, value);
                keysQueue.offer(key);
                return value;
            }
        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(3);
        lruCache.put("1", "one");
        lruCache.put("2", "two");
        lruCache.put("3", "three");

        System.out.println(lruCache.get("1")); // 输出: one
        lruCache.put("4", "four"); // 移除键 "2"
        System.out.println(lruCache.get("2")); // 输出: null
        System.out.println(lruCache.get("3")); // 输出: three
    }
}
