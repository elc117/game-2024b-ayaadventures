package com.aventurasaya.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */

public class Main extends Game {

    static public final float WORLD_WIDTH = 1280;
    static public final float WORLD_HEIGHT = 720;
    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private FitViewport fitViewport;
    private Vector2 savedAyaPosition;


    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        fitViewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        spriteBatch = new SpriteBatch();

        this.setScreen(new HomeScreen(this));
    }

    public Vector2 getSavedAyaPosition() {
        return savedAyaPosition;
    }

    public void setSavedAyaPosition(Vector2 position) {
        this.savedAyaPosition = position;
    }


    public FitViewport getFitViewport() {
        return fitViewport;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    @Override
    public void render() {
        camera.update();
        super.render();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }
}

