package com.yst.onecity;


import com.yst.basic.framework.utils.Utils;

import org.junit.Test;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    private static Pattern pattern = Pattern.compile("^(?:([0-9])\\1{5})$");

    @Test
    public void getSign() {
        String pwd = "112111";
        String regeX = "^(?:([0-9])\\1{5})$";
        if (pwd.matches(regeX)) {
            System.out.println("=========" + pwd.matches(regeX));
        } else {
            System.out.print("bbb===" + pwd.matches(regeX));
        }
    }

    @Test
    public void test() {
        String idCard = "0";
        double v = 0;
        boolean continuityCharacter = isContinuityCharacter("123456");
        System.out.print(Utils.doubleToString(v)+"\n");
        System.out.print("------123456-------" + continuityCharacter+"\n");
        System.out.print("------121212-------" + isContinuityCharacter("121212")+"\n");
        System.out.print("------654321-------" + isContinuityCharacter("654321")+"\n");
        System.out.print("------122456-------" + isContinuityCharacter("122456")+"\n");

    }

    public static String doubleToString(double num) {
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }

    private static boolean isContinuityCharacter(String s) {
        boolean up = true;
        boolean down = true;
        char[] data = s.toCharArray();
        for (int i = 0; i < data.length - 1; i++) {
            int a = Integer.parseInt(data[i] + "");
            int b = Integer.parseInt(data[i + 1] + "");
            up = up && (a + 1 == b);
            down = down && (a - 1 == b);
        }
        return up || down;
    }
}