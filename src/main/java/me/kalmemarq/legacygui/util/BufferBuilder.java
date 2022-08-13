package me.kalmemarq.legacygui.util;

import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferBuilder {
    private int capacity;

    private final ByteBuffer byteBuffer;
    private final IntBuffer intBuffer;
    private final FloatBuffer floatBuffer;
    private final int[] array;

    private boolean hasColor = false;
    private boolean hasTexture = false;
    private boolean noColor = false;

    private boolean building;
    private int vertices;
    private int vertexFormatMode;

    private int cursor;

    private int _color;
    private float _u;
    private float _v;
    private float _x;
    private float _y;
    private float _z;

    private static boolean field_2055 = true;
    private static boolean allowsVertexArrayData = false;
    private double field_2072;
    private double field_2073;
    private double field_2074;
    private int _normal;
    private int field_2069 = 0;
    private boolean canUseVRDExt = false;
    private IntBuffer buffer;
    private int bufferCursor = 0;
    private final int bufferCapacity = 10;
    private boolean hasNormal = false;

    public BufferBuilder(int capacity) {
        this.capacity = capacity;
        this.byteBuffer = ByteBuffer.allocateDirect(capacity * 4).order(ByteOrder.nativeOrder());
        this.intBuffer = this.byteBuffer.asIntBuffer();
        this.floatBuffer = this.byteBuffer.asFloatBuffer();
        this.array = new int[capacity];
        this.canUseVRDExt = allowsVertexArrayData && GLContext.getCapabilities().GL_ARB_vertex_buffer_object;
        if (this.canUseVRDExt) {
            this.buffer = ByteBuffer.allocateDirect(this.bufferCapacity << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
            ARBVertexBufferObject.glGenBuffersARB(this.buffer);
        }
    }

    public void draw() {
        if (!this.building) {
            throw new IllegalStateException("Not building!");
        } else {
            this.building = false;

            if (this.vertices > 0) {
                this.intBuffer.clear();
                this.intBuffer.put(this.array, 0, this.cursor);
                this.byteBuffer.position(0);
                this.byteBuffer.limit(this.cursor * 4);

                if (this.canUseVRDExt) {
                    this.bufferCursor = (this.bufferCursor + 1) % this.bufferCapacity;
                    ARBVertexBufferObject.glBindBufferARB(GlConst.GL_ARRAY_BUFFER, this.buffer.get(this.bufferCursor));
                    ARBVertexBufferObject.glBufferDataARB(GlConst.GL_ARRAY_BUFFER, this.byteBuffer, 35040);
                }

                if (this.hasTexture) {
                    if (this.canUseVRDExt) {
                        GL11.glTexCoordPointer(2, GlConst.GL_FLOAT, 32, 12L);
                    } else {
                        this.floatBuffer.position(3);
                        GL11.glTexCoordPointer(2, 32, this.floatBuffer);
                    }

                    GL11.glEnableClientState(GlConst.GL_TEXTURE_COORD_ARRAY);
                }

                if (this.hasColor) {
                    if (this.canUseVRDExt) {
                        GL11.glColorPointer(4, GlConst.GL_UNSIGNED_BYTE, 32, 20L);
                    } else {
                        this.byteBuffer.position(20);
                        GL11.glColorPointer(4, true, 32, this.byteBuffer);
                    }

                    GL11.glEnableClientState(GlConst.GL_COLOR_ARRAY);
                }

                if (this.hasNormal) {
                    if (this.canUseVRDExt) {
                        GL11.glNormalPointer(GlConst.GL_BYTE, 32, 24L);
                    } else {
                        this.byteBuffer.position(24);
                        GL11.glNormalPointer(32, this.byteBuffer);
                    }

                    GL11.glEnableClientState(GlConst.GL_NORMAL_ARRAY);
                }

                if (this.canUseVRDExt) {
                    GL11.glVertexPointer(3, GlConst.GL_FLOAT, 32, 0L);
                } else {
                    this.floatBuffer.position(0);
                    GL11.glVertexPointer(3, 32, this.floatBuffer);
                }

                GL11.glEnableClientState(GlConst.GL_VERTEX_ARRAY);
                if (this.vertexFormatMode == 7 && field_2055) {
                    GL11.glDrawArrays(GlConst.GL_TRIANGLES, 0, this.vertices);
                } else {
                    GL11.glDrawArrays(this.vertexFormatMode, 0, this.vertices);
                }

                GL11.glDisableClientState(GlConst.GL_VERTEX_ARRAY);
                if (this.hasTexture) {
                    GL11.glDisableClientState(GlConst.GL_TEXTURE_COORD_ARRAY);
                }

                if (this.hasColor) {
                    GL11.glDisableClientState(GlConst.GL_COLOR_ARRAY);
                }

                if (this.hasNormal) {
                    GL11.glDisableClientState(GlConst.GL_NORMAL_ARRAY);
                }
            }

            this.clear();
        }
    }

    public void begin() {
        begin(GlConst.GL_QUADS);
    }

    public void begin(int vertexFormatMode) {
        if (this.building) {
            throw new IllegalStateException("Already building!");
        } else {
            this.building = true;
            this.clear();
            this.vertexFormatMode = vertexFormatMode;
            this.hasNormal = false;
            this.hasTexture = false;
            this.hasColor = false;
            this.noColor = false;
        }
    }

    public void clear() {
        this.vertices = 0;
        this.byteBuffer.clear();
        this.cursor = 0;
        this.field_2069 = 0;
    }

    public BufferBuilder vertex(float x, float y, float z) {
        this._x = x;
        this._y = y;
        this._z = z;
        return this;
    }

    public BufferBuilder uv(float u, float v) {
        this.hasTexture = true;
        this._u = u;
        this._v = v;
        return this;
    }

    public BufferBuilder color(float r, float g, float b) {
        return this.color((int)(r * 255.0f), (int)(g * 255.0f), (int)(b * 255.0f));
    }

    public BufferBuilder color(float r, float g, float b, float a) {
        return this.color((int)(r * 255.0f), (int)(g * 255.0f), (int)(b * 255.0f), (int)(a * 255.0f));
    }

    public BufferBuilder color(int r, int g, int b) {
        return this.color(r, g, b, 255);
    }

    public BufferBuilder color(int r, int g, int b, int a) {
        if (!this.noColor) {

            r = MathHelper.clamp(r, 0, 255);
            g = MathHelper.clamp(g, 0, 255);
            b = MathHelper.clamp(b, 0, 255);
            a = MathHelper.clamp(a, 0, 255);

            this.hasColor = true;

            if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
                this._color = a << 24 | b << 16 | g << 8 | r;
            } else {
                this._color = r << 24 | g << 16 | b << 8 | a;
            }
        }

        return this;
    }

    public void endVertex() {
        ++this.field_2069;
        if (this.vertexFormatMode == 7 && field_2055 && this.field_2069 % 4 == 0) {
            for(int var7 = 0; var7 < 2; ++var7) {
                int var8 = 8 * (3 - var7);
                if (this.hasTexture) {
                    this.array[this.cursor + 3] = this.array[this.cursor - var8 + 3];
                    this.array[this.cursor + 4] = this.array[this.cursor - var8 + 4];
                }

                if (this.hasColor) {
                    this.array[this.cursor + 5] = this.array[this.cursor - var8 + 5];
                }

                this.array[this.cursor] = this.array[this.cursor - var8];
                this.array[this.cursor + 1] = this.array[this.cursor - var8 + 1];
                this.array[this.cursor + 2] = this.array[this.cursor - var8 + 2];
                ++this.vertices;
                this.cursor += 8;
            }
        }

        if (this.hasTexture) {
            this.array[this.cursor + 3] = Float.floatToRawIntBits((float)this._u);
            this.array[this.cursor + 4] = Float.floatToRawIntBits((float)this._v);
        }

        if (this.hasColor) {
            this.array[this.cursor + 5] = this._color;
        }

        if (this.hasNormal) {
            this.array[this.cursor + 6] = this._normal;
        }

        this.array[this.cursor] = Float.floatToRawIntBits((float)(_x + this.field_2072));
        this.array[this.cursor + 1] = Float.floatToRawIntBits((float)(_y + this.field_2073));
        this.array[this.cursor + 2] = Float.floatToRawIntBits((float)(_z + this.field_2074));
        this.cursor += 8;
        ++this.vertices;
        if (this.vertices % 4 == 0 && this.cursor >= this.capacity - 32) {
            this.draw();
            this.building = true;
        }
    }

    public void normal(float x, float y, float z) {
        if (!this.building) {
            System.out.println("Not building! (Normal)");
        }

        this.hasNormal = true;
        int x0 = (byte)((int)(x * 128.0F));
        int y0 = (byte)((int)(y * 127.0F));
        int z0 = (byte)((int)(z * 127.0F));
        this._normal = x0 | y0 << 8 | z0 << 16;
    }

    public void method_1696(double d, double d1, double d2) {
        this.field_2072 = d;
        this.field_2073 = d1;
        this.field_2074 = d2;
    }

    public void method_1700(float f, float f1, float f2) {
        this.field_2072 += (double)f;
        this.field_2073 += (double)f1;
        this.field_2074 += (double)f2;
    }
}
