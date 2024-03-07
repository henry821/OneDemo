package com.demo.opengl

/**
 * Created by hengwei on 2022/9/19.
 */
class Triangle : AbsShape() {
    override fun getVertices(): FloatArray {
        return floatArrayOf(
            -0.6f, -0.4f, 0f,
            -0.8f, 0.4f, 0f,
            -0.4f, 0.4f, 0f
        )
    }

    /**
     * 由于只有三个顶点，所以按照0->1->2的顺序即可
     */
    override fun getVertexIndices(): IntArray {
        return intArrayOf(0, 1, 2)
    }
}