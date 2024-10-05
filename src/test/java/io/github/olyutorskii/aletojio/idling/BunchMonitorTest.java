/*
 */

package io.github.olyutorskii.aletojio.idling;

import io.github.olyutorskii.aletojio.rng.dull.SeqRepeater;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class BunchMonitorTest {

    public BunchMonitorTest() {
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
