package com.company;
/**
 * Created by lucas63 on 15.12.15.
 */
public class Main {

    public static void main(String[] args) {
        String original = "Something";
        String s1 = "Play\n";
        String s2 = "Padd\n";

        Ssdeep s = new Ssdeep();

        System.out.println(s.HashString(s1));

        System.out.println(s.HashString(s2));
        System.out.println("Similarity result : "+HammingDistance.distance(s.HashString(s1),s.HashString(s2)));

    }
}
