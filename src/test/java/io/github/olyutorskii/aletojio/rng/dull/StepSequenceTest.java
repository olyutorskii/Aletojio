/*
 */

package io.github.olyutorskii.aletojio.rng.dull;

import io.github.olyutorskii.aletojio.rng.dull.StepSequence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class StepSequenceTest {

    public StepSequenceTest() {
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
     * Test of createCounter method, of class StepSequence.
     */
    @Test
    public void testCreateCounter() {
        System.out.println("createCounter");

        StepSequence seq;

        seq = StepSequence.createCounter();
        assertEquals(0, seq.nextInt32());
        assertEquals(1, seq.nextInt32());
        assertEquals(2, seq.nextInt32());

        return;
    }

    /**
     * Test of createQuasiRandom method, of class StepSequence.
     */
    @Test
    public void testCreateQuasiRandom() {
        System.out.println("createQuasiRandom");

        StepSequence seq;

        seq = StepSequence.createQuasiRandom();
        assertEquals(1642716433, seq.nextInt32());
        assertEquals(-1009534430, seq.nextInt32());
        assertEquals(633182003, seq.nextInt32());

        return;
    }

    /**
     * Test of nextInt32 method, of class StepSequence.
     */
    @Test
    public void testNextInt32() {
        System.out.println("nextInt32");

        StepSequence seq;

        seq = new StepSequence(10, 3);
        assertEquals(10, seq.nextInt32());
        assertEquals(13, seq.nextInt32());
        assertEquals(16, seq.nextInt32());

        seq = new StepSequence(-10, 20);
        assertEquals(-10, seq.nextInt32());
        assertEquals(10, seq.nextInt32());
        assertEquals(30, seq.nextInt32());

        seq = new StepSequence(5, -3);
        assertEquals(5, seq.nextInt32());
        assertEquals(2, seq.nextInt32());
        assertEquals(-1, seq.nextInt32());
        assertEquals(-4, seq.nextInt32());

        seq = new StepSequence(-2, 1);
        assertEquals(-2, seq.nextInt32());
        assertEquals(-1, seq.nextInt32());
        assertEquals(0, seq.nextInt32());
        assertEquals(1, seq.nextInt32());

        seq = new StepSequence(2147483646, 1);
        assertEquals(2147483646, seq.nextInt32());
        assertEquals(2147483647, seq.nextInt32());
        assertEquals(-2147483648, seq.nextInt32());
        assertEquals(-2147483647, seq.nextInt32());

        return;
    }

}
