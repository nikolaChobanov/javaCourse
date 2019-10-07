package fmi.uni.cache;

import java.time.LocalDateTime;

public interface Cache<K, V> {


    V get(K key);

    void set(K key, V value, LocalDateTime expiresAt) throws CapacityExceededException;

    LocalDateTime getExpiration(K key);

    boolean remove(K key);

    long size();


    void clear();

    double getHitRate();

}