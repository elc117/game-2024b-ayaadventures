package com.aventurasaya.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Display {

    private Texture tHeart;
    private Sprite heart;
    private Texture tMissionIcon;
    private Sprite missionIcon;
    private Texture tMessage; // Imagem da mensagem
    private Sprite messageSprite; // Sprite da mensagem

    private boolean showMessage = true; // Controle para exibir ou ocultar a mensagem
    private float messageTimer = 0; // Temporizador para a mensagem

    public Display(SpriteBatch spriteBatch) {
        // Corações (vidas)
        tHeart = new Texture("heart.png");
        heart = new Sprite(tHeart);
        heart.setSize(heart.getWidth() / 10f, heart.getHeight() / 10f);

        // Ícone de missões
        tMissionIcon = new Texture("mail.png"); 
        missionIcon = new Sprite(tMissionIcon);
        missionIcon.setSize(missionIcon.getWidth() / 9f, missionIcon.getHeight() / 9f);
        missionIcon.setPosition(Main.WORLD_WIDTH - missionIcon.getWidth() - 30,
                                Main.WORLD_HEIGHT - missionIcon.getHeight() - 20); 

        // Mensagem
        tMessage = new Texture("aviso.png"); 
        messageSprite = new Sprite(tMessage);
        messageSprite.setSize(messageSprite.getWidth() / 1.5f, messageSprite.getHeight() / 1.5f);
        messageSprite.setPosition(Main.WORLD_WIDTH - messageSprite.getWidth() - 30,
                                Main.WORLD_HEIGHT - messageSprite.getHeight() - 85); 
    }

    public void desenhaVidas(Player p, SpriteBatch spriteBatch, float delta) {
        // Atualiza o temporizador da mensagem
        if (showMessage) {
            messageTimer += delta; // Incrementa o tempo decorrido
            if (messageTimer >= 5) { // 5 segundos
                showMessage = false; // Oculta a mensagem
            }
        }

        // Desenhar as vidas
        for (int i = 0; i < p.getVidas(); i++) {
            float xOffset = i * (heart.getWidth() + 10); // Espaçamento de 10 pixels entre os corações
            heart.setPosition(Main.WORLD_WIDTH - 150 - xOffset, // Começa no canto superior direito
                Main.WORLD_HEIGHT - heart.getHeight() - 20); // Y fixo no topo
            heart.draw(spriteBatch);
        }

        // Desenhar o ícone de missões
        missionIcon.draw(spriteBatch);

        // Desenhar a mensagem, se ainda estiver ativa
        if (showMessage) {
            messageSprite.draw(spriteBatch);
        }
    }

    public void dispose() {
        tHeart.dispose();
        tMissionIcon.dispose();
        tMessage.dispose();
    }
}