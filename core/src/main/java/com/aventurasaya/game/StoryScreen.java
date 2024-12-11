package com.aventurasaya.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class StoryScreen implements Screen {

    private final Main game;
    private SpriteBatch batch;
    private Texture[] images;
    private Sprite nextButton;
    private Sprite startButton; // Botão "Começar"
    private int currentImageIndex;

    // Coordenadas de cada imagem
    private float[][] imagePositions = {
        {100, 300}, // msg1.png
        {200, 200}, // msg2.png
        {300, 50},  // msg3.png
        {300, 150}  // msg4.png
    };

    // Coordenadas do botão para cada imagem
    private float[][] buttonPositions = {
        {550, 349}, // Coordenadas para msg1.png
        {650, 249}, // Coordenadas para msg2.png
        {950, 153}, // Coordenadas para msg3.png
        {750, 195}  // Coordenadas para msg4.png
    };

    private Sound buttonhover;
    private boolean hoverSoundPlayed = false;

    public StoryScreen(Main game) {
        this.game = game;
        this.batch = game.getSpriteBatch();

        // Carregar o som de hover
        buttonhover = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));

        // Carregar as imagens
        images = new Texture[] {
            new Texture("msg1.png"),
            new Texture("msg2.png"),
            new Texture("msg3.png"),
            new Texture("msg4.png")
        };

        // Botão "Próximo"
        Texture tNextButton = new Texture("next.png");
        nextButton = new Sprite(tNextButton);
        nextButton.setSize(170, 69); // Ajuste o tamanho do botão

        // Botão "Começar"
        Texture tStartButton = new Texture("start.png");
        startButton = new Sprite(tStartButton);
        startButton.setSize(170, 69); // Ajuste o tamanho do botão

        updateButtonPosition();

        // Inicializar índice da imagem
        currentImageIndex = 0;
    }

    private void updateButtonPosition() {
        // Atualizar posição com base na imagem atual
        float[] position = buttonPositions[currentImageIndex];
        if (currentImageIndex < images.length - 1) {
            nextButton.setPosition(position[0], position[1]);
        } else {
            startButton.setPosition(position[0], position[1]);
        }
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        game.getFitViewport().apply();
        game.getCamera().update();
        batch.setProjectionMatrix(game.getCamera().combined);

        // Limpar a tela para branco
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Desenhar todas as imagens até o índice atual (sobreposição)
        for (int i = 0; i <= currentImageIndex; i++) {
            float[] imagePos = imagePositions[i];
            batch.draw(images[i], imagePos[0], imagePos[1]);
        }

        // Desenhar o botão correspondente
        if (currentImageIndex < images.length - 1) {
            nextButton.draw(batch); // Botão "Próximo"
        } else {
            startButton.draw(batch); // Botão "Começar"
        }

        batch.end();

        // Verificar cliques no botão
        handleButtonClick();
        handleHover();
    }

    private void handleButtonClick() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.getFitViewport().unproject(touchPos);

            if (currentImageIndex < images.length - 1) {
                // Verificar clique no botão "Próximo"
                if (nextButton.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
                    currentImageIndex++;
                    updateButtonPosition();
                }
            } else {
                // Verificar clique no botão "Começar"
                if (startButton.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
                    game.setScreen(new GameScreen(game, null)); // Transição para GameScreen
                }
            }
        }
    }

    private void handleHover() {
        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        game.getFitViewport().unproject(mousePos);

        boolean isHovering = false;

        if (currentImageIndex < images.length - 1) {
            isHovering = nextButton.getBoundingRectangle().contains(mousePos.x, mousePos.y);
        } else {
            isHovering = startButton.getBoundingRectangle().contains(mousePos.x, mousePos.y);
        }

        if (isHovering) {
            if (!hoverSoundPlayed) {
                buttonhover.play();
                hoverSoundPlayed = true;
            }
        } else {
            hoverSoundPlayed = false;
        }
    }

    @Override
    public void resize(int width, int height) {
        game.getFitViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        for (Texture image : images) {
            image.dispose();
        }
        nextButton.getTexture().dispose();
        startButton.getTexture().dispose();
        buttonhover.dispose(); // Liberar o som
    }
}
