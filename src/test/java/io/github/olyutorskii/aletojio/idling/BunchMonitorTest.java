/*
 */

package io.github.olyutorskii.aletojio.idling;

import io.github.olyutorskii.aletojio.rng.dull.SeqRepeater;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class BunchMonitorTest {

    public BunchMonitorTest() {
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
     * Test of probeImpl method, of class BunchMonitor.
     */
    @Test
    public void testProbe() {
        System.out.println("probeImpl");

        BunchMonitor mon;
        SeqRepeater rnd;
        PopCntMonitor pmon1;
        PopCntMonitor pmon2;
        RndSkipper skipper;

        pmon1 = new PopCntMonitor(1);
        pmon2 = new PopCntMonitor(2);
        mon = new BunchMonitor(pmon1, pmon2);
        skipper = new RndSkipper(mon);

        rnd = new SeqRepeater(0, 1, 2, 3);
        assertEquals(4, skipper.skip(rnd));

        rnd = new SeqRepeater(3, 2, 1, 0);
        assertEquals(2, skipper.skip(rnd));

        mon = new BunchMonitor(Arrays.asList(pmon1, pmon2));
        skipper = new RndSkipper(mon);
        rnd = new SeqRepeater(0, 1, 2, 3);
        assertEquals(4, skipper.skip(rnd));

        try {
            mon = new BunchMonitor((PopCntMonitor[])null);
            fail();
        } catch(NullPointerException e) {
            assert true;
        }

        try {
            mon = new BunchMonitor(pmon1, null);
            fail();
        } catch(NullPointerException e) {
            assert true;
        }

        try {
            mon = new BunchMonitor(Arrays.asList(pmon1, null));
            fail();
        } catch(NullPointerException e) {
            assert true;
        }

        assert mon == mon;

        return;
    }

}
