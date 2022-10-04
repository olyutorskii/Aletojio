/*
 */

package io.github.olyutorskii.aletojio.rng.xorsft;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class Xoroshiro128ssTest {

    public Xoroshiro128ssTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
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
