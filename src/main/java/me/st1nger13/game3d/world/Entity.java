package me.st1nger13.game3d.world;

/**
 * Created by st1nger13 on 30.07.16.
 * Entity
 */
import me.st1nger13.game3d.GameHelper;
import me.st1nger13.game3d.mechanics.Mesh;
import me.st1nger13.game3d.mechanics.render.gui.Texture;
import org.lwjgl.util.vector.Vector3f;

public class Entity
{
    private Texture texture ;
    private Mesh mesh ;
    private Vector3f position ;
    private Vector3f rotation ;
    private float scale ;
    public boolean isHasTransparency ;
    public boolean isHasTransparencyChannel ;

    private Entity() {}

    public void increasePosition( float dx, float dy, float dz )
    {
        this.position.x = position.x + dx ;
        this.position.y = position.y + dy ;
        this.position.z = position.z + dz ;
    }

    public void increaseRotation( float dx, float dy, float dz )
    {
        this.rotation.x += dx ;
        this.rotation.y += dy ;
        this.rotation.z += dz ;
    }

    public void setPosition(Vector3f position) {
        this.position.x = position.x ;
        this.position.y = position.y ;
        this.position.z = position.z ;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation.x = rotation.x ;
        this.rotation.y = rotation.y ;
        this.rotation.z = rotation.z ;
    }


    public void setScale(float scale)
    {
        this.scale = scale;
    }

    public Vector3f getPosition()
    {
        return position;
    }

    public Vector3f getRotation() {
        return rotation ;
    }

    public float getScale()
    {
        return scale;
    }

    public Texture getTexture() {
        return texture ;
    }

    public Mesh getMesh() {
        return mesh ;
    }

    /**
     * Created by st1nger13 on 1.08.16.
     * Entity Builder.
     */
    public static EntityBuilder newBuilder() {
        return new Entity().new EntityBuilder() ;
    }

    public class EntityBuilder {
        private EntityBuilder() {}

        public EntityBuilder setMesh(Mesh mesh) {
            Entity.this.mesh = mesh ;
            return this ;
        }

        /**
         * Loads a new Mesh via Loader.
         * @param meshName - Name of a OBJ file
         * @return new Mesh
         */
        public EntityBuilder loadMesh(String meshName) {
            Entity.this.mesh = GameHelper.loader.loadObj(meshName) ;
            return this ;
        }

        public EntityBuilder setTexture(Texture texture) {
            Entity.this.texture = texture ;
            return this ;
        }

        /**
         * Loads a new Texture via Loader.
         * @param textureName - Name of a PNG file
         * @return new Texture
         */
        public EntityBuilder loadTexture(String textureName) {
            Entity.this.texture = GameHelper.loader.loadTexture(textureName) ;
            return this ;
        }

        public EntityBuilder setPosition(float x, float y, float z) {
            Entity.this.position = new Vector3f(x, y, z) ;
            return this ;
        }

        public EntityBuilder setRotation(float rotX, float rotY, float rotZ) {
            Entity.this.rotation = new Vector3f(rotX, rotY, rotZ) ;
            return this ;
        }

        public EntityBuilder setScale(float scale) {
            Entity.this.scale = scale ;
            return this ;
        }

        public EntityBuilder enableTransparency() {
            Entity.this.isHasTransparency = true ;
            return this ;
        }

        public EntityBuilder enableTransparencyChannel() {
            Entity.this.isHasTransparencyChannel = true ;
            return this ;
        }

        public Entity build() {
            if(Entity.this.position == null) {
                Entity.this.position = new Vector3f(0, 0, 0) ;
                throw new IllegalStateException("Didn't set a position! Default value will be used {0, 0, 0}");
            }

            if(Entity.this.mesh == null) {
                throw new IllegalStateException("Didn't set a mesh!");
            }

            if(Entity.this.texture == null) {
                throw new IllegalStateException("Didn't set a texture!");
            }

            return Entity.this ;
        }
    }
}