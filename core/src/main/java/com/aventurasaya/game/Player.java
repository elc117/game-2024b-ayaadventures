package com.aventurasaya.game;

public class Player {
    private int vidas;
    private Main game;

    public Player (int vidas, Main game) {
        this.vidas = vidas;
        this.game = game;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;

    }

    public int getVidas() {
        return vidas;
    }

    public void perdeVida() {
        if (vidas > 1) {
            vidas--; // Apenas reduz se ainda houver vidas
        } else {
            game.setScreen(new gameOverScreen(game));
        }
    }
}
