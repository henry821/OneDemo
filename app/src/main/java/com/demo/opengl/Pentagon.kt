package com.demo.opengl

import kotlin.math.cos
import kotlin.math.sin

/**
 * Created by hengwei on 2022/9/19.
 */
class Pentagon : AbsShape() {
    override fun getVertices(): FloatArray {
        val centerX = 0.5F
        val centerY = 0F
        val radius = 0.3F

        // 五边形每一扇的弧度
        val sector = Math.PI * 2 / 5
        val temp = Math.PI / 2

        // 5个顶点，每个顶点3个维度
        val res = FloatArray(5 * 3)
        for (i in 4 downTo 0) {
            // 逆时针，用极坐标生成直角坐标
            val (x, y) = polar2XY(radius, (sector * i + temp).toFloat())

            // 将直角坐标填充到 res
            res[i * 3 + 0] = centerX + x
            res[i * 3 + 1] = centerY + y
            res[i * 3 + 2] = 0F
        }

        return res
    }

    override fun getVertexIndices(): IntArray {
        return intArrayOf(
            // 用3个三角形拼成一个五边形
            0, 1, 2,
            2, 3, 4,
            0, 2, 4
        )
    }

    /**
     * 极坐标 -> 直角坐标
     *
     * @param theta 弧度
     */
    @Suppress("SameParameterValue")
    private fun polar2XY(r: Float, theta: Float): Pair<Float, Float> {
        val x = r * cos(theta)
        val y = r * sin(theta)
        return Pair(x, y)
    }
}