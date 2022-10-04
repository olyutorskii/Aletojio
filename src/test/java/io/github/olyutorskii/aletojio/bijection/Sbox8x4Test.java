/*
 */

package io.github.olyutorskii.aletojio.bijection;

import io.github.olyutorskii.aletojio.rng.RndInt32;
import io.github.olyutorskii.aletojio.rng.dull.SeqRepeater;
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
public class Sbox8x4Test {

    public Sbox8x4Test() {
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
     * Test of constructor, of class Sbox_8x4.
     */
    @Test
    public void testConstructor() {
        System.out.println("constructor");

        Sbox8x4 sb;
        RndInt32 rd = StepSequence.createCounter();

        try {
            sb = new Sbox8x4(null);
            sb.getClass();
            fail();
        } catch(NullPointerException e) {
            assert true;
        }

        try {
            sb = new Sbox8x4(rd, null);
            sb.getClass();
            fail();
        } catch(NullPointerException e) {
            assert true;
        }

        try {
            sb = new Sbox8x4(rd, new int[255]);
            sb.getClass();
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        try {
            sb = new Sbox8x4(rd, new int[257]);
            sb.getClass();
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        try {
            sb = new Sbox8x4(rd, new int[256]);
            sb.getClass();
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        int[] tbl;

        tbl = new int[256];
        for (int idx = 1; idx < 256; idx++) {
            tbl[idx] = idx - 1;
        }

        tbl[0] = 0;
        try {
            sb = new Sbox8x4(rd, tbl);
            sb.getClass();
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        tbl[0] = -1;
        try {
            sb = new Sbox8x4(rd, tbl);
            sb.getClass();
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        tbl[0] = 256;
        try {
            sb = new Sbox8x4(rd, tbl);
            sb.getClass();
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        tbl[0] = 255;
        sb = new Sbox8x4(rd, tbl);
        assertEquals(0xffffffff, sb.nextInt32());
        assertEquals(0xffffff00, sb.nextInt32());

        return;
    }

    /**
     * Test of nextInt32 method, of class Sbox_8x4.
     */
    @Test
    public void testNextInt32() {
        System.out.println("nextInt32");

        Sbox8x4 sb;

        RndInt32 r32 = new SeqRepeater(0x00010203, 0xfcfdfeff);
        sb = new Sbox8x4(r32);

        assertEquals(0x637c777b, sb.nextInt32());
        assertEquals(0xb054bb16, sb.nextInt32());

        return;
    }

}
