package me.st1nger13.game3d.mechanics.render.shader;

import me.st1nger13.game3d.utils.MathHelper;
import me.st1nger13.game3d.world.Camera;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class ShaderSkybox extends ShaderProgram {

    private static final String VERTEX_FILE = "resources/shaders/skybox/vert.shader";
    private static final String FRAGMENT_FILE = "resources/shaders/skybox/frag.shader";
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_transformationMatrix;
    private int location_skyColor;
    private int location_cubeMapDay;
    private int location_cubeMapNight;
    private int location_blendFactor;
    private int location_viewMode;
    private int location_colorBlendFactor;

    float currentRotation = 1 ;

    public ShaderSkybox() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(location_projectionMatrix, matrix);
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadViewModeState(int state, float colorBlendFactor) {
        super.loadInt(location_viewMode, state);
        super.loadFloat(location_colorBlendFactor, colorBlendFactor);
    }

    public void loadViewMatrix(Camera camera) {

        Matrix4f matrix = MathHelper.createViewMatrix(camera);
        matrix.m30 = 0;
        matrix.m31 = 0;
        matrix.m32 = 0;
        Matrix4f.rotate((float) Math.toRadians(currentRotation), new Vector3f(0, 1, 0), matrix, matrix);
        super.loadMatrix(location_viewMatrix, matrix);
    }

    public void loadSkyColor(float r, float g, float b) {
        super.loadVector(location_skyColor, new Vector3f(r, g, b));
    }

    public void loadBlendFactor(float arg) {
        super.loadFloat(location_blendFactor, arg);
    }

    public void connectTextureUnits() {
        super.loadInt(location_cubeMapDay, 0);
    }

    @Override
    protected void getAllUniformLocation() {
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_skyColor = super.getUniformLocation("skyColor");
        location_cubeMapDay = super.getUniformLocation("cubeMapDay");
        location_cubeMapNight = super.getUniformLocation("cubeMapNight");
        location_blendFactor = super.getUniformLocation("blendFactor");
        location_viewMode = super.getUniformLocation("viewMode");
        location_colorBlendFactor = super.getUniformLocation("colorBlendFactor");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
}