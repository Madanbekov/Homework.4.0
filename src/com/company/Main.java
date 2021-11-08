package com.company;

import java.util.Random;

public class Main {

    public static int bossHealth = 1100;
    public static int bossDamage = 60;
    public static String bossDefence = "";
    public static int[] heroesHealth = {270, 280, 240, 200,400,200,250,230};
    public static int[] heroesDamage = {20, 15, 25, 0, 10,30,25,35};
    public static String[] heroesAttackType = {"Physical",
            "Magical", "Kinetic", "Medic", "Golem", "lucky","Berserk","Thor"};
    public static int round_number = 0;
    public static Random random = new Random();


    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            round();
        }
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(
                heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
        System.out.println("Boss chose " + bossDefence);
    }

    public static void round() {
        round_number++;
        chooseBossDefence();
        if (bossHealth > 0) { // на всякий случай
            bossHits();
        }
        heroesHit();
        medicRehabilitation();
        printStatistics();
        lucky();
        Golem();
        Berserk();
        Thor();
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
       /* if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0
                && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(12); //0,1,2,3,4,5,6,7,8,9
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    System.out.println("Critical damage: " + heroesDamage[i] * coeff);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    public static void medicRehabilitation() {


        for (int i = 0; i < heroesAttackType.length; i++) {
            if (i == 3) {
                continue;
            }
            if (heroesHealth[i] > 0 && heroesHealth[i] < 200 && heroesHealth[3] > 0) {
                heroesHealth[i] += 20;
                System.out.println("Док вылечил - " + heroesAttackType[i]);
                break;

            }
        }


    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println(round_number + " ROUND ______________");
        System.out.println("Boss health: " + bossHealth
                + " (" + bossDamage + ")");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i]
                    + " health: " + heroesHealth[i]
                    + " (" + heroesDamage[i] + ")");
        }
        System.out.println("____________________");
    }

    public static void Golem() {

        int damageBoss = bossDamage / 5;
        int choto = 0;

        if (heroesHealth[4] > 0) {
            for (int i = 0; i > heroesHealth.length; i++) {
                if (i == 4) {
                    continue;
                } else if (heroesHealth[i] > 0) {
                    choto++;

                }
            }
            heroesHealth[4] -= damageBoss * choto ;
            System.out.println("Урон голему " + damageBoss * choto + "+" + bossDamage);

        }

    }

    public static void lucky() {
        boolean udacha = random.nextBoolean();
        if (heroesHealth[5] > 0) {
            if (udacha) {
                heroesHealth[5] += bossDamage - 25;
                System.out.println("lucky_spassya");

            }
        }
    }


    public static void Berserk() {
        int blocked = random.nextInt(30 - 10) + 10;
        if (heroesHealth[6] > 0) {
            heroesHealth[6] += blocked;
            bossHealth -= blocked;
            System.out.println(heroesAttackType[6] + " заблокировал " + blocked + "% урона и ответил Боссу " + blocked + "% урона");
        }
    }

    public static void Thor () {
        int muffle = random.nextInt(3);
        if (heroesHealth[7] > 0) {
            for (int i = 0; i < bossDamage; i++) {
                if (muffle == 1) {
                    System.out.println("Thor заглушил Босса");
                    bossDamage = 0;
                    break;
                } else if (muffle == 2) {
                    System.out.println("У Thor не получилось");
                    break; }
            }
        }
    }
}
