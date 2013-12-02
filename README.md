Genetic Algorithms for Traveling Salesperson Problem
=========

In this project, I implement a genetic algorithm for the NP-hard traveling sales person problem. This returns an approximate solution. The remainder of this README is as follows:

1. Introduction to genetic algorithms
2. Overview of the parameters used
3. Description of each source filer

You can read the term paper on this project HERE.

Genetic Algorithms
----
Genetic algorithms attempt to mimic real life evolution and are commonly used in artificial intelligence and optimization problems. Consider our traveling salesperson problem (TSP). An *individual* is a single solution to the TSP. A *population* consists of several *individuals*, all of which are different and need not be unique. Each individual is given a score based on a *fitness function*. In the TSP, the score is the length of the tour/path. The lower the score an individual has, the better of a solution it is.

Given our starting population, we evolve the population. This requires selecting two parents (from the population of individuals) and *crossing* them. We take some attributes from parent 1 and some from parent 2 to create a new child. This child is then scored and then placed back into the population. The more *fit* parents are more likely to reproduce and thus the next generation of children should be better off than the parents, that is, they are better solutions to the TSP.

We can introduce *mutations*, that is, random events that change each individual (with regards to the TSP, it swaps the order of two cities at random). We continue to evolve the population until our population becomes the same solution or we reach some threshold or time limit.

Parameters
----

The following parameters are located in `src/GeneticManager.java`
* Population Size: This is number of individuals in our population
* Number of Evolution Iterations:  Number of times to *advance* the population and create offspring/mutations
* Tournament Size: To create a child, we must have two parents. To select these two parents, we create a *tournament* consisting of `TOURNAMENT_SIZE` individuals. We then select the most fit individual from this tournament to become the parent.
* Mutation Rate: The probability that a given individual will incur a single mutation
* Clone Rate: The probability that a child will be an exact copy of one of its parents
* Elite Percent: The percent of the population to deem as *elite* individuals. Elite individuals have very high fitness scores (or low path costs)
* Elite Parent Rate: The probability that one of the parents (when creating a child) is elite

Source Files
----
* `src/Individual.java` contains the class information for an Individual.
* `src/Population.java` contains the class information for a Population.
* `src/GeneticManager.java` contains the main method and handles population management and evolution. It also reads the input file and selects the final, optimal solution.