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
public class XorShift128Test {

    public XorShift128Test() {
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
     * Test of nextInt32 method, of class XorShift128.
     */
    @Test
    public void testNextInt32() {
        System.out.println("nextInt32");


        XorShift128 instance;
        instance = new XorShift128();

        assertEquals(33, instance.nextInt32());
        assertEquals(33, instance.nextInt32());
        assertEquals(33, instance.nextInt32());
        assertEquals(33, instance.nextInt32());
        assertEquals(1056, instance.nextInt32());

        instance.setSeed(0x12345678, 0xffeeddcc, 0xa55a5aa5, 0x456789ab);

        assertEquals( 299653944, instance.nextInt32());
        assertEquals( 334044194, instance.nextInt32());
        assertEquals( 502854455, instance.nextInt32());
        assertEquals(-194980201, instance.nextInt32());
        assertEquals(-566931493, instance.nextInt32());

        return;
    }

    /**
     * Test of setSeed method, of class XorShift128.
     */
    @Test
    public void testSetSeed() {
        System.out.println("setSeed");

        XorShift128 instance;
        instance = new XorShift128();

        try {
            instance.setSeed(0, 0, 0, 0);
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        return;
    }

}
