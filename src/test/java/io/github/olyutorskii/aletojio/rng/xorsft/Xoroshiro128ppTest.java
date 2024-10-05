/*
 */

package io.github.olyutorskii.aletojio.rng.xorsft;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class Xoroshiro128ppTest {

    public Xoroshiro128ppTest() {
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
     * Test of nextInt64 method, of class Xoroshiro128pp.
     */
    @Test
    public void testNextInt64() {
        System.out.println("nextInt64");

        Xoroshiro128pp instance;
        instance = new Xoroshiro128pp();

        assertEquals(              131073L, instance.nextInt64());
        assertEquals(     598409205645317L, instance.nextInt64());
        assertEquals(  579350287391785545L, instance.nextInt64());
        assertEquals(-8281516684126485947L, instance.nextInt64());
        assertEquals(  543646121330869598L, instance.nextInt64());

        instance.setSeed(1L, 0L);
        assertEquals(              131073L, instance.nextInt64());

        return;
    }

    /**
     * Test of setSeed method, of class Xoroshiro128pp.
     */
    @Test
    public void testSetSeed() {
        System.out.println("setSeed");

        Xoroshiro128pp instance;
        instance = new Xoroshiro128pp();

        try {
            instance.setSeed(0L, 0L);
            fail();
        } catch(IllegalArgumentException e) {
            assert true;
        }

        return;
    }

}
