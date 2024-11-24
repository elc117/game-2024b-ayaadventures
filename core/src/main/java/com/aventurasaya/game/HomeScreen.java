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
        playButton.setCenter(Main.WORLD_WIDTH / 2f, Main.WORLD_HEIGHT / 3f);

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
        game.getSpriteBatch().begin();

        checkButtonPress();

        game.getSpriteBatch().draw(backGround, 0, 0, Main.WORLD_WIDTH, Main.WORLD_HEIGHT);
        // Define o tamanho e a posição do título
        float titleWidth = title.getWidth() * 0.8f;  // Reduz para 50% da largura
        float titleHeight = title.getHeight() * 0.8f; // Reduz para 50% da altura
        float titleX = (Main.WORLD_WIDTH - titleWidth) / 2; // Centraliza no eixo X
        float titleY = Main.WORLD_HEIGHT * 0.5f; // Define a posição no eixo Y

        // Desenha o título redimensionado e posicionado
        game.getSpriteBatch().draw(title, titleX, titleY, titleWidth, titleHeight);
        
        playButton.draw(game.getSpriteBatch());

        game.getSpriteBatch().end();
    }

    void checkButtonPress() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.getCamera().unproject(touchPos);

            if (playButton.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
                clickSound.play();
                game.setScreen(new GameScreen(game));
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
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
