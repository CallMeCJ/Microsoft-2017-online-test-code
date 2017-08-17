package online1.four;
//#1492 : Parentheses Sequence

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str1 = scanner.nextLine();
        scanner.close();
        Map<String, Integer> part1 = getNeedAndMethods(str1);
        int need1 = part1.get("need"), methods1 = part1.get("methods");
        StringBuffer str2 = new StringBuffer(str1);
        str2.reverse();
        for (int i = 0; i < str2.length(); i++) {
            if (str2.charAt(i) == '(')
                str2.setCharAt(i, ')');
            else
                str2.setCharAt(i, '(');
        }
        Map<String, Integer> part2 = getNeedAndMethods(str2.toString());
        int need2 = part2.get("need"), methods2 = part2.get("methods");
        System.out.println((need1 + need2) + " " + (methods1 * methods2) % 1000000007);
    }

    static Map<String, Integer> getNeedAndMethods(String string) {
        Map<String, Integer> result = new HashMap<>();
        int left = 0, right = 0, need = 0;
        char[] strs = string.toCharArray();
        List<Integer> least = new ArrayList<>();
        List<Integer> tmpLeast = new ArrayList<>();
        for (char ch : strs) {
            if (ch == '(')
                left++;
            else {
                right++;
                if (right - left > need) {
                    need = right - left;
                    tmpLeast.add(need);
                    least.addAll(tmpLeast);
                    tmpLeast.clear();
                } else if (right - left > 0)
                    tmpLeast.add(right - left);
                else
                    tmpLeast.add(0);
            }
        }

        result.put("need", need);
        if (need == 0) {
            result.put("methods", 1);
            return result;
        }
        int[] dp = new int[need + 1];
        dp[0] = 1;
        for (Integer insert : least) {
            int[] preSum = new int[need + 1];
            preSum[0] = dp[0];
            for (int i = 1; i <= need; i++) {
                preSum[i] = (preSum[i - 1] + dp[i]) % 1000000007;
            }
            for (int i = 0; i <= need; i++) {
                if (i < insert)
                    dp[i] = 0;
                else
                    dp[i] = preSum[i];
            }
        }
        result.put("methods", dp[need]);
        return result;
    }
}
