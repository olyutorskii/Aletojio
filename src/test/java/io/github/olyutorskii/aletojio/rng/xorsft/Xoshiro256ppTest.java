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
public class Xoshiro256ppTest {

    public Xoshiro256ppTest() {
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
