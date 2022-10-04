/*
 * License : The MIT License
 * Copyright(c) 2022 olyutorskii
 */

/**
 * Bijection hash functions.
 *
 * <p>AKA Minimal perfect hash function.
 *
 * <p>If the input is a random sequence, the output is also a random sequence.
 * These functions are expected to destroy statistical correlations between sequence.
 *
 * <p>To achieve an ideal 32-bit hash function
 * (1-bit change in input, the output is statistically changed 16-bits),
 * a round consisting of S-box(8bitx4) and P-box(32bit) should be repeated 4 rounds.
 *
 * <p>These functions are useful for implementing Counter-based random number generator. (CBRNG)
 *
 * @see <a href="https://en.wikipedia.org/wiki/Confusion_and_diffusion">
 * Confusion_and_diffusion (Wikipedia)
 * </a>
 * @see <a href="https://en.wikipedia.org/wiki/Substitution%E2%80%93permutation_network">
 * Substitution-permutation network (SPN) (Wikipedia)
 * </a>
 * @see <a href="https://en.wikipedia.org/wiki/Counter-based_random_number_generator_(CBRNG)">
 * Counter-based random number generator (CBRNG) (Wikipedia)
 * </a>
 */

package io.github.olyutorskii.aletojio.bijection;

/* EOF */
