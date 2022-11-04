/*******************************************************************************
 * Copyright (C) 2022 Gancheng Zhu
 * org.easypsycho.EasyPsycho is a Free Software project under the GNU Affero General
 * Public License v3, which means all its code is available for everyone 
 * to download, examine, use, modify, and distribute, subject to the usual 
 * restrictions attached to any GPL software. If you are not familiar with the AGPL, 
 * see the COPYING file for for more details on license terms and other legal issues.
 ******************************************************************************/
package org.easypsycho.visual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;

import java.nio.Buffer;

/**
 * Rewrite SpriteBatch class. Draws batched maskTexture and Texture.
 *
 * @author Gancheng Zhu
 */
public class PsychSpriteBatch implements Disposable {
    @Deprecated
    public static Mesh.VertexDataType defaultVertexDataType = Mesh.VertexDataType.VertexArray;

    private Mesh mesh;

    final float[] vertices;
    int idx = 0;

    boolean drawing = false;

    private final Matrix4 transformMatrix = new Matrix4();
    private final Matrix4 projectionMatrix = new Matrix4();
    private final Matrix4 combinedMatrix = new Matrix4();

    private boolean blendingDisabled = false;
    private int blendSrcFunc = GL20.GL_SRC_ALPHA;
    private int blendDstFunc = GL20.GL_ONE_MINUS_SRC_ALPHA;
    private int blendSrcFuncAlpha = GL20.GL_SRC_ALPHA;
    private int blendDstFuncAlpha = GL20.GL_ONE_MINUS_SRC_ALPHA;

    private final ShaderProgram shader;
    private ShaderProgram customShader = null;
    private boolean ownsShader;

    private final Color color = new Color(1, 1, 1, 1);
    float colorPacked = Color.WHITE_FLOAT_BITS;

    /**
     * Number of render calls since the last {@link #begin()}.
     **/
    public int renderCalls = 0;

    /**
     * Number of rendering calls, ever. Will not be reset unless set manually.
     **/
    public int totalRenderCalls = 0;

    /**
     * The maximum number of sprites rendered in one batch so far.
     **/
    public int maxSpritesInBatch = 0;
    Texture texture;
    Texture alphaTexture;
    Texture defaultAlphaTexture;
    float invTexWidth = 0, invAlphaTextureWidth = 0, invTexHeight = 0, invAlphaTexHeight = 0;

    int opacityLoc = -1;
    int contrastLoc = -1;

    private float contrast = 1f;
    private float opacity = 1f;
    final int SPRITE_SIZE = 4 * (2 + 1 + 2);

    /**
     * Constructs a new SpriteBatch with a size of 1000, one buffer, and the default shader.
     *
     * @see SpriteBatch#SpriteBatch(int, ShaderProgram)
     */
    public PsychSpriteBatch() {
        this(1000, null);
    }

    /**
     * Constructs a SpriteBatch with one buffer and the default shader.
     *
     * @see SpriteBatch#SpriteBatch(int, ShaderProgram)
     */
    public PsychSpriteBatch(int size) {
        this(size, null);
    }

    /**
     * Constructs a new SpriteBatch. Sets the projection matrix to an orthographic projection with y-axis point upwards, x-axis
     * point to the right and the origin being in the bottom left corner of the screen. The projection will be pixel perfect with
     * respect to the current screen resolution.
     * <p>
     * The defaultShader specifies the shader to use. Note that the names for uniforms for this default shader are different than
     * the ones expect for shaders set with {@link #setShader(ShaderProgram)}. See {@link #createDefaultShader()}.
     *
     * @param size          The max number of sprites in a single batch. Max of 8191.
     * @param defaultShader The default shader to use. This is not owned by the SpriteBatch and must be disposed separately.
     */
    public PsychSpriteBatch(int size, ShaderProgram defaultShader) {
        // 32767 is max vertex index, so 32767 / 4 vertices per sprite = 8191 sprites max.
        if (size > 8191) throw new IllegalArgumentException("Can't have more than 8191 sprites per batch: " + size);

        Mesh.VertexDataType vertexDataType = (Gdx.gl30 != null) ? Mesh.VertexDataType.VertexBufferObjectWithVAO : defaultVertexDataType;


        mesh = new Mesh(vertexDataType, false, size * 4, size * 6,
                new VertexAttribute(VertexAttributes.Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE), new VertexAttribute(VertexAttributes.Usage.ColorPacked, 4, ShaderProgram.COLOR_ATTRIBUTE),
                new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE));

        projectionMatrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        vertices = new float[size * SPRITE_SIZE];

        int len = size * 6;
        short[] indices = new short[len];
        short j = 0;
        for (int i = 0; i < len; i += 6, j += 4) {
            indices[i] = j;
            indices[i + 1] = (short) (j + 1);
            indices[i + 2] = (short) (j + 2);
            indices[i + 3] = (short) (j + 2);
            indices[i + 4] = (short) (j + 3);
            indices[i + 5] = j;
        }
        mesh.setIndices(indices);

        if (defaultShader == null) {
            shader = createDefaultShader();
            ownsShader = true;
        } else shader = defaultShader;
//        brightnessLoc = shader.getUniformLocation("brightness");
        contrastLoc = shader.getUniformLocation("contrast");
        opacityLoc = shader.getUniformLocation("opacity");

        loadDefaultAlphaTexture();
    }

    private void loadDefaultAlphaTexture() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.Alpha);
        pixmap.setColor(0f, 0f, 0f, 1f);
        pixmap.fillRectangle(0, 0, 1, 1);
        defaultAlphaTexture = new Texture(pixmap);
        pixmap.dispose();
    }

    /**
     * Returns a new instance of the default shader used by SpriteBatch for GL2 when no shader is specified.
     */
    static public ShaderProgram createDefaultShader() {
        String vertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
                + "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
                + "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + ";\n" //
//                + "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "1;\n" //
                + "uniform mat4 u_projTrans;\n" //
                + "varying vec4 v_color;\n" //
                + "varying vec2 v_texCoord;\n" //
//                + "varying vec2 v_texCoords1;\n" //
                + "\n" //
                + "void main()\n" //
                + "{\n" //
                + "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
                + "   v_color.a = v_color.a * (255.0/254.0);\n" //
                + "   v_texCoord = " + ShaderProgram.TEXCOORD_ATTRIBUTE + ";\n" //
//                + "   v_texCoords1 = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "1;\n" //
                + "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
                + "}\n";
        String fragmentShader = "#ifdef GL_ES\n" //
                + "#define LOWP lowp\n" //
                + "precision mediump float;\n" //
                + "#else\n" //
                + "#define LOWP \n" //
                + "#endif\n" //
                + "varying LOWP vec4 v_color;\n" //
                + "varying vec2 v_texCoord;\n" //
                + "uniform sampler2D u_texture;\n" //
                + "uniform sampler2D u_mask;\n" //
                + "uniform float brightness;\n" //
                + "uniform float contrast;\n" //
                + "uniform float opacity;\n" //
                + "void main()\n"//
                + "{\n" //
                + "vec4 texColor = texture2D(u_texture, v_texCoord);\n"//
                + "vec4 mask = texture2D(u_mask, v_texCoord);\n"//
                + "gl_FragColor.a = texColor.a * mask.a;\n"//
                + "gl_FragColor.rgb = v_color.rgb * texColor.rgb;\n"//
                + "gl_FragColor.rgb /= gl_FragColor.a;\n" //ignore alpha
                + "gl_FragColor.rgb = ((gl_FragColor.rgb - 0.5) * max(contrast, 0.0)) + 0.5;\n" //apply contrast
                + "gl_FragColor.rgb *= gl_FragColor.a;\n" //return alpha
                + "gl_FragColor.a = gl_FragColor.a * opacity;\n" //
                + "}";
        ShaderProgram.pedantic = false;
        ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
        if (!shader.isCompiled()) throw new IllegalArgumentException("Error compiling shader: " + shader.getLog());
        return shader;
    }


    public void begin() {
        if (drawing) throw new IllegalStateException("SpriteBatch.end must be called before begin.");
        renderCalls = 0;
        Gdx.gl.glDepthMask(false);
        if (customShader != null) customShader.bind();
        else shader.bind();
        setupMatrices();
        drawing = true;
        shader.setUniformf(contrastLoc, contrast);
        shader.setUniformf(opacityLoc, opacity);
    }


    public void end() {
        if (!drawing) throw new IllegalStateException("SpriteBatch.begin must be called before end.");
        if (idx > 0) flush();
        drawing = false;
        GL20 gl = Gdx.gl;
        gl.glDepthMask(true);
        if (isBlendingEnabled()) gl.glDisable(GL20.GL_BLEND);
    }

    protected void setTextures(Texture texture, Texture alphaTexture) {
        flush();
        this.texture = texture;
        this.alphaTexture = alphaTexture;
        invTexWidth = 1.0f / texture.getWidth();
        invTexHeight = 1.0f / texture.getHeight();
        invAlphaTextureWidth = 1.0f / alphaTexture.getWidth();
        invAlphaTexHeight = 1.0f / alphaTexture.getHeight();
    }

    public void draw(Sprite sprite) {
        draw(sprite, defaultAlphaTexture);
    }

    public void draw(Sprite sprite, Texture alphaTexture) {
        draw(sprite, alphaTexture, sprite.getVertices(), 0, SPRITE_SIZE);
    }


    public void draw(Sprite sprite, Texture alphaTexture, float[] spriteVertices, int offset, int count) {
        if (!drawing) throw new IllegalStateException("SpriteBatch.begin must be called before draw.");

        int verticesLength = vertices.length;
        int remainingVertices = verticesLength;

        if (sprite.getTexture() != texture || this.alphaTexture != alphaTexture)
            setTextures(sprite.getTexture(), alphaTexture);
        else {
            remainingVertices -= idx;
            if (remainingVertices == 0) {
                flush();
                remainingVertices = verticesLength;
            }
        }

        int copyCount = Math.min(remainingVertices, count);

        System.arraycopy(spriteVertices, offset, vertices, idx, copyCount);
        idx += copyCount;
        count -= copyCount;
        while (count > 0) {
            offset += copyCount;
            flush();
            copyCount = Math.min(verticesLength, count);
            System.arraycopy(spriteVertices, offset, vertices, 0, copyCount);
            idx += copyCount;
            count -= copyCount;
        }
    }


    public void draw(Sprite sprite, Sprite alphaSprite, float x, float y, float width, float height, Color[] vertexColors) {
        if (!drawing) throw new IllegalStateException("SpriteBatch.begin must be called before draw.");

        float[] vertices = this.vertices;

        if (sprite.getTexture() != texture || alphaSprite.getTexture() != alphaTexture) {
            setTextures(sprite.getTexture(), alphaSprite.getTexture());
        }

        if (idx == vertices.length) //
            flush();

        final float fx2 = x + width;
        final float fy2 = y + height;
        final float color_u = sprite.getU();
        final float color_v = sprite.getV2();
        final float color_u2 = sprite.getU2();
        final float color_v2 = sprite.getV();
        final float alpha_u = alphaSprite.getU();
        final float alpha_v = alphaSprite.getV2();
        final float alpha_u2 = alphaSprite.getU2();
        final float alpha_v2 = alphaSprite.getV();

        vertices[idx++] = x;
        vertices[idx++] = y;
        vertices[idx++] = vertexColors[0].toFloatBits();
        vertices[idx++] = color_u;
        vertices[idx++] = color_v;
        vertices[idx++] = alpha_u;
        vertices[idx++] = alpha_v;

        vertices[idx++] = x;
        vertices[idx++] = fy2;
        vertices[idx++] = vertexColors[1].toFloatBits();
        vertices[idx++] = color_u;
        vertices[idx++] = color_v2;
        vertices[idx++] = alpha_u;
        vertices[idx++] = alpha_v2;

        vertices[idx++] = fx2;
        vertices[idx++] = fy2;
        vertices[idx++] = vertexColors[2].toFloatBits();
        vertices[idx++] = color_u2;
        vertices[idx++] = color_v2;
        vertices[idx++] = alpha_u2;
        vertices[idx++] = alpha_v2;

        vertices[idx++] = fx2;
        vertices[idx++] = y;
        vertices[idx++] = vertexColors[3].toFloatBits();
        vertices[idx++] = color_u2;
        vertices[idx++] = color_v;
        vertices[idx++] = alpha_u2;
        vertices[idx++] = alpha_v;
    }


    public void setColor(Color tint) {
        color.set(tint);
        colorPacked = tint.toFloatBits();
    }


    public void setColor(float r, float g, float b, float a) {
        color.set(r, g, b, a);
        colorPacked = color.toFloatBits();
    }


    public Color getColor() {
        return color;
    }


    public void setPackedColor(float packedColor) {
        Color.abgr8888ToColor(color, packedColor);
        this.colorPacked = packedColor;
    }


    public float getPackedColor() {
        return colorPacked;
    }


    public void flush() {
        if (idx == 0) return;

        renderCalls++;
        totalRenderCalls++;
        int spritesInBatch = idx / 20;
        if (spritesInBatch > maxSpritesInBatch) maxSpritesInBatch = spritesInBatch;
        int count = spritesInBatch * 6;

        texture.bind(0);
        alphaTexture.bind(1);

        Mesh mesh = this.mesh;
        mesh.setVertices(vertices, 0, idx);
        ((Buffer) mesh.getIndicesBuffer()).position(0);
        ((Buffer) mesh.getIndicesBuffer()).limit(count);

        if (blendingDisabled) {
            Gdx.gl.glDisable(GL20.GL_BLEND);
        } else {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            if (blendSrcFunc != -1)
                Gdx.gl.glBlendFuncSeparate(blendSrcFunc, blendDstFunc, blendSrcFuncAlpha, blendDstFuncAlpha);
        }

        mesh.render(customShader != null ? customShader : shader, GL20.GL_TRIANGLES, 0, count);

        idx = 0;
    }


    public void disableBlending() {
        if (blendingDisabled) return;
        flush();
        blendingDisabled = true;
    }


    public void enableBlending() {
        if (!blendingDisabled) return;
        flush();
        blendingDisabled = false;
    }


    public void setBlendFunction(int srcFunc, int dstFunc) {
        setBlendFunctionSeparate(srcFunc, dstFunc, srcFunc, dstFunc);
    }


    public void setBlendFunctionSeparate(int srcFuncColor, int dstFuncColor, int srcFuncAlpha, int dstFuncAlpha) {
        if (blendSrcFunc == srcFuncColor && blendDstFunc == dstFuncColor && blendSrcFuncAlpha == srcFuncAlpha && blendDstFuncAlpha == dstFuncAlpha)
            return;
        flush();
        blendSrcFunc = srcFuncColor;
        blendDstFunc = dstFuncColor;
        blendSrcFuncAlpha = srcFuncAlpha;
        blendDstFuncAlpha = dstFuncAlpha;
    }


    public int getBlendSrcFunc() {
        return blendSrcFunc;
    }


    public int getBlendDstFunc() {
        return blendDstFunc;
    }


    public int getBlendSrcFuncAlpha() {
        return blendSrcFuncAlpha;
    }


    public int getBlendDstFuncAlpha() {
        return blendDstFuncAlpha;
    }


    public void dispose() {
        mesh.dispose();
        if (ownsShader && shader != null) shader.dispose();
    }


    public Matrix4 getProjectionMatrix() {
        return projectionMatrix;
    }


    public Matrix4 getTransformMatrix() {
        return transformMatrix;
    }


    public void setProjectionMatrix(Matrix4 projection) {
        if (drawing) flush();
        projectionMatrix.set(projection);
        if (drawing) setupMatrices();
    }


    public void setTransformMatrix(Matrix4 transform) {
        if (drawing) flush();
        transformMatrix.set(transform);
        if (drawing) setupMatrices();
    }

    protected void setupMatrices() {
        combinedMatrix.set(projectionMatrix).mul(transformMatrix);
        if (customShader != null) {
            customShader.setUniformMatrix("u_projTrans", combinedMatrix);
            customShader.setUniformi("u_texture", 0);
            customShader.setUniformi("u_mask", 1);
        } else {
            shader.setUniformMatrix("u_projTrans", combinedMatrix);
            shader.setUniformi("u_texture", 0);
            shader.setUniformi("u_mask", 1);
        }
    }

    public void setShader(ShaderProgram shader) {
        if (drawing) {
            flush();
        }
        customShader = shader;
        if (drawing) {
            if (customShader != null) customShader.bind();
            else this.shader.bind();
            setupMatrices();
        }
    }


    public ShaderProgram getShader() {
        if (customShader == null) {
            return shader;
        }
        return customShader;
    }


    public boolean isBlendingEnabled() {
        return !blendingDisabled;
    }

    public boolean isDrawing() {
        return drawing;
    }

    public float getContrast() {
        return contrast;
    }

    public PsychSpriteBatch setContrast(float contrast) {
        if (contrast > 2f || contrast < 0) {
            throw new IllegalArgumentException("contrast must be between 0.0 and 2.0");
        }
        this.contrast = contrast;
        shader.setUniformf(contrastLoc, contrast);
        return this;
    }

    public float getOpacity() {
        return opacity;
    }

    public PsychSpriteBatch setOpacity(float opacity) {
        if (opacity > 1f || opacity < 0) throw new IllegalArgumentException("opacity must be between 0.0 and 1.0");
        this.opacity = opacity;
        shader.setUniformf(opacityLoc, opacity);
        return this;
    }


}
