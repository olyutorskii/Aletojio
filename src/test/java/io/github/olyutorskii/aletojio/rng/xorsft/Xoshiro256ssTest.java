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
public class Xoshiro256ssTest {

    public Xoshiro256ssTest() {
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
