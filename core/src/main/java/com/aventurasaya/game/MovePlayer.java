package com.aventurasaya.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;
import java.util.Queue;

public class MovePlayer {

    private Vector2 spawnAya, esquina1, esquina2, esquina3, esquina4, esquina5, esquina6, pontoFinal;
    private Sprite aya;
    private float speed = 400f; // Velocidade de movimento
    private Vector2 currentPosition, targetPosition, direction;
    private boolean isMoving;
    private Queue<Vector2> destinations; // Fila de destinos



    public MovePlayer(Sprite aya, Main game, Vector2 posAtual) {
        this.aya = aya;

        float worldWidth = game.getFitViewport().getCamera().viewportWidth;
        float worldHeight = game.getFitViewport().getCamera().viewportHeight;

        spawnAya = new Vector2(worldWidth - 1195f, worldHeight - 449f);
        esquina1 = new Vector2(worldWidth - 1015f, worldHeight - 449f);
        esquina2 = new Vector2(worldWidth - 1015f, worldHeight - 320f);
        esquina3 = new Vector2(worldWidth - 775f, worldHeight - 320f);
        esquina4 = new Vector2(worldWidth - 770f, worldHeight - 160f);
        esquina5 = new Vector2(worldWidth - 530f, worldHeight - 160f);
        esquina6 = new Vector2(worldWidth - 530f, worldHeight - 449f);
        pontoFinal = new Vector2(worldWidth - 360f, worldHeight - 449f);

        currentPosition = new Vector2(posAtual != null ? posAtual :spawnAya);
        targetPosition = new Vector2();
        direction = new Vector2();
        destinations = new LinkedList<>();
        isMoving = false;

        aya.setPosition(spawnAya.x - aya.getWidth() / 2, spawnAya.y - aya.getHeight() / 2);
    }

    // Adiciona destinos à fila
    public void moveTo(Vector2... targets) {
        for (Vector2 target : targets) {
            destinations.add(new Vector2(target)); // Adiciona cada destino à fila
        }
        if (!isMoving) {
            startNextMove(); // Inicia o movimento se não estiver se movendo
        }
    }

    // Inicia o próximo movimento na fila
    private void startNextMove() {
        if (!destinations.isEmpty()) {
            targetPosition.set(destinations.poll()); // Remove o próximo destino da fila
            direction.set(targetPosition).sub(currentPosition).nor();
            isMoving = true;
        }
    }

    // Atualiza o movimento
    public void update(float delta) {
        if (isMoving) {
            float distance = speed * delta;
            Vector2 moveStep = new Vector2(direction).scl(distance);

            if (currentPosition.dst(targetPosition) <= distance) {
                currentPosition.set(targetPosition);
                isMoving = false;
                startNextMove(); // Inicia o próximo movimento
            } else {
                currentPosition.add(moveStep);
            }
            aya.setPosition(currentPosition.x - aya.getWidth() / 2, currentPosition.y - aya.getHeight() / 2);
        }
    }

    // Métodos de atalho para movimentos
    public void moveToEsquina1() {
        moveTo(esquina1);
    }

    public void moveToEsquina2() {
        moveTo(esquina2);
    }

    public void moveToEsquina3() {
        moveTo(esquina3);
    }

    public void moveToEsquina4() {
        moveTo(esquina4);
    }

    public void moveToEsquina5() {
        moveTo(esquina5);
    }

    public void moveToEsquina6() {
        moveTo(esquina6);
    }

    public void moveToPontoFinal() {
        moveTo(pontoFinal);
    }

    // Getters para as posições
    public Vector2 getEsquina1() {
        return esquina1;
    }

    public Vector2 getEsquina2() {
        return esquina2;
    }

    public Vector2 getEsquina3() {
        return esquina3;
    }

    public Vector2 getEsquina4() {
        return esquina4;
    }

    public Vector2 getEsquina5() {
        return esquina5;
    }

    public Vector2 getEsquina6() {
        return esquina6;
    }

    public Vector2 getPontoFinal() {
        return pontoFinal;
    }

    public Vector2 getSpawnAya() {
        return spawnAya;
    }


    public boolean isAtSpawn() {
        float tolerance = 1;
        return currentPosition.epsilonEquals(spawnAya, tolerance);
    }

    public boolean isAtEsquina2() {
        float tolerance = 1;
        return currentPosition.epsilonEquals(esquina2, tolerance);
    }


    public boolean isAtEsquina3() {
        return currentPosition.epsilonEquals(esquina3,1f);
    }

    public boolean isAtEsquina5() {
        return currentPosition.epsilonEquals(esquina5,1f);
    }

    public boolean isAtEsquina6() {
        return currentPosition.epsilonEquals(esquina6,1f);
    }

    public boolean isAtPontoFinal() {
        return currentPosition.epsilonEquals(pontoFinal,1f);
    }

}
