package com.aventurasaya.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector3;

/** First screen of the application. Displayed after the application is created. */
public class HomeScreen implements Screen {

    private final Main game;
    private Texture backGround, tPlayButton, title;
    private Sprite playButton;
    private Sound clickSound;

    public HomeScreen(Main game) {
        this.game = game;

        tPlayButton = new Texture("start.png");
        playButton = new Sprite(tPlayButton);
        playButton.setSize(playButton.getWidth() /3f, playButton.getHeight()/ 3f);

        float buttonX = (game.getCamera().viewportWidth - playButton.getWidth()) / 2;
        float buttonY = game.getCamera().viewportHeight / 3f;


        playButton.setPosition(buttonX, buttonY);

        backGround = new Texture("background.png");

        title = new Texture("title.png");

        clickSound = Gdx.audio.newSound(Gdx.files.internal("meow.ogg"));
    }


    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void render(float delta) {
        game.getFitViewport().apply();
        game.getCamera().update();
        game.getSpriteBatch().setProjectionMatrix(game.getCamera().combined);

        game.getSpriteBatch().begin();

        game.getSpriteBatch().draw(backGround, 0, 0, game.getCamera().viewportWidth, game.getCamera().viewportHeight);

        // Define o tamanho e a posição do título
        float titleWidth = title.getWidth() * 0.8f;  // Reduzir para 80% da largura original
        float titleHeight = title.getHeight() * 0.8f; // Reduzir para 80% da altura original
        float titleX = (game.getCamera().viewportWidth - titleWidth) / 2; // Centraliza no eixo X
        float titleY = game.getCamera().viewportHeight * 0.5f; // Define a posição no eixo Y


        game.getSpriteBatch().draw(title, titleX, titleY, titleWidth, titleHeight);
        playButton.draw(game.getSpriteBatch());

        game.getSpriteBatch().end();

        checkButtonPress();
    }

    void checkButtonPress() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.getFitViewport().unproject(touchPos);
            if (playButton.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
                clickSound.play();
                game.setScreen(new GameScreen(game));
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        game.getFitViewport().update(width, height, true);
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

    @Override
    public void dispose() {
        backGround.dispose();
        title.dispose();
        tPlayButton.dispose();
    }
}
