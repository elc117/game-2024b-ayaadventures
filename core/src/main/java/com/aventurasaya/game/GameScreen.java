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

    // Botão "X"
    private Texture backButtonT;
    private float backButtonX, backButtonY, backButtonWidth, backButtonHeight;

        private MovePlayer movePlayer;

    public GameScreen(Main game) {
        this.game = game;

        this.camera = game.getCamera();
        this.spriteBatch = game.getSpriteBatch();
        this.backGround = new Texture("map.png");
        p = new Player(5);

        tAya = new Texture("aya.png");
        aya = new Sprite(tAya);
        aya.setSize(aya.getWidth() / 5f, aya.getHeight() / 5f);

        float x = Main.WORLD_WIDTH - 1195f;
        float y = Main.WORLD_HEIGHT - 449f;


        Vector3 worldCoords = game.getCamera().project(new Vector3(x, y, 0));
        aya.setPosition(worldCoords.x, worldCoords.y);

        movePlayer = new MovePlayer(aya, game);
        display = new Display(spriteBatch);


    }

    @Override
    public void show() {
        // Carregue a textura do botão
        backButtonT = new Texture("x.png");

        // Defina as dimensões e a posição do botão
        backButtonWidth = 40; // Largura do botão
        backButtonHeight = 30; // Altura do botão
        backButtonX = 10;// Margem da direita
        backButtonY = Main.WORLD_HEIGHT - backButtonHeight - 10; // Margem do topo


    }

    @Override
    public void render(float delta) {
        game.getFitViewport().apply();
        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();

        spriteBatch.draw(backGround, 0, 0, game.getCamera().viewportWidth, game.getCamera().viewportHeight);
        aya.draw(spriteBatch);
        display.desenhaVidas(p, spriteBatch);

        // Desenhe o botão "X"
        spriteBatch.draw(backButtonT, backButtonX, backButtonY, backButtonWidth, backButtonHeight);

        movePlayer.update(delta);

        if (shouldSwitchToQuizScreen && movePlayer.isAtEsquina2()) {
            game.setScreen(new QuizScreen(1, game, p));
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
            } else if (isPointClicked(touchPos, movePlayer.getEsquina5()) && movePlayer.isAtEsquina3()) {
                // Mover para esquina4 antes de esquina5
                movePlayer.moveTo(movePlayer.getEsquina4(), movePlayer.getEsquina5());
            } else if (isPointClicked(touchPos, movePlayer.getEsquina6()) && movePlayer.isAtEsquina5()) {
                movePlayer.moveToEsquina6();
            } else if (isPointClicked(touchPos, movePlayer.getPontoFinal()) && movePlayer.isAtEsquina6()) {
                movePlayer.moveToPontoFinal();
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
        backButtonT.dispose(); // Libera a textura do botão
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


