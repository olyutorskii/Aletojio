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
public class XorShiftTest {

    public XorShiftTest() {
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
     * Test of nextInt32 method, of class XorShift.
     */
    @Test
    public void testNextInt32() {
        System.out.println("nextInt32");

        XorShift instance;
        instance = new XorShift();

        assertEquals(     270369, instance.nextInt32());
        assertEquals(   67634689, instance.nextInt32());
        assertEquals(-1647531835, instance.nextInt32());
        assertEquals(  307599695, instance.nextInt32());
        assertEquals(-1896278063, instance.nextInt32());

        instance.setSeed(1);
        assertEquals(     270369, instance.nextInt32());

        instance.setSeed(0x12345678);
        assertEquals(-2020058459, instance.nextInt32());

        return;
    }

    /**
     * Test of setSeed method, of class XorShift.
     */
    @Test
    public void testSetSeed() {
        System.out.println("setSeed");

        XorShift instance;
        instance = new XorShift();

        try {
            instance.setSeed(0);
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        return;
    }

}
