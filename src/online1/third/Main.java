package online1.third;

import java.util.*;

class Monster {
    String ij;
    int hp;
    int ap;

    Monster(String ij, int hp, int ap) {
        this.ij = ij;
        this.hp = hp;
        this.ap = ap;
    }
}

public class Main {
    private static final int MAX_STATE = (1 << 20), ROUND = 5;
    static int N, M, HP, AP;
    static int[][] dp = new int[ROUND + 1][MAX_STATE + 1];
    static Map<String, Monster> monsterMap = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        N = scanner.nextInt();
        M = scanner.nextInt();
        char[][] mazeMap = new char[N][M];
        List<String> monsterIndex = new ArrayList<>();
        scanner.nextLine();
        for (int i = 0; i < N; i++) {
            String line = scanner.nextLine();
            line = line.trim();
            mazeMap[i] = line.toCharArray();
            for (int j = 0; j < M; j++) {
                if (mazeMap[i][j] == 'S' || mazeMap[i][j] == 'M')
                    monsterIndex.add(i + "" + j);
            }
        }
        for (int pos = 0; pos < monsterIndex.size(); pos++) {
            int hp, ap;
            hp = scanner.nextInt();
            ap = scanner.nextInt();
            String index = monsterIndex.get(pos);
            Monster monster = new Monster(index, hp, ap);
            monsterMap.put(index, monster);
        }
        HP = scanner.nextInt();
        AP = scanner.nextInt();
        for (int i = 0; i <= ROUND; i++) {
            for (int j = 0; j <= MAX_STATE; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        int remainHp = HP - letKill(mazeMap, ROUND);
        if (remainHp <= 0)
            System.out.println("DEAD");
        else
            System.out.println(remainHp);
    }

    static int letKill(char[][] mazeMap, int buff) {
        int state = 0, remainMonster = 0;
        Set<String> candidates = new HashSet<>();
        for (int i = 0; i < mazeMap.length; i++) {
            for (int j = 0; j < mazeMap[i].length; j++) {
                state <<= 1;
                if (mazeMap[i][j] != 'D') {
                    state += 1;
                    if (mazeMap[i][j] != '.')
                        remainMonster++;
                    if (i - 1 >= 0 && mazeMap[i - 1][j] == 'D' || j - 1 >= 0 && mazeMap[i][j - 1] == 'D' || i + 1 < N && mazeMap[i + 1][j] == 'D' || j + 1 < M && mazeMap[i][j + 1] == 'D')
                        candidates.add(i + "" + j);
                }
            }
        }
        if (remainMonster == 0 || state == 0)
            return 0;
        if (dp[buff][state] != Integer.MAX_VALUE)
            return dp[buff][state];
        dp[buff][state] = HP;
        for (String candidate : candidates) {
            int i = candidate.charAt(0) - '0', j = candidate.charAt(1) - '0';
            char curChar = mazeMap[i][j];
            int curBuff = buff - 1, needHP = 0;
            if (mazeMap[i][j] == 'M' || mazeMap[i][j] == 'S') {//attack
                Monster monster = monsterMap.get(i + "" + j);
                int monsterHP = monster.hp;
                while (monsterHP > 0) {
                    monsterHP -= AP;
                    if (curBuff <= 0)
                        needHP += monster.ap;
                    curBuff--;
                }
            }
            if (needHP >= HP)
                continue;
            if (mazeMap[i][j] == 'S')
                curBuff = ROUND;
            else
                curBuff = curBuff <= 0 ? 0 : curBuff;
            mazeMap[i][j] = 'D';
            int minHP = letKill(mazeMap, curBuff);
            mazeMap[i][j] = curChar;
            if (minHP >= HP)
                continue;
            dp[buff][state] = Math.min(dp[buff][state], needHP + minHP);
        }
        return dp[buff][state];
    }
}