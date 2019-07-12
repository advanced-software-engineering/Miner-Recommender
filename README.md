# Recommendation System

[![Build Status](https://travis-ci.org/advanced-software-engineering/Miner-Recommender.svg?branch=master)](https://travis-ci.org/advanced-software-engineering/Miner-Recommender)

## Introduction

This project mimics the original implementation of the paper [Amman et. al Method-Call Recommendations from Implicit Developer Feedback](https://doi.org/10.1145/2593728.2593730).

The recommendation system learns from developer feedback through code completion events in the IDE. 

The recommender is splitted up in two parts:

- Miner & Recommender (this repository)
- Evaluation & Demo (repository [here](https://github.com/advanced-software-engineering/Evaluation-Examples))

The evaluation outputs a csv file which is analysed in python. The python code snippets are [here available](https://github.com/advanced-software-engineering/Evaluation_Python/).

All repositories are under the Apache License 2.0.

Our project is implemented in Java and we used git to coordinate the team development.

The recommender and miner project contains a test suite to verify the fundamental functionality. We have written 68 jUnit tests. To write short but powerful tests we used Mockito in combination with jUnit5. Especially the similarity calculation was tested extensively. We have line coverage of 75% (measured with the Intellij).

We didn't use a continuous inspection tool such as sonarqube. However we used Intellij internal analyse tool (under analyze -> inspect code) to improve the code quality.

Our project setup was the following:

![Final](https://user-images.githubusercontent.com/9574324/61124333-e4b36800-a4a6-11e9-89e6-ef70fd6ea274.png)

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

Our recommender implements the predefined [IMemberRecommender](https://github.com/kave-cc/java-cc-kave/blob/master/cc.kave.rsse.calls/src/main/java/cc/kave/rsse/calls/IMemberRecommender.java) interface. For querying we either need a Context  or a [query](https://github.com/advanced-software-engineering/Miner-Recommender/blob/master/src/main/java/ch/uzh/ifi/seal/ase19/core/models/Query.java) object. Additionally the recommender has a `persist` method to incremental update the model.

In the paper they extracted we following information for a completion context:
* receiver object type
* how the object entered the enclosing method
* the kind of statement or expression surrounding the completion point
* value type required for the final expression
* signature of the enclosing method and the super type

We are using the following information to calculate the similarity of two contexts:
* fully qualified name of the receiver object type
* how the object entered the enclosing method we differ between three options. Class (class or instance variable), method parameter and local (local variable in a method body).
* the kind of statement or expression surrounding the completion point. We differ between the following options: Method body, branching condition, loop, assignment, return statement, lambda and try.
* fully qualified name of the final value type
* we splitted the enclosing method signature in multiple sub-elements
    * fully qualified name of return type of the enclosing method
    * number of method parameters of the enclosing method
    * name and fully qualified name of the method parameters
    * fully qualified name of super type    
    
__Per default the similarity calculation is done with equal weights, but a developer can optionally set the weights as constructor argument__
    
## Example

Run the [`Example.java`](https://github.com/advanced-software-engineering/Evaluation-Examples/blob/master/src/main/java/ch/uzh/ifi/seal/ase19/examples/Example.java) file in the `evaluation & example` repository. No command line arguments are needed. The same query is asked multiple times. After each request the model is manually updated and the prediction becomes more meaningful.

## Evaluation 

__A replication of the original evaluation is not possible because they only briefly summarized some findings but no concrete statistics which we could compare. We used the provided dataset to design our own evaluation.__

Before the evaluation the recommendation models are built with the context dataset which contains over 175'000 completion events (see section miner). Afterwards, we used the event dataset to analyse the prediction quality. We can only recommend a method if we previously learend to receiver type. In the pre-processing we filtered out completion events of the event dataset for which no prediction is possible. So the evaluation was done with over 3'000 completion events.

We used the Top-K accuracy to analyse the prediction quality. It checks if under the top k elements the correct recommendation occurs.

### Basic evaluation with equally weighted attributes

![baseline](https://user-images.githubusercontent.com/9574324/58184195-edf73400-7cb0-11e9-88f0-3bd4f6272364.png)

The average prediction time takes is approximately 100ms.

However we can only predict a recommendation, if this receiver type was mined before. SO if a class or method was renamed a class was moved it is an issues for our approach.

### Similarity Weights Evaluation

The run the recommender with different weights. The results are summarized in the following plot:

![weight_change](https://user-images.githubusercontent.com/9574324/58183880-63aed000-7cb0-11e9-9835-b607c5b2dec8.png)

Additionally we did a point-biserial correlation analysis between the binary attribute (is the correct recommendation selected) and the similarity value of the attributes listed below (the similarity is a value between 0 and 1).

The correlation coefficient are summarized in this table (the assiciation is determined on [this guideline](https://statistics.laerd.com/statistical-guides/pearson-correlation-coefficient-statistical-guide.php)):

| Attribute     | Correlation coefficient        | association |
| ------------- |:-------------:|:-------------:|
| final object type      | 0.42 | positive medium association |
| object origin      | 0.20      | positive small association |
| surrounding expression | 0.11      | positive small association |
| enclosing method: return type | 0.13      | positive small association |
| enclosing method: number of parameters | 0.21      | positive small association |
| enclosing method: parameter types and names | 0.08      | positive very small association |
| enclosing method:super type | 0.12      | positive small association |

Based on assocation we decided to change the weights as follows:

| Attribute     | Weight        |
| ------------- |:-------------:|
| final object type       | 2.0 |
| object origin      | 1.0      |
| surrounding expression | 1.0      |
| enclosing method: return type | 1.0      |
| enclosing method: number of parameters | 1.0      |
| enclosing method: parameter types and names | 0.5      |
| enclosing method:super type | 1.0      |

We rerun the evaluation scripts and get the following results

![weighed_changed](https://user-images.githubusercontent.com/9574324/58184245-08311200-7cb1-11e9-9ee6-ec52d74c376f.png)

It is an improvement of +2% for the top 1 accuracy and +5% for the top 3 and top 5 accuracy.

However we think that an enhancement of the similarity calculation would be the better approach to  improve the recommendations. For example we could use the sequence of surrounding expressions instead of only the last one.

## Models

As the mining process takes several hours we provide the full mined models here.

https://drive.google.com/file/d/1_JFXkf_N_xuQfmv4VEjTvhWdpVhrv6F7/view?usp=sharing
