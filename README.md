# Aletojio #

[![Java CI with Maven](https://github.com/olyutorskii/Aletojio/actions/workflows/maven.yml/badge.svg)](https://github.com/olyutorskii/Aletojio/actions/workflows/maven.yml)
[![CodeQL](https://github.com/olyutorskii/Aletojio/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/olyutorskii/Aletojio/actions/workflows/codeql-analysis.yml)

-----------------------------------------------------------------------

## What is Aletojio ? ##

* **Aletojio** is a Java library
that supports manipulation of random number and entropy.

* Aletojio provides compressor to enrich entropy for poor random numbers.

* Aletojio provides 32bit bijection hush functions(S-box & P-box)
 to realize Shannon's "Confusion and diffusion".

* Aletojio provides some classic and modern implementations of pseudo random number generators. (PRNG)

    * RANDU :   IBM Scientific Subroutine Library for IBM System/360
    * MINSTD0 : C++11's minstd_rand0
    * MINSTD :  C++11's minstd_rand
    * GLIBC :   glibc rand()
    * LRAND48 : glibc lrand48()
    * MRAND48 : glibc mrand48()
    * MWC : Multiply-with-carry
    * CMWC : Complementary-multiply-with-carry
    * Lagged Fibonacci
    * LFSR : Fibonacci Linear-feedback shift register
    * XorShift
    * Xoshiro256++
    * Xoshiro256**
    * Xoroshiro128++
    * Xoroshiro128**


## API document ##
* [API docs](https://olyutorskii.github.io/Aletojio/apidocs/index.html)
* [Maven report](https://olyutorskii.github.io/Aletojio/)


## How to build ##

* Aletojio needs to use [Maven 3.3.9+](https://maven.apache.org/)
and JDK 1.8+ to be built.

* Aletojio runtime does not depend on any other library at all.
Just compile Java sources under `src/main/java/`
if you don't use Maven nor JUnit nor resource-access.


## License ##

* Code is under [The MIT License][MIT].


## Project founder ##

* By [Olyutorskii](https://github.com/olyutorskii) at 2022


## Key technology ##

- [Pseudorandom number generator (Wikipedia)](https://en.wikipedia.org/wiki/Pseudorandom_number_generator)
- [Confusion_and_diffusion (Wikipedia)](https://en.wikipedia.org/wiki/Confusion_and_diffusion)
- [Open Java Development Kit][JDK]


[MIT]: https://opensource.org/licenses/MIT
[JDK]: https://openjdk.java.net/


--- EOF ---
