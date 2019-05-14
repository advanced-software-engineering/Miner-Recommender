# Recommendation System

[![Build Status](https://travis-ci.org/advanced-software-engineering/Miner-Recommender.svg?branch=master)](https://travis-ci.org/advanced-software-engineering/Miner-Recommender)

## Introduction

This project mimics the original implementation of the paper [Amman et. al Method-Call Recommendations from Implicit Developer Feedback](https://doi.org/10.1145/2593728.2593730).

The recommendation system learns from developer feedback through code completion events in the IDE. 

The recommender is splitted up in two parts:

- Miner & Recommender (this repository)
- Evaluation & Demo (repository [here](https://github.com/advanced-software-engineering/Evaluation-Examples))

Both repositories are under the Apache License 2.0.

## Installation

The Miner & Recommender project is built with Travis CI. The artifacts are deployed to a [Github repository](https://github.com/advanced-software-engineering/Maven-Repo).

To add the project as dependency the following snippets must be add to the Maven pom.xml file:

```xml
<dependencies>
    <dependency>
        <groupId>ch.uzh.ifi.seal</groupId>
        <artifactId>ase19</artifactId>
        <version>0.0.1</version>
    </dependency>
</dependencies>

<repositories>
    <repository>
        <id>Maven-Repo-mvn-repo</id>
        <url>https://raw.github.com/advanced-software-engineering/Maven-Repo/mvn-repo/</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
</repositories>
```

## Dataset

We used the KaVe's [context and event dataset](http://www.kave.cc/datasets). The Miner uses the context information to build inital recommendation models. The evaluation is done on the event dataset.

## Miner

Run the `Miner.java` file in the `miner & recommender` repository.

The miner needs two command line arguments. First the context directory (path to the context files from kave-cc) and second the model directory where the mined data is persisted.

You can run the application with `ch.uzh.ifi.seal.ase19.miner.Miner C:\ase\Contexts\ C:\ase\Models\` (the last `\` is important).

> Note: You should set `-Xmx8g` as VM option, because the application is memory hungry.

## Recommendation

TODO

## Example

Run the `Example.java` file in the `evaluation & example` repository. No command line arguments are needed. The same query is asked multiple times. After each request the model is manually updated and the prediction becomes more meaningful.

## Evaluation 

TODO

## Sample Model

