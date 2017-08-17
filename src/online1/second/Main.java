package online1.second;
//#1490 : Tree Restoration

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int n, m, k;
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        m = scanner.nextInt();
        k = scanner.nextInt();
        int[][] nodes = new int[m][];
        for (int i = 0; i < m; i++) {
            int count = scanner.nextInt();
            nodes[i] = new int[count];
        }
        //保存节点的形状
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < nodes[i].length; j++) {
                nodes[i][j] = scanner.nextInt();
            }
        }
        //保存叶子节点
        int[] leaf = new int[k];
        for (int i = 0; i < k; i++)
            leaf[i] = scanner.nextInt();

        //保存距离矩阵，但是大小应该是N+1 * N+1
        int[][] distance = new int[n + 1][n + 1];
        for (int i = 0; i <= n; i++)//初始化
            for (int j = 0; j <= n; j++)
                distance[i][j] = -1;

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {//先得到叶子节点间的距离矩阵
                distance[leaf[i]][leaf[j]] = scanner.nextInt();
            }
        }
        int[] parentList = findParent(nodes, distance, leaf, n);//计算父节点方法

        for (int i = 1; i < n; i++)
            System.out.print(parentList[i] + " ");
        System.out.println(parentList[n]);
    }

    static int[] findParent(int[][] nodes, int[][] distance, int[] leaf, int n) {
        int[] parentList = new int[n + 1];//计算所有的父节点
        boolean[] isLeaf = new boolean[n + 1];//判断是否叶子节点
        for (int i = 0; i < leaf.length; i++) {
            isLeaf[leaf[i]] = true;
        }
        int curLevel = nodes.length - 1, index = 0, parentIndex = 0;
        while (curLevel > 0) {
            while (parentIndex < nodes[curLevel - 1].length && isLeaf[nodes[curLevel - 1][parentIndex]])//上层节点是叶子节点，则继续移动父节点游标
                parentIndex++;
            while (index < nodes[curLevel].length) {
                parentList[nodes[curLevel][index]] = nodes[curLevel - 1][parentIndex];//取到当前父节点
                for (int i = 0; i < distance.length; i++) {//更新父节点到其他节点的距离
                    if (i != nodes[curLevel - 1][parentIndex]) {
                        distance[i][nodes[curLevel - 1][parentIndex]] = distance[i][nodes[curLevel][index]] - 1;
                        distance[nodes[curLevel - 1][parentIndex]][i] = distance[nodes[curLevel][index]][i] - 1;
                    } else
                        distance[i][i] = 0;
                }
                //如果相邻节点的距离大于2，说明不属于同一个父节点的子节点。父节点要右移
                if (index + 1 < nodes[curLevel].length && distance[nodes[curLevel][index]][nodes[curLevel][index + 1]] > 2) {
                    parentIndex++;
                    while (parentIndex < nodes[curLevel - 1].length && isLeaf[nodes[curLevel - 1][parentIndex]])
                        parentIndex++;
                }
                index++;
            }
            curLevel--;
            parentIndex = 0;
            index = 0;
        }
        return parentList;
    }
}
