package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static int syllables;
    public static int polysyllables;
    private static int words;
    private static int sentences;
    private static int characters;
    private static int ARIAge;
    private static int FKAge;
    private static int SMOGAge;
    private static int CLAge;
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        String fileName = "";
        if (args.length > 0) {
            fileName = args[0];
        } else {
            System.out.println("No filename arg found");
        }

        File file = new File(fileName);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                words += getWords(line);
                sentences += getSentences(line);
                characters += getCharacters(line);
                syllables += getSyllables(line);
                polysyllables += getPolysyllables(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + fileName);
        }

        printInfo();
        getScoreCalcAS();
    }

    private static int getPolysyllables(String line) {
        Syllable syllable = new Syllable();
        int counter = 0;
        String[] array = line.toLowerCase().split("\\s");
        for (String word : array) {
            word = word.replaceAll("le", "a");
            word = word.replaceAll("[.!?]", "");
            word = word.replaceAll("o$", "");
            var s = syllable.countSyllables(word);
            if (s > 2) {
                counter++;
            }
        }
        return counter;
    }


    private static int getSyllables(String line) {
        Syllable syllable = new Syllable();
        int counter = 0;
        String[] array = line.toLowerCase().split("\\s");
        for (String word : array) {
            word = word.replaceAll("le", "a");
            word = word.replaceAll("[.!?]", "");
            word = word.replaceAll("o$", "");
            counter += syllable.countSyllables(word);
        }
        return counter;
    }

    private static void getScoreCalcAS() {
        String option = in.nextLine();
        switch (option) {
            case "ARI":
                System.out.println(getARI());
                break;
            case "FK":
                System.out.println(getFK());
                break;
            case "SMOG":
                System.out.println(getSMOG());
                break;
            case "CL":
                System.out.println(getCL());
                break;
            case "all":
                System.out.println(getARI());
                System.out.println(getFK());
                System.out.println(getSMOG());
                System.out.println(getCL());
                System.out.println();
                System.out.printf("This text should be understood in average by %.2f-year-olds.%n",getAvgAge());
                break;
        }
    }

    private static String getCL() {
        final double l = 100. * characters / words;
        final double s = 100. * sentences / words;
        double score = 0.0588 * l - 0.296 * s - 15.8;
        CLAge = Integer.parseInt(getAge(score));
        return String.format("Coleman–Liau index: %.02f(about %s-year-olds).", score, getAge(score));
    }

    private static String getFK() {
        double score = 0.39 * words / sentences + 11.8 * syllables / words - 15.59;
        FKAge = Integer.parseInt(getAge(score));
        return String.format("Flesch–Kincaid readability tests: %.2f (about %s-year-olds).", score, getAge(score));
    }

    private static String getSMOG() {
        double score = 1.043 * Math.sqrt((float) polysyllables * 30 / sentences) + 3.1291;
        SMOGAge = Integer.parseInt(getAge(score));
        return String.format("Simple Measure of Gobbledygook: %.2f (about %s-year-olds).", score, getAge(score));
    }

    private static String getARI() {
        double score = 4.71 * characters / words + 0.5 * words / sentences - 21.43;
        ARIAge = Integer.parseInt(getAge(score));
        return String.format("Automated Readability Index: %.2f (about %s-year-olds).", score, getAge(score));
    }

    static void printInfo() {
        System.out.println("Words: " + words + "\n" +
                "Sentences: " + sentences + "\n" +
                "Characters: " + characters + "\n" +
                "Syllables: " + syllables + "\n" +
                "Polysyllables: " + polysyllables + "\n" +
                "Enter the score you want to calculate (ARI, FK, SMOG, CL, all):");
    }


    private static int getWords(String line) {
        return line.split("\\s").length;
    }

    private static int getSentences(String line) {
        String[] text = line.split("[.?!]");
        return text.length;
    }

    private static int getCharacters(String line) {
        int count = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) != ' ') {
                count++;
            }
        }
        return count;
    }

    private static double getAvgAge() {
        var sum = ARIAge + FKAge + SMOGAge + CLAge;
        return (double) sum / 4;
    }

    private static String getAge(double score) {
        switch ((int) Math.round(score)) {
            case 1:
                return "6";
            case 2:
                return "7";
            case 3:
                return "9";
            case 4:
                return "10";
            case 5:
                return "11";
            case 6:
                return "12";
            case 7:
                return "13";
            case 8:
                return "14";
            case 9:
                return "15";
            case 10:
                return "16";
            case 11:
                return "17";
            case 12:
                return "18";
            case 13:
            case 14:
                return "24";

        }
        return "-1";
    }
}
