package com.aventurasaya.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class gameOverScreen implements Screen {

    private Texture tela;
    private Main game;
    private float tempo = 0;

    public gameOverScreen(Main game) {
        this.game = game;
        tela = new Texture("gameover.png");
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.getFitViewport().apply();
        game.getSpriteBatch().begin();

        game.getSpriteBatch().draw(tela,
            (game.getCamera().viewportWidth - tela.getWidth()) / 2,
            (game.getCamera().viewportHeight - tela.getHeight()) / 2);

        game.getSpriteBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        game.getFitViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        tela.dispose();
    }
}
