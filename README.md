Beanpiece: Java binding to [Google SentencePiece](https://github.com/google/sentencepiece)
=====

[![Build Status](https://api.travis-ci.org/dongjinleekr/beanpiece.svg)](https://travis-ci.org/dongjinleekr/beanpiece)
[![codecov.io](http://codecov.io/github/dongjinleekr/beanpiece/coverage.svg?branch=master)](http://codecov.io/github/dongjinleekr/beanpiece?branch=master)
[![Maven Central](https://img.shields.io/maven-central/v/com.dongjinlee/beanpiece.svg)](https://maven-badges.herokuapp.com/maven-central/com.dongjinlee/beanpiece)

SentencePiece is an unsupervised text tokenizer and detokenizer, developed by Google. Beanpiece provides a Java API to SentencePiece.

This project uses Adam Heinrich's awesome library, [native-utils](https://www.adamheinrich.com/blog/2012/12/how-to-load-native-jni-library-from-jar/), which is distributed under MIT License.

# Compatibility

As of version 0.2, this library provides API compatibility to [commit 1ff5904(Apr 1, 2018)](https://github.com/google/sentencepiece/commit/1ff5904e6606c2ece00d52fd419c9e199ce56596).

# How to build

The following tools and libraries are required to build Beanpiece:

* g++ compiler, which supports c++ 11.
* [SentencePiece](https://github.com/google/sentencepiece) library

Since there is no pre-built package of SentencePiece library, please download the project and install it following the guide provided.
