/*
 */

package io.github.olyutorskii.aletojio.rng.fibo;

import java.util.Objects;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class LagFibTest {

    public LagFibTest() {
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
     * Test of hasOdd method, of class LagFib.
     */
    @Test
    public void testHasOdd() {
        System.out.println("hasOdd");

        int[] iVec;

        iVec = new int[]{};
        assertFalse(LagFib.hasOdd(iVec));

        iVec = new int[]{1};
        assertTrue(LagFib.hasOdd(iVec));

        iVec = new int[]{2};
        assertFalse(LagFib.hasOdd(iVec));

        iVec = new int[]{2, 3};
        assertTrue(LagFib.hasOdd(iVec));

        iVec = new int[]{2, 4};
        assertFalse(LagFib.hasOdd(iVec));

        iVec = new int[]{2, 4, 6, 8, 10};
        assertFalse(LagFib.hasOdd(iVec));

        iVec = new int[]{2, 4, 7, 8, 10};
        assertTrue(LagFib.hasOdd(iVec));

        try {
            LagFib.hasOdd(null);
            fail();
        } catch (NullPointerException e) {
            assert true;
        }

        return;
    }

    /**
     * Test of setSeed method, of class LagFib.
     */
    @Test
    public void testSetSeed() {
        System.out.println("setSeed");

        LagFib lf;

        lf = new LagFib(3, 5);

        lf.setSeed(new int[]{16, 8, 4, 2, 1});
        assertEquals(5, lf.nextInt32());
        assertEquals(10, lf.nextInt32());

        lf.setSeed(new int[]{513, 256, 128, 64, 32});
        assertEquals(160, lf.nextInt32());
        assertEquals(320, lf.nextInt32());
        assertEquals(641, lf.nextInt32());
        assertEquals(416, lf.nextInt32());
        assertEquals(833, lf.nextInt32());
        assertEquals(801, lf.nextInt32());

        try {
            lf.setSeed(null);
            fail();
        } catch (NullPointerException e) {
            assert true;
        }

        try {
            lf.setSeed(new int[]{});
            fail();
        } catch (IndexOutOfBoundsException e) {
            assert true;
        }

        try {
            lf.setSeed(new int[]{1, 2, 3, 4});
            fail();
        } catch (IndexOutOfBoundsException e) {
            assert true;
        }

        try {
            lf.setSeed(new int[]{1, 2, 3, 4, 5, 6});
            fail();
        } catch (IndexOutOfBoundsException e) {
            assert true;
        }

        try {
            lf.setSeed(new int[]{2, 4, 6, 8, 10});
            fail();
        } catch (IllegalArgumentException e) {
            assert true;
        }

        return;
    }

    /**
     * Test of binOp method, of class LagFib.
     */
    @Test
    public void testBinOp() {
        System.out.println("binOp");

        LagFib lf = new LagFib(1, 2);

        assertEquals(579, lf.binOp(123, 456));

        return;
    }

    /**
     * Test of nextInt32 method, of class LagFib.
     */
    @Test
    public void testNextInt32() {
        System.out.println("nextInt32");

        LagFib lf = new LagFib(1, 2);

        int[] testSeq = {
                     1,
                     1,
                     2,
                     3,
                     5,
                     8,
                    13,
                    21,
                    34,
                    55,
                    89,
                   144,
                   233,
                   377,
                   610,
                   987,
                  1597,
                  2584,
                  4181,
                  6765,
                 10946,
                 17711,
                 28657,
                 46368,
                 75025,
        };

        for(int ti : testSeq) {
            assertEquals(ti, lf.nextInt32());
        }

        return;
    }

    /**
     * Test of constructor, of class LagFib.
     */
    @Test
    public void testConstructor() {
        System.out.println("Constructor");

        LagFib lf;

        lf = new LagFib(7, 10);
        Objects.requireNonNull(lf);

        try {
            lf = new LagFib(10, 7);
            fail();
            Objects.requireNonNull(lf);
        } catch(IllegalArgumentException e) {
            assert true;
        }

        try {
            lf = new LagFib(0, 10);
            fail();
            Objects.requireNonNull(lf);
        } catch(IndexOutOfBoundsException e) {
            assert true;
        }

        return;
    }

}
