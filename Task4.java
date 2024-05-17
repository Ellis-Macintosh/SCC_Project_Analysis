import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Task4 {

    public static void task_4(String filename) {
        String firstWord = null;
        /* initalise firstword, if its not an empty set split the line into 
        words using spaces and assign first word to firstWord*/
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String firstLine = br.readLine();
            if (firstLine != null) {
                firstWord = firstLine.split(" ")[0];
            }
            // create followers map
            Map<String, Set<String>> followersMap = new HashMap<>();

            // Parse input and populate followersMap
            String line;
            while ((line = br.readLine()) != null) {  
                // split each line into words(names) add followers to set of followers for that person in followersmap
                String[] names = line.split(" ");
                String follower = names[0];
                for (int i = 1; i < names.length; i++) {
                    followersMap.computeIfAbsent(names[i], k -> new HashSet<>()).add(follower);
                }
            }

            // Retrive set of followers for firstword(firstdegree), if no followers empty set
            Set<String> firstFollowers = followersMap.getOrDefault(firstWord, Collections.emptySet());

            // Calculate the count of unique people at two degrees of separation
            // Iterates through each follower of first word, retrieve followers of followers (second degree)
            Set<String> secondDegreeFollowers = new HashSet<>();
            for (String follower : firstFollowers) {
                Set<String> followersOfFollower = followersMap.getOrDefault(follower, Collections.emptySet());
                for (String secondFollower : followersOfFollower) {
                    if (!firstFollowers.contains(secondFollower) && !secondFollower.equals(firstWord)) { // Exclude first-degree followers and self
                        secondDegreeFollowers.add(secondFollower);
                    }
                }
            }

            System.out.println("Task 4 Number of people with degree seperation of 2: "  + secondDegreeFollowers.size());
        } catch (IOException ex) {
            System.err.println("Error reading the file: " + ex.getMessage());
        }
    }
}