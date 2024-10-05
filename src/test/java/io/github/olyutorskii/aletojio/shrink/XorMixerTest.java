/*
 */

package io.github.olyutorskii.aletojio.shrink;

import io.github.olyutorskii.aletojio.rng.RndInt32;
import io.github.olyutorskii.aletojio.rng.dull.SeqRepeater;
import java.util.Collection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class XorMixerTest {

    public XorMixerTest() {
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
     * Test of nextInt32 method, of class XorMixer.
     */
    @Test
    public void testNextInt32() {
        System.out.println("nextInt32");

        XorMixer instance;

        RndInt32 r1;
        RndInt32 r2;
        RndInt32 r3;

        r1 = new SeqRepeater(0b1111);
        r2 = new SeqRepeater(0b0011);
        r3 = new SeqRepeater(0b0101);

        instance = new XorMixer(r1, r2, r3);

        assertEquals(0b1001, instance.nextInt32());

        try {
            instance = new XorMixer((Collection<RndInt32>)null);
            fail();
        } catch(NullPointerException e) {
            assert true;
        }

        try {
            instance = new XorMixer((RndInt32[])null);
            fail();
        } catch(NullPointerException e) {
            assert true;
        }

        try {
            instance = new XorMixer(r1, null, r3);
            fail();
        } catch(NullPointerException e) {
            assert true;
        }

        try {
            instance = new XorMixer();
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        assert instance == instance;

        return;
    }

}
