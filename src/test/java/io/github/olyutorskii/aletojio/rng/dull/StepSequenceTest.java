/*
 */

package io.github.olyutorskii.aletojio.rng.dull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class StepSequenceTest {

    public StepSequenceTest() {
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
