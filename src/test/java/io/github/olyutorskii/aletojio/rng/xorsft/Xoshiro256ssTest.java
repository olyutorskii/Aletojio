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
public class Xoshiro256ssTest {

    public Xoshiro256ssTest() {
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
     * Test of nextInt64 method, of class Xoshiro256ss.
     */
    @Test
    public void testNextInt64() {
        System.out.println("nextInt64");

        Xoshiro256ss instance;
        instance = new Xoshiro256ss();

        assertEquals(                 0L, instance.nextInt64());
        assertEquals(              5760L, instance.nextInt64());
        assertEquals(              5760L, instance.nextInt64());
        assertEquals(         754980480L, instance.nextInt64());
        assertEquals(202661983986647040L, instance.nextInt64());

        instance.setSeed(1, 0, 0, 0);
        assertEquals(                 0L, instance.nextInt64());

        return;
    }

    /**
     * Test of setSeed method, of class Xoshiro256ss.
     */
    @Test
    public void testSetSeed() {
        System.out.println("setSeed");

        Xoshiro256ss instance;
        instance = new Xoshiro256ss();

        try {
            instance.setSeed(0, 0, 0, 0);
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        return;
    }

}
