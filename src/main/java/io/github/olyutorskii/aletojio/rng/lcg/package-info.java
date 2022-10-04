/*
 * License : The MIT License
 * Copyright(c) 2022 olyutorskii
 */

/**
 * Linear congruential generator(LCG) implementations.
 *
 * <p>LCG is a commonly used random number generator in the past.
 * {@link java.util.Random} is also a kind of LCG.
 *
 * <p>Recurrence relation sequences : {@code X(n+1) = (X(n) * Mul + Inc) mod Mod}
 *
 * <ul>
 * <li>Mul : Multiplier
 * <li>Inc : Increment
 * <li>Mod : Modulus
 * </ul>
 *
 * <p>Provides compatibility with well-known random number generators of the past.
 *
 * <ul>
 * <li>RANDU :   IBM Scientific Subroutine Library for IBM System/360
 * <li>MINSTD0 : C++11's minstd_rand0
 * <li>MINSTD :  C++11's minstd_rand
 * <li>GLIBC :   glibc rand()
 * <li>LRAND48 : glibc lrand48()
 * <li>MRAND48 : glibc mrand48()
 * </ul>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Linear_congruential_generator">
 * Linear congruential generator (Wikipedia)
 * </a>
 */

package io.github.olyutorskii.aletojio.rng.lcg;

/* EOF */
