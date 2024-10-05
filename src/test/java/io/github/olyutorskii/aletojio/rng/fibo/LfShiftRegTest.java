/*
 */

package io.github.olyutorskii.aletojio.rng.fibo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class LfShiftRegTest {

    public LfShiftRegTest() {
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
     * Test of xorBitSeq method, of class LfShiftReg.
     */
    @Test
    public void testXorBitSeq() {
        System.out.println("xorBitSeq");

        assertEquals(0, LfShiftReg.xorBitSeq(0b0));
        assertEquals(0, LfShiftReg.xorBitSeq(-1));
        assertEquals(1, LfShiftReg.xorBitSeq(0b1));
        assertEquals(1, LfShiftReg.xorBitSeq(0b10));
        assertEquals(0, LfShiftReg.xorBitSeq(0b11));
        assertEquals(1, LfShiftReg.xorBitSeq(0b100));
        assertEquals(0, LfShiftReg.xorBitSeq(0b1010_1010_1010_1010));
        assertEquals(1, LfShiftReg.xorBitSeq(0b1010_1010_1010_1011));
        assertEquals(1, LfShiftReg.xorBitSeq(0b0010_1010_1010_1010));
        assertEquals(1, LfShiftReg.xorBitSeq(0b1010_1011_1010_1010));
        assertEquals(1, LfShiftReg.xorBitSeq(0b1010_1010_1110_1010));

        return;
    }

    /**
     * Test of taps2Mask method, of class LfShiftReg.
     */
    @Test
    public void testTaps2Mask() {
        System.out.println("taps2Mask");

        assertEquals(0b0, LfShiftReg.taps2Mask());
        assertEquals(0b1000_0000_0000_0000_0000_0000_0000_0000, LfShiftReg.taps2Mask(32));
        assertEquals(0b0100_0000_0000_0000_0000_0000_0000_0000, LfShiftReg.taps2Mask(31));
        assertEquals(0b0010_0000_0000_0000_0000_0000_0000_0000, LfShiftReg.taps2Mask(30));
        assertEquals(0b0000_0000_0000_0000_0000_0000_0000_0100, LfShiftReg.taps2Mask(3));
        assertEquals(0b0000_0000_0000_0000_0000_0000_0000_0010, LfShiftReg.taps2Mask(2));
        assertEquals(0b0000_0000_0000_0000_0000_0000_0000_0001, LfShiftReg.taps2Mask(1));
        assertEquals(0b0000_0000_0000_0000_0000_0000_0101_0101, LfShiftReg.taps2Mask(1, 3, 5, 7));

        try {
            LfShiftReg.taps2Mask(null);
            fail();
        } catch (NullPointerException e) {
            assert true;
        }

        try {
            LfShiftReg.taps2Mask(0);
            fail();
        } catch (IllegalArgumentException e) {
            assert true;
        }

        try {
            LfShiftReg.taps2Mask(33);
            fail();
        } catch (IllegalArgumentException e) {
            assert true;
        }

        return;
    }

    /**
     * Test of nextInt32 method, of class LfShiftReg.
     */
    @Test
    public void testNextInt32() {
        System.out.println("nextInt32");

        LfShiftReg lsr;

        lsr = new LfShiftReg();
        assertEquals(4198403, lsr.nextInt32());
        assertEquals(16789509, lsr.nextInt32());
        assertEquals(104923227, lsr.nextInt32());

        lsr = new LfShiftReg(32, 31);
        assertEquals(0b11, lsr.nextInt32());
        assertEquals(0b101, lsr.nextInt32());

        lsr = new LfShiftReg(32, 30);
        assertEquals(0b101, lsr.nextInt32());
        assertEquals(0b10001, lsr.nextInt32());

        lsr = new LfShiftReg(32, 1);
        assertEquals(0xffff_fffe, lsr.nextInt32());
        assertEquals(0xaaaa_aaab, lsr.nextInt32());


        return;
    }

    /**
     * Test of setSeed method, of class LfShiftReg.
     */
    @Test
    public void testSetSeed() {
        System.out.println("setSeed");

        LfShiftReg lsr;

        lsr = new LfShiftReg();
        assertEquals(4198403, lsr.nextInt32());
        assertEquals(16789509, lsr.nextInt32());
        assertEquals(104923227, lsr.nextInt32());
        lsr.setSeed(LfShiftReg.DEF_SEED);
        assertEquals(4198403, lsr.nextInt32());
        assertEquals(16789509, lsr.nextInt32());
        assertEquals(104923227, lsr.nextInt32());

        lsr.setSeed(-1);
        assertEquals(4190209, lsr.nextInt32());
        assertEquals(16773116, lsr.nextInt32());
        assertEquals(-37683255, lsr.nextInt32());
        lsr.setSeed(-1);
        assertEquals(4190209, lsr.nextInt32());
        assertEquals(16773116, lsr.nextInt32());
        assertEquals(-37683255, lsr.nextInt32());

        try {
            lsr.setSeed(0);
            fail();
        } catch (IllegalArgumentException e) {
            assert true;
        }

        return;
    }

    /**
     * Test of toString method, of class LfShiftReg.
     */
    @Test
    public void testToString() {
        System.out.println("toString");

        LfShiftReg lsr;

        lsr = new LfShiftReg();
        assertEquals("OOOo-oooo-oooo-oooo-oooo-ooOo-oooo-oooi", lsr.toString());

        lsr.nextInt32();
        assertEquals("OOOo-oooo-oioo-oooo-oooi-ooOo-oooo-ooii", lsr.toString());

        lsr.setSeed(0xaa55aa55);
        assertEquals("IOIo-ioio-oioi-oioi-ioio-ioIo-oioi-oioi", lsr.toString());

        return;
    }

}
