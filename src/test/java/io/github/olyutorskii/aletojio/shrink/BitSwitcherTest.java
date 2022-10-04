/*
 */

package io.github.olyutorskii.aletojio.shrink;

import io.github.olyutorskii.aletojio.rng.RndInt32;
import io.github.olyutorskii.aletojio.rng.dull.SeqRepeater;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class BitSwitcherTest {

    public BitSwitcherTest() {
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
     * Test of nextInt32 method, of class BitSwitcher.
     */
    @Test
    public void testNextInt32() {
        System.out.println("nextInt32");

        BitSwitcher instance;

        RndInt32 sw;
        RndInt32 r0;
        RndInt32 r1;

        sw = new SeqRepeater(0x0f, 0xffff0000);
        r0 = new SeqRepeater(0x55, 0x55555555);
        r1 = new SeqRepeater(0xee, 0xaaaaaaaa);

        instance = new BitSwitcher(sw, r0, r1);

        assertEquals(0x5e, instance.nextInt32());
        assertEquals(0xaaaa5555, instance.nextInt32());

        try {
            instance = new BitSwitcher(null, r0, r1);
            fail();
        } catch(NullPointerException e) {
            assert true;
        }

        try {
            instance = new BitSwitcher(sw, null, r1);
            fail();
        } catch(NullPointerException e) {
            assert true;
        }

        try {
            instance = new BitSwitcher(sw, r0, null);
            fail();
        } catch(NullPointerException e) {
            assert true;
        }

        assert instance == instance;

        return;
    }

}
