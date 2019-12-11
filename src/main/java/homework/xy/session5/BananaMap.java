package homework.xy.session5;


import javax.xml.soap.Node;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * @Author xy
 * Map
 */
public class BananaMap<K, V> {

    /**
     * 链节点结构
     *
     * @param <K>
     * @param <V>
     */
    class Node<K, V> implements Map.Entry<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final K getKey() {
            return key;
        }

        public final V getValue() {
            return value;
        }

        public String toString() {
            return this.key + "=" + this.value;
        }

        public V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof Map.Entry) {
                Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
                if (Objects.equals(key, e.getKey()) &&
                        Objects.equals(value, e.getValue())) {
                    return true;
                }
            }
            return false;
        }
    }
    // Create a regular (non-tree) node
    Node<K,V> newNode(int hash, K key, V value, Node<K,V> next) {
        return new Node<>(hash, key, value, next);
    }
    /**
     * key mapping index
     *
     * @param key
     * @return
     */
    private int hash(K key) {
        int hash;
        return (null == key) ? 0 : (hash = key.hashCode()) ^ (hash >>> 16);
    }

    /**
     * 容器存储结构
     */
    Node<K, V>[] table;
    /**
     * 容器当前存储元素个数
     */
    int size;
    /**
     * 当数组大小超过75%时， 扩容
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    /**
     * 数组初始化大小 16
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    /**
     * 数组的最大大小
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    final float loadFactor;
    int capacity;

    public BananaMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
        this.capacity = DEFAULT_INITIAL_CAPACITY;
        table = new Node[DEFAULT_INITIAL_CAPACITY];
    }

    public BananaMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public BananaMap(int initialCapacity, float loadFactor) {
        if (initialCapacity <= 0)
            throw new IllegalArgumentException("size is to small:" + loadFactor);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor:" + loadFactor);
        this.loadFactor = loadFactor;
        this.capacity = tableSizeFor(initialCapacity);
    }

    /**
     * 找到大于等于整数的最小2的幂次整数
     *
     * @param initialCapacity
     * @return
     */
    private int tableSizeFor(int initialCapacity) {
        int n = initialCapacity - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n > MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : (n + 1);
    }

    /**
     * Map 元素个数
     *
     * @return
     */
    int size() {
        return this.size;
    }

    /**
     * 是否为空
     *
     * @return
     */
    boolean isEmpty() {
        return size == 0;
    }

    /**
     * 是否存在某个key
     *
     * @param key
     * @return
     */
    boolean containsKey(K key) {
        return getNode(key) == null ? false : true;
    }

    /**
     * 获取Node
     *
     * @param key
     * @return
     */
    private Node<K, V> getNode(K key) {
        if (null == key) {
            return table[0];
        }
        Node<K, V> e;
        return (e = getNode(hash(key), key)) == null ? null : e;
    }

    /**
     * 获取Node
     *
     * @param key
     * @return
     */
    private Node<K, V> getNode(int hash, K key) {
        Node<K, V>[] tab;
        Node<K, V> first, e;
        int n;
        K k;
        if ((tab = table) != null &&
                (n = table.length) > 0 &&
                (first = tab[(n - 1) & hash]) != null) {
            if (first.hash == hash &&
                    ((k = first.key) == key || (key != null && key.equals(k)))) {
                return first;
            }
            if ((e = first.next) != null)
                do
                {
                    if (first.hash == hash &&
                            ((k = first.key) == key || (key != null && key.equals(k)))) {
                        return e;
                    }
                } while ((e = first.next) != null);
        }
        return null;
    }

    /**
     * 根据 key 取得值
     *
     * @param key
     * @return
     */
    V get(K key) {
        if (null == key) {
            return table[0].value;
        }
        Node<K, V> e;
        return (e = getNode(key)) == null ? null : e.getValue();
    }

    /**
     * 存储 key-value 对
     *
     * @param key
     * @return
     */
    V put(K key, V value) {
        return putValue(hash(key), key, value);
    }

    /**
     * put value
     *
     * @param hash
     * @param key
     * @param value
     */
    private V putValue(int hash, K key, V value) {
        if (null == key) {
            table[0] = new Node<>(hash(key), key, value, null);
            return table[0].value;
        }
        // if size is too long, resize size
        if ((table.length - size) <= (table.length >> 2)) {
            resize();
        }
        Node<K, V>[] tab;
        Node<K, V> p;
        int n, i;
        // 当前节点值为空
        if ((tab = table) == null || (n = tab.length) == 0) {
            n = (tab = resize()).length;
        }
        if ((p = tab[i = (n - 1) & hash]) == null) {
            tab[i] = newNode(hash, key, value, null);
        } else {
            Node<K, V> e;
            K k;
            if (p.hash == hash &&
                    ((k = p.key) == key || (key != null && key.equals(k)))) {
                e = p;
            } else {
                for (; ; ) {
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        break;
                    }
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (oldValue == null)
                    e.value = value;
                return oldValue;
            }
        }

        if ((table.length - ++size) <= (table.length >> 2)) {
            resize();
        }
        return null;
    }

    /**
     * 调整Map size
     */
    private Node<K, V>[] resize() {
        if (size <= 0) {
            new BananaMap();
        }
        Node<K, V>[] oldTab = table;
        int oldCap = capacity;
        // 如果已经最大了，直接返回原数组
        if (oldCap >= MAXIMUM_CAPACITY) {
            capacity = MAXIMUM_CAPACITY;
            return oldTab;
        }
        // 生成新数组，迁移数据
        int newCap = oldCap << 1;
        capacity = newCap;
        Node<K, V>[] newTab = (Node<K, V>[]) new Node[capacity];
        table = newTab;
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                Node<K, V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;
                    if (e.next == null)
                        newTab[e.hash & (newCap - 1)] = e;
                    else { // preserve order
                        Node<K, V> loHead = null, loTail = null;
                        Node<K, V> hiHead = null, hiTail = null;
                        Node<K, V> next;
                        do
                        {
                            next = e.next;
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            } else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }

    /**
     * 移除某个值
     *
     * @param key
     * @return
     */
    V remove(K key) {
        Node<K, V> e;
        return (e = removeNode(hash(key), key)) == null ? null : e.value;
    }

    /**
     * 移除某个值
     *
     * @param hash
     * @param key
     * @return
     */
    private Node<K, V> removeNode(int hash, K key) {
        Node<K, V>[] tab;
        Node<K, V> p;
        int n, index;
        if ((tab = table) != null &&
                (n = table.length) > 0 &&
                (p = tab[index = (n - 1) & hash]) != null) {
            Node<K, V> node = null, e;
            K k;
            V v;
            if (p.hash == hash &&
                    ((k = p.key) == key || (key != null && key.equals(k))))
                node = p;
            else if ((e = p.next) != null) {
                do
                {
                    if (e.hash == hash &&
                            ((k = e.key) == key ||
                                    (key != null && key.equals(k)))) {
                        node = e;
                        break;
                    }
                    p = e;
                } while ((e = e.next) != null);
            }
            if (node != null) {
                if (node == p)
                    tab[index] = node.next;
                else
                    p.next = node.next;
                --size;
                return node;
            }
        }
        return null;
    }
}
