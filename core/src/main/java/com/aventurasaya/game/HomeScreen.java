package com.aventurasaya.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/** First screen of the application. Displayed after the application is created. */
public class HomeScreen implements Screen {

    private final Main game;
    private Texture backGround;
    private Sound clickSound, buttonhover;
    private Rectangle playButtonArea; 
    private boolean hoverSoundPlayed = false;

    public HomeScreen(Main game) {
        this.game = game;

        // Define a área do botão
        float buttonWidth = 100; 
        float buttonHeight = 50; 
        float buttonX = game.getCamera().viewportWidth - buttonWidth - 750; 
        float buttonY = 250; 

        playButtonArea = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);

        backGround = new Texture("homescreen.png");

        clickSound = Gdx.audio.newSound(Gdx.files.internal("meow.ogg"));
        buttonhover = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));
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

        game.getSpriteBatch().end();

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        game.getFitViewport().unproject(mousePos);

        if (playButtonArea.contains(mousePos.x, mousePos.y)) {
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
            if (playButtonArea.contains(touchPos.x, touchPos.y)) {
                clickSound.play();
                game.setScreen(new StoryScreen(game));
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
        clickSound.dispose();
        buttonhover.dispose();
    }
}
