/*
 */

package io.github.olyutorskii.aletojio.idling;

import io.github.olyutorskii.aletojio.rng.RndInt32;
import io.github.olyutorskii.aletojio.rng.dull.SeqRepeater;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class UniqRunMonitorTest {

    public UniqRunMonitorTest() {
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
     * Test of probeImpl method, of class UniqRunMonitor.
     */
    @Test
    public void testProbeImpl() {
        System.out.println("probeImpl");

        RndInt32 rnd;
        UniqRunMonitor mon;
        RndSkipper skipper;
        List<Integer> rndList;
        int iVal;

        rndList = new ArrayList<>();
        mon = new UniqRunMonitor();
        skipper = new RndSkipper(mon);

        rndList.clear();
        iVal = 0x00_01_02_03;
        for (int ct = 0; ct < 64; ct++) {
            rndList.add(iVal);
            iVal += 0x04_04_04_04;
        }
        rnd = new SeqRepeater(rndList);

        assertEquals(8, skipper.skip(rnd));

        mon = new UniqRunMonitor(256);
        skipper = new RndSkipper(mon);
        assertEquals(64, skipper.skip(rnd));

        mon = new UniqRunMonitor(3);
        skipper = new RndSkipper(mon);

        rndList.clear();
        rndList.addAll(Arrays.asList(0x00_00_00_01, 0x03_00_00_00));
        rnd = new SeqRepeater(rndList);
        assertEquals(2, skipper.skip(rnd));

        rndList.clear();
        rndList.addAll(Arrays.asList(0x00_00_00_00, 0x00_00_00_01, 0x03_00_00_00));
        rnd = new SeqRepeater(rndList);
        assertEquals(3, skipper.skip(rnd));

        mon = new UniqRunMonitor(5);
        skipper = new RndSkipper(mon);

        rndList.clear();
        rndList.addAll(Arrays.asList(0x01_02_03_04, 0x05_00_00_00));
        rnd = new SeqRepeater(rndList);
        assertEquals(2, skipper.skip(rnd));

        rndList.clear();
        rndList.addAll(Arrays.asList(0x01_01_02_03, 0x04_05_00_00));
        rnd = new SeqRepeater(rndList);
        assertEquals(2, skipper.skip(rnd));

        rndList.clear();
        rndList.addAll(Arrays.asList(0x01_01_01_02, 0x03_04_05_00));
        rnd = new SeqRepeater(rndList);
        assertEquals(2, skipper.skip(rnd));

        rndList.clear();
        rndList.addAll(Arrays.asList(0x01_01_01_01, 0x02_03_04_05));
        rnd = new SeqRepeater(rndList);
        assertEquals(2, skipper.skip(rnd));

        try {
            mon = new UniqRunMonitor(0);
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        try {
            mon = new UniqRunMonitor(257);
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        assert mon == mon;

        return;
    }

}
