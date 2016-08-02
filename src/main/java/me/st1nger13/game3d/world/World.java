package me.st1nger13.game3d.world;

import me.st1nger13.game3d.GameHelper;
import me.st1nger13.game3d.mechanics.Mesh;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by st1nger13 on 31.07.16.
 * Game World
 */
public class World {
    private Camera camera ;
    private List<Entity> entities ;

    public Entity e ;

    public World() {
        entities = new ArrayList<>() ;
    }

    public void load() {
        camera = new Camera(410, -37, 0) ;

        Entity diskEntity = Entity.newBuilder()
                .loadMesh("disk")
                .loadTexture("disk2")
                .setPosition(24f, -470f, -442f)
                .setRotation(-45.5f, 100.5f, 12f)
                .setScale(5.3999f)
                .enableTransparency()
                .enableTransparencyChannel()
                .build() ;
        entities.add(diskEntity) ;

        Mesh planetMesh = GameHelper.loader.loadObj("earthhalf") ;
        Entity earthEntity = Entity.newBuilder()
                .setMesh(planetMesh)
                .loadTexture("earth4")
                .setPosition(0f, -600f, -600f)
                .setRotation(0f, 93f, -45f)
                .setScale(5.3999f)
                .build() ;
        entities.add(earthEntity) ;

        Entity armosphereEntity = Entity.newBuilder()
                .setMesh(planetMesh)
                .loadTexture("earth_clouds2")
                .setPosition(0f, -600f, -600f)
                .setRotation(0f, 93f, -45f)
                .setScale(5.4999f)
                .enableTransparency()
                .enableTransparencyChannel()
                .build() ;
        entities.add(armosphereEntity) ;

        Entity shipEntity = Entity.newBuilder()
                .loadMesh("ship")
                .loadTexture("ship")
                .setPosition(377f, -57f, 747f)
                .setRotation(0f, 0f, 0f)
                .setScale(1f)
                .build() ;
        entities.add(shipEntity) ;

        Entity couchEntity = Entity.newBuilder()
                .loadMesh("couch")
                .loadTexture("couch")
                .setPosition(377f, -57f, 747f)
                .setRotation(0f, 0f, 0f)
                .setScale(1f)
                .build() ;
        entities.add(couchEntity) ;

        Entity astroEntity = Entity.newBuilder()
                .loadMesh("astronaut")
                .loadTexture("astronaut")
                .setPosition(377f, -57f, 747f)
                .setRotation(0f, 0f, 0f)
                .setScale(1f)
                .build() ;
        //astroEntity.getMesh().cullFace = GL11.GL_FRONT ;
        entities.add(astroEntity) ;

        Entity sunEntity = Entity.newBuilder()
                .loadMesh("sun")
                .loadTexture("sun")
                .setPosition(1414.5f, -864f, -2238.5f)
                .setRotation(53f, 24.5f, 11.5f)
                .setScale(21.7f)
                .enableTransparency()
                .enableTransparencyChannel()
                .build() ;
        entities.add(sunEntity) ;

        e = sunEntity ;
    }

    public List<Entity> getEntities() {
        if(entities.size() == 0)
            throw new IllegalStateException("World is empty!") ;
        return entities ;
    }

    public Camera getCamera() {
        return camera ;
    }
}
