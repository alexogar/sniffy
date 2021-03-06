package io.sniffy;

public final class SpyWithValue<V> extends Spy<SpyWithValue<V>> {

    private final V value;

    SpyWithValue(V value) {
        this.value = value;
    }

    public V getValue() {
        return value;
    }

}
