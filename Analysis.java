import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.*;

public class Analysis {
    public static String followMetrics;

    public static class FollowHighest {
        // constructor to initalise followHighest
        private String followHighest;

        public FollowHighest() {
            followHighest = null;
        }

        public void updateFollowHighest(String word) {
            // updates followHighest if the input word is smaller than `current follow highest
            if (followHighest == null || word.compareTo(followHighest) < 0) {
                followHighest = word;
            }
        }

        public String getFollowHighest() {
            // return to be printed 
            return followHighest;
        }
    }

    public static class GraphDensity {
        // pass in number of edges and number of nodes into densitycalculator
        public double calcGraphDensity(int numberOfEdges, int numberOfNodes) {
            //return density with given 
            return Math.round((double) numberOfEdges / (numberOfNodes * (numberOfNodes - 1)) * 1e8) / 1e8;
        }
    }
    public static String calcMostCommonWord(Map<String, Integer> wordCounts) {
        // keeps track of most common word 
        Map.Entry<String, Integer> maxEntry = null;
        // keeps track of highest count found
        int maxCount = 0;
    
        for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
            String word = entry.getKey();
            int count = entry.getValue();
            //update the entry if the current count is greater or if equal count then update to alphabeticaly first one
            if (maxEntry == null || count > maxCount || (count == maxCount && word.compareTo(maxEntry.getKey()) < 0)) {
                maxCount = count;
                maxEntry = entry;
            }
        }
    
        return maxEntry != null ? maxEntry.getKey() : null;
        // return most common word or null if empty
    }

    


    public static void main(String[] args) throws FileNotFoundException, IOException{
        // read the input txt file which is specified during run time as an argument 
        if (args.length != 1) {
            System.out.println("Usage: java ReadTextFile cc");
            System.exit(1);
        }

        String[] words = new String[0];
        int numberOfEdges = 0;
        int following = 0;
        String lineWithMaxWords = null;
        String followMetrics = null;
        

        Set<String> uniqueNames = new HashSet<>();
        Map<String, Integer> wordCounts = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            String line;
            // split the txt document into words by using the space inbetween
            while ((line = br.readLine()) != null) {
                words = line.split(" ");
                // keep track of total number of edges (word pairs), if more than one word increment numberofedges
                // add each word to the unique names set and updates its count in wordcounts
            if (words.length > 1) {
                    numberOfEdges += words.length - 1;
                }
                // maintain a set of unique 
                for (String word : words) {
                    uniqueNames.add(word);
                    wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                }
                if (words.length > 1) {
                    // updates following if current line has more words following the first word
                    int wordsAfterFirst = words.length - 1;
                    if (wordsAfterFirst > following) {
                        following = wordsAfterFirst;
                        // track line with the most words
                        lineWithMaxWords = line;
                        FollowHighest followMetricsObj = new FollowHighest();
                        followMetricsObj.updateFollowHighest(words[0]);
                        followMetrics = words[0];
                        // calcs the alphabetically smallest word (follow metrics)
                    } else if (wordsAfterFirst == following) {
                        String firstName = words[0];
                        if (firstName.compareTo(followMetrics) < 0) {
                            followMetrics = firstName;
                        }
                    }
                }
            }

            // decrease count for each unique name found
            for (String name : uniqueNames) {
                wordCounts.put(name, wordCounts.getOrDefault(name, 0) - 1);
            }                     

            String mostCommonWord = null;
            int maxCount = 0;
            // Find the most common word and its count                                     
            for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
                String word = entry.getKey();
                int count = entry.getValue();
                if (count > maxCount) {
                    maxCount = count;
                    mostCommonWord = word;
                    // if counts are equal then choose the alphabetically first count/word
                } else if (count == maxCount && word.compareTo(mostCommonWord) < 0) {
                    mostCommonWord = word;
                }
            }
        } catch (IOException ex) {
            System.err.println("Error reading the file: " + ex.getMessage());
        }

        // print out various tasks, and initalise instances of other tasks outside of Analysis file
        int numberOfNodes = uniqueNames.size();

        GraphDensity graphMetrics = new GraphDensity();
        double roundedDensity = graphMetrics.calcGraphDensity(numberOfEdges, numberOfNodes);
        System.out.println("Task 1 Graph Density: " + roundedDensity);

        String mostCommonWord = calcMostCommonWord(wordCounts);
        System.out.println("Task 2 Person with most followers (Alphabetically): " + mostCommonWord);

        System.out.println("Task 3 " + followMetrics + " follows the highest amount of people");

        Task4.task_4(args[0]);
        
        String filename = args[0];
        Task5 task = new Task5();
        task.loadNames(filename);
        task.medianValue();

    }
}