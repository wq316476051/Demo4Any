package com.wang.demo4any;

import android.app.Person;

import org.junit.Test;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class TestActivityTest {

    private AtomicReference<User> mExecutingTask = new AtomicReference<>();
    @Test
    public void test() {
        mExecutingTask.set(null);
        if (mExecutingTask.compareAndSet(new User("wang", 15), new User("wang", 15))) {
            User user = mExecutingTask.get();
            System.out.println("get = " + user.name);
        }
    }

    private class User {
        public String name;
        public int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return age == user.age &&
                    Objects.equals(name, user.name);
        }

        @Override
        public int hashCode() {

            return Objects.hash(name, age);
        }
    }
}