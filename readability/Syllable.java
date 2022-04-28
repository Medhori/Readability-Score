package readability;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Syllable {
    protected int countSyllables(String word) {
        int count = 0;
        word = word.toLowerCase();

        if (word.charAt(word.length() - 1) == 'e') {
            if (silente(word)) {
                String newword = word.substring(0, word.length() - 1);
                count = count + countit(newword);
            } else {
                count++;
            }
        } else {
            count = count + countit(word);
        }
        return count;
    }

    private int countit(String word) {
        int count = 0;
        Pattern splitter = Pattern.compile("[^aeiouy]*[aeiouy]+");
        Matcher m = splitter.matcher(word);

        while (m.find()) {
            count++;
        }
        return count;
    }

    private boolean silente(String word) {
        word = word.substring(0, word.length() - 1);

        Pattern yup = Pattern.compile("[aeiouy]");
        Matcher m = yup.matcher(word);

        if (m.find()) {

            return true;
        } else
            return false;
    }
}
