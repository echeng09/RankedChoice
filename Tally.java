package RankedChoice;

import java.util.ArrayList;
import java.util.Arrays;

public class Tally {
    //attributes
    //candidates
    private static final String[] cands = {"A", "B", "C", "D"};
    //list of ballots
    private ArrayList<Ballot> ballots;
    //count current votes during a round
    private int[] counter;
    //tracks eliminated candidates
    private boolean[] eliminated;
    //counts which round it is
    private int roundCount;

    //constructor
    public Tally(ArrayList<Ballot> ballots) {
        this.ballots = ballots;
        this.counter = new int[cands.length];
        this.eliminated = new boolean[cands.length];
        this.roundCount = 0;
    }

    //countNext() counts the next round of votes
    public void countNext() {
        Arrays.fill(counter, 0); // Reset counter using the fill (makes all parts 0)

        // Tally highest ranked vote for each ballot
        for (Ballot b : ballots) {
            String[] votes = b.getVoteList();
            // Go through votes
            for (String v : votes) {
                // Find the index of the candidate with rank
                int idx = -1;
                for (int i = 0; i < cands.length; i++) {
                    if (cands[i].equals(v)) {
                        idx = i;
                        break;
                    }
                }
                // Only count highest ranked valid vote (if idx is valid and not eliminated yet)
                if (idx != -1 && !(eliminated[idx])) {
                    counter[idx]++;
                    break;
                }
            }
        }

        roundCount++;

        // If no majority, eliminate the lowest
        if (!isMajority()) {
            // Initialize minVotes to a value that is larger than any possible count
            int minVotes = counter.length + 1;
            int minIndex = -1;

            // Find the first valid candidate (that is not eliminated)
            for (int i = 0; i < counter.length; i++) {
                if (!eliminated[i] && counter[i] < minVotes) {
                    minVotes = counter[i];
                    minIndex = i;
                }
            }

            // Eliminate the candidate with the fewest votes
            eliminated[minIndex] = true;
        }
    }

    //isMajority() checks if anyone has over 50% of current votes
    public boolean isMajority() {
        int totalVotes = 0;
        int maxVotes = 0;
        //add votes to find highest
        for (int i = 0; i < counter.length; i++) {
            if (!eliminated[i]) {
                totalVotes += counter[i];
                if (counter[i] > maxVotes) {
                    maxVotes = counter[i];
                }
            }
        }
        //return if it is 50% or over
        return maxVotes > (totalVotes / 2);
    }

    //toString() display winner, rounds, and percent or says not counted yet
    @Override
    public String toString() {
        //the values we need
        int totalVotes = 0;
        int maxVotes = 0;
        int winnerIndex = -1;

        //find the total votes and max votes
        for (int i = 0; i < counter.length; i++) {
            if (!eliminated[i]) {
                totalVotes += counter[i];
                if (counter[i] > maxVotes) {
                    maxVotes = counter[i];
                    winnerIndex = i;
                }
            }
        }

        //if no votes counted yet
        if (maxVotes == 0) {
            return "Votes have not been counted yet.";
        }

        //get the winner candidate
        String winner = cands[winnerIndex];
        int percentage = (int) ((100.0 * maxVotes) / totalVotes); //cast to int for whole number percentage so it doesnt print the decimal points

        //return the result /n to put in a new line
        return "Winner: " + winner + 
            "\nRounds: " + roundCount + 
            "\nPercent: " + percentage + "%";
    }

}