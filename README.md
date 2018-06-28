Beanpiece: A Java binding to [Google SentencePiece](https://github.com/google/sentencepiece)
=====

[![Build Status](https://api.travis-ci.org/dongjinleekr/beanpiece.svg)](https://travis-ci.org/dongjinleekr/beanpiece)
[![codecov.io](http://codecov.io/github/dongjinleekr/beanpiece/coverage.svg?branch=master)](http://codecov.io/github/dongjinleekr/beanpiece?branch=master)
[![Maven Central](https://img.shields.io/maven-central/v/com.dongjinlee/beanpiece.svg)](https://maven-badges.herokuapp.com/maven-central/com.dongjinlee/beanpiece)

SentencePiece is an unsupervised text tokenizer and detokenizer, developed by Google. Beanpiece provides a Java API to SentencePiece.

# Compatibility

As of version 0.2, this library provides API compatibility to [commit 1ff5904(Apr 1, 2018)](https://github.com/google/sentencepiece/commit/1ff5904e6606c2ece00d52fd419c9e199ce56596).

# How to build

The following tools are required to build Beanpiece:

- sbt
- g++ compiler, which supports c++ 11.

To build the project, just give:

```sh
sbt package
```

It will take all the tasks needed, from copying shared libraries from compiling, packaging the Java source code.

# Note for Windows/Mac Users

As of version 0.2, the project only contains `libsentencepiece.so` for Linux (amd64) only. Because of that, the built jar will not run on osx or windows - they will be added at 0.3.

Until then, please build the sentencepiece shared library by yourself and copy them into:

- windows: `/library/windows/[i386|amd64|ppc]`
- osx: `/library/windows/[i386|amd64|ppc]`

After then, you can build the project as described above.
