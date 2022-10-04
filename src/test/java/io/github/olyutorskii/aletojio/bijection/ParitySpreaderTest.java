/*
 */

package io.github.olyutorskii.aletojio.bijection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class ParitySpreaderTest {

    public ParitySpreaderTest() {
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
     * Test of spread method, of class ParitySpreader.
     */
    @Test
    public void testSpread_int() {
        System.out.println("spread");

        assertEquals(0, ParitySpreader.spread(0));
        assertEquals(0xffffffff, ParitySpreader.spread(0xffffffff));
        assertEquals(0x01010100, ParitySpreader.spread(1));
        assertEquals(0x0f0f0f00, ParitySpreader.spread(0b1111));
        assertEquals(0x070b0d0e, ParitySpreader.spread(0x08040201));
        assertEquals(0x050d090b, ParitySpreader.spread(0x0f070301));
        assertEquals(0x0f070301, ParitySpreader.spread(0x050d090b));

        return;
    }

    /**
     * Test of spread method, of class ParitySpreader.
     */
    @Test
    public void testSpread_byteArr_int() {
        System.out.println("spread");

        byte[] bytes;

        bytes = new byte[]{0, 0, 0, 0};
        ParitySpreader.spread(bytes, 4);
        assertArrayEquals(new byte[]{0, 0, 0, 0}, bytes);

        bytes = new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff};
        ParitySpreader.spread(bytes, 4);
        assertArrayEquals(new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff}, bytes);

        bytes = new byte[]{0x0f, 0x07, 0x03, 0x01};
        ParitySpreader.spread(bytes, 4);
        assertArrayEquals(new byte[]{0x05, 0x0d, 0x09, 0x0b}, bytes);

        bytes = new byte[]{0x0f, 0x07, 0x03, 0x01};
        ParitySpreader.spread(bytes, 2);
        assertArrayEquals(new byte[]{0x07, 0x0f, 0x03, 0x01}, bytes);

        bytes = new byte[]{0x0f, 0x07, 0x03, 0x01};
        try {
            ParitySpreader.spread(bytes, -1);
            fail();
        } catch(IndexOutOfBoundsException e) {
            assert true;
        }

        bytes = new byte[]{0x0f, 0x07, 0x03, 0x01};
        try {
            ParitySpreader.spread(bytes, 3);
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        bytes = new byte[]{0x0f, 0x07, 0x03, 0x01};
        try {
            ParitySpreader.spread(bytes, 6);
            fail();
        } catch(IndexOutOfBoundsException e) {
            assert true;
        }

        try {
            ParitySpreader.spread(null, 6);
            fail();
        } catch(NullPointerException e) {
            assert true;
        }

        return;
    }

}
