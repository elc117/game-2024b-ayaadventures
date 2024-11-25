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

    // Botão "X"
    private Texture backButtonT;
    private float backButtonX, backButtonY, backButtonWidth, backButtonHeight;

    private float speed = 500f;
    private boolean isMoving = false;
    private boolean toParada = false; // Indicador de direção
    private boolean toPoint1 = false;


    private Vector2 currentPosition;
    private Vector2 targetPosition;
    private Vector2 direction;

    // Coordenadas dos pontos
    private final Vector2 parada = new Vector2(Main.WORLD_WIDTH - 725f, Main.WORLD_HEIGHT - 345f);
    private final Vector2 point1 = new Vector2(Main.WORLD_WIDTH - 635f, Main.WORLD_HEIGHT - 335f);

    public GameScreen(Main game) {
        this.game = game;

        this.camera = game.getCamera();
        this.spriteBatch = game.getSpriteBatch();
        this.backGround = new Texture("map.png");
        p = new Player(5);

        tAya = new Texture("aya.png");
        aya = new Sprite(tAya);
        aya.setSize(aya.getWidth() / 5f, aya.getHeight() / 5f);
        aya.setCenter(Main.WORLD_WIDTH - 825f, Main.WORLD_HEIGHT - 365f);
        display = new Display(spriteBatch);

        currentPosition = new Vector2(aya.getX(), aya.getY()); // Posição inicial
        targetPosition = new Vector2(); // Ponto de destino (definido ao clicar)
        direction = new Vector2(); // Vetor de direção
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
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        spriteBatch.draw(backGround, 0, 0, Main.WORLD_WIDTH, Main.WORLD_HEIGHT);
        aya.draw(spriteBatch);
        display.desenhaVidas(p, spriteBatch);

          // Desenhe o botão "X"
        spriteBatch.draw(backButtonT, backButtonX, backButtonY, backButtonWidth, backButtonHeight);

        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            // Verifica se o clique foi no botão "x"
            if (touchPos.x >= backButtonX && touchPos.x <= backButtonX + backButtonWidth && touchPos.y >= backButtonY && touchPos.y <= backButtonY + backButtonHeight){
                game.setScreen(new HomeScreen(game)); // Retorna para o menu inicial
            }

            // Verifica se o clique foi no botão (point1)
            if (touchPos.x >= point1.x - 30 && touchPos.x <= point1.x + 30 &&
                touchPos.y >= point1.y - 30 && touchPos.y <= point1.y + 30) {
                toParada = true; // Inicia o movimento para a parada
                isMoving = true;
                targetPosition.set(parada); // Configura o próximo destino
                direction.set(targetPosition).sub(currentPosition).nor(); // Calcula direção
            }
        }

        if (isMoving) {
            float distance = speed * delta; // Distância a mover neste frame
            Vector2 moveStep = new Vector2(direction).scl(distance);

            // Chega ao destino atual
            if (currentPosition.dst(targetPosition) <= distance) {
                currentPosition.set(targetPosition);
                isMoving = false;

                // Se chegou na parada, vá para point1
                if (toParada) {
                    toParada = false;
                    toPoint1 = true;
                    isMoving = true;
                    targetPosition.set(point1); // Próximo destino: point1
                    direction.set(targetPosition).sub(currentPosition).nor();
                } else if (toPoint1) {
                    toPoint1 = false; // Chegou no point1, fim do movimento
                }
            } else {
                currentPosition.add(moveStep);
            }
            // Atualiza posição da Aya
            aya.setPosition(currentPosition.x - aya.getWidth() / 2, currentPosition.y - aya.getHeight() / 2);
        }
        spriteBatch.end();
    }


    @Override
    public void dispose() {
        backGround.dispose();
        backButtonT.dispose(); // Libera a textura do botão
        tAya.dispose(); // Libera a textura da Aya
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


