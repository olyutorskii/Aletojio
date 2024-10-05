/*
 */

package io.github.olyutorskii.aletojio.shrink;

import io.github.olyutorskii.aletojio.rng.RndInt32;
import io.github.olyutorskii.aletojio.rng.dull.SeqRepeater;
import io.github.olyutorskii.aletojio.rng.dull.StepSequence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class VnExtractorTest {

    public VnExtractorTest() {
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
     * Test of nextInt32 method, of class VnExtractor.
     */
    @Test
    public void testNextInt32() {
        System.out.println("nextInt32");

        RndInt32 rnd;
        rnd = new StepSequence(0, 1);
        rnd = new VnExtractor(rnd);

        int[] expected = {
            0x42dd0804,
            0x5342de96,
            0xf7f742dd,
            0x08045342,
            0x01000824,
            0x64045352,
            0x2a6ced4d,
            0x08045342,
            0xde96f7f7,
            0xa5a44cb5,
            0x7696f7f6,
            0x6efdffdf,
            0xde96f7f7,
            0x42dd0804,
            0x5342de96,
            0xf7f742dd,
            0x08045342,
            0x01000824,
            0x64045352,
            0x2a6ced4d,
            0x08045342,
            0x01000824,
            0x64040020,
            0x0010110c,
            0x40082464,
            0x8852331c,
            0xc9190100,
            0x08246404,
            0x53522a6c,
            0xed4d48a9,
        };

        for (int ct = 0; ct < 30; ct++) {
            assertEquals(expected[ct], rnd.nextInt32());
        }

        int[] iArr;
        iArr = new int[]{
            0x569a65a9,
            0xa9659a56,
            0x556699aa,
            0x695a96a5,
        };
        rnd = new SeqRepeater(iArr);
        rnd = new VnExtractor(rnd);

        assertEquals(0x1b4ee4b1, rnd.nextInt32());
        assertEquals(0x05af639c, rnd.nextInt32());

        try {
            rnd = new VnExtractor(null);
            fail();
            assert rnd != null;
        } catch (NullPointerException e) {
            assert true;
        }

        return;
    }

}
