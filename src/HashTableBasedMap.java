import java.util.*;

public class HashTableBasedMap<K, V> implements Map<K, V> {

    private static final int NUMBER_OF_BUCKETS = 2;
    private final ArrayList<ArrayList<Pair>> buckets = new ArrayList<ArrayList<Pair>>(NUMBER_OF_BUCKETS);

    HashTableBasedMap() {
        for (int i = 0; i < NUMBER_OF_BUCKETS; i++) {
            buckets.add(new ArrayList<Pair>());
        }
    }

    public ArrayList<ArrayList<Pair>> getBuckets() {
        return buckets;
    }

    @Override
    public int size() {
        // BEGIN (write your solution here)
        int size = 0;
        for (int i = 0; i < NUMBER_OF_BUCKETS; i++) {
            size += buckets.get(i).size();
        }
        return size;
        // END
    }

    @Override
    public boolean isEmpty() {
        // BEGIN (write your solution here)
        return size() == 0;
        // END
    }

    @Override
    public boolean containsKey(Object key) {
        // BEGIN (write your solution here)
        for (final Pair pair : buckets.get(getCellNumber(key))) {
            if (key == null ? pair.getKey() == null : key.equals(pair.getKey()))
                return true;
        }
        return false;
        // END
    }

    @Override
    public boolean containsValue(Object value) {
        // BEGIN (write your solution here)
        for (int i = 0; i < buckets.size(); i++) {
            for (final Pair pair : buckets.get(i)) {
                if (value == null ? pair.getValue() == null : value.equals(pair.getValue()))
                    return true;
            }
        }
        return false;
        // END
    }

    @Override
    public V get(Object key) {
        // BEGIN (write your solution here)
        for (final Pair pair : buckets.get(getCellNumber(key))) {
            if (key == null ? pair.getKey() == null : key.equals(pair.getKey()))
                return pair.getValue();
        }
        return null;
        // END
    }

    @Override
    public V put(K key, V value) {
        // BEGIN (write your solution here)
        if (!this.containsKey(key)) {
            buckets.get(getCellNumber(key)).add(new Pair(key, value));
            return null;
        }
        for (final Pair pair : buckets.get(getCellNumber(key))) {
            if (key == null ? pair.getKey() == null : key.equals(pair.getKey()))
                return pair.setValue(value);
        }
        return null;
        // END
    }

    @Override
    public V remove(Object key) {
        // BEGIN (write your solution here)
        if (!this.containsKey(key))
            return null;

        for (final Pair pair : buckets.get(getCellNumber(key))) {
            if (key == null ? pair.getKey() == null : key.equals(pair.getKey())) {
                final V oldValue = this.get(key);
                buckets.get(getCellNumber(key)).remove(pair);
                return oldValue;
            }
        }
        return null;
        // END
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<K, V> e : (Set<Map.Entry<K, V>>)(Set)m.entrySet())
            put(e.getKey(), e.getValue());
    }

    @Override
    public void clear() {
        // BEGIN (write your solution here)
        for (int i = 0; i < NUMBER_OF_BUCKETS; i++) {
            buckets.get(i).clear();
        }
        // END
    }

    @Override
    public Set<K> keySet() {
        final Set<K> keys = new HashSet<K>();
        for (int i = 0; i < NUMBER_OF_BUCKETS; i++) {
            for (Pair p : buckets.get(i)) keys.add(p.getKey());
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        // BEGIN (write your solution here)
        final Collection<V> colValues = new ArrayList<V>();
        for (int i = 0; i < NUMBER_OF_BUCKETS; i++) {
            for (Pair p : buckets.get(i)) colValues.add(p.getValue());
        }
        return colValues;
        // END
    }

    @Override
    public Set<Entry<K, V>> entrySet() {

        Set<Entry<K, V>> set = new HashSet<>(buckets.get(0));

        for (int i = 1; i < NUMBER_OF_BUCKETS; i++) {
            set.addAll(new HashSet<Entry<K, V>>(buckets.get(i)));
        }
        return set;
    }

    private int getCellNumber(Object key) {
        return key.hashCode() % NUMBER_OF_BUCKETS;
    }

    private class Pair implements Map.Entry<K, V> {

        private final K key;

        private V value;

        private Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            final V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            Map.Entry<K, V> pair = (Map.Entry<K, V>) o;


            if (key != null ? !key.equals(pair.getKey()) : pair.getKey() != null) return false;
            return !(value != null ? !value.equals(pair.getValue()) : pair.getValue() != null);

        }

        @Override
        public int hashCode() {
            return (key   == null ? 0 :   key.hashCode()) ^
                    (value == null ? 0 : value.hashCode());
        }

        @Override
        public String toString() {
            return key + " : " + value;
        }
    }
}
