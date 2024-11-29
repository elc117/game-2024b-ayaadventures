package com.aventurasaya.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;



import java.util.ArrayList;


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



    public QuizScreen (int fase, Main game, Player p) {
        this.fase = fase;
        this.game = game;
        this.p = p;
        backGroundFase = new Texture("fase1.png");
        font = new BitmapFont();
        font.getData().setScale(2);
        d = new Display(game.getSpriteBatch());
        lerFasesJson(fase);
    }

    private void lerFasesJson(int fase) {
        FileHandle file = Gdx.files.internal("fases.json");
        String jsonString = file.readString();

        JsonValue raiz = new JsonReader().parse(jsonString); // Parseia o JSON para um JsonValue

        JsonValue fasesArray = raiz.get("fases"); // Obtém o array de fases

        for (JsonValue faseObj : fasesArray) {
            if (faseObj.getInt("numero") == fase) {
                // Atualiza os atributos com os dados da fase
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


    private int verificaAlternativa(int x, int y) {
        int margemDeFolga = 20;     // Definindo a margem de folga ao redor da alternativa

        // Calculando a altura e a posição Y de cada alternativa com base no índice
        for (int i = 0; i < alternativas.size(); i++) {
            int yPosition = 500 - (i * 100) - 10;

            GlyphLayout layout = new GlyphLayout(font, alternativas.get(i));
            float alternativaWidth = layout.width;

            if (x >= 600 && x <= 600 + alternativaWidth
                && y >= yPosition - margemDeFolga && y <= yPosition + margemDeFolga) {
                return i + 1;  // Retorna o número da alternativa (começando de 1)
            }
        }
        // Se o clique não foi em nenhuma alternativa, retorna 0
        return 0;
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.getSpriteBatch().begin();
        game.getSpriteBatch().draw(backGroundFase, 0, 0, Main.WORLD_WIDTH, Main.WORLD_HEIGHT);
        d.desenhaVidas(p, game.getSpriteBatch());

        if (pergunta != null) {
            font.draw(game.getSpriteBatch(), pergunta, 300, 600);
        }

        for (int i = 0; i < alternativas.size(); i++) {
            String alternativa = alternativas.get(i);
            font.draw(game.getSpriteBatch(), alternativa, 600, 500 - (i * 100));

        }

        if(Gdx.input.justTouched()) {
            int clickX = Gdx.input.getX();
            int clickY = Gdx.graphics.getHeight() - Gdx.input.getY();

            int respotaEscolhida = verificaAlternativa(clickX, clickY);
            if (respotaEscolhida != resposta && respotaEscolhida != 0) {
                p.perdeVida();
            } else if (respotaEscolhida == resposta) {
                // volta para tela do jogo atualizada (pensar em como fazer ainda)
            }
        }
        game.getSpriteBatch().end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
