package net.h1ddengames.imagedownloader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TestArea {
    public static void main(String[] args) {
        //stringToList();
        String str = "r\nu\nn\n\n\n4\n\n5";
        stringToList2(str);
    }

    public static void stringToList() {
        String s = "r\nu\nn\n\n\n4\n\n5";
        List<String> list = new ArrayList<>();
        String[] res = s.split("\n");

        for (String re : res) {
            if (re.contentEquals("")) {
                continue;
            }
            list.add(re);
            System.out.println(re);
        }
    }

    public static void stringToList2(String str) {
        //Predicate<String> isEmpty = Predicate.not(i -> i.contentEquals(""));
        List<String> list = Arrays.stream(str.split("\n"))
                            .filter(Predicate.not(i -> i.contentEquals("")))
                            .collect(Collectors.toList());
        list.stream().forEach(System.out::println);
    }
}
