/*
 */

package io.github.olyutorskii.aletojio.idling;

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
public class PopCntMonitorTest {

    public PopCntMonitorTest() {
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
     * Test of probe method, of class PopCntMonitor.
     */
    @Test
    public void testProbe() {
        System.out.println("resetImpl");

        PopCntMonitor mon;
        RndSkipper skipper;
        RndInt32 rnd;

        mon = new PopCntMonitor(16);
        skipper = new RndSkipper(mon);

        rnd = new SeqRepeater(0xa5a5a5a5);
        assertEquals(1, skipper.skip(rnd));

        rnd = new SeqRepeater(1, 2, 0xa5a5a5a5, 4, 5);
        assertEquals(3, skipper.skip(rnd));
        assertEquals(4, rnd.nextInt32());

        // Infinite loop
        /*
        rnd = new SeqRepeater(1, 2, 3);
        skipper.skip(rnd);
        */

        rnd = new SeqRepeater(0, 1, -1, 0xa5a5a50e, 0xa5a5a51f, 0xa5a5a5a5);
        assertEquals(6, skipper.skip(rnd));

        mon = new PopCntMonitor(0);
        skipper = new RndSkipper(mon);
        rnd = new SeqRepeater(1, -1, 0);
        assertEquals(3, skipper.skip(rnd));

        mon = new PopCntMonitor(32);
        skipper = new RndSkipper(mon);
        rnd = new SeqRepeater(0, 1, 0xffffffff);
        assertEquals(3, skipper.skip(rnd));

        try {
            mon = new PopCntMonitor(-1);
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        try {
            mon = new PopCntMonitor(33);
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        assert mon == mon;

        mon = new PopCntMonitor(1);
        assertFalse(mon.probe(0));
        assertTrue(mon.probe(1));
        assertTrue(mon.probe(0));
        mon.reset();
        assertFalse(mon.probe(0));

        return;
    }

}
