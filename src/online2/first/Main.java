package online2.first;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by ZhengChaoJie on 2017/4/12/0012.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        scanner.nextLine();
        Map<Integer, Integer> rowMap = new HashMap<>();
        Map<Integer, Integer> columnMap = new HashMap<>();
        Map<Integer, Integer> diagonalMap = new HashMap<>();
        Map<Integer, Integer> reverseMap = new HashMap<>();
        for (int i = 0; i < N; i++) {
            int row = scanner.nextInt(), column = scanner.nextInt();
            scanner.nextLine();
            insertIntoMap(rowMap, row);
            insertIntoMap(columnMap, column);
            insertIntoMap(diagonalMap, column - row);
            insertIntoMap(reverseMap, column + row);
        }
        scanner.close();
        System.out.println(calculatePair(rowMap) + calculatePair(columnMap) + calculatePair(diagonalMap) + calculatePair(reverseMap));
    }

    private static void insertIntoMap(Map<Integer, Integer> map, int target) {
        if (map.containsKey(target)) {
            map.put(target, map.get(target) + 1);
        } else {
            map.put(target, 1);
        }
    }

    private static int calculatePair(Map<Integer, Integer> map) {
        int sum = 0;
        for (Integer count : map.values()) {
            if (count > 1)
                sum += (count * (count - 1) / 2);
        }
        return sum;
    }
}
