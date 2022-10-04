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
public class LcgRndInt32Test {

    public LcgRndInt32Test() {
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
