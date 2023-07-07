import java.io.InputStream;
import java.util.*;

public class SentimentAnalysis {
    public static void main(String[] args) {
        final boolean PRINT_TREES = false;  // whether or not to print extra info about the maps.

        BSTMap<String, Integer> wordFreqs = new BSTMap<String, Integer>();
        BSTMap<String, Integer> wordTotalScores = new BSTMap<String, Integer>();
        Set<String> stopwords = new TreeSet<String>();

        System.out.print("Enter filename: ");
        try (Scanner scan = new Scanner(System.in)) {
            String filename = scan.nextLine();

            processFile(filename, wordFreqs, wordTotalScores);

            System.out.println("Number of words is: " + wordFreqs.size());
            System.out.println("Height of the tree is: " + wordFreqs.height());

            if (PRINT_TREES)
            {
                System.out.println("Preorder:  " + wordFreqs.preorderKeys());
                System.out.println("Inorder:   " + wordFreqs.inorderKeys());
                System.out.println("Postorder: " + wordFreqs.postorderKeys());
                printFreqsAndScores(wordFreqs, wordTotalScores);
            }

            removeStopWords(wordFreqs, wordTotalScores, stopwords);

            System.out.println("After removing stopwords:");
            System.out.println("Number of words is: " + wordFreqs.size());
            System.out.println("Height of the tree is: " + wordFreqs.height());

            if (PRINT_TREES)
            {
                System.out.println("Preorder:  " + wordFreqs.preorderKeys());
                System.out.println("Inorder:   " + wordFreqs.inorderKeys());
                System.out.println("Postorder: " + wordFreqs.postorderKeys());
                printFreqsAndScores(wordFreqs, wordTotalScores);
            }

            while (true)
            {
                System.out.print("\nEnter a new review to analyze: ");
                String line = scan.nextLine();
                if (line.equals("quit")) break;

                String[] words = line.split(" ");
                // Initialize overall sentiment double to act as the sentiment score of the review.
                Double overallSentiment = 0.0;
                // Initialize parsed word count which will be the number of words that are considered (none stop word/new word).
                int parsedWordCount = 0;

                // Iterate through the words one by one skipping if they are stopwords or new words.
                // If they are recognized calculate the average sentiment score of the word from the review and print out the 
                // word and its average sentiment score.
                for (String i : words) {
                    if (stopwords.contains(i)) {
                        System.out.println("Skipping " + i + " (stopword)");
                    }
                    else if (!wordFreqs.inorderKeys().contains(i)) {
                        System.out.println("Skipping " + i + " (never seen before)");
                    }
                    else {
                        Double avgSentiment;
                        parsedWordCount += 1;
                        int wordFreq = wordFreqs.get(i);
                        int wordScore = wordTotalScores.get(i);
                        avgSentiment = ((double) wordScore) / wordFreq;
                        overallSentiment += avgSentiment;
                        System.out.println("The average sentiment of " + i + " is " + avgSentiment);
                    }
                }
                // Calculate overall sentiment by dividing by the number of words that were considered (none stop word/new word).
                overallSentiment /= parsedWordCount;
                System.out.println("Sentiment score for this review is " + overallSentiment);
            }
        }
    }

    /**
     * Read the file specified to add proper items to the word frequencies and word scores maps.
     */
    private static void processFile(String filename, BSTMap<String, Integer> wordFreqs, BSTMap<String, Integer> wordTotalScores) {
        InputStream is = SentimentAnalysis.class.getResourceAsStream(filename);
        if (is == null) {
            System.err.println("Bad filename: " + filename);
            System.exit(1);
        }
        Scanner scan = new Scanner(is);

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] words = line.split(" ");
            // Parse the score of the review.
            int score = Integer.parseInt(words[0]);
            // Iterate through the words of the review.
            for (int i = 1; i < words.length; i ++) {
                // If the word is new, add it to the wordFreqs map and set score of 1
                // and set the total score of the word to the score of the movie.
                if (!wordFreqs.containsKey(words[i])) {
                    wordFreqs.put(words[i], 1);
                    wordTotalScores.put(words[i], score);
                }
                // Otherwise increment the frequency by 1 and then add the score of the
                // film to the total score for the word.
                else {
                    int count = wordFreqs.get(words[i]);
                    wordFreqs.put(words[i], count + 1);
                    int scoreCount = wordTotalScores.get(words[i]);
                    wordTotalScores.put(words[i], scoreCount + score);
                }
            }
        }
        scan.close();
    }

    /**
     * Print a table of the words found in the movie reviews, along with their frequencies and total scores.
     */
    private static void printFreqsAndScores(BSTMap<String, Integer> wordFreqs, BSTMap<String, Integer> wordTotalScores) {
        List<String> list = wordFreqs.inorderKeys();
        // Iterate through words in the list and print out the word, its frequency, and its total score.
        for (String i : list) {
            System.out.print("Word = " + i + "; ");
            System.out.print("Frequency = " + wordFreqs.get(i) + "; ");
            System.out.println("Scores = " + wordTotalScores.get(i) + ".");
        }
    }

    /**
     * Read the stopwords.txt file and add each word to the stopwords set.  Also remove each word from the
     * word frequencies and word scores maps.
     */
    private static void removeStopWords(BSTMap<String, Integer> wordFreqs, BSTMap<String, Integer> wordTotalScores, Set<String> stopwords) {
        InputStream is = SentimentAnalysis.class.getResourceAsStream("stopwords.txt");
        if (is == null) {
            System.err.println("Bad filename: " + "stopwords.txt");
            System.exit(1);
        }
        Scanner scan = new Scanner(is);

        while (scan.hasNextLine()) {
            String word = scan.nextLine();

            // Add the word to the stop words list and remove it from the wordFreqs and wordTotalScores maps.
            stopwords.add(word);
            wordFreqs.remove(word);
            wordTotalScores.remove(word);
        }
        scan.close();
    }
}
