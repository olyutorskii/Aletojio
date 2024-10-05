/*
 */
package io.github.olyutorskii.aletojio.bijection;

import io.github.olyutorskii.aletojio.rng.RndInt32;
import io.github.olyutorskii.aletojio.rng.dull.StepSequence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class Pbox32Test {

    public Pbox32Test() {
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
     * Test of constructor, of class Pbox32.
     */
    @Test
    public void testConstructor() {
        System.out.println("constructor");

        Pbox32 pbox;
        RndInt32 rd = StepSequence.createCounter();

        try {
            pbox = new Pbox32(null);
            pbox.getClass();
            fail();
        } catch(NullPointerException e) {
            assert true;
        }

        try {
            pbox = new Pbox32(rd, null);
            pbox.getClass();
            fail();
        } catch(NullPointerException e) {
            assert true;
        }

        try {
            pbox = new Pbox32(rd, new int[31]);
            pbox.getClass();
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        try {
            pbox = new Pbox32(rd, new int[33]);
            pbox.getClass();
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        try {
            pbox = new Pbox32(rd, new int[32]);
            pbox.getClass();
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        int[] tbl;

        tbl = new int[32];
        for (int idx = 1; idx < 32; idx++) {
            tbl[idx] = idx - 1;
        }

        tbl[0] = 0;
        try {
            pbox = new Pbox32(rd, tbl);
            pbox.getClass();
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        tbl[0] = -1;
        try {
            pbox = new Pbox32(rd, tbl);
            pbox.getClass();
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        tbl[0] = 32;
        try {
            pbox = new Pbox32(rd, tbl);
            pbox.getClass();
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        tbl[0] = 31;
        pbox = new Pbox32(rd, tbl);
        assertEquals(0x0000_0000, pbox.nextInt32());
        assertEquals(0x8000_0000, pbox.nextInt32());

        return;
    }

    /**
     * Test of diffuse method, of class Pbox32.
     */
    @Test
    public void testDiffuse() {
        System.out.println("diffuse");

        assertEquals(0x0002_0000, Pbox32.mapPermutation(0b1));
        assertEquals(0x0002_0010, Pbox32.mapPermutation(0b11));
        assertEquals(0x0082_0010, Pbox32.mapPermutation(0b111));

        assertEquals(0x4100_0800, Pbox32.mapPermutation(0xe000_0000));
        assertEquals(0x4000_0800, Pbox32.mapPermutation(0xc000_0000));
        assertEquals(0x4000_0000, Pbox32.mapPermutation(0x8000_0000));

        assertEquals(0x0000_0000, Pbox32.mapPermutation(0x0000_0000));
        assertEquals(0xffff_ffff, Pbox32.mapPermutation(0xffff_ffff));

        return;
    }

    /**
     * Test of nextInt32 method, of class Pbox32.
     */
    @Test
    public void testNextInt32() {
        System.out.println("nextInt32");

        Pbox32 pbox;

        RndInt32 r32 = new StepSequence(0, 1);
        pbox = new Pbox32(r32);

        assertEquals(0x0000_0000, pbox.nextInt32());
        assertEquals(0x0002_0000, pbox.nextInt32());
        assertEquals(0x0000_0010, pbox.nextInt32());
        assertEquals(0x0002_0010, pbox.nextInt32());

        return;
    }

}
