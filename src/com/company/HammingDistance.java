package com.company;

/**
 * Created by lucas63 on 23.12.15.
 */
public class HammingDistance {
    public static double distance(String s1,String s2)
    {
        double a = 0;
        double b = 0;
        for (int x = 0; x < s1.length(); x++) {
            if (s1.charAt(x) == s2.charAt(x)) {
                a += 1;
            }
            b++;
        }
        return a/b;
    }
}
