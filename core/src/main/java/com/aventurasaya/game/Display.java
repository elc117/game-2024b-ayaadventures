package com.aventurasaya.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Display {

    private Texture tHeart;
    private Sprite heart;
    

    public Display(SpriteBatch spriteBatch) {
        tHeart = new Texture("heart.png");
        heart = new Sprite(tHeart);
        heart.setSize(heart.getWidth() /10f, heart.getHeight() / 10f);
    }

    public void desenhaVidas(Player p ,SpriteBatch spriteBatch) {
        for (int i = 0; i < p.getVidas(); i++) {
            float xOffset = i * (heart.getWidth() + 10); // Espaçamento de 10 pixels entre os corações
            heart.setPosition(Main.WORLD_WIDTH - 60 - xOffset, // Começa no canto superior direito
                Main.WORLD_HEIGHT - heart.getHeight() - 20); // Y fixo no topo
            heart.draw(spriteBatch);
        }
    }

    public void dispose() {
        tHeart.dispose();
    }
}
