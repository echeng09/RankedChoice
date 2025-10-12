package RankedChoice;
import java.util.Arrays; //to get user input
import java.util.Scanner; //to print out last part


public class Ballot {
    //attributes
    //candidate array
    private static final String[] cands = {"A", "B", "C", "D"};
    //votes array
    private String[] votes;

    //constructors
    //no input constructor
    public Ballot() {
        votes = new String[4];
    }

    //constructor with strings that get added to the votes array
    public Ballot(String v1, String v2, String v3, String v4) {
        votes = new String[4];
        votes[0] = v1;
        votes[1] = v2;
        votes[2] = v3;
        votes[3] = v4;
    }

    //castVote()
    public void castVote() {
        //use the scanner thing
        Scanner scanner = new Scanner(System.in);
        //counter for array
        int count = 0;
        //get voters votes for the 4
        while (count < 4) {
            System.out.print("Enter choice #" + (count + 1) + " (A, B, C, D): ");
            String input = scanner.nextLine().toUpperCase();

            //check if is an actual candidate
            boolean isValid = false;
            for (String c : cands) {
                if (c.equals(input)) {
                    isValid = true;
                    break;
                }
            }

            if (!isValid) {
                System.out.println("Invalid candidate choice. Please enter either A, B, C, or D.");
                continue;
            }

            //check if multiple times
            boolean alreadyChosen = false;
            for (String v: votes) {
                if (input.equals(v)) {
                    alreadyChosen = true;
                    break;
                }
            }

            //if chosen already, give message and redo, ortherwise, increment counter and add to votes array
            if (alreadyChosen) {
                System.out.println("You already voted for that candidate. Please try again.");
            } else {
                votes[count] = input;
                count++;
            }
        }
    }

    //get vote list array
    public String[] getVoteList() {
        return votes;
    }

    //get the list of candidates
    public static String[] getCandidateList() {
        return cands;
    }

    //to string (print out) use arrays to string to get rid of brackets (not used)
    public String toString() {
        return Arrays.toString(votes);
    }

}
