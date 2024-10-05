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
public class XorShiftTest {

    public XorShiftTest() {
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
