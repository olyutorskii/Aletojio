/*
 */

package io.github.olyutorskii.aletojio.rng.mwc;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class CmwcTest {

    public CmwcTest() {
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
     * Test of nextInt32 method, of class Mwc.
     */
    @Test
    public void testNextInt32() {
        System.out.println("nextInt32");

        Cmwc cmwc;

        cmwc = new Cmwc();
        assertEquals(-987769341, cmwc.nextInt32());
        assertEquals(-2, cmwc.nextInt32());
        assertEquals(-2, cmwc.nextInt32());

        cmwc = new Cmwc(3, 1L << (8 + 4), 1L << 32);
        cmwc.setSeeds(new int[]{0x123456, 0x654321, 0xa5a5a5});
        assertEquals(0xdcba9ffe, cmwc.nextInt32());
        assertEquals(0xabcdeffe, cmwc.nextInt32());
        assertEquals(0xa5a5aff9, cmwc.nextInt32());
        assertEquals(0x56001ff5, cmwc.nextInt32());
        assertEquals(0x21002233, cmwc.nextInt32());
        assertEquals(0xa5007542, cmwc.nextInt32());
        assertEquals(0xfe00b5a4, cmwc.nextInt32());

        return;
    }

}
