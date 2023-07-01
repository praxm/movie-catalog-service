package com.praxy.moviecatalogservice.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PracticeDemo {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        System.out.println("################");
        list.stream().filter(i-> i%2==0).forEach(System.out::println);
        System.out.println("################");
        list.stream().map(i->i*i).forEach(System.out::println);
        System.out.println("################");
        Integer min = list.stream().min(((o1, o2) ->o1.compareTo(o2))).get();
        System.out.println("Min "+ min);

        List<String> items =
                Arrays.asList("apple", "apple", "banana",
                        "apple", "orange", "banana", "papaya");

        Map<String, Long> result =
                items.stream().collect(
                        Collectors.groupingBy(
                                Function.identity(), Collectors.counting()
                        )
                );
        System.out.println("Grouping By Example ###################");
        System.out.println(result);

    }
}
