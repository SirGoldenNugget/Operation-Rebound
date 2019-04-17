package org.minhvu.operationrebound.essentials;

import org.minhvu.operationrebound.Game;
import org.minhvu.operationrebound.entity.Player;

import java.awt.*;

public class Scoreboard {
    public static int score = 0;
    public static int bulletsFired = 0;
    public static int bulletsHit = 0;
    public static int reloads = 0;
    public static int damageInflicted = 0;
    public static int damageRecieved = 0;
    public static int bluePowerUps = 0;
    public static int redPowerUps = 0;
    public static int yellowPowerUps = 0;
    public static int greenPowerUps = 0;
    public static int totalEnemies = 0;
    public static int totalPowerUps = 0;

    private static long timer = System.currentTimeMillis();

    private static int accuracy;
    private static int time;

    public static void paint(Graphics2D g2d) {
        accuracy = bulletsFired > 0 ? (int) ((bulletsHit / (double) bulletsFired) * 100.0) : 0;
        time = (int) ((System.currentTimeMillis() - timer) / 1000.0);
        Player player = Game.getInstance().getPlayer();

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("calibri", Font.BOLD, 16));
        g2d.drawString("Ammo: " + player.getAmmo() + "/" + player.getMaxAmmo() + " Time: " + time + "s Score: " + score + " Accuracy: " + accuracy + "%", 10, 20);
    }

    public static void output() {
//        try {
//            FileWriter fileWriter = new FileWriter(Calendar.getInstance().getTime().getTime() + ".txt");
//            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//            bufferedWriter.write("Time: " + time + "s");
//            bufferedWriter.newLine();
//            bufferedWriter.write("Score: " + score);
//            bufferedWriter.newLine();
//            bufferedWriter.write("Accuracy: " + accuracy + "%");
//            bufferedWriter.newLine();
//            bufferedWriter.write("\tBullets Fired: " + bulletsFired);
//            bufferedWriter.newLine();
//            bufferedWriter.write("\tBullets Hit: " + bulletsHit);
//            bufferedWriter.newLine();
//            bufferedWriter.write("Reloads: " + reloads);
//            bufferedWriter.newLine();
//            bufferedWriter.write("Damage Inflicted: " + damageInflicted);
//            bufferedWriter.newLine();
//            bufferedWriter.write("Damage Recieved: " + damageRecieved);
//            bufferedWriter.newLine();
//            bufferedWriter.write("Power Ups Picked Up: " + (bluePowerUps + yellowPowerUps + greenPowerUps + redPowerUps));
//            bufferedWriter.newLine();
//            bufferedWriter.write("\tBlue Power Ups: " + bluePowerUps);
//            bufferedWriter.newLine();
//            bufferedWriter.write("\tYellow Power Ups: " + yellowPowerUps);
//            bufferedWriter.newLine();
//            bufferedWriter.write("\tGreen Power Ups: " + greenPowerUps);
//            bufferedWriter.newLine();
//            bufferedWriter.write("\tRed Power Ups: " + redPowerUps);
//            bufferedWriter.newLine();
//            bufferedWriter.write("Power Ups Spawned: " + totalPowerUps);
//            bufferedWriter.newLine();
//            bufferedWriter.write("Enemies Spawned: " + totalEnemies);
//            bufferedWriter.newLine();
//            bufferedWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void reset() {
        score = 0;
        bulletsFired = 0;
        bulletsHit = 0;
        reloads = 0;
        damageInflicted = 0;
        damageRecieved = 0;
        bluePowerUps = 0;
        redPowerUps = 0;
        yellowPowerUps = 0;
        greenPowerUps = 0;
        totalEnemies = 0;
        totalPowerUps = 0;
        timer = System.currentTimeMillis();
    }
}
