package me.st1nger13.game3d.states;

/**
 * Created by st1nger13 on 27.07.16.
 * Screen
 */
public interface Screen {
    void init() ;
    void update(float delta) ;
    void draw() ;
    void dispose() ;
}
