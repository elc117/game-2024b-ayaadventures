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
    private Texture backGround, tPlayButton;
    private Sprite playButton;
    private Sound clickSound, buttonhover;
    private boolean hoverSoundPlayed = false;


    public HomeScreen(Main game) {
        this.game = game;

        tPlayButton = new Texture("play.png");
        playButton = new Sprite(tPlayButton);
        playButton.setSize(playButton.getWidth() / 2.5f, playButton.getHeight()/ 2.5f);

        float buttonX = (game.getCamera().viewportWidth - playButton.getWidth()) / 2;
        float buttonY = game.getCamera().viewportHeight / 3.5f;


        playButton.setPosition(buttonX, buttonY);

        backGround = new Texture("background.png");

        clickSound = Gdx.audio.newSound(Gdx.files.internal("meow.ogg"));
        buttonhover = Gdx.audio.newSound(Gdx.files.internal("button hover.mp3"));

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

        playButton.draw(game.getSpriteBatch());

        game.getSpriteBatch().end();

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        game.getFitViewport().unproject(mousePos);

        if (playButton.getBoundingRectangle().contains(mousePos.x, mousePos.y)) {
            if (!hoverSoundPlayed) {
                buttonhover.play();
                hoverSoundPlayed = true;
            }
        } else {
            hoverSoundPlayed = false;
        }

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
        tPlayButton.dispose();
    }
}
