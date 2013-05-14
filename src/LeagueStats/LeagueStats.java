package LeagueStats;

import WebsiteParser.lolkingParser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created with IntelliJ IDEA.
 * User: Jason
 * Date: 10.05.13
 * Time: 19:00
 * To change this template use File | Settings | File Templates.
 */
public class LeagueStats {
    public static final Champions champions = new Champions();
    private final lolkingParser kParser = new lolkingParser();
    public JProgressBar progressBar;
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
    private JButton mobafireButton;
    private JComboBox summonerServerBox;
    private JTabbedPane tabbedPane1;
    private JLabel apMidStatsLabel;
    private JLabel adCarryStatsLabel;
    private JLabel supportStatsLabel;
    private JLabel jungleStatsLabel;
    private JLabel topStatsLabel;
    private JLabel summonerIconLabel;
    private JLabel selectedChampionIconLabel;
    private JTable lastMatches;
    private JLabel normalWinsLabel;
    private JLabel normalLossesLabel;
    private JLabel lolkingScoreLabel;
    private JTextArea changelistSoFarSummonerTextArea;
    private JLabel levelLabel;
    private ButtonGroup radioButtonGroup;
    private String selectedChampion;
    private String summoner;

    public LeagueStats() {

        champBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                selectedChampion = e.getItem().toString();
                counterListModel.clear();
                if (!champions.allUpToDate() && champions.getPUpdate() == 0) {
                    counterListModel.addElement("Downloading counter lib...");
                    mobafireButton.setEnabled(false);
                    updateLibButton.setEnabled(false);
                    progressBar.setIndeterminate(true);
                    champions.updateAll();
                } else if (champions.allUpToDate() && selectedChampion.equals("---")) {
                    counterListModel.clear();
                    counterListModel.addElement("Download complete!");
                    mobafireButton.setEnabled(false);
                    updateLibButton.setEnabled(true);
                    progressBar.setIndeterminate(false);

                } else if (!selectedChampion.equals("---")) {
                    counterListModel.clear();
                    progressBar.setIndeterminate(false);
                    mobafireButton.setEnabled(true);

                    int indexByName = champions.getIndexByName(selectedChampion);
                    byte[] bytes = champions.get(indexByName).getBadAgainst();
                    for (byte b : bytes) {
                        if (b != -1) {
                            String name = champions.get(b).getName();
                            if (!counterListModel.contains(name)) {
                                counterListModel.addElement(name);
                            }
                        } else {
                            counterListModel.addElement("Parser Error (champID = -1)");
                        }
                    }
                }
            }
        });


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("Search")) {
                    summoner = summonerField.getText();
                    System.out.println(summonerServerBox.getSelectedIndex());
                    if (kParser.isValid(summoner, summonerServerBox.getSelectedIndex())) {
                        kParser.parse();
                        summonerIconLabel.setIcon(kParser.getSummonerIcon());
                        byte[] wlTemp = kParser.getWlApm();
                        float percentage;
                        if (wlTemp[0] + wlTemp[1] == 0)
                            percentage = 0;
                        else
                            percentage = 100 * wlTemp[0] / (wlTemp[0] + wlTemp[1]);
                        apMidStatsLabel.setText(wlTemp[0] + " Wins | " + (wlTemp[0] + wlTemp[1]) + " Losses - " + percentage + "%");

                        wlTemp = kParser.getWlAdc();
                        if (wlTemp[0] + wlTemp[1] == 0)
                            percentage = 0;
                        else
                            percentage = 100 * wlTemp[0] / (wlTemp[0] + wlTemp[1]);
                        adCarryStatsLabel.setText(wlTemp[0] + " Wins | " + (wlTemp[0] + wlTemp[1]) + " Losses - " + percentage + "%");

                        wlTemp = kParser.getWlSupport();
                        if (wlTemp[0] + wlTemp[1] == 0) {
                            percentage = 0;
                        } else {
                            percentage = 100 * wlTemp[0] / (wlTemp[0] + wlTemp[1]);
                        }
                        supportStatsLabel.setText(wlTemp[0] + " Wins | " + (wlTemp[0] + wlTemp[1]) + " Losses - " + percentage + "%");

                        wlTemp = kParser.getWlJungle();
                        if (wlTemp[0] + wlTemp[1] == 0)
                            percentage = 0;
                        else
                            percentage = 100 * wlTemp[0] / (wlTemp[0] + wlTemp[1]);
                        jungleStatsLabel.setText(wlTemp[0] + " Wins | " + (wlTemp[0] + wlTemp[1]) + " Losses - " + percentage + "%");

                        wlTemp = kParser.getWlTop();
                        if (wlTemp[0] + wlTemp[1] == 0)
                            percentage = 0;
                        else
                            percentage = 100 * wlTemp[0] / (wlTemp[0] + wlTemp[1]);
                        topStatsLabel.setText(wlTemp[0] + " Wins | " + (wlTemp[0] + wlTemp[1]) + " Losses - " + percentage + "%");

                        summonerIconLabel.setText(kParser.getName() + " - Level: " + kParser.getLevel());

                        normalWinsLabel.setText("" + kParser.getWlNormal()[0]);
                        normalLossesLabel.setText("" + kParser.getWlNormal()[1]);
                    }
                }
            }
        });
        updateLibButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("Update counter libary")) {
                    counterListModel.clear();
                    counterListModel.addElement("Downloading counter lib...");
                    mobafireButton.setEnabled(false);
                    updateLibButton.setEnabled(false);
                    progressBar.setIndeterminate(true);
                    champions.updateAll();
                }
            }
        });
        mobafireButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("Mobafire") && champions.allUpToDate()) {
                    try {
                        openUrl("http://www.mobafire.com/league-of-legends/" + counterListModel.get(counterList.getSelectedIndex()).toLowerCase().replaceAll("['. ]", "") + "-guide");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LeagueStats");
        frame.setContentPane(new LeagueStats().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    void openUrl(String url) throws IOException, URISyntaxException {
        if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

            if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
                java.net.URI uri = new java.net.URI(url);
                desktop.browse(uri);
            }
        }
    }

    private void createUIComponents() {
        counterListModel = new DefaultListModel<String>();
        counterList = new JList<String>(counterListModel);
        badAgainstRadioButton = new JRadioButton("good against", true);
        goodAgainstRadioButton = new JRadioButton("good against", false);
        goodWithRadioButton = new JRadioButton("good against", false);
        progressBar = new JProgressBar();
        levelLabel = new JLabel();
        selectedChampionIconLabel = new JLabel();
        summonerIconLabel = new JLabel();


        DefaultTableModel lastMatchesModel = new DefaultTableModel();
        lastMatchesModel.addColumn("Champion");
        lastMatchesModel.addColumn("Length");
        lastMatchesModel.addColumn("Stats");
        lastMatchesModel.addColumn("Gold");
        lastMatchesModel.addColumn("Minions");
        lastMatchesModel.addColumn("Spells");
        lastMatchesModel.addColumn("Items");
        lastMatches = new JTable(lastMatchesModel);


    }
}
