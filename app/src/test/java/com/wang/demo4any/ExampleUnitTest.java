package com.wang.demo4any;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);


    }

    @Test
    public void testTradition() {
       // 复利计算公式：投资期末资金=最初本金×（1+年复合收益率）投资年数
//        double v = 100000 * Math.pow((1 + 0.13), 5);
//        System.out.println("v = " + v);

        print(3000, 0.08, 5);

        double base = 3000;
        double x = base * 1.08;   // 1
        x = (x + base) * 1.08;  // 2
        x = (x + base) * 1.08;  // 3
        x = (x + base) * 1.08;  // 4
        x = (x + base) * 1.08;  // 5
        x = (x + base) * 1.08;  // 5
        x = (x + base) * 1.08;  // 5
        x = (x + base) * 1.08;  // 5
        x = (x + base) * 1.08;  // 5
        x = (x + base) * 1.08;  // 5
        System.out.println("x = " + x);

        double i = (x - base * 10)  * 1.0 / 15000;
        System.out.println("i = " + i);

        System.out.println("i/5 = " + i/10);
    }

    private void print(int base, double liXi, int year) {
        double sum = base;
        for (int i = 0; i < year; i++) {
            sum += sum * (1 + liXi) + base;
        }
        System.out.println("sum = " + sum);
    }
}