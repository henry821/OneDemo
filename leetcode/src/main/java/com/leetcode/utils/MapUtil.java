package com.leetcode.utils;

import com.leetcode.beans.GraphNode;

import java.util.LinkedList;

/**
 * Description 图操作工具类
 * Author wanghengwei
 * Date   2019/6/25 16:56
 */
public class MapUtil {

    public static void main(String[] args) {

    }

    /**
     * 生成一张图,表示形式1
     * 0--1--2
     * |  |  |
     * 3--4--5
     * |  |  |
     * 6--7--8
     *
     * @return 左上角顶点
     */
    public static GraphNode createMap1() {
        GraphNode node0 = new GraphNode(0);
        GraphNode node1 = new GraphNode(1);
        GraphNode node2 = new GraphNode(2);
        GraphNode node3 = new GraphNode(3);
        GraphNode node4 = new GraphNode(4);
        GraphNode node5 = new GraphNode(5);
        GraphNode node6 = new GraphNode(6);
        GraphNode node7 = new GraphNode(7);
        GraphNode node8 = new GraphNode(8);

        node0.addNode(node1);
        node0.addNode(node3);

        node1.addNode(node0);
        node1.addNode(node2);
        node1.addNode(node4);

        node2.addNode(node1);
        node2.addNode(node5);

        node3.addNode(node0);
        node3.addNode(node4);
        node3.addNode(node6);

        node4.addNode(node1);
        node4.addNode(node3);
        node4.addNode(node5);
        node4.addNode(node7);

        node5.addNode(node2);
        node5.addNode(node4);
        node5.addNode(node8);

        node6.addNode(node3);
        node6.addNode(node7);

        node7.addNode(node4);
        node7.addNode(node6);
        node7.addNode(node8);

        node8.addNode(node5);
        node8.addNode(node7);

        return node0;
    }

    /**
     * 生成一张图,表示形式2
     * 0--1--2
     * |  |  |
     * 3--4--5
     * |  |  |
     * 6--7--8
     *
     * @return 邻接表数组，数组每一项代表当前顶点，邻接表存储相邻顶点
     */
    public static LinkedList[] createMap2() {
        LinkedList<Integer>[] list = new LinkedList[9];

        list[0].add(1);
        list[0].add(3);

        list[1].add(0);
        list[1].add(2);
        list[1].add(4);

        list[2].add(1);
        list[2].add(5);

        list[3].add(0);
        list[3].add(4);
        list[3].add(6);

        list[4].add(1);
        list[4].add(3);
        list[4].add(5);
        list[4].add(7);

        list[5].add(2);
        list[5].add(4);
        list[5].add(8);

        list[6].add(3);
        list[6].add(7);

        list[7].add(4);
        list[7].add(6);
        list[7].add(8);

        list[8].add(5);
        list[8].add(7);

        return list;
    }
}
