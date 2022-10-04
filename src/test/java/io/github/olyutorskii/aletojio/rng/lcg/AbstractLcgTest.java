/*
 */

package io.github.olyutorskii.aletojio.rng.lcg;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class AbstractLcgTest {

    public AbstractLcgTest() {
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
     * Test of calcModMask method, of class AbstractLcg.
     */
    @Test
    public void testCalcModMask() {
        System.out.println("calcModMask");

        assertEquals(0L, AbstractLcg.calcModMask(0b000));
        assertEquals(0L, AbstractLcg.calcModMask(0b001));
        assertEquals(0b1L, AbstractLcg.calcModMask(0b010));
        assertEquals(0L, AbstractLcg.calcModMask(0b011));
        assertEquals(0b011L, AbstractLcg.calcModMask(0b100));
        assertEquals(0L, AbstractLcg.calcModMask(0b101));
        assertEquals(0L, AbstractLcg.calcModMask(0b110));
        assertEquals(0L, AbstractLcg.calcModMask(0b111));
        assertEquals(0b111L, AbstractLcg.calcModMask(0b1000));

        assertEquals(0L, AbstractLcg.calcModMask(0x7fff_ffff_ffff_ffffL));
        assertEquals(0x7fff_ffff_ffff_ffffL, AbstractLcg.calcModMask(0x8000_0000_0000_0000L));
        assertEquals(0L, AbstractLcg.calcModMask(0x8000_0000_0000_0001L));

        assertEquals(0L, AbstractLcg.calcModMask(0xffff_ffff_ffff_ffffL));

        return;
    }

}
