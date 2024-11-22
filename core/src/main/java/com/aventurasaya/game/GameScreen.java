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
    private Texture backGround, tHeart;
    private Sprite heart;
    private Player p;


    public GameScreen(Main game) {
        this.game = game;

        this.camera = game.getCamera();
        this.spriteBatch = game.getSpriteBatch();
        this.backGround = new Texture("Game_Background.png");
        p = new Player(5);

        tHeart = new Texture("heart.png");
        heart = new Sprite(tHeart);
        heart.setSize(heart.getWidth() /10f, heart.getHeight() / 10f);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        spriteBatch.setProjectionMatrix(camera.combined);
        game.getSpriteBatch().begin();
        game.getSpriteBatch().draw(backGround, 0, 0, Main.WORLD_WIDTH, Main.WORLD_HEIGHT);

        for (int i = 0; i < p.getVidas(); i++) {
            float xOffset = i * (heart.getWidth() + 10); // Espaçamento de 10 pixels entre os corações
            heart.setPosition(Main.WORLD_WIDTH - 60 - xOffset, // Começa no canto superior direito
                Main.WORLD_HEIGHT - heart.getHeight() - 40); // Y fixo no topo
            heart.draw(game.getSpriteBatch());
        }

        game.getSpriteBatch().end();
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


