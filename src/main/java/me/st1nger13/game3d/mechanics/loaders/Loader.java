package me.st1nger13.game3d.mechanics.loaders;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.matthiasmann.twl.utils.PNGDecoder;
import me.st1nger13.game3d.GameHelper;
import me.st1nger13.game3d.mechanics.Mesh;
import me.st1nger13.game3d.mechanics.MeshData;
import me.st1nger13.game3d.mechanics.TextureData;
import me.st1nger13.game3d.mechanics.render.gui.Texture;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.TextureLoader;

/**
 * Created by thinMatrix
 * Modified by st1nger13
 * Loader
 */
public class Loader {
    private Map<Integer, List<Integer>> vaoMap = new HashMap<>() ;
    private List<Integer> textures = new ArrayList<Integer>() ;


    public Mesh loadToVAO(float[] positions, float[] textureCoords, int[] indices, float[] normals) {
        int vaoID = createAndBindVAO() ;
        bindIndicesBuffer(vaoID, indices) ;
        putData(vaoID, (byte) 3, 0, positions) ;
        putData(vaoID, (byte) 2, 1, textureCoords) ;
        putData(vaoID, (byte) 3, 2, normals) ;
        GL30.glBindVertexArray(0) ;

        return new Mesh(vaoID, indices.length) ;
    }

    public Mesh loadToVAO( float[] positions, float[] textureCoords, int[] indices) {
        int vaoID = createAndBindVAO() ;
        bindIndicesBuffer(vaoID, indices) ;
        putData(vaoID, (byte) 3, 0, positions) ;
        putData(vaoID, (byte) 2, 1, textureCoords) ;
        //putData((byte) 3, 2, normals) ;
        GL30.glBindVertexArray(0) ;

        return new Mesh(vaoID, indices.length) ;
    }

    public Mesh loadToVAO(float[] positions) {
       return loadToVAO(positions, 2) ;
    }

    public Mesh loadToVAO(float[] positions, int dimensions) {
        int vaoID = createAndBindVAO() ;
        this.putData(vaoID, (byte)dimensions, 0, positions) ;
        GL30.glBindVertexArray(0) ;

        return new Mesh(vaoID, positions.length/2) ;
    }

    public Texture loadTexture(String fileName) {
        org.newdawn.slick.opengl.Texture texture = null ;
        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream(GameHelper.files.getFile("resources/textures/" + fileName + ".png"))) ;
            /** MipMapping */
            GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D) ;
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR) ;
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -1.4f) ;

        }
        catch (IOException e) {
            e.printStackTrace() ;
        }
        int textureID = texture.getTextureID() ;
        textures.add( textureID ) ;

        return new Texture(textureID) ;
    }


    public Mesh loadObj(String fileName) {
        MeshData meshData = OBJFileLoader.loadOBJ(fileName) ;
        return loadToVAO(meshData.getVertexCoordinates(), meshData.getTextureCoordinates(),
                meshData.getIndices(), meshData.getNormals()) ;
    }

    public int loadCubeMapTexture(String[] names) {
        int textureID = GL11.glGenTextures() ;
        GL13.glActiveTexture(GL13.GL_TEXTURE0) ;
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, textureID) ;
        int i = 0 ;
        for(String name : names) {
            TextureData textureData = decodeTextureFile(GameHelper.files.getFile("resources/textures/cubemap/" + name + ".png")) ;
            GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i++, 0, GL11.GL_RGBA, textureData.getWidth(), textureData.getHeight(), 0,
                    GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, textureData.getBuffer()) ;
        }
        GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR) ;
        GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR) ;
        return textureID ;
    }


    /** Create a new empty VAO
     * @return id - ID of VAO binded in OpenGL
     */
    public int createAndBindVAO() {
        int vaoID = GL30.glGenVertexArrays() ;
        vaoMap.put(vaoID, new ArrayList<>()) ;
        GL30.glBindVertexArray( vaoID ) ;
        return vaoID ;
    }

    private void putData(int vaoID, byte coordSize, int attribNumber, float[] data ) {
        int vboID = GL15.glGenBuffers() ;
        vaoMap.get(vaoID).add(vboID) ;
        GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, vboID ) ;
        FloatBuffer buffer = storeDataInFloatBuffer( data ) ;
        GL15.glBufferData( GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW ) ;
        GL20.glVertexAttribPointer( attribNumber, coordSize, GL11.GL_FLOAT, false, 0, 0 ) ;
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0 ) ;
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length) ;
        buffer.put(data) ;
        buffer.flip() ;

        return buffer ;
    }

    public void deleteVAO(int vaoID) {
        GL30.glDeleteVertexArrays(vaoID) ;
        vaoMap.get(vaoID).forEach(GL15::glDeleteBuffers) ;
        vaoMap.remove(vaoID) ;
    }

    public void deleteTexture(int textureID) {
        if(textures.contains(textureID)) {
            GL11.glDeleteTextures(textureID) ;
            textures.remove((Object) textureID) ;
        }
    }

    public void cleanUp() {
        for(int vaoID : vaoMap.keySet()) {
            GL30.glDeleteVertexArrays(vaoID) ;
            vaoMap.get(vaoID).forEach(GL15::glDeleteBuffers);
        }
        vaoMap.clear() ;

        for(int texture : textures)
            GL11.glDeleteTextures(texture) ;
        textures.clear() ;
    }

    public void bindIndicesBuffer(int vaoID, int[] indices) {
        int vboID = GL15.glGenBuffers() ;
        vaoMap.get(vaoID).add(vboID) ;
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID) ;
        IntBuffer buffer = storeDataInIntBuffer(indices) ;
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW) ;

    }

    private IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length) ;
        buffer.put(data) ;
        buffer.flip() ;

        return buffer ;
    }

    private void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures, List<Vector3f> normals, float[] textureArray, float[] normalsArray) {

        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);
        Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
        textureArray[currentVertexPointer * 2] = currentTex.x;
        textureArray[currentVertexPointer * 2 + 1] = 1 - currentTex.y;
        Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
        normalsArray[currentVertexPointer * 3] = currentNorm.x;
        normalsArray[currentVertexPointer * 3 + 1] = currentNorm.y;
        normalsArray[currentVertexPointer * 3 + 2] = currentNorm.z;

    }

    private TextureData decodeTextureFile(File file) {
        int width = 0;
        int height = 0;
        ByteBuffer buffer = null;
        try {
            FileInputStream in = new FileInputStream(file);
            PNGDecoder decoder = new PNGDecoder(in);
            width = decoder.getWidth();
            height = decoder.getHeight();
            buffer = ByteBuffer.allocateDirect(4 * width * height);
            decoder.decode(buffer, width * 4, PNGDecoder.Format.RGBA);
            buffer.flip();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Tried to load texture " + file.getName() + ", didn't work");
            System.exit(-1);
        }
        return new TextureData(buffer, width, height);
    }
}