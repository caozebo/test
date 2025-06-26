package myHashMap;

public class MyHashMap {

    private final int capacity = 16;
    private final float loadFactor = 0.75f;
    private Node<String, String>[] table;

    private int size;
    private int threshold;

    public MyHashMap() {
        table = new Node[capacity];
        size = 0;
        threshold = (int) (capacity * loadFactor);
    }

    private int hash(String key) {
        return (key == null) ? 0 : (key.hashCode() & 0x7fffffff) % table.length;
    }

    public String get(String key) {
        int index = hash(key);
        Node<String, String> node = table[index];
        while (node != null) {
            if (node.key.equals(key)) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    public String put(String key, String value) {
        // 扩容
        if (size >= threshold) {
            resize();
        }
        int index = hash(key);
        Node<String, String> node = table[index];
        // 查找是否存在相同的key
        while (node != null) {
            if (node.key.equals(key)) {
                String oldValue = node.value;
                node.value = value;
                return oldValue;
            }
            node = node.next;
        }
        Node<String, String> newNode = new Node<>(key, value);
        newNode.next = table[index];
        table[index] = newNode;
        size++;
        return null;
    }

    private void resize() {
        int newCapacity = table.length * 2;
        Node<String, String>[] newTable = new Node[newCapacity];
        for (Node<String, String> node : table) {
            while (node != null) {
                int index = (node.key.hashCode() & 0x7fffffff) % newCapacity;
                Node<String, String> nextNode = node.next;
                node.next = newTable[index];
                newTable[index] = node;
                node = nextNode;
            }
        }
        table = newTable;
        threshold = (int) (newCapacity * loadFactor);
    }

    public String remove(String key) {
        int index = hash(key);
        Node<String, String> cur = table[index];
        Node<String, String> prev = null;
        while (cur != null) {
            if (cur.key.equals(key)) {
                if (prev == null) {
                    table[index] = cur.next;
                } else {
                    prev.next = cur.next;
                }
                size--;
                return cur.value;
            }
            prev = cur;
            cur = cur.next;
        }

        return null;
    }

    public static void main(String[] args) {
        MyHashMap myHashMap = new MyHashMap();
        myHashMap.put("1", "one");
        myHashMap.put("2", "two");
        myHashMap.put("3", "three");

        System.out.println(myHashMap.get("1"));
        myHashMap.put("4", "four");
        System.out.println(myHashMap.get("2"));
        System.out.println(myHashMap.remove("3"));
        System.out.println(myHashMap.get("3"));
    }

}

class Node<K, V> {
    K key;
    V value;
    Node<K, V> next;

    Node(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }
}
