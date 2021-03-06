package fmi.uni.cache;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

public class MemCacheTest {


    private final static double PRECISION = 0.1;
    private final static long CACHE_TEST_SIZE = 5;
    private final static int FAKE_ELEMENT = 230;
    private final static double EXPECTED_HIT_RATE = 0.33;

    @Test
    public void testEmptyDefaultCache() {

        MemCache<String, Integer> cache = new MemCache<>();

        assertEquals("Empty cache's size must be 0.", 0, cache.size());
        assertEquals("Empty cache's hit rate must be 0.", 0, cache.getHitRate(), PRECISION);
    }

    @Test(expected = CapacityExceededException.class)
    public void testWithZeroCapacity() throws CapacityExceededException {
        MemCache<String, Integer> cache = new MemCache<>(0);
        cache.set("one", 1, null);
    }

    @Test
    public void testAddingTwoItemsWithTheSameKey() throws CapacityExceededException {
        MemCache<Integer, String> cache = new MemCache<>(CACHE_TEST_SIZE);

        cache.set(1, "a", LocalDateTime.now());
        cache.set(1, "b", LocalDateTime.now().plusMinutes(1));

        assertEquals("Adding two items with the same key is supposed to count as 1 item", 1, cache.size());

    }

    @Test
    public void testExceedCapacity() throws CapacityExceededException {
        MemCache<Integer, String> cache = new MemCache<>(CACHE_TEST_SIZE);

        for (int i = 0; i < CACHE_TEST_SIZE; i++) {
            cache.set(i, "a", LocalDateTime.now().plusMinutes(1));
        }

        LocalDateTime afterOneMinute = LocalDateTime.now().plusMinutes(1);
        cache.set((int) CACHE_TEST_SIZE, "a", afterOneMinute);
        LocalDateTime actual = cache.getExpiration((int) CACHE_TEST_SIZE);
        assertEquals(afterOneMinute, actual);
    }

    @Test
    public void testAddNull() throws CapacityExceededException {
        MemCache<Integer, String> cache = new MemCache<>();

        cache.set(null, "a", LocalDateTime.now());
        cache.set(1, null, LocalDateTime.now());
        cache.set(null, null, LocalDateTime.now());
        cache.set(null, null, null);

        assertEquals("Adding null items with null keys and/or values" + "isn't supposed to register them in the cachec.", 0, cache.size());
    }

    @Test(expected = CapacityExceededException.class)
    public void testAddNullExpiration() throws CapacityExceededException {
        MemCache<Integer, String> cache = new MemCache<>(CACHE_TEST_SIZE);

        for (int i = 0; i < CACHE_TEST_SIZE + 1; i++) {
            cache.set(i, "a", null);
        }
    }

    @Test
    public void testClear() throws CapacityExceededException {
        MemCache<Integer, String> cache = new MemCache<>(CACHE_TEST_SIZE);

        for (int i = 0; i < CACHE_TEST_SIZE; i++) {
            cache.set(i, "a", null);
        }

        cache.clear();

        assertEquals("Clear should remove all elemtns from collection", 0, cache.size());
    }

    @Test
    public void testGet() throws CapacityExceededException {
        MemCache<Integer, String> cache = new MemCache<>(CACHE_TEST_SIZE);

        cache.set(1, "a", LocalDateTime.now().plusMinutes(1));

        assertEquals("key should be maped correctly to its value in the cache", "a", cache.get(1));
    }


    @Test
    public void testGetExpiration() throws CapacityExceededException {
        MemCache<Integer, String> cache = new MemCache<>(CACHE_TEST_SIZE);

        LocalDateTime currentTime = LocalDateTime.now();
        cache.set(1, "a", currentTime);

        assertEquals("Key should be mapped  correctly to its expiration time in the cache", currentTime, cache.getExpiration(1));
    }

    @Test
    public void testGetExpirationMissingItem() {
        MemCache<Integer, String> cache = new MemCache<>(CACHE_TEST_SIZE);
        LocalDateTime actual = cache.getExpiration(55);
        assertNull(actual);

    }

    @Test
    public void testRemoveExistingElement() throws CapacityExceededException {
        MemCache<Integer, String> cache = new MemCache<>(CACHE_TEST_SIZE);
        cache.set(1, "a", LocalDateTime.now());
        cache.remove(FAKE_ELEMENT);

        assertEquals("Removing an non existing key from the cache", 1, cache.size());
    }

    @Test
    public void testHitRate() throws CapacityExceededException {
        MemCache<Integer, String> cache = new MemCache<>(CACHE_TEST_SIZE);
        cache.set(1, "a", LocalDateTime.now().plusMinutes(1));
        cache.get(1);
        cache.get(0);
        cache.get(0);

        assertEquals("get hit rate should be calculated correctly ", EXPECTED_HIT_RATE, cache.getHitRate(), PRECISION);
    }

    @Test
    public void testAddOnFullCacheWithExpiredItems() throws CapacityExceededException {
        MemCache<Integer, String> cache = new MemCache<>(CACHE_TEST_SIZE);

        for (int i = 0; i < CACHE_TEST_SIZE; i++) {
            cache.set(i, "a", LocalDateTime.now().minusMinutes(1));

        }


        cache.set(FAKE_ELEMENT, "a", LocalDateTime.now().plusMinutes(1));

        assertNotNull("adding a new item should be possible when the cache is full but there an expired item ", cache.get(FAKE_ELEMENT));
    }

}
