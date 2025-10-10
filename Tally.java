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
        Arrays.fill(counter, 0); //reset counter using the fill (makes all parts 0)

        //tally highest ranked vote for each ballot
        for (Ballot b: ballots) {
            String[] votes = b.getVoteList();
            //go through votes
            for (String v : votes) {
                // find the index of the candidate w/ rank
                int idx = -1;
                for (int i = 0; i < cands.length; i++) {
                    if (cands[i].equals(v)) {
                        idx = i;
                        break;
                    }
                }
                //only count highest ranked valid vote (if idx is invalid and not found or not eliminated yt)
                if (idx != -1 && !(eliminated[idx])) {
                    counter[idx]++;
                    break;
                }
            }
        }
        //round done
        roundCount++;

        //if no majority, eliminate lowest
        if (!isMajority()) {
            //not yet set
            int minVotes = -1;
            int minIndex = -1;

            //find first val to initialize (that is valid - sorry if this doesnt make sense)
            for (int i = 0; i < counter.length; i++) {
                //only if valid
                if (!eliminated[i]) {
                    minVotes = counter[i];
                    minIndex = i;
                    break;
                }
            }

            //comparing with the rest of the counter values
            for (int i = 0; i < counter.length; i++) {
                if (!eliminated[i] && (counter[i] < minVotes)) {
                    minVotes = counter[i];
                    minIndex = i;
                }
            }
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

        for (int i = 0; i < counter.length; i++) {
            if (!eliminated[i]) {
                totalVotes += counter[i];
                if (counter[i] > maxVotes) {
                    maxVotes = counter[i];
                    winnerIndex = i;
                }
            }
        }

        //if not set yet
        if (maxVotes == 0) {
            return "Votes have not been counted yet.";
        }

        String winner = cands[winnerIndex];
        double percent = 100.0 * maxVotes / totalVotes;

        return "Winner: " + winner +
               "\nRounds: " + roundCount +
               "\nPercent: " + percent;
    }
}