package com.aventurasaya.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {

    private final Main game;
    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private Texture backGround;
    private Player p;
    private Display display;

    public GameScreen(Main game) {
        this.game = game;

        this.camera = game.getCamera();
        this.spriteBatch = game.getSpriteBatch();
        this.backGround = new Texture("Game_Background.png");
        p = new Player(5);

        display = new Display(spriteBatch);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(backGround, 0, 0, Main.WORLD_WIDTH, Main.WORLD_HEIGHT);

        display.desenhaVidas(p, spriteBatch);

        spriteBatch.end();
    }


    @Override
    public void dispose() {
      backGround.dispose();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, Main.WORLD_WIDTH, Main.WORLD_HEIGHT);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

}


