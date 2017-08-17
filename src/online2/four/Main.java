package online2.four;

import java.util.*;

/**
 * Created by ZhengChaoJie on 2017/4/14/0014.
 * dp[i + 1][j] = min(dp[i][max(0, j - I[i])] + calculateCostToKill(i) + C[i], dp[i][j]);
 * calculateCostToKill(i)代表为了凑齐杀死第i个人所需要的最小代价
 */
class Target {
    int i;
    int f;
    int in;
    int ip;
    long c;
    List<Target> children = new ArrayList<>();

    Target(int i, int f, int in, int ip, int c) {
        this.i = i;
        this.f = f;
        this.in = in;
        this.ip = ip;
        this.c = c;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        Map<Integer, Target> targetMap = new HashMap<>();
        int Fi, INi, IPi, Ci;
        for (int i = 1; i <= N; i++) {
            Fi = scanner.nextInt();
            INi = scanner.nextInt();
            IPi = scanner.nextInt();
            Ci = scanner.nextInt();
            Target target = new Target(i, Fi, INi, IPi, Ci);
            targetMap.put(i, target);
        }
        //build the tree
        Target root = null;
        for (int i = 1; i <= N; i++) {
            Target target = targetMap.get(i);
            int parentIndex = target.f;
            if (parentIndex != 0) {
                Target parent = targetMap.get(parentIndex);
                parent.children.add(target);
                targetMap.put(parentIndex, parent);
            } else
                root = target;
        }
        //calculate the cost
        long cost = calculateCostToKill(root);
        if (cost == Integer.MAX_VALUE)
            System.out.println(-1);
        else
            System.out.println(cost + root.c);
    }

    static long calculateCostToKill(Target root) {
        if (root == null)
            return 0;
        List<Target> children = root.children;
        if (children.isEmpty())
            return 0;
        int need = root.in;
        long[] dp = new long[need + 1];
        dp[0] = 0;
        for (int i = 1; i <= need; i++) {
            dp[i] = Integer.MAX_VALUE;
        }
        for (Target child : children) {
            long preCost = calculateCostToKill(child);
            for (int i = need; i > 0; i--) {
                dp[i] = Math.min(dp[Math.max(0, i - child.ip)] + preCost + child.c, dp[i]);
            }
        }
        return dp[need];
    }
}
