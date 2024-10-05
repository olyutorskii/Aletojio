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
public class Xoshiro256ppTest {

    public Xoshiro256ppTest() {
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
     * Test of nextInt64 method, of class Xoshiro256pp.
     */
    @Test
    public void testNextInt64() {
        System.out.println("nextInt64");

        Xoshiro256pp instance;
        instance = new Xoshiro256pp();

        assertEquals(         8388609L, instance.nextInt64());
        assertEquals(         8388609L, instance.nextInt64());
        assertEquals(              16L, instance.nextInt64());
        assertEquals( 598134333898785L, instance.nextInt64());
        assertEquals(1127000561549328L, instance.nextInt64());

        instance.setSeed(1, 0, 0, 0);
        assertEquals(         8388609L, instance.nextInt64());

        return;
    }

    /**
     * Test of setSeed method, of class Xoshiro256pp.
     */
    @Test
    public void testSetSeed() {
        System.out.println("setSeed");

        Xoshiro256pp instance;
        instance = new Xoshiro256pp();

        try {
            instance.setSeed(0, 0, 0, 0);
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        return;
    }

}
