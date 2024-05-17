import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Task5 {

    private HashSet<String> uniquePeople = new HashSet<>();
    private ArrayList<String> allNames = new ArrayList<>();

    public void loadNames(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                uniquePeople.add(parts[0]); // add account holder
                Collections.addAll(allNames, parts); // add all names to allNames
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public int medianValue() {
        int followerCount;
        ArrayList<Integer> followerCounts = new ArrayList<>();
        int median;
        int median_index;

        // Find out how many followers each person has and add it to an array
        for (String account : uniquePeople) { // for each username in uniquePeople
            followerCount = 0;
            for (String follower : allNames) { // check if it has the account name
                if (follower.equals(account)) { // increment follower count for every instance of the name
                    followerCount++;
                }
            }
            followerCount--; // removes 1 so their account isn't included
            followerCounts.add(followerCount);
        }

        Collections.sort(followerCounts);

        // Work out median value
        // if even, median is average of middle 2 elements
        // if odd, median is middle value

        int length = followerCounts.size();

        if (length % 2 == 0) { // if even
            median_index = Math.floorDiv(length, 2);
            median = (followerCounts.get(median_index) + followerCounts.get(median_index - 1)) / 2;
        } else { // if odd median is middle element
            median_index = Math.floorDiv(length, 2);
            median = followerCounts.get(median_index);
        }

        System.out.println("Task 5 Median: " + median);

        return median;
    }
}

