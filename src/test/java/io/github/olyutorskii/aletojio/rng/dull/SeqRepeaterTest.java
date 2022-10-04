/*
 */

package io.github.olyutorskii.aletojio.rng.dull;

import io.github.olyutorskii.aletojio.rng.dull.SeqRepeater;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class SeqRepeaterTest {

    public SeqRepeaterTest() {
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
     * Test of nextInt32 method, of class SeqRepeater.
     */
    @Test
    public void testNextInt32() {
        System.out.println("nextInt32");

        SeqRepeater sr;
        List<Integer> li;

        sr = new SeqRepeater(1, 0, -1, 999, Integer.MAX_VALUE,Integer.MIN_VALUE);
        assertEquals(1, sr.nextInt32());
        assertEquals(0, sr.nextInt32());
        assertEquals(-1, sr.nextInt32());
        assertEquals(999, sr.nextInt32());
        assertEquals(Integer.MAX_VALUE, sr.nextInt32());
        assertEquals(Integer.MIN_VALUE, sr.nextInt32());
        assertEquals(1, sr.nextInt32());
        assertEquals(0, sr.nextInt32());

        sr = new SeqRepeater(1, 2);
        assertEquals(1, sr.nextInt32());
        assertEquals(2, sr.nextInt32());
        assertEquals(1, sr.nextInt32());
        assertEquals(2, sr.nextInt32());
        assertEquals(1, sr.nextInt32());

        sr = new SeqRepeater(999);
        assertEquals(999, sr.nextInt32());
        assertEquals(999, sr.nextInt32());
        assertEquals(999, sr.nextInt32());

        li = Arrays.asList(1, 2, 3);
        sr = new SeqRepeater(li);
        assertEquals(1, sr.nextInt32());
        assertEquals(2, sr.nextInt32());
        assertEquals(3, sr.nextInt32());

        li = Arrays.asList();
        try {
            sr = new SeqRepeater(li);
            fail();
            Objects.requireNonNull(sr);
        } catch (IllegalArgumentException e) {
            assert true;
        }

        li = Arrays.asList(1, null, 3);
        try {
            sr = new SeqRepeater(li);
            fail();
            Objects.requireNonNull(sr);
        } catch (NullPointerException e) {
            assert true;
        }

        try {
            sr = new SeqRepeater();
            fail();
            Objects.requireNonNull(sr);
        } catch (IllegalArgumentException e) {
            assert true;
        }

        try {
            sr = new SeqRepeater((int[])null);
            fail();
            Objects.requireNonNull(sr);
        } catch (NullPointerException e) {
            assert true;
        }

        try {
            sr = new SeqRepeater((List<Integer>)null);
            fail();
            Objects.requireNonNull(sr);
        } catch (NullPointerException e) {
            assert true;
        }

        return;
    }

    /**
     * Test of nextInt64 method, of class SeqRepeater.
     */
    @Test
    public void testNextInt64() {
        System.out.println("nextInt64");

        SeqRepeater instance;

        instance = new SeqRepeater(-1, -2);
        assertEquals(-2L, instance.nextInt64());

        instance = new SeqRepeater(0x12345678, 0xccddeeff, 0xa5a5a5a5);
        assertEquals(0x12345678ccddeeffL, instance.nextInt64());
        assertEquals(0xa5a5a5a512345678L, instance.nextInt64());

        return;
    }

}
