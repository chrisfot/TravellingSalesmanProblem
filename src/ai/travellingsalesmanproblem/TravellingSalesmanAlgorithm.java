package ai.travellingsalesmanproblem;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Chris on 18/05/2016.
 */
public class TravellingSalesmanAlgorithm {

    private final double mutationRate  = 0.1; // 10%

    private ArrayList<Path> initialPopulation = new ArrayList<>();
    private ArrayList<Path> evolvedPopulation = new ArrayList<>();

    // Counters
    private int generatedChildren = 0;
    private int mutations = 0;
    private int generations = 0;



    private void initializePopulation(int populationSize) {
        for (int i = 0; i < populationSize; i++) {
            // Add new random path
            this.initialPopulation.add(new Path(true));
        }
    }

    private void evolvePopulation(int generations, boolean printPaths) {
        ArrayList<Path> population    = this.initialPopulation;
        ArrayList<Path> newPopulation = new ArrayList<>();

        newPopulation.addAll(population);

        for (int g = 0; g < generations; g++) {
            this.generations++;

            // Create children
            for (int q = 0; q < population.size(); q++) {
                Path [][] crossoverCandidates = new Path[2][5]; // 2x5
                Path [] parents = new Path[2], children;
                Path winner;

                // Pick candidate parents
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 5; j++) {
                        int random = (int) (Math.random() * newPopulation.size());
                        crossoverCandidates[i][j] = newPopulation.get(random);
                    }
                }

                // Select best candidates
                parents[0] = this.getShortestPath(new ArrayList<>(Arrays.asList(crossoverCandidates[0])));
                parents[1] = this.getShortestPath(new ArrayList<>(Arrays.asList(crossoverCandidates[1])));
                children   = this.crossover(parents[0], parents[1]);

                Path [] selectionCandidates = {parents[0], parents[1], children[0], children[1]};

                winner = this.getShortestPath(new ArrayList<>(Arrays.asList(selectionCandidates)));

                int replacementIndex = newPopulation.indexOf(parents[(int) (Math.random() * parents.length)]);
                newPopulation.set(replacementIndex, winner);

                if (printPaths) {
                    for (Path candidate : selectionCandidates) {
                        System.out.println("Candidate  : " + candidate);
                    }
                    System.out.println("[!] Winner : " + winner);
                }
            }
        }

        this.evolvedPopulation = newPopulation;
    }

    private Path [] crossover(Path parent1, Path parent2) {
        Path [] parents  = {parent1, parent2};
        Path [] children = {new Path(false), new Path(false)};

        int r1 = (int) (Math.random() * parents[0].size());
        int r2 = (int) (Math.random() * parents[0].size());

        // Each child inherits specific properties (cities) from one of the parents.

        for (int i = Math.min(r1, r2); i <= Math.max(r1, r2); i++) {
            children[0].setCity(i, parents[0].getCity(i));
            children[1].setCity(i, parents[1].getCity(i));
        }

        // Complete the children's empty spots with the other parent's properties.

        for (int c = 0, p = 1; c < children.length; c++, p--) { // For each child
            this.generatedChildren++;
            for (int i = 0; i < parents[p].size(); i++) { // For each position
                if (!children[c].contains(parents[p].getCity(i))) { // If doesn't contain a city
                    for (int j = 0; j < parents[p].size(); j++) { // Find an empty spot (child & parents will have the same size)
                        if (children[c].getCity(j) == null) {
                            children[c].setCity(j, parents[p].getCity(i));
                            break;
                        }
                    }
                }
            }

            // Mutation
            children[c] = this.mutate(children[c]);
        }

        return children;
    }

    private Path mutate(Path path) {
        if (Math.random() < this.mutationRate) {
            this.mutations++;
            int r1 = (int) (Math.random() * path.size());
            int r2 = (int) (Math.random() * path.size());
            path.swapCities(r1, r2);
        }
        return path;
    }

    private Path getShortestPath(ArrayList<Path> population) {
        Path shortest = null;
        for (Path path : population) {
            if (shortest == null || path.getLength() <= shortest.getLength()) {
                shortest = path;
            }
        }
        return shortest;
    }

    private double getActualMutationRate() {
        return (this.mutations / (double) this.generatedChildren);
    }

    private void test() {
        Path a = new Path(true);
        Path b = new Path(true);
        Path [] children = this.crossover(a, b);

        System.out.println("Parent 1: " + a);
        System.out.println("Child 1 : " + children[0]);
        System.out.println("Parent 2: " + b);
        System.out.println("Child 2 : " + children[1]);
        System.out.println("----------------------------------------------");
    }

    public static void main(String [] args) {
        try {

            TravellingSalesmanAlgorithm tsa = new TravellingSalesmanAlgorithm();

            //tsa.test();

            tsa.initializePopulation(10);
            tsa.evolvePopulation(5, false);

            System.out.println("Population size (initial/evolved) : " + tsa.initialPopulation.size() + " / " + tsa.evolvedPopulation.size());
            System.out.println("Generations : " + tsa.generations);
            System.out.println("Mutation rate (settings/actual) : " + (tsa.mutationRate * 100) + "% / " + (tsa.getActualMutationRate() * 100) + "%");
            System.out.println("");
            System.out.println("Shortest path in initial population : " + tsa.getShortestPath(tsa.initialPopulation));
            System.out.println("Shortest path in evolved population : " + tsa.getShortestPath(tsa.evolvedPopulation));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
