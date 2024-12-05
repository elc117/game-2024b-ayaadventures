package com.aventurasaya.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Display {

    private Texture tHeart;
    private Sprite heart;
    private Texture tMissionIcon;
    private Sprite missionIcon;
    private Texture tMessage;
    private Sprite messageSprite;

    private Texture tQuestIcon; // Imagem da quest
    private Sprite questIcon; // Sprite da quest

    private boolean showMessage = true;
    private boolean showQuest = false; // Controle para exibir a quest
    private float messageTimer = 0;
    private Main game;

    public Display(SpriteBatch spriteBatch, Main game) {
        this.game = game;
        // Corações (vidas)
        tHeart = new Texture("heart.png");
        heart = new Sprite(tHeart);
        heart.setSize(heart.getWidth() / 16f, heart.getHeight() / 16f);

        // Ícone de missões
        tMissionIcon = new Texture("mail1.png");
        missionIcon = new Sprite(tMissionIcon);
        missionIcon.setSize(missionIcon.getWidth() / 9f, missionIcon.getHeight() / 9f);
        missionIcon.setPosition(Main.WORLD_WIDTH - missionIcon.getWidth() - 30,
                Main.WORLD_HEIGHT - missionIcon.getHeight() - 20);

        // Mensagem
        tMessage = new Texture("aviso.png");
        messageSprite = new Sprite(tMessage);
        messageSprite.setSize(messageSprite.getWidth() / 1.2f, messageSprite.getHeight() / 1.2f);
        messageSprite.setPosition(Main.WORLD_WIDTH - messageSprite.getWidth() - 30,
                Main.WORLD_HEIGHT - messageSprite.getHeight() - 85);

        // Quest ícone - Posicionado no canto superior esquerdo
        tQuestIcon = new Texture("quests.png");
        questIcon = new Sprite(tQuestIcon);
        questIcon.setSize(questIcon.getWidth() / 1.2f, questIcon.getHeight() / 1.2f);
        questIcon.setPosition(950, Main.WORLD_HEIGHT - questIcon.getHeight() - 85); // Margens de 30px
    }

    public void desenhaVidas(Player p, SpriteBatch spriteBatch, float delta) {

        // Detectar clique no ícone de missões
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.getFitViewport().unproject(touchPos);
            if (missionIcon.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
                showQuest = !showQuest; // Alterna o valor de showQuest
            }
        }
        // Desenhar as vidas

        for (int i = 0; i < p.getVidas(); i++) {
            float xOffset = i * (heart.getWidth() + 10);
            heart.setPosition(Main.WORLD_WIDTH - 150 - xOffset,
                Main.WORLD_HEIGHT - heart.getHeight() - 35);
            heart.draw(spriteBatch);
        }

        // Desenhar o ícone de missões
        missionIcon.draw(spriteBatch);


        // Desenhar a quest, se ativada
        if (showQuest) {
            questIcon.draw(spriteBatch);
        }
    }


    public void atualizaMensagem(float delta) {
        // Atualiza o temporizador da mensagem
        if (showMessage) {
            messageTimer += delta;
            if (messageTimer >= 5) {
                showMessage = false;
            }
        }
    }

    public void desenhaMensagem(SpriteBatch spriteBatch) {
        // Desenha a mensagem se ela ainda estiver ativa
        if (showMessage) {
            messageSprite.draw(spriteBatch);
        }
    }


    public void dispose() {
        tHeart.dispose();
        tMissionIcon.dispose();
        tMessage.dispose();
        tQuestIcon.dispose();
    }
}
