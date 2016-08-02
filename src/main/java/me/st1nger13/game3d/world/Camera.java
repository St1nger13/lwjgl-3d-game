package me.st1nger13.game3d.world;

/**
 * Created by st1nger13 on 30.07.16.
 */
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
    private final float MIN_DISTANCE = 20f ;
    private final float MAX_DISTANCE = 4000f ;
    private final Vector3f targetPosition = new Vector3f(409.5f, -41f, 646.5f) ;
    private Vector3f position = new Vector3f(0, 0, 0);
    private float pitch = 0f;
    private float yaw = 0f;
    private float roll = 0f ;

    private float distanceFromPlayer = 50 ;
    private float angleAroundPalyer = 0 ;

    public Camera(float x, float y, float z) {
        this.position = new Vector3f(x, y, z);
    }

    public void move()
    {
        calculatePitch() ;
        calculateZoom() ;

        if( Mouse.isButtonDown(0) )
        {
            float angleChange = Mouse.getDX() * 0.3f ;
            angleAroundPalyer -= angleChange ;
        }

        float horizontalDistance = (float) (distanceFromPlayer * Math.cos( Math.toRadians( pitch ))) ;
        float verticalDistance = (float) (distanceFromPlayer * Math.sin( Math.toRadians( pitch ))) ;

        float theta = angleAroundPalyer ;
        float offsetX = (float) (horizontalDistance * Math.sin( Math.toRadians( theta ))) ;
        float offsetZ = (float) (horizontalDistance * Math.cos( Math.toRadians( theta ))) ;

        this.position.x = targetPosition.x + offsetX ;
        this.position.y = targetPosition.y + verticalDistance ;
        this.position.z = targetPosition.z + offsetZ ;

        this.yaw = 0 - angleAroundPalyer ;
    }
    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getRoll() {
        return roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }

    public Vector3f getPosition() {
        return position ;
    }

    private void calculatePitch()
    {
        if( Mouse.isButtonDown(1) )
        {
            float pitchChange = Mouse.getDY() * 0.1f ;
            pitch -= pitchChange ;
        }
    }

    private void calculateZoom() {
        float zoomLvl = Mouse.getDWheel() * 0.1f;
        distanceFromPlayer -= zoomLvl;
        if(distanceFromPlayer > MAX_DISTANCE)
            distanceFromPlayer = MAX_DISTANCE ;
        if(distanceFromPlayer < MIN_DISTANCE)
            distanceFromPlayer = MIN_DISTANCE ;
    }
}
