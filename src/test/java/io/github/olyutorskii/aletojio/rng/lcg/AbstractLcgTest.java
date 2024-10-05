/*
 */

package io.github.olyutorskii.aletojio.rng.lcg;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class AbstractLcgTest {

    public AbstractLcgTest() {
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
