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
public class LcgRndInt32Test {

    public LcgRndInt32Test() {
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
     * Test of constructor method, of class AbstractLcg.
     */
    @Test
    public void testConstructor() {
        System.out.println("AbstractLcg");

        LcgRndInt32 r32;
        r32 = new LcgRndInt32(1, 0, 2);
        r32.nextInt32();

        try {
            r32 = new LcgRndInt32(0, 0, 2);
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        try {
            r32 = new LcgRndInt32(1, -1, 2);
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        try {
            r32 = new LcgRndInt32(1, 0, 1);
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        r32.hashCode();

        return;
    }

    /**
     * Test of nextSeed method, of class AbstractLcg.
     */
    @Test
    public void testNextSeed() {
        System.out.println("nextSeed");

        LcgRndInt32 r32;
        r32 = new LcgRndInt32(2, 2, 3);
        r32.setSeed(Long.MAX_VALUE - 10L);

        try {
            r32.nextInt32();
            fail();
        } catch(IllegalStateException e) {
            assert true;
        }

        return;
    }

}
