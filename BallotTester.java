package RankedChoice;

import java.util.ArrayList;

public class BallotTester {
    public static void main(String[] args) {
        //create 5 ballot objs

        // 4 ballots created with the 4 arg constructor
        Ballot b1 = new Ballot("A", "B", "C", "D");
        Ballot b2 = new Ballot("B", "A", "D", "C");
        Ballot b3 = new Ballot("C", "B", "A", "D");
        Ballot b4 = new Ballot("D", "C", "A", "B");

        //fifth ballot is no arg constructor
        System.out.println("Please cast your vote for Ballot 5:");
        Ballot b5 = new Ballot();
        b5.castVote();

        //add all ballots to an array list
        ArrayList<Ballot> ballots = new ArrayList<>();
        ballots.add(b1);
        ballots.add(b2);
        ballots.add(b3);
        ballots.add(b4);
        ballots.add(b5);

        //make a tally object and count votes
        Tally tally = new Tally(ballots);

        //count until there is a majority winner
        while (!tally.isMajority()) {
            tally.countNext();
        }

        //print out the final result
        System.out.println("Election Results:");
        System.out.println(tally);
    }
}
