package com.wang.demo4any;

import org.junit.Test;

import java.util.Objects;
import java.util.function.Predicate;

public class LambdaTest {

    @Test
    public void test() {
//        Predicate<String> predicate = new Predicate<String>() {
//            @Override
//            public boolean test(String s) {
//                return s != null && s.equals("hello");
//            }
//        };
//        Predicate<String> predicate = (String s) -> {
//            return s != null && s.equals("hello");
//        };

//        Predicate<String> predicate = s -> s != null && s.equals("hello");

//        Predicate<String> predicate = "hello"::equals;

        Predicate<String> predicate = ((Predicate<String>) "hello"::equals).or("world"::equals);

        boolean hello = predicate.test("world");
        System.out.println("hello = " + hello);

        Predicate<Integer> isAge = ((Predicate<Integer>) age -> age > 16).or(age -> age < 60);
        boolean test = isAge.test(40);
        System.out.println("test = " + test);

        test2(((Predicate<Integer>) age -> age > 16).or(age -> age < 60));
    }

    public void test2(Predicate predicate) {
        boolean test = predicate.test(33);
    }

    static <T> Predicate<T> isEqual(Object targetRef) {
        return (targetRef == null) ? Objects::isNull : targetRef::equals;
    }
}
