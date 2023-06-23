import java.util.*;

public class Autocorrector {

    // Holds frequencies of every possible word.
    private final HashtableMap<String, Integer> wordFreqs;

    /**
     * Create a new Autocorrector, based on the supplied map of word frequencies.
     */
    public Autocorrector(HashtableMap<String, Integer> wordFrequencies) {
        wordFreqs = wordFrequencies;
    }

    /**
     * Returns the most frequent word in the wordFreqs map that has inputString as a prefix.
     * If no string in the wordFreq map starts with this string, return null.
     */
    public String getBestAutocomplete(String inputString) {
        // Create a set with all the keys in wordFreqs.
        Set<String> words = wordFreqs.keySet();
        // Initialize the max frequency value (max) the best Autocomplete value (bestAutocomplete)
        // And a boolean to see if we encounter a word that inputString is a prefix of.
        int max = 0;
        String bestAutocomplete = "";
        boolean foundWord = false;
        // Iterate through the strings of the set seeing if inputString is a prefix of the string.
        for (String i: words) {
            if (i.startsWith(inputString)) {
                int freq = wordFreqs.get(i);
                // If we find a word that has inputString as a prefix check if it appears more frequently
                // than the current max frequency and if so replace the max frequency value and best autocomplete value.
                if (freq > max) {
                    max = freq;
                    bestAutocomplete = i;
                    foundWord = true;
                }
            }
        }
        if (foundWord == false) {
            return null;
        }
        else {
            return bestAutocomplete;
        }
    }

    /**
     * Return the set of possible words that are *both* an edit distance of 1 away from the inputString,
     * *and* are contained in the dictionary (wordFreq).
     */
    public Set<String> getBestAutocorrect(String inputString) {
        Set<String> edits = editDistance1(inputString);
        edits.retainAll(wordFreqs.keySet());
        return edits;
    }

    /**
     * Returns the "best suggestions" for an inputString, based on both the most likely autocompletion,
     * and the set of possible autocorrections.  The suggestions are in decreasing sorted order of frequency.
     */
    public List<String> getBestSuggestions(String inputString) {
        // Store autocomplete and autocorrect of inputString as variables.
        String autoComp = getBestAutocomplete(inputString);
        Set<String> autoCorr = getBestAutocorrect(inputString);

        // Initialize an empty ArrayList. Add autocorrect and autocomplete (if applicable) values to the ArrayList.
        ArrayList<String> finalResult = new ArrayList<>();
        if (autoComp != null && !autoCorr.contains(autoComp)) {
            finalResult.add(autoComp);
        }
        finalResult.addAll(autoCorr);

        
        WordByFrequencyComparator comp = new WordByFrequencyComparator();
        Quicksort.quicksort(finalResult, comp);

        return finalResult;
    }

    /**
     * Returns the set of possible strings that have an edit distance of 1 from string s.
     */
    private static Set<String> editDistance1(String s) {
        HashSet<String[]> splits = new HashSet<>();
        for (int x = 0; x < s.length() + 1; x++) {
            splits.add(new String[] { s.substring(0, x), s.substring(x) });
        }

        HashSet<String> edits = new HashSet<>();

        // deletions
        for (String[] splitString : splits) {
            String L = splitString[0], R = splitString[1];

            // deletion
            if (!R.equals(""))
                edits.add(L + R.substring(1));

            // transposition
            if (R.length() > 1)
                edits.add(L + R.charAt(1) + R.charAt(0)+ R.substring(2));

            String alphabet = "abcdefghijklmnopqrstuvwxyz";

            // replace
            if (!R.equals("")) {
                for (char ch : alphabet.toCharArray()) {
                    edits.add(L + ch + R.substring(1));
                }
            }

            // insert
            for (char ch : alphabet.toCharArray())
                edits.add(L + ch + R);
        }

        return edits;
    }

    /**
     * This comparator compares two strings according to their frequency in the wordFreq map.
     * The string that appears more frequently should be "less than" the other string.
     * This will sort the more common strings earlier in the list.
     * If two words have the same frequency, compare them alphabetically.
     */
    class WordByFrequencyComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            // Get frequencies
            int s1Freq = wordFreqs.get(s1);
            int s2Freq = wordFreqs.get(s2);
            // If the first string appears more frequently return -1
            if (s1Freq > s2Freq) {
                return -1;
            }
            // If the second one does return 1.
            else if (s1Freq < s2Freq) {
                return 1;
            }
            // Otherwise compare alphabetically.
            else {
                return s1.compareTo(s2);
            }
        }
    }
}
