package online1.first;
//#1489 : Legendary Items

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int P = sc.nextInt();
        int Q = sc.nextInt();
        int N = sc.nextInt();
        double result = 0.0;
        double double_Q = 1.0 * Q / 100;
        for (int i = 0; i < N; i++) {
            double getLegendary = getNextLegendary(P, double_Q);
            result += getLegendary;
            P /= 2;//得到一个传奇后，概率变为 ⌊P/(2^i)⌋%  i代表得到的传奇数
        }
        System.out.printf("%.2f\n", result);
    }

    static double getNextLegendary(int P, double q) {
        double double_p = 1.0 * P / 100;
        double needQuest = 1;//得到下一个传奇，必须至少需要的quest
        double another = 1;//额外需要的quest
        while (true) {
            another *= (1 - double_p);//每次探索，额外需要的quest概率增加
            needQuest += another;
            double_p += q;//每次都在p的基础上加q
            if (double_p >= 1.0)
                break;
        }
        return needQuest;
    }
}
