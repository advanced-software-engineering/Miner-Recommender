# ASE19

[Amman et. al Method-Call Recommendations from Implicit Developer Feedback](https://doi.org/10.1145/2593728.2593730)

[![Build Status](https://travis-ci.org/advanced-software-engineering/Miner-Recommender.svg?branch=master)](https://travis-ci.org/mustard123/ASE19)

## Miner

Run the `Miner.java` file in the `miner & recommender` repository.

The miner needs two command line arguments. First the context directory (path to the context files from kave-cc) and second the model directory where the mined data is persisted.

You can run the application with `ch.uzh.ifi.seal.ase19.miner.Miner C:\ase\Contexts\ C:\ase\Models\` (the last `\` is important).

> Note: You should set `-Xmx8g` as VM option, because the application is memory hungry.
