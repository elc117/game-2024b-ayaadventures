package com.aventurasaya.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class QuizScreen implements Screen {

    private Texture backGroundFase;
    private Texture respostaCorretaImage;  // Imagem a ser exibida após a resposta correta
    private float tempoImagemExibida;  // Temporizador
    private boolean respostaCorreta;   // Flag para verificar se a resposta foi correta
    private int fase;
    private Main game;
    private Display d;
    private Player p;
    private Vector2 touchPos;

    private float xResposta;
    private float yResposta;
    private float larguraResposta;
    private float alturaResposta;

    public QuizScreen(int fase, Main game, Player p) {
        this.fase = fase;
        this.game = game;
        this.p = p;
        backGroundFase = new Texture("jardimbotanico1.png");
        respostaCorretaImage = new Texture("rmimi.png");  // Imagem de resposta correta
        d = new Display(game.getSpriteBatch(), game);
        touchPos = new Vector2();
        tempoImagemExibida = 0;
        respostaCorreta = false;

        definirAreaCorreta(fase);
    }

    private void definirAreaCorreta(int fase) {
        if (fase == 1) {
            xResposta = 742;
            yResposta = 461;
            larguraResposta = 212;
            alturaResposta = 48;
        }
    }

    private boolean verificaToque(Vector2 touchPos) {
        return touchPos.x >= xResposta && touchPos.x <= xResposta + larguraResposta &&
               touchPos.y >= yResposta && touchPos.y <= yResposta + alturaResposta;
    }

    @Override
    public void render(float delta) {
        game.getFitViewport().apply();
        game.getSpriteBatch().begin();

        // Se a resposta foi correta, mostrar a imagem por 5 segundos
        if (respostaCorreta) {
            tempoImagemExibida += delta;  // Incrementa o temporizador
            game.getSpriteBatch().draw(respostaCorretaImage, (game.getCamera().viewportWidth - respostaCorretaImage.getWidth()) / 2, 
                                        (game.getCamera().viewportHeight - respostaCorretaImage.getHeight()) / 2);
            if (tempoImagemExibida >= 5) {
                game.setScreen(new GameScreen(game, p)); // Muda para a tela de GameScreen após 5 segundos
            }
        } else {
            // Caso contrário, exibe o fundo normalmente
            game.getSpriteBatch().draw(backGroundFase, (game.getCamera().viewportWidth - backGroundFase.getWidth()) / 2, 
                                        (game.getCamera().viewportHeight - backGroundFase.getHeight()) / 2);
        }

        // Detecta o toque e verifica a resposta
        if (Gdx.input.justTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            game.getFitViewport().unproject(touchPos);

            if (verificaToque(touchPos)) {
                respostaCorreta = true;  // Marca como resposta correta
                tempoImagemExibida = 0;  // Reinicia o temporizador
            } else {
                p.perdeVida();  // Resposta errada
            }
        }

        game.getSpriteBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        game.getFitViewport().update(width, height, true);
    }

    @Override
    public void show() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        backGroundFase.dispose();
        respostaCorretaImage.dispose();
    }
}