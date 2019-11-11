package net.h1ddengames.imagedownloader;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class HelperMethods {
    private static int lengthOfStrings = 10;
    private static boolean allowLetters = true;
    private static boolean allowNumbers = true;
    private static final List<String> femaleFirstNames = new ArrayList<>(
            Arrays.asList("Mary", "Patricia", "Linda", "Barbara", "Elizabeth",
                    "Jennifer", "Maria", "Susan", "Margaret", "Dorothy", "Lisa",
                    "Nancy", "Karen", "Betty", "Helen", "Sandra", "Donna", "Carol",
                    "Ruth", "Sharon", "Michelle", "Laura", "Sarah", "Cynthia", "Jessica"
    ));
    private static final List<String> maleFirstNames = new ArrayList<>(
            Arrays.asList("James", "John", "Robert", "Michael", "William",
                    "David", "Richard", "Charles", "Joseph", "Thomas", "Christopher",
                    "Daniel", "Paul", "Mark", "George", "Kenneth", "Steven", "Edward",
                    "Brian", "Ron", "Anthony", "Kevin", "Jason", "Matthew", "Gary"
    ));
    private static final List<String> lastNames = new ArrayList<>(
            Arrays.asList("Smith", "Johnson", "Williams", "Jones", "Brown",
                    "Black", "White", "Davis", "Miller", "Wilson", "Moore",
                    "Taylor", "Anderson", "Thomas", "Jackson", "Harris", "Martin", "Thompson",
                    "Garcia", "Martinez", "Robinson", "Clark", "Rodriguez", "Lewis", "Lee"
    ));

    static void generateDirectory(String filePath) {
        File directory = new File(filePath);
        if (!directory.exists()){
            directory.mkdir();
        } else { System.out.println("Already created the file."); }
    }

    static String generateFemaleFullName() {
        return new StringBuilder()
                .append(getRandomElementFromList(femaleFirstNames))
                .append(" ")
                .append(getRandomElementFromList(lastNames))
                .toString();
    }

    static String generateMaleFullName() {
        return new StringBuilder()
                .append(getRandomElementFromList(maleFirstNames))
                .append(" ")
                .append(getRandomElementFromList(lastNames))
                .toString();
    }

    static <T> T getRandomElementFromList(List<T> list) {
        return list.get(generateRandomNumberInRange(0, list.size() - 1));
    }

    static <T> T getRandomElementFromArray(T[] array) {
        return array[generateRandomNumberInRange(0, array.length - 1)];
    }

    static String convertStringArrayToString(String[] arr) {
        StringBuilder result = new StringBuilder();

        for(String s : arr ) {
            result.append("Downloaded: ").append(s).append("\n");
        }

        return result.toString();
    }

    static String convertCharacterArrayToString(Character[] arr) {
        StringBuilder result = new StringBuilder();

        for(Character s : arr ) {
            result.append("Downloaded: ").append(s).append("\n");
        }

        return result.toString();
    }

    static int generateRandomNumberInRange(int min, int max) {
        return RandomUtils.nextInt(min, max);
    }

    static List<Integer> generateMultipleRandomNumbersInRange(int amountOfNumbers, int min, int max) {
        List<Integer> numList = new ArrayList<>();
        for (int i = 0; i < amountOfNumbers; i++) {
            numList.add(generateRandomNumberInRange(min, max));
        }
        return numList;
    }

    static int generateRandomNumber(int length) {
        List<String> numList = Arrays.asList(generateRandomString(length, false, true).split(""));

        while (numList.get(0).contentEquals("0")) {
            numList.add(0, RandomStringUtils.random(1, false, true));
        }

        return Integer.parseInt(numList.stream()
                .map((String s) -> new StringBuilder().append(s))
                .collect(Collectors.toList())
                .toString());
    }

    static List<Integer> generateMultipleRandomNumbers(int amountOfNumbers, int minLengthOfNumbers, int maxLengthOfNumbers) {
        List<Integer> numbersList = new ArrayList<>();
        for (int i = 0; i < amountOfNumbers; i++) {
            numbersList.add(
                    HelperMethods.generateRandomNumber(
                            Integer.parseInt(RandomStringUtils.randomNumeric(minLengthOfNumbers, maxLengthOfNumbers))
                    ));
        }
        return numbersList;
    }

    static String generateRandomString() {
        return generateRandomString(lengthOfStrings, allowLetters, allowNumbers);
    }

    static String generateRandomString(int length, boolean letters, boolean numbers) {
        return RandomStringUtils.random(length, letters, numbers);
    }

    static List<String> generateMultipleRandomStrings(int amountOfStrings, int lengthOfString, boolean letters, boolean numbers) {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < amountOfStrings; i++) {
            stringList.add(HelperMethods.generateRandomString(lengthOfString, letters, numbers));
        }
        return stringList;
    }

    static List<String> generateMultipleRandomStrings(int amountOfStrings, int min, int max, boolean letters, boolean numbers) {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < amountOfStrings; i++) {
            stringList.add(HelperMethods.generateRandomString(generateRandomNumberInRange(min, max), letters, numbers));
        }
        return stringList;
    }
}
