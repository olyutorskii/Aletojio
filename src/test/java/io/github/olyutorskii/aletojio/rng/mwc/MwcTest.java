/*
 */

package io.github.olyutorskii.aletojio.rng.mwc;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class MwcTest {

    public MwcTest() {
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
     * Test of nextInt32 method, of class Mwc.
     */
    @Test
    public void testNextInt32() {
        System.out.println("nextInt32");

        Mwc mwc;

        mwc = new Mwc();
        assertEquals(1, mwc.getCarry());
        assertEquals(611373679, mwc.nextInt32());
        assertEquals(0, mwc.nextInt32());
        assertEquals(0, mwc.nextInt32());

        mwc = new Mwc(3, 1L << (8 + 4), 1L << 32);
        mwc.setSeeds(new int[]{0x123456, 0x654321, 0xa5a5a5});
        assertEquals(0x23456001, mwc.nextInt32());
        assertEquals(0x01, mwc.getCarry());
        assertEquals(0x54321001, mwc.nextInt32());
        assertEquals(0x06, mwc.getCarry());
        assertEquals(0x5a5a5006, mwc.nextInt32());
        assertEquals(0x0a, mwc.getCarry());
        assertEquals(0x5600100a, mwc.nextInt32());
        assertEquals(0x21001234, mwc.nextInt32());
        assertEquals(0xa5006543, mwc.nextInt32());
        assertEquals(0x0100a5a5, mwc.nextInt32());

        mwc = new Mwc(3, 1L << (8 + 4), 1L << 32);
        mwc.setSeeds(new int[]{0x123456, 0x654321, 0xa5a5a5});
        mwc.setCarry(0x0f);
        assertEquals(0x2345600f, mwc.nextInt32());

        return;
    }

    /**
     * Test of getRecords method, of class Mwc.
     */
    @Test
    public void testGetRecords() {
        System.out.println("getRecords");

        Mwc mwc;

        mwc = new Mwc();
        assertEquals(1038, mwc.getRecords());

        mwc = new Mwc(3, 4, 5);
        assertEquals(3, mwc.getRecords());

        return;
    }

    /**
     * Test of constructor, of class Mwc.
     */
    @Test
    public void testConstructor() {
        System.out.println("constructor");

        Mwc mwc;

        mwc = new Mwc(3, 4, 5);

        mwc = new Mwc(1, 4, 5);
        try {
            mwc = new Mwc(0, 4, 5);
            fail();
        } catch (IllegalArgumentException e) {
            assert true;
        }

        mwc = new Mwc(3, 2, 5);
        try {
            mwc = new Mwc(3, 1, 5);
            fail();
        } catch (IllegalArgumentException e) {
            assert true;
        }

        mwc = new Mwc(3, 4, 2);
        try {
            mwc = new Mwc(3, 4, 1);
            fail();
        } catch (IllegalArgumentException e) {
            assert true;
        }

        return;
    }

    /**
     * Test of setCarry method, of class Mwc.
     */
    @Test
    public void testSetCarry() {
        System.out.println("setCarry");

        Mwc mwc;

        mwc = new Mwc(3, 1L << (8 + 4), 1L << 32);
        mwc.setSeeds(new int[]{0x123456, 0x654321, 0xa5a5a5});
        mwc.setCarry(0x0f);
        assertEquals(0x2345600f, mwc.nextInt32());
        assertEquals(0x01, mwc.getCarry());

        return;
    }

    /**
     * Test of setSeeds method, of class Mwc.
     */
    @Test
    public void testSetSeeds() {
        System.out.println("setSeeds");

        Mwc mwc;

        mwc = new Mwc(3, 4, 5);
        mwc.setSeeds(new int[]{1, 2, 3});

        try {
            mwc.setSeeds(new int[]{1, 2});
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        try {
            mwc.setSeeds(new int[]{1, 2, 3, 4});
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        try {
            mwc.setSeeds(null);
            fail();
        } catch(NullPointerException e) {
            assert true;
        }

        return;
    }

}
