package me.st1nger13.game3d.mechanics.render.shader;

import me.st1nger13.game3d.world.Camera;
import me.st1nger13.game3d.utils.MathHelper;
import org.lwjgl.util.vector.Matrix4f;

public class ShaderEntity extends ShaderProgram{

    private static final String VERTEX_FILE = "resources/shaders/entity/vert.shader";
    private static final String FRAGMENT_FILE = "resources/shaders/entity/frag.shader";
    private int location_transformationMatrix ;
    private int location_projectionMatrix ;
    private int location_viewMatrix ;
    private int location_transparencyChannelMode ;


    public ShaderEntity() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadTransformation(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void enableTransparencyChannel() {
        super.loadBoolean(location_transparencyChannelMode, true) ;
    }

    public void disableTransparencyChannel() {
        super.loadBoolean(location_transparencyChannelMode, false) ;
    }

    @Override
    protected void getAllUniformLocation() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix") ;
        location_projectionMatrix = super.getUniformLocation( "projectionMatrix" ) ;
        location_viewMatrix = super.getUniformLocation( "viewMatrix" ) ;
        location_transparencyChannelMode = super.getUniformLocation("transparencyChannel") ;
    }

    public void loadProjectionMatrix( Matrix4f matrix )
    {
        super.loadMatrix( location_projectionMatrix, matrix ) ;
    }

    public void loadViewMatrix(Camera camera)
    {
        Matrix4f matrix = MathHelper.createViewMatrix(camera) ;
        super.loadMatrix( location_viewMatrix, matrix ) ;
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
}