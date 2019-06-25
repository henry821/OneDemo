package com.leetcode.utils;

import java.util.LinkedList;
import java.util.Queue;

public class Graph {

    private int v; //顶点个数
    private LinkedList<Integer>[] adj;//邻接表

    public Graph(int v) {
        this.v = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph(9);

        graph.addEdge(0, 1);
        graph.addEdge(0, 3);
        graph.addEdge(1, 2);
        graph.addEdge(1, 4);
        graph.addEdge(2, 5);
        graph.addEdge(3, 4);
        graph.addEdge(3, 6);
        graph.addEdge(4, 5);
        graph.addEdge(4, 7);
        graph.addEdge(6, 7);
        graph.addEdge(7, 8);

        graph.bfs(0, 8);

    }

    /**
     * BreadthFirstSearch 广度优先算法
     *
     * @param s 起点
     * @param t 终点
     */
    private void bfs(int s, int t) {
        if (s == t) {
            return;
        }
        //用来记录已经被访问的顶点，避免顶点被重复访问。
        // 如果顶点q被访问，相应的visited[q]会被设置为true
        boolean[] visited = new boolean[v];
        //是一个队列，用来存储已经被访问、但相连的顶点还没有被访问的顶点。
        //因为广度优先搜索是逐层访问的，也就是说，我们只有把第k层的顶点都访问完成之后，才能访问第k+1层的顶点。
        //当我们访问到第k层顶点的时候，我们需要把第k层的顶点记录下来，稍后才能通过第k层的顶点来找第k+1层的顶点。
        //所以，我们用这个队列来实现记录的功能
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        //用来记录搜索路径。当我们从顶点s开始，广度优先搜索到顶点t后。prev数组中存储的就是搜索路径。
        //不过，这个路径是反向存储的。
        //prev[w]存储的是，顶点w是从哪个前驱顶点遍历过来的。
        //比如，我们通过2的邻接表访问到顶点3，那prev[3]就等于2。为了正向打印出路径，我们需要递归地来打印。
        int[] prev = new int[v];
        for (int i = 0; i < v; i++) {
            prev[i] = -1;
        }
        while (queue.size() != 0) {
            int w = queue.poll();
            for (int i = 0; i < adj[w].size(); i++) {
                int q = adj[w].get(i);
                if (!visited[q]) {
                    prev[q] = w;
                    if (q == t) {
                        print(prev, s, t);
                        return;
                    }
                    visited[q] = true;
                    queue.add(q);
                }
            }
        }
    }

    private void print(int[] prev, int s, int t) {
        if (prev[t] != -1 && t != s) {
            print(prev, s, prev[t]);
        }
        System.out.print(t + ",");
    }

    public void addEdge(int s, int t) { //无向图一条边存两次
        adj[s].add(t);
        adj[t].add(s);
    }

}
