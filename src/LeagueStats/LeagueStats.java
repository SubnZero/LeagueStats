package LeagueStats;

import WebsiteParser.lolcounterParser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jason
 * Date: 10.05.13
 * Time: 19:00
 * To change this template use File | Settings | File Templates.
 */
public class LeagueStats {
    private JPanel panel1;
    private JComboBox champBox;
    private JButton updateLibButton;
    private JTextField summonerField;
    private JButton searchButton;
    private JComboBox Team1;
    private JComboBox Team2;
    private JComboBox Team3;
    private JComboBox Team4;
    private JComboBox Team5;
    private JComboBox Enemy1;
    private JComboBox Enemy2;
    private JComboBox Enemy3;
    private JComboBox Enemy4;
    private JComboBox Enemy5;
    private JList counterList;
    private DefaultListModel<String> counterListModel;
    private JList counterListTeam;
    private JRadioButton goodWithRadioButton;
    private JRadioButton goodAgainstRadioButton;
    private JRadioButton badAgainstRadioButton;
    private JProgressBar updateLibBar;
    private JButton mobafireButton;
    private String selectedChampion = "";
    private String summoner = "";
    private static List<int[]> cList = new ArrayList<int[]>();
    private static lolcounterParser lolcP;
    private static final int nChampions = 112;
    public static final List<String> champions = new ArrayList<String>(Arrays.asList(
            "Ahri",
            "Akali",
            "Alistar",
            "Amumu",
            "Anivia",
            "Annie",
            "Ashe",
            "Blitzcrank",
            "Brand",
            "Caitlyn",
            "Cassiopeia",
            "Cho'Gath",
            "Corki",
            "Darius",
            "Diana",
            "Dr. Mundo",
            "Draven",
            "Elise",
            "Evelynn",
            "Ezreal",
            "Fiddlesticks",
            "Fiora",
            "Fizz",
            "Galio",
            "Gangplank",
            "Garen",
            "Gragas",
            "Hecarim",
            "Heimerdinger",
            "Irelia",
            "Janna",
            "Jarvan IV",
            "Jax",
            "Jayce",
            "Karma",
            "Karthus",
            "Kassadin",
            "Katarina",
            "Kayle",
            "Kennen",
            "Kha'Zix",
            "Kog'Maw",
            "LeBlanc",
            "Lee Sin",
            "Leona",
            "Lissandra",
            "Lulu",
            "Lux",
            "Malphite",
            "Malzahar",
            "Maokai",
            "Master Yi",
            "Miss Fortune",
            "Mordekaiser",
            "Morgana",
            "Nami",
            "Nasus",
            "Nautilus",
            "Nidalee",
            "Nocturne",
            "Nunu",
            "Olaf",
            "Orianna",
            "Pantheon",
            "Poppy",
            "Quinn",
            "Rammus",
            "Renekton",
            "Rengar",
            "Riven",
            "Rumble",
            "Ryze",
            "Sejuani",
            "Shaco",
            "Shen",
            "Shyvana",
            "Singed",
            "Sion",
            "Sivir",
            "Skarner",
            "Sona",
            "Soraka",
            "Swain",
            "Syndra",
            "Talon",
            "Taric",
            "Teemo",
            "Thresh",
            "Tristana",
            "Trundle",
            "Tryndamere",
            "Twisted Fate",
            "Twitch",
            "Udyr",
            "Urgot",
            "Varus",
            "Vayne",
            "Veigar",
            "Vi",
            "Viktor",
            "Vladimir",
            "Volibear",
            "Warwick",
            "Wukong",
            "Xerath",
            "Xin Zhao",
            "Yorick",
            "Zac",
            "Zed",
            "Ziggs",
            "Zilean",
            "Zyra")
    );


    public LeagueStats() {
        lolcP = new lolcounterParser();

        champBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                selectedChampion = e.getItem().toString();
                if (selectedChampion != "---") {
                    counterListModel.clear();
                    int i = champions.indexOf(selectedChampion);
                    if (cList == null || cList.size() == 0) {
                        counterListModel.addElement("Downloading counter lib...");
                        update();
                    } else {
                        int[] integers = cList.get(i);
                        for (int champID : integers) {
                            if (champID != -1) {
                                counterListModel.addElement(champions.get(champID));
                            } else {
                                counterListModel.addElement("Parser Error (champID = -1)");
                            }

                        }
                    }

                }
            }
        });


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand() == "Search") {
                    summoner = summonerField.getText();
                }
            }
        });
        updateLibButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand() == "Update counter libary") {
                    update();
                }
            }
        });
        mobafireButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand() == "Mobafire") {
                    try {
                        openUrl("" + counterList.getSelectedIndex() + "");
                    } catch (IOException e1) {
                        e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }
        });
    }

    private void update() {
        new Thread() {
            public void run() {
                updateLibBar.setString("parsing lolcounter...");
                for (int i = 0; i < nChampions; i++) {
                    int[] counters = lolcP.getCounters(i);
                    if (counters == null) return;
                    cList.add(i, counters);
                    System.out.println(champions.get(i));
                    updateLibBar.setValue((i + 1) * 100 / nChampions);
                }
                updateLibBar.setString("saving to file...");
                updateLibBar.setIndeterminate(true);
                listToFile(cList);
                updateLibBar.setString("DONE");
                updateLibBar.setIndeterminate(false);
            }
        }.start();


    }

    private void listToFile(List<int[]> list) {
        try {
            FileWriter writer = new FileWriter("counters.txt");
            for (int[] IDs : list) {
                for (int ID : IDs) {
                    writer.write(ID + " ");
                }
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<int[]> fileToList() {
        List<int[]> list = new ArrayList<int[]>();

        try {
            Scanner scanner = new Scanner(new File("counters.txt"));
            for (int i = 0; i < nChampions; i++) {
                int[] counterIDs = new int[]{scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt()};
                list.add(i, counterIDs);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void openUrl(String url) throws IOException, URISyntaxException {
        if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

            if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
                java.net.URI uri = new java.net.URI(url);
                desktop.browse(uri);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LeagueStats");
        frame.setContentPane(new LeagueStats().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        cList = fileToList();
    }

    private void createUIComponents() {
        counterListModel = new DefaultListModel<String>();
        counterList = new JList<String>(counterListModel);
        badAgainstRadioButton = new JRadioButton("good against", true);
        goodAgainstRadioButton = new JRadioButton("good against", false);
        goodWithRadioButton = new JRadioButton("good against", false);
        ButtonGroup rButtonGroup = new ButtonGroup();
        rButtonGroup.add(badAgainstRadioButton);
        rButtonGroup.add(goodAgainstRadioButton);
        rButtonGroup.add(goodWithRadioButton);
        updateLibBar = new JProgressBar();
    }
}
