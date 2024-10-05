/*
 */

package io.github.olyutorskii.aletojio.rng.xorsft;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class Xoroshiro128ssTest {

    public Xoroshiro128ssTest() {
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    /**
     * Test of nextInt64 method, of class Xoroshiro128ss.
     */
    @Test
    public void testNextInt64() {
        System.out.println("nextInt64");

        Xoroshiro128ss instance;
        instance = new Xoroshiro128ss();

        assertEquals(                5760L, instance.nextInt64());
        assertEquals(         97014257280L, instance.nextInt64());
        assertEquals(-1836652260583532928L, instance.nextInt64());
        assertEquals(-9223346814227278190L, instance.nextInt64());
        assertEquals(-2621964256160814912L, instance.nextInt64());

        instance.setSeed(1L, 0L);
        assertEquals(                5760L, instance.nextInt64());

        return;
    }

    /**
     * Test of setSeed method, of class Xoroshiro128ss.
     */
    @Test
    public void testSetSeed() {
        System.out.println("setSeed");

        Xoroshiro128ss instance;
        instance = new Xoroshiro128ss();

        try {
            instance.setSeed(0L, 0L);
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        return;
    }

}
