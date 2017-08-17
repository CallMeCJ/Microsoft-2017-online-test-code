package online2.second;

import java.util.Scanner;

/**
 * Created by ZhengChaoJie on 2017/4/13/0013.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long N = scanner.nextLong();
        int Q = scanner.nextInt();
        scanner.close();
        long minHours = N, base = N;
        for (int i = 1; i < minHours; i++) {
            base = (base + 1) >> 1;
            long needHours = i * Q + base;
            if (needHours < minHours)
                minHours = needHours;
        }
        System.out.println(minHours);
    }
}
