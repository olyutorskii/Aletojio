/*
 */

package io.github.olyutorskii.aletojio.rng;

import io.github.olyutorskii.aletojio.rng.dull.SeqRepeater;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class RndAdapterTest {

    private static final long SEED_COMMON = 0x1234567890abcdefL;


    public RndAdapterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        Random rnd = new Random(SEED_COMMON);

        assertEquals(0x228f1d3cL, rnd.nextInt());
        assertEquals(0x2a3c2fd1L, rnd.nextInt());
        assertEquals(0x3845d703L, rnd.nextInt());

        return;
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
     * Test of setSeed method, of class RndAdapter.
     */
    @Test
    public void testSetSeed() {
        System.out.println("setSeed");

        RndAdapter rnd = new RndAdapter(new Random());

        try {
            rnd.setSeed(SEED_COMMON);
            fail();
        } catch (UnsupportedOperationException e) {
            assert true;
        }

        return;
    }

    /**
     * Test of nextBit method, of class RndAdapter.
     */
    @Test
    public void testNextBit() {
        System.out.println("nextBit");

        Random rnd1;
        Random rnd2;

        rnd1 = new Random(SEED_COMMON);
        rnd2 = new Random(SEED_COMMON);

        RndAdapter instance;

        instance = new RndAdapter(rnd1);

        assertEquals((rnd2.nextInt() >>> 31) != 0, instance.nextBit());

        return;
    }

    /**
     * Test of nextByte method, of class RndAdapter.
     */
    @Test
    public void testNextByte() {
        System.out.println("nextByte");

        Random rnd1;
        Random rnd2;

        rnd1 = new Random(SEED_COMMON);
        rnd2 = new Random(SEED_COMMON);

        RndAdapter instance;

        instance = new RndAdapter(rnd1);

        assertEquals(rnd2.nextInt() >>> 24, instance.nextByte());

        return;
    }

    /**
     * Test of nextInt32 method, of class RndAdapter.
     */
    @Test
    public void testNextInt32() {
        System.out.println("nextInt32");

        Random rnd1;
        Random rnd2;

        rnd1 = new Random(SEED_COMMON);
        rnd2 = new Random(SEED_COMMON);

        RndAdapter instance;

        instance = new RndAdapter(rnd1);

        assertEquals(rnd2.nextInt(), instance.nextInt32());

        return;
    }

    /**
     * Test of nextInt64 method, of class RndAdapter.
     */
    @Test
    public void testNextInt64() {
        System.out.println("nextInt64");

        Random rnd1;
        Random rnd2;

        rnd1 = new Random(SEED_COMMON);
        rnd2 = new Random(SEED_COMMON);

        RndAdapter instance;

        instance = new RndAdapter(rnd1);

        assertEquals(rnd2.nextLong(), instance.nextInt64());

        return;
    }

    /**
     * Test of nextInt31 method, of class RndAdapter.
     */
    @Test
    public void testNextInt31() {
        System.out.println("nextInt31");

        Random rnd1;
        Random rnd2;

        rnd1 = new Random(SEED_COMMON);
        rnd2 = new Random(SEED_COMMON);

        RndAdapter instance;

        instance = new RndAdapter(rnd1);

        int rnd1st = instance.nextInt31();
        int rnd2nd = instance.nextInt31();
        int rnd3rd = instance.nextInt31();
        int rnd4th = instance.nextInt31();

        int expected1 = (rnd1st << 1) | (rnd2nd >>> 30);
        int expected2 = (rnd2nd << 2) | (rnd3rd >>> 29);
        int expected3 = (rnd3rd << 3) | (rnd4th >>> 28);

        assertEquals(expected1, rnd2.nextInt());
        assertEquals(expected2, rnd2.nextInt());
        assertEquals(expected3, rnd2.nextInt());

        return;
    }

    /**
     * Test of next method, of class RndAdapter.
     */
    @Test
    public void testNext() {
        System.out.println("next");

        Random rnd1;
        Random rnd2;

        rnd1 = new Random(SEED_COMMON);
        rnd2 = new Random(SEED_COMMON);

        RndAdapter instance;

        instance = new RndAdapter(rnd1);

        assertEquals(rnd2.nextInt(), instance.nextInt());
        assertEquals(rnd2.nextLong(), instance.nextLong());

        return;
    }

    /**
     *
     */
    @Test
    public void testSourceEtc() {
        System.out.println("valious source");

        RndInt32 r32;
        RndInt64 r64;
        RndAdapter instance;

        r32 = new SeqRepeater(0x12345678, 0x9abcdef0);
        instance = new RndAdapter(r32);

        assertEquals((byte)0x12, instance.nextByte());
        assertEquals((byte)0x34, instance.nextByte());
        assertEquals((byte)0x56, instance.nextByte());
        assertEquals((byte)0x78, instance.nextByte());
        assertEquals((byte)0x9a, instance.nextByte());

        r64 = new SeqRepeater(0x12345678, 0x00aabbcc);
        instance = new RndAdapter(r64);

        assertEquals(0x1234567800aabbccL, instance.nextInt64());

        SeqRepeater sr = new SeqRepeater(
            0b10100101_10100101_10100101_10100101,
            0b01011010_01011010_01011010_01011010
        );
        RndInt31 r31 = () -> {
            return sr.nextInt32() & ((1 << 31) - 1);
        };
        instance = new RndAdapter(r31);
        assertEquals((byte)0b0100_1011, instance.nextByte());
        assertEquals((byte)0b0100_1011, instance.nextByte());
        assertEquals((byte)0b0100_1011, instance.nextByte());
        assertEquals((byte)0b0100_1011, instance.nextByte());
        assertEquals((byte)0b0110_1001, instance.nextByte());

        return;
    }

}
