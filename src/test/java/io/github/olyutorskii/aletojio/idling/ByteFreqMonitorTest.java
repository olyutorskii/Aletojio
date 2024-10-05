/*
 */

package io.github.olyutorskii.aletojio.idling;

import io.github.olyutorskii.aletojio.rng.RndInt32;
import io.github.olyutorskii.aletojio.rng.dull.SeqRepeater;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class ByteFreqMonitorTest {

    public ByteFreqMonitorTest() {
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
     * Test of probeImpl method, of class ByteFreqMonitor.
     */
    @Test
    public void testProbeImpl() {
        System.out.println("probeImpl");

        RndInt32 rnd;
        ByteFreqMonitor mon;
        RndSkipper skipper;
        List<Integer> rndList;
        int bVal0;
        int bVal1;
        int bVal2;
        int bVal3;

        rndList = new ArrayList<>();
        mon = new ByteFreqMonitor();
        skipper = new RndSkipper(mon);

        rndList.clear();
        bVal0 = 0;
        bVal1 = 0;
        bVal2 = 0;
        bVal3 = 0;
        for (int ct = 0; ct < 256; ct++) {
            int iVal = (bVal3 << 24) | (bVal2 << 16) | (bVal1 << 8) | bVal0;
            rndList.add(iVal);
            bVal0 = bVal0 + 1 & 0xff;
            bVal1 = bVal1 + 1 & 0xff;
            bVal2 = bVal2 + 1 & 0xff;
            bVal3 = bVal3 + 1 & 0xff;
        }
        rnd = new SeqRepeater(rndList);

        assertEquals(256, skipper.skip(rnd));

        rndList.clear();
        bVal0 = 0x73;
        bVal1 = 0x55;
        bVal2 = 0xeb;
        bVal3 = 0x04;
        for (int ct = 0; ct < 256; ct++) {
            int iVal = (bVal3 << 24) | (bVal2 << 16) | (bVal1 << 8) | bVal0;
            rndList.add(iVal);
            bVal0 = bVal0 + 3 & 0xff;
            bVal1 = bVal1 + 5 & 0xff;
            bVal2 = bVal2 + 7 & 0xff;
            bVal3 = bVal3 + 11 & 0xff;
        }
        rnd = new SeqRepeater(rndList);

        assertEquals(256, skipper.skip(rnd));

        rndList.clear();
        bVal0 = 0;
        bVal1 = 0;
        bVal2 = 0;
        bVal3 = 0;
        for (int ct = 0; ct < 256; ct++) {
            int iVal = (bVal3 << 24) | (bVal2 << 16) | (bVal1 << 8) | bVal0;
            rndList.add(iVal);
            bVal0 = bVal0 + 1 & 0xff;
            bVal1 = bVal1 + 1 & 0xff;
            bVal2 = bVal2 + 1 & 0xff;
            bVal3 = bVal3 + 1 & 0xff;
        }
        rndList.set(255, 0xff_ff_ff_fe);
        rndList.add(0x00_00_00_00);
        rndList.add(0x00_00_00_ff);

        rnd = new SeqRepeater(rndList);

        assertEquals(258, skipper.skip(rnd));

        return;
    }

}
