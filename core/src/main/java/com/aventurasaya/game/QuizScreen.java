package com.aventurasaya.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class QuizScreen implements Screen {

    private Texture backGroundFase;
    private Texture[] imagensFase; // Imagens para cada fase
    private Texture respostaCorretaImage, respostaCorretaImage2; // Imagem a ser exibida após a resposta correta
    private Texture imagemAtual; // Imagem a ser exibida no hover
    private float tempoImagemExibida; // Temporizador
    private boolean respostaCorreta; // Flag para verificar se a resposta foi correta
    private boolean mostrarImagem; // Controle para exibir/ocultar imagem no hover
    private int fase;
    private Main game;
    private Display d;
    private Player p;
    private Vector2 touchPos;

    // Coordenadas da área correta
    private float xResposta, yResposta, larguraResposta, alturaResposta;

    // Coordenadas das áreas erradas
    private float[][] areasErradas;

    // Coordenadas do botão
    private float botaoX, botaoY, botaoLargura, botaoAltura;

    public QuizScreen(int fase, Main game, Player p) {
        this.fase = fase;
        this.game = game;
        this.p = p;

        // Inicializar as imagens das fases
        imagensFase = new Texture[]{
            new Texture("mestacaoferroviaria.png"),
            new Texture("mcatedral.png"),
            new Texture("mvilabelga.png"),
            new Texture("mjardimbotanico.png"),
            new Texture("mcarrefour.png")
        };

        imagemAtual = imagensFase[fase - 1]; // Seleciona a imagem correspondente à fase
        mostrarImagem = false;

        if (fase == 1) {
            backGroundFase = new Texture("estacaoferroviaria.png");
            respostaCorretaImage = new Texture("rmimi.png");
        } else if (fase == 2) {
            backGroundFase = new Texture("catedral.png");
            respostaCorretaImage = new Texture("rkira.png");
        } else if (fase == 3) {
            backGroundFase = new Texture("vilabelga.png");
            respostaCorretaImage = new Texture("rfofao.png");
        } else if (fase == 4) {
            backGroundFase = new Texture("jardimbotanico1.png");
            respostaCorretaImage = new Texture("rheart.png");
        } else {
            backGroundFase = new Texture("carrefour.png");
            respostaCorretaImage = new Texture("FIM1.png");
            respostaCorretaImage2 = new Texture("FIM2.png");
        }

        d = new Display(game.getSpriteBatch(), game);
        touchPos = new Vector2();
        tempoImagemExibida = 0;
        respostaCorreta = false;

        definirAreaCorreta(fase);
        definirAreasErradas();

        // Configuração do botão
        botaoX = 290;
        botaoY = 80;
        botaoLargura = 212;
        botaoAltura = 48;
    }

    private void definirAreaCorreta(int fase) {
        xResposta = 742;
        larguraResposta = 212;
        alturaResposta = 48;
        if (fase == 1 || fase == 2 || fase == 5) {
            yResposta = 409;
        } else if (fase == 3) {
            yResposta = 298;
            xResposta = 709;
            larguraResposta = 253;
            alturaResposta = 50;
        } else if (fase == 4) {
            yResposta = 461;
        }
    }

    private void definirAreasErradas() {
        if (fase == 1 || fase == 2 || fase == 5) {
            areasErradas = new float[][]{
                {742, 461, 212, 48}, // Área 1: x, y, largura, altura
                {742, 351, 212, 48}, // Área 2
                {742, 301, 212, 48}  // Área 3
            };
        } else if (fase == 3) {
            areasErradas = new float[][]{
                {709, 456, 253, 50}, // Área 1: x, y, largura, altura
                {709, 377, 253, 50}, // Área 2
                {709, 219, 253, 50}  // Área 3
            };
        } else if (fase == 4) {
            areasErradas = new float[][]{
                {742, 351, 212, 48}, // Área 1: x, y, largura, altura
                {742, 409, 212, 48}, // Área 2
                {742, 301, 212, 48}  // Área 3
            };
        }
    }

    private boolean verificaToque(Vector2 touchPos, float x, float y, float largura, float altura) {
        return touchPos.x >= x && touchPos.x <= x + largura &&
               touchPos.y >= y && touchPos.y <= y + altura;
    }

    @Override
    public void render(float delta) {
        game.getFitViewport().apply();
        game.getSpriteBatch().begin();
        d.desenhaVidas(p, game.getSpriteBatch(), delta);

        // Exibe o fundo normalmente
        game.getSpriteBatch().draw(backGroundFase,
            (game.getCamera().viewportWidth - backGroundFase.getWidth()) / 2,
            (game.getCamera().viewportHeight - backGroundFase.getHeight()) / 2);

        // Verifica a posição do cursor
        float cursorX = Gdx.input.getX();
        float cursorY = Gdx.input.getY();
        // Verifica a posição do cursor no mundo do jogo
        Vector2 cursorPos = game.getFitViewport().unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        cursorX = cursorPos.x;
        cursorY = cursorPos.y;

        // Se o cursor estiver sobre o botão, mostrar a imagem
        if (cursorX >= botaoX && cursorX <= botaoX + botaoLargura &&
            cursorY >= botaoY && cursorY <= botaoY + botaoAltura) {
            mostrarImagem = true;
        } else {
            mostrarImagem = false;
        }

        // Exibe a imagem se o cursor estiver sobre o botão
        if (mostrarImagem) {
            game.getSpriteBatch().draw(imagemAtual,
                (game.getCamera().viewportWidth - imagemAtual.getWidth()) / 2,
                (game.getCamera().viewportHeight - imagemAtual.getHeight()) / 2);
        }

        // Se a resposta foi correta, mostrar a imagem por 3 segundos
        if (respostaCorreta) {
            tempoImagemExibida += delta; // Incrementa o temporizador
            game.getSpriteBatch().draw(respostaCorretaImage,
                (game.getCamera().viewportWidth - respostaCorretaImage.getWidth()) / 2,
                (game.getCamera().viewportHeight - respostaCorretaImage.getHeight()) / 2);

            if (tempoImagemExibida >= 3 && fase != 5) {
                game.setScreen(new GameScreen(game, p)); // Muda para a tela de GameScreen após 3 segundos
            }

            if (tempoImagemExibida >= 10 && fase == 5) {
                game.setScreen(new HomeScreen(game));
            }

        }

        // Detecta o toque e verifica a resposta
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            game.getFitViewport().unproject(touchPos);

            if (verificaToque(touchPos, xResposta, yResposta, larguraResposta, alturaResposta)) {
                respostaCorreta = true; // Marca como resposta correta
                tempoImagemExibida = 0;// Reinicia o temporizador
            } else {
                boolean tocouErrado = false;
                // Verifica se tocou em uma das áreas erradas
                for (float[] area : areasErradas) {
                    if (verificaToque(touchPos, area[0], area[1], area[2], area[3])) {
                        p.perdeVida(); // Resposta errada
                        tocouErrado = true;
                        break;
                    }
                }
            }


        }
        if (fase == 5 && respostaCorreta) {
            if (cursorPos.x >= 474 && cursorPos.x <= 474 + 69 &&
                cursorPos.y >= 269 && cursorPos.y <= 269 + 23) {
                game.getSpriteBatch().draw(respostaCorretaImage2,
                    (game.getCamera().viewportWidth - respostaCorretaImage2.getWidth()) / 2,
                    (game.getCamera().viewportHeight - respostaCorretaImage2.getHeight()) / 2);
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
        for (Texture img : imagensFase) {
            img.dispose();
        }
    }
}
