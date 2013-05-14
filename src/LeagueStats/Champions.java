package LeagueStats;

import WebsiteParser.lolcounterParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Jason
 * Date: 12.05.13
 * Time: 14:20
 * To change this template use File | Settings | File Templates.
 */

public class Champions {
    private static final int iChampions = 112;  // a total of 113 Champions
    private final List<Champion> champions;
    private final lolcounterParser cParser;
    private boolean badAgainstUpToDate;
    private boolean goodAgainstUpToDate;
    private boolean goodWithUpToDate;
    private float pUpdate = 0;
    private boolean isUpdateRunning = false;

    public Champions() {
        cParser = new lolcounterParser();
        champions = new ArrayList<Champion>();

        //champions.add(new Champion("Aatrox", "", (byte) 114));

        champions.add(new Champion("Ahri", ""));
        champions.add(new Champion("Akali", ""));
        champions.add(new Champion("Alistar", ""));
        champions.add(new Champion("Amumu", ""));
        champions.add(new Champion("Anivia", ""));
        champions.add(new Champion("Annie", ""));
        champions.add(new Champion("Ashe", ""));
        champions.add(new Champion("Blitzcrank", ""));
        champions.add(new Champion("Brand", ""));
        champions.add(new Champion("Caitlyn", ""));

        champions.add(new Champion("Cassiopeia", ""));
        champions.add(new Champion("Cho'Gath", ""));
        champions.add(new Champion("Corki", ""));
        champions.add(new Champion("Darius", ""));
        champions.add(new Champion("Diana", ""));
        champions.add(new Champion("Dr. Mundo", ""));
        champions.add(new Champion("Draven", ""));
        champions.add(new Champion("Elise", ""));
        champions.add(new Champion("Evelynn", ""));
        champions.add(new Champion("Ezreal", ""));

        champions.add(new Champion("Fiddlesticks", ""));
        champions.add(new Champion("Fiora", ""));
        champions.add(new Champion("Fizz", ""));
        champions.add(new Champion("Galio", ""));
        champions.add(new Champion("Gangplank", ""));
        champions.add(new Champion("Garen", ""));
        champions.add(new Champion("Gragas", ""));
        champions.add(new Champion("Graves", ""));
        champions.add(new Champion("Hecarim", ""));
        champions.add(new Champion("Heimerdinger", ""));

        champions.add(new Champion("Irelia", ""));
        champions.add(new Champion("Janna", ""));
        champions.add(new Champion("Jarvan IV", ""));
        champions.add(new Champion("Jax", ""));
        champions.add(new Champion("Jayce", ""));
        champions.add(new Champion("Karma", ""));
        champions.add(new Champion("Karthus", ""));
        champions.add(new Champion("Kassadin", ""));
        champions.add(new Champion("Katarina", ""));
        champions.add(new Champion("Kayle", ""));

        champions.add(new Champion("Kennen", ""));
        champions.add(new Champion("Kha'Zix", ""));
        champions.add(new Champion("Kog'Maw", ""));
        champions.add(new Champion("LeBlanc", ""));
        champions.add(new Champion("Lee Sin", ""));
        champions.add(new Champion("Leona", ""));
        champions.add(new Champion("Lissandra", ""));
        champions.add(new Champion("Lulu", ""));
        champions.add(new Champion("Lux", ""));
        champions.add(new Champion("Malphite", ""));

        champions.add(new Champion("Malzahar", ""));
        champions.add(new Champion("Maokai", ""));
        champions.add(new Champion("Master Yi", ""));
        champions.add(new Champion("Miss Fortune", ""));
        champions.add(new Champion("Mordekaiser", ""));
        champions.add(new Champion("Morgana", ""));
        champions.add(new Champion("Nami", ""));
        champions.add(new Champion("Nasus", ""));
        champions.add(new Champion("Nautilus", ""));
        champions.add(new Champion("Nidalee", ""));

        champions.add(new Champion("Nocturne", ""));
        champions.add(new Champion("Nunu", ""));
        champions.add(new Champion("Olaf", ""));
        champions.add(new Champion("Orianna", ""));
        champions.add(new Champion("Pantheon", ""));
        champions.add(new Champion("Poppy", ""));
        champions.add(new Champion("Quinn", ""));
        champions.add(new Champion("Rammus", ""));
        champions.add(new Champion("Renekton", ""));
        champions.add(new Champion("Rengar", ""));

        champions.add(new Champion("Riven", ""));
        champions.add(new Champion("Rumble", ""));
        champions.add(new Champion("Ryze", ""));
        champions.add(new Champion("Sejuani", ""));
        champions.add(new Champion("Shaco", ""));
        champions.add(new Champion("Shen", ""));
        champions.add(new Champion("Shyvana", ""));
        champions.add(new Champion("Singed", ""));
        champions.add(new Champion("Sion", ""));
        champions.add(new Champion("Sivir", ""));

        champions.add(new Champion("Skarner", ""));
        champions.add(new Champion("Sona", ""));
        champions.add(new Champion("Soraka", ""));
        champions.add(new Champion("Swain", ""));
        champions.add(new Champion("Syndra", ""));
        champions.add(new Champion("Talon", ""));
        champions.add(new Champion("Taric", ""));
        champions.add(new Champion("Teemo", ""));
        champions.add(new Champion("Thresh", ""));
        champions.add(new Champion("Tristana", ""));

        champions.add(new Champion("Trundle", ""));
        champions.add(new Champion("Tryndamere", ""));
        champions.add(new Champion("Twisted Fate", ""));
        champions.add(new Champion("Twitch", ""));
        champions.add(new Champion("Udyr", ""));
        champions.add(new Champion("Urgot", ""));
        champions.add(new Champion("Varus", ""));
        champions.add(new Champion("Vayne", ""));
        champions.add(new Champion("Veigar", ""));
        champions.add(new Champion("Vi", ""));

        champions.add(new Champion("Viktor", ""));
        champions.add(new Champion("Vladimir", ""));
        champions.add(new Champion("Volibear", ""));
        champions.add(new Champion("Warwick", ""));
        champions.add(new Champion("Wukong", ""));
        champions.add(new Champion("Xerath", ""));
        champions.add(new Champion("Xin Zhao", ""));
        champions.add(new Champion("Yorick", ""));
        champions.add(new Champion("Zac", ""));
        champions.add(new Champion("Zed", ""));

        champions.add(new Champion("Ziggs", ""));
        champions.add(new Champion("Zilean", ""));
        champions.add(new Champion("Zyra", ""));

        readFromFile();
    }

    void updateBadAgainst() {
        new Thread() {
            public void run() {
                for (Champion c : champions) {
                    int id = champions.indexOf(c);
                    byte[] counters = cParser.getBadAgainst(champions.get(id));
                    if (counters == null) {
                        pUpdate = 0;
                        isUpdateRunning = false;
                        return;
                    }
                    c.setBadAgainst(counters);
                    System.out.println(c.getName());
                    pUpdate += (100 / iChampions) / 3;
                }
                badAgainstUpToDate = true;
            }
        }.start();
    }

    void updateGoodAgainst() {
        goodAgainstUpToDate = true;
    }

    void updateGoodWith() {
        goodWithUpToDate = true;
    }

    public void updateAll() {
        if (!isUpdateRunning) {
            isUpdateRunning = true;
            updateBadAgainst();
            updateGoodAgainst();
            updateGoodWith();

            new Thread(new Runnable() {
                public void run() {
                    while (!allUpToDate()) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    writeToFile();
                }
            });
        }
    }

    void writeToFile() {
        try {
            FileWriter writer = new FileWriter("data");

            for (Champion c : champions) {
                byte[] badAgainst = c.getBadAgainst();
                byte[] goodAgainst = c.getGoodAgainst();
                byte[] goodWith = c.getGoodWith();

                String d = " ";
                for (byte b : badAgainst) {
                    writer.write(d + b);
                }

                for (byte b : goodAgainst) {
                    writer.write(d + b);
                }

                for (byte b : goodWith) {
                    writer.write(d + b);
                }

                writer.write(d);
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Cannot open data file!");
        }

    }

    void readFromFile() {
        try {
            Scanner scanner = new Scanner(new File("data"));
            for (Champion c : champions) {
                byte[] badAgainst = new byte[10];
                for (int i = 0; i < 10; i++) {
                    if (scanner.hasNext()) {
                        badAgainst[i] = scanner.nextByte();
                    } else {
                        System.err.println("Error in File: Champion: " + c.getName() + ", BadAgainst Nr." + (i + 1));
                        return;
                    }
                }
                byte[] goodAgainst = new byte[10];
                for (int i = 0; i < 10; i++) {
                    if (scanner.hasNext()) {
                        goodAgainst[i] = scanner.nextByte();
                    } else {
                        System.err.println("Error in File: Champion: " + c.getName() + ", GoodAgainst Nr." + (i + 1));
                        return;
                    }
                }
                byte[] goodWith = new byte[10];
                for (int i = 0; i < 10; i++) {
                    if (scanner.hasNext()) {
                        goodWith[i] = scanner.nextByte();
                    } else {
                        System.err.println("Error in File: Champion: " + c.getName() + ", GoodWith Nr." + (i + 1));
                        return;
                    }
                }

                c.setBadAgainst(badAgainst);
                c.setGoodAgainst(goodAgainst);
                c.setGoodWith(goodWith);
            }
            badAgainstUpToDate = true;
            goodAgainstUpToDate = true;
            goodWithUpToDate = true;
            allUpToDate();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Data file not found!");
        }
    }

    public int getIndexByName(String s) {
        for (Champion c : champions) {
            if (c.getName().equals(s)) {
                return champions.indexOf(c);
            }
        }
        System.err.println("No found champion for given name: " + s);
        return -1;
    }

    public boolean allUpToDate() {
        return badAgainstUpToDate & goodAgainstUpToDate & goodWithUpToDate;
    }

    public Champion get(int i) {
        return champions.get(i);
    }

    public float getPUpdate() {
        return pUpdate;
    }
}
