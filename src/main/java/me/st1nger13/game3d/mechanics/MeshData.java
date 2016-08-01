package me.st1nger13.game3d.mechanics;

/**
 * Created by st1nger13 on 29.07.16.
 * The Structure to temporary store Mesh Data.
 * @see Mesh
 */
public class MeshData {
    private float[] vertexCoordinates ;
    private float[] textureCoordinates ;
    private float[] normals ;
    private int[] indices ;

    public MeshData(float[] vertexCoordinates, float[] textureCoordinates, float[] normals, int[] indices) {
        this.vertexCoordinates = vertexCoordinates ;
        this.textureCoordinates = textureCoordinates ;
        this.normals = normals ;
        this.indices = indices ;
    }

    public float[] getVertexCoordinates() {
        return vertexCoordinates ;
    }

    public float[] getTextureCoordinates() {
        return textureCoordinates ;
    }

    public float[] getNormals() {
        return normals ;
    }

    public int[] getIndices() {
        return indices ;
    }
}