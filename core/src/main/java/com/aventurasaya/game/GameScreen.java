package com.aventurasaya.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


public class GameScreen implements Screen {

    private final Main game;
    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private Texture backGround, tAya;
    private Player p;
    private Display display;
    private Sprite aya;
    private boolean shouldSwitchToQuizScreen = false;

    private MovePlayer movePlayer;



    public GameScreen(Main game, Player p) {
        this.game = game;

        this.camera = game.getCamera();
        this.spriteBatch = game.getSpriteBatch();
        this.backGround = new Texture("map.png");

        if (p == null) {
            this.p = new Player(5);
        } else {
           this.p = p;
        }

        tAya = new Texture("aya.png");
        aya = new Sprite(tAya);
        aya.setSize(aya.getWidth() / 1.5f, aya.getHeight() / 1.5f);

        movePlayer = new MovePlayer(aya, game,game.getSavedAyaPosition());

        if (game.getSavedAyaPosition() != null) {
            aya.setPosition(game.getSavedAyaPosition().x - aya.getWidth() / 2,
                game.getSavedAyaPosition().y - aya.getHeight() / 2);
        } else {
            aya.setPosition(movePlayer.getSpawnAya().x, movePlayer.getSpawnAya().y);
        }

        display = new Display(spriteBatch, game);


    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        game.getFitViewport().apply();
        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();

        spriteBatch.draw(backGround, 0, 0, game.getCamera().viewportWidth, game.getCamera().viewportHeight);
        aya.draw(spriteBatch);
        display.desenhaVidas(p, spriteBatch, Gdx.graphics.getDeltaTime());


        movePlayer.update(delta);

        if(game.getSavedAyaPosition() == null) {
            display.atualizaMensagem(delta);
            display.desenhaMensagem(game.getSpriteBatch());
        }

        if (shouldSwitchToQuizScreen && movePlayer.isAtEsquina2()) {
            game.setSavedAyaPosition(movePlayer.getEsquina2());
            game.setScreen(new QuizScreen(1, game, p));
        }

        if (shouldSwitchToQuizScreen && movePlayer.isAtEsquina3()) {
            game.setSavedAyaPosition(movePlayer.getEsquina3());
            game.setScreen(new QuizScreen(2, game, p));
        }

        if (shouldSwitchToQuizScreen && movePlayer.isAtEsquina5()) {
            game.setSavedAyaPosition(movePlayer.getEsquina5());
            //game.setScreen(new QuizScreen(3, game, p));
        }

        if (shouldSwitchToQuizScreen && movePlayer.isAtEsquina6()) {
            game.setSavedAyaPosition(movePlayer.getEsquina6());
            game.setScreen(new QuizScreen(4, game, p));
        }

        if (shouldSwitchToQuizScreen && movePlayer.isAtPontoFinal()) {
            game.setSavedAyaPosition(movePlayer.getPontoFinal());
            game.setScreen(new QuizScreen(5, game, p));
        }


        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.getFitViewport().unproject(touchPos);

            if (isPointClicked(touchPos, movePlayer.getEsquina2()) && movePlayer.isAtSpawn()) {
                // Mover para esquina1 e depois esquina2
                movePlayer.moveTo(movePlayer.getEsquina1(), movePlayer.getEsquina2());
                shouldSwitchToQuizScreen = true;
            } else if (isPointClicked(touchPos, movePlayer.getEsquina3()) && movePlayer.isAtEsquina2()) {
                movePlayer.moveToEsquina3();
                shouldSwitchToQuizScreen = true;
            } else if (isPointClicked(touchPos, movePlayer.getEsquina5()) && movePlayer.isAtEsquina3()) {
                movePlayer.moveTo(movePlayer.getEsquina4(), movePlayer.getEsquina5());
                shouldSwitchToQuizScreen = true;
            } else if (isPointClicked(touchPos, movePlayer.getEsquina6()) && movePlayer.isAtEsquina5()) {
                movePlayer.moveToEsquina6();
                shouldSwitchToQuizScreen = true;
            } else if (isPointClicked(touchPos, movePlayer.getPontoFinal()) && movePlayer.isAtEsquina6()) {
                movePlayer.moveToPontoFinal();
                shouldSwitchToQuizScreen = true;
            }
        }

        spriteBatch.end();
    }

    private boolean isPointClicked(Vector3 touchPos, Vector2 point) {
        float tolerance = 30f; // Margem de clique
        return touchPos.x >= point.x - tolerance && touchPos.x <= point.x + tolerance &&
            touchPos.y >= point.y - tolerance && touchPos.y <= point.y + tolerance;
    }


    @Override
    public void dispose() {
        backGround.dispose();
        tAya.dispose(); // Libera a textura da Aya
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

}


