package online2.third;

import java.util.Scanner;

/**
 * Created by ZhengChaoJie on 2017/4/13/0013.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();

        long[] A = new long[N];
        long[] B = new long[N];
        long sum = 0;
        for (int i = 0; i < N; i++) {
            A[i] = scanner.nextLong();
            B[i] = scanner.nextLong();
            sum += (A[i] + B[i]);
        }
        scanner.close();
        long average = sum / 2 / N, ans = 0;
        for (int i = 0; i < N; i++) {
            long dela = A[i] - average, delb = B[i] - average;
            ans += Math.abs(dela + delb);
            if (dela * delb >= 0) {//A[i],B[i]同时大于等于或者同时小于平均数，则直接对右边的数进行操作
                if (i + 1 < N) {
                    A[i + 1] += dela;
                    B[i + 1] += delb;
                }
            } else {
                long dela1 = Math.abs(dela), delb1 = Math.abs(delb);
                if (dela1 >= delb1) {
                    ans += delb1;
                    if (i + 1 < N)
                        A[i + 1] += (dela + delb);
                } else {
                    ans += dela1;
                    if (i + 1 < N)
                        B[i + 1] += (dela + delb);
                }
            }
            A[i] = average;
            B[i] = average;
        }
        System.out.println(ans);
    }
}
