/*
 */

package io.github.olyutorskii.aletojio.rng.xorsft;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class XorShift64Test {

    public XorShift64Test() {
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
     * Test of nextInt64 method, of class XorShift64.
     */
    @Test
    public void testNextInt64() {
        System.out.println("nextInt64");

        XorShift64 instance;
        instance = new XorShift64();

        assertEquals(          1082269761L, instance.nextInt64());
        assertEquals( 1152992998833853505L, instance.nextInt64());
        assertEquals(-7269227409276787159L, instance.nextInt64());
        assertEquals( -768720241707614171L, instance.nextInt64());
        assertEquals(-8787613929710185883L, instance.nextInt64());

        instance.setSeed(1L);
        assertEquals(          1082269761L, instance.nextInt64());

        return;
    }

    /**
     * Test of setSeed method, of class XorShift64.
     */
    @Test
    public void testSetSeed() {
        System.out.println("setSeed");

        XorShift64 instance;
        instance = new XorShift64();

        try {
            instance.setSeed(0);
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        return;
    }

}
