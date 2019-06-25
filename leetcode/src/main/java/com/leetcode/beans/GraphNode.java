package com.leetcode.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Description 图的顶点
 * Author wanghengwei
 * Date   2019/6/25 16:47
 */
public class GraphNode {

    public int data;
    public List<GraphNode> neighborList; //邻接表

    public GraphNode(int data) {
        this.data = data;
        neighborList = new ArrayList<>();
    }

    public void addNode(GraphNode node) {
        neighborList.add(node);
    }
}
