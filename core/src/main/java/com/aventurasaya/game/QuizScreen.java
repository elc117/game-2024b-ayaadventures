package com.aventurasaya.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import java.util.ArrayList;
import com.badlogic.gdx.files.FileHandle;



public class QuizScreen implements Screen {

    private Texture backGroundFase;
    private int fase;
    private Main game;
    private String pergunta;
    private ArrayList<String> alternativas;
    private int resposta;
    private BitmapFont font;
    private Display d;
    private Player p;
    private Vector2 touchPos;


    public QuizScreen (int fase, Main game, Player p) {
        this.fase = fase;
        this.game = game;
        this.p = p;
        backGroundFase = new Texture("fase1.png");
        font = new BitmapFont();
        font.getData().setScale(2);
        d = new Display(game.getSpriteBatch(), game);
        touchPos = new Vector2();
        lerFasesJson(fase);
    }

    private void lerFasesJson(int fase) {
        FileHandle file = Gdx.files.internal("fases.json");
        String jsonString = file.readString();
        JsonValue raiz = new JsonReader().parse(jsonString);
        JsonValue fasesArray = raiz.get("fases");

        for (JsonValue faseObj : fasesArray) {
            if (faseObj.getInt("numero") == fase) {
                pergunta = faseObj.getString("pergunta");
                alternativas = new ArrayList<>();
                for (JsonValue alternativa : faseObj.get("alternativas")) {
                    alternativas.add(alternativa.asString());
                }
                resposta = faseObj.getInt("resposta");
                break;
            }
        }
    }

    private int verificaAlternativa(Vector2 touchPos) {
        float xInicio = game.getCamera().viewportWidth / 2f;
        float yInicio = game.getCamera().viewportHeight / 1.5f;
        float larguraAlternativa = 200; // Largura hipotética de cada alternativa
        float alturaAlternativa = 20;  // Altura hipotética de cada alternativa
        float espacoEntreAlternativas = 100; // Espaço entre as alternativas

        for (int i = 0; i < alternativas.size(); i++) {
            float yAtual = yInicio - (i * espacoEntreAlternativas);

            if (touchPos.x >= xInicio && touchPos.x <= xInicio + larguraAlternativa &&
                touchPos.y >= yAtual - alturaAlternativa && touchPos.y <= yAtual) {
                return i + 1;
            }
        }
        return 0; // Nenhuma alternativa foi clicada
    }


    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        game.getFitViewport().apply();
        game.getSpriteBatch().begin();
        game.getSpriteBatch().draw(backGroundFase, 0, 0, game.getCamera().viewportWidth, game.getCamera().viewportHeight);


        d.desenhaVidas(p, game.getSpriteBatch(), delta); // Passando o delta aqui


        if (pergunta != null) {
            font.draw(game.getSpriteBatch(), pergunta, 300, 600);
        }

        for (int i = 0; i < alternativas.size(); i++) {
            String alternativa = alternativas.get(i);
            font.draw(game.getSpriteBatch(), alternativa, game.getCamera().viewportWidth / 2f, game.getCamera().viewportHeight / 1.5f - (i * 100));
        }

        if(Gdx.input.justTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            game.getFitViewport().unproject(touchPos);

            int respostaEscolhida = verificaAlternativa(touchPos);
            if (respostaEscolhida != resposta && respostaEscolhida != 0) {
                p.perdeVida();
            } else if (respostaEscolhida == resposta) {
                game.setScreen(new GameScreen(game, p));
            }
        }

        game.getSpriteBatch().end();
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
        backGroundFase.dispose();
    }
}
