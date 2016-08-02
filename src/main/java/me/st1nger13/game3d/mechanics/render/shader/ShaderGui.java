package me.st1nger13.game3d.mechanics.render.shader;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

public class ShaderGui extends ShaderProgram{

    private static final String VERTEX_FILE = "resources/shaders/gui/vert.shader";
    private static final String FRAGMENT_FILE = "resources/shaders/gui/frag.shader";
    private int location_transformationMatrix ;
    private int location_color ;
    private int location_isUseColorFlag ;


    public ShaderGui() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadTransformation(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadColor(Vector4f color) {
        super.loadBoolean(location_isUseColorFlag, true) ;
        super.loadVector4f(location_color, color) ;
    }

    public void disableColoring() {
        super.loadBoolean(location_isUseColorFlag, false) ;
    }

    @Override
    protected void getAllUniformLocation() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix") ;
        location_color = super.getUniformLocation("color") ;
        location_isUseColorFlag = super.getUniformLocation("isUseColor") ;
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
}