package com.demo.opengl

import android.opengl.GLES30
import java.nio.Buffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Created by hengwei on 2022/9/19.
 */
abstract class AbsShape {

    companion object {
        /**
         * 每个坐标有几维
         */
        private const val VERTEX_COORDINATE_DIMS = 3
    }

    /**
     * 编译生成的 OpenGL 程序
     */
    private val program: Int

    /**
     * 通过「反射」获取到的OpenGL代码中定义的字段
     */
    private var positionLocation: Int = 0

    /**
     * 用于保存申请到的 GPU 上的内存的句柄
     */
    private var vaoID = 0
    private var vboID = 0
    private var eboID = 0

    /**
     * 当前图形中，三角形的个数
     */
    private var triangleCount = 0

    private var colorLocation: Int = 0

    init {
        // 创建 OpenGL 程序
        program = load()
    }

    fun draw() {
        //使用编译好的程序
        GLES30.glUseProgram(program)

        //设置顶点信息
        handleVertices()

        //准备纹理信息
        handleColor()

        //绘制顶点
        GLES30.glDrawElements(
            /*mode   =*/GLES30.GL_TRIANGLES,
            /*count  =*/ triangleCount * 3,
            /*type   =*/GLES30.GL_UNSIGNED_INT,
            /*offset =*/0
        )

        //关闭其可修改
        GLES30.glDisableVertexAttribArray(positionLocation)

        //释放资源
        GLES30.glBindVertexArray(0)
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, 0)
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0)
        GLES30.glDeleteVertexArrays(1, intArrayOf(vaoID), 0)
        GLES30.glDeleteBuffers(1, intArrayOf(vboID), 0)
        GLES30.glDeleteBuffers(1, intArrayOf(eboID), 0)
    }

    private fun load(): Int {
        //顶点着色器
        val vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, vertexShaderCode())
        //片段着色器
        val fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, fragmentShaderCode())
        //创建GL程序
        val program = GLES30.glCreateProgram()
        //绑定shader
        GLES30.glAttachShader(program, vertexShader)
        GLES30.glAttachShader(program, fragmentShader)
        //链接程序
        GLES30.glLinkProgram(program)
        return program
    }

    private fun loadShader(type: Int, code: String): Int {
        //创建shader
        val shader = GLES30.glCreateShader(type)
        //设置代码
        GLES30.glShaderSource(shader, code)
        //编译代码
        GLES30.glCompileShader(shader)
        return shader
    }

    /**
     * 顶点着色器的GLSL代码
     */
    private fun vertexShaderCode(): String {
        return "attribute vec4 vPosition;" +
                "void main(){" +
                "   gl_Position = vPosition;" +
                "}"
    }

    /**
     * 片段着色器的GLSL代码
     */
    private fun fragmentShaderCode(): String {
        return "precision mediump float;" +
                "uniform vec4 vColor;" +
                "void main(){" +
                "   gl_FragColor = vColor;" +
                "}"
    }

    private fun handleVertices() {
        //生成 VAO，顶点数组对象，Vertex Array Object
        handleVAO()
        //生成 VBO，顶点缓冲对象，Vertex Buffer Object
        handleVBO()
        //生成 EBO，索引缓冲对象，Element Buffer Object
        handleEBO()

        //找到在 Shader 中定义的 vPosition 变量的位置（类似于 Java 中的反射）
        positionLocation = GLES30.glGetAttribLocation(program, "vPosition")

        //使能 positionFieldID
        GLES30.glEnableVertexAttribArray(positionLocation)

        GLES30.glVertexAttribPointer(
            /*index      = */ positionLocation,
            /*size       = */ VERTEX_COORDINATE_DIMS,
            /*type       = */ GLES30.GL_FLOAT,
            /*normalized = */ false,
            /*stride     = */ 0,
            /*offset     = */ 0
        )
    }

    private fun handleColor() {
        //获取 Shader 程序中的 vColor 变量的字段位置
        colorLocation = GLES30.glGetUniformLocation(program, "vColor")

        //RGBA
        val color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)

        //填充数据
        GLES30.glUniform4fv(colorLocation, 1, color, 0)
    }

    private fun handleVAO() {
        val vaoIDs = IntArray(1)
        GLES30.glGenVertexArrays(1, vaoIDs, 0)
        vaoID = vaoIDs[0]
        //绑定 VAO
        GLES30.glBindVertexArray(vaoID)
    }

    private fun handleVBO() {
        val vboIDs = IntArray(1)
        GLES30.glGenBuffers(1, vboIDs, 0)
        vboID = vboIDs[0]
        //绑定 VBO 为 vboID 对应的内存
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboID)
        //填充 VBO
        val vertices = getVertices()
        val verticesBuffer = numberArray2Buffer(vertices)
        GLES30.glBufferData(
            /*target =*/ GLES30.GL_ARRAY_BUFFER,
            /*size   =*/ vertices.size * 4,
            /*data   =*/ verticesBuffer,
            /*usage  =*/ GLES30.GL_STATIC_DRAW
        )
    }

    private fun handleEBO() {
        val eboIDs = IntArray(1)
        GLES30.glGenBuffers(1, eboIDs, 0)
        eboID = eboIDs[0]
        //绑定 EBO 为 eboID 对应的内存
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, eboID)
        //填充 EBO
        val indices = getVertexIndices()
        val indicesBuffer = numberArray2Buffer(indices)
        GLES30.glBufferData(
            /*target =*/ GLES30.GL_ELEMENT_ARRAY_BUFFER,
            /*size   =*/ indices.size * 4,
            /*data   =*/ indicesBuffer,
            /*usage  =*/ GLES30.GL_STATIC_DRAW
        )
        triangleCount = indices.size / 3
    }

    /**
     * 将 number array 转为 number buffer。
     * Kotlin 的 IntArray、FloatArray 没有共同的父类，只好用泛型啦。
     */
    private fun <ArrayType> numberArray2Buffer(values: ArrayType): Buffer {
        //计算 bytes 大小，short int 占据2个字节
        val size = when (values) {
            is ShortArray -> values.size * Short.SIZE_BYTES
            is IntArray -> values.size * Int.SIZE_BYTES
            is FloatArray -> values.size * Int.SIZE_BYTES
            is DoubleArray -> values.size * Int.SIZE_BYTES * 2
            is LongArray -> values.size * Long.SIZE_BYTES
            else -> throw IllegalArgumentException("不支持的数组类型")
        }

        //申请空间
        val buffer = ByteBuffer.allocateDirect(size)

        //获取当前设备的 byte order
        val order = ByteOrder.nativeOrder()

        //设置 buffer 的字节序
        buffer.order(order)

        //把当前 bytes buffer强转为指定类型的Buffer，并 put 数据
        val typedBuffer: Buffer = when (values) {
            is ShortArray -> {
                buffer.asShortBuffer().apply { put(values) }
            }
            is IntArray -> {
                buffer.asIntBuffer().apply { put(values) }
            }
            is FloatArray -> {
                buffer.asFloatBuffer().apply { put(values) }
            }
            is DoubleArray -> {
                buffer.asDoubleBuffer().apply { put(values) }
            }
            is LongArray -> {
                buffer.asLongBuffer().apply { put(values) }
            }
            else -> throw IllegalArgumentException("不支持的数组类型")
        }

        typedBuffer.position(0)
        return typedBuffer
    }

    /**
     * 顶点坐标
     * 定义了一个顶点的坐标集合
     */
    abstract fun getVertices(): FloatArray

    /**
     * 构成形状的索引
     *
     * 「顶点坐标」只是提供了有哪些顶点，但这些顶点如何构成图形，则是由这个方法定义
     */
    abstract fun getVertexIndices(): IntArray

}