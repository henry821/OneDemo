package com.demo.opengl

/**
 * Created by hengwei on 2022/9/19.
 */
class Rectangle : AbsShape() {
    override fun getVertices(): FloatArray {
        val minX = -0.20F;
        val maxX = 0.15F;
        val minY = -0.3F;
        val maxY = 0.3F;

        // 逆时针方向的4个坐标
        // 在OpenGL中所有组合的图形的绘制方向必须一致。
        // 要么都是顺时针，要么都是逆时针。
        return floatArrayOf(
            minX, maxY, 0F,
            minX, minY, 0F,
            maxX, minY, 0F,
            maxX, maxY, 0F
        )
    }

    override fun getVertexIndices(): IntArray {
        return intArrayOf(
            // 第1个三角形
            0, 1, 2,

            // 第2个三角形
            0, 2, 3
        )
    }
}