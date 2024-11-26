package com.aventurasaya.game;

public class Player {
    private int vidas;

    public Player (int vidas) {
        this.vidas = vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;

    }

    public int getVidas() {
        return this.vidas;
    }

    public void perdeVida() {
        if (vidas > 0) {
            vidas--; // Apenas reduz se ainda houver vidas
        } else {
            System.out.println("Game Over! O jogador já não tem mais vidas.");
        }
    }
}
