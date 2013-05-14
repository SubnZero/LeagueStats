package WebsiteParser;

import LeagueStats.Match;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jason
 * Date: 13.05.13
 * Time: 20:18
 * To change this template use File | Settings | File Templates.
 */
public class lolkingParser {
    private final Parser p;
    private String name;
    private byte level = 0;
    private String urlExt;
    private ImageIcon summonerIcon;
    private int serverID;
    private String info;
    private byte[] wlApm = new byte[2];
    private byte[] wlAdc = new byte[2];
    private byte[] wlSupport = new byte[2];
    private byte[] wlJungle = new byte[2];
    private byte[] wlTop = new byte[2];
    private int[] wlNormal = new int[2];
    private String[] wlSoloRanked = new String[2];
    private String soloRankedStatus;
    private byte soloRankedLP = 0;
    private String[] wlTeamRanked3 = new String[2];
    private String team3RankedStatus;
    private byte team3RankedLP = 0;
    private String[] wlTeamRanked5 = new String[2];
    private String team5RankedStatus;
    private byte team5RankedLP = 0;
    private String rSolo;
    private String rTeam;
    private List<Match> lastMatches;

    public lolkingParser() {
        p = new Parser("http://www.lolking.net");
    }

    /**
     * Checking if the summoner exists on the selected Server.
     */
    public boolean isValid(String name, int serverID) {
        this.name = name;
        this.serverID = serverID;
        String source;
        String subSource;
        int iStart;
        int iEnd;

        p.setUrlExt("/search?name=" + name);
        p.init();
        source = p.getSource();

        if (source == null || source.isEmpty()) return false;

        iStart = source.indexOf("\"margin-t");  // <div style="margin-top: 20px; margin-bottom: 20px; overflow: hidden; width: 816px;">
        iEnd = source.indexOf("<p", iStart);    // <p style="font-size: 14px; color: #999; text-shadow: 1px 1px 1px #000; margin: 0px 10px 20px 10px;">Note: Please allow up to 48 hours for name changes to be reflected.</p>

        subSource = source.substring(iStart, iEnd);

        int stringIndex = 0;
        for (int i = 0; i < (serverID + 1); i++)
            stringIndex = subSource.indexOf("search_result_item") + "search_result_item".length();
        String nextString = subSource.substring(stringIndex + "search_result_item".length(), stringIndex + "search_result_item".length() + 1);


        if (nextString.equals("_")) {
            return false;
        } else {
            int iIDStart = subSource.indexOf("onclick=\"window.location='", stringIndex) + "onclick=\"window.location='".length();
            int iIDEnd = subSource.indexOf('\'', iIDStart);
            urlExt = subSource.substring(iIDStart, iIDEnd);
            return true;
        }
    }

    /**
     * Parsing Summoner information.
     */
    public void parse() {
        p.setUrlExt(urlExt);
        p.init();
        String source;
        String subSource;
        String temp;
        int currentIndex = 0;

        source = p.getSource();
        subSource = source.substring(source.indexOf("summoner_titlebar"), source.indexOf("<!-- STREAM -->"));

        // Summoner Icon
        currentIndex = subSource.indexOf("src=", currentIndex) + "src=\"".length();
        String iconPath = "http:" + subSource.substring(currentIndex, subSource.indexOf('\"', currentIndex));
        try {
            URL iconUrl = new URL(iconPath);
            ImageIcon tempIcon = new ImageIcon(iconUrl);
            Image img = tempIcon.getImage();
            Image newimg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            summonerIcon = new ImageIcon(newimg);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.err.println("Summoner icon URL not accepted: " + iconPath);
        }

        // Summoner Name
        currentIndex = subSource.indexOf("nowrap", currentIndex) + "nowrap;\">".length();
        name = subSource.substring(currentIndex, subSource.indexOf('<', currentIndex));

        // Summoner Level
        currentIndex = subSource.indexOf("Level", currentIndex) + "Level ".length();
        level = Byte.valueOf(subSource.substring(currentIndex, subSource.indexOf('<', currentIndex)));

        // Win Losses: APM ADC SUPP JUNGLE TOP
        for (int i = 0; i < 5; i++) {
            currentIndex = subSource.indexOf("data-wins=\"", currentIndex) + "data-wins=\"".length();
            switch (i) {
                case 0:
                    wlApm[0] = Byte.valueOf(subSource.substring(currentIndex, currentIndex + 1));
                    break;
                case 1:
                    wlAdc[0] = Byte.valueOf(subSource.substring(currentIndex, currentIndex + 1));
                    break;
                case 2:
                    wlSupport[0] = Byte.valueOf(subSource.substring(currentIndex, currentIndex + 1));
                    break;
                case 3:
                    wlJungle[0] = Byte.valueOf(subSource.substring(currentIndex, currentIndex + 1));
                    break;
                case 4:
                    wlTop[0] = Byte.valueOf(subSource.substring(currentIndex, currentIndex + 1));
                    break;
            }

            currentIndex = subSource.indexOf("data-losses=\"", currentIndex) + "data-losses=\"".length();
            switch (i) {
                case 0:
                    wlApm[1] = Byte.valueOf(subSource.substring(currentIndex, currentIndex + 1));
                    break;
                case 1:
                    wlAdc[1] = Byte.valueOf(subSource.substring(currentIndex, currentIndex + 1));
                    break;
                case 2:
                    wlSupport[1] = Byte.valueOf(subSource.substring(currentIndex, currentIndex + 1));
                    break;
                case 3:
                    wlJungle[1] = Byte.valueOf(subSource.substring(currentIndex, currentIndex + 1));
                    break;
                case 4:
                    wlTop[1] = Byte.valueOf(subSource.substring(currentIndex, currentIndex + 1));
                    break;
            }
        }


        // 3v3 Team Ranked
        currentIndex = subSource.indexOf('>', subSource.indexOf("personal_ratings_rating", currentIndex));       // <div class="personal_ratings_rating" style="margin-bottom: 0; font-size: 20px; height: 30px; font: bold 20px 'Trebuchet MS';">Seeding</div>
        team3RankedStatus = subSource.substring(currentIndex, subSource.indexOf('<', currentIndex));             //             ^                                                                                                                 ^
        currentIndex = subSource.indexOf("3px", currentIndex) + "3px;\">".length();
        temp = subSource.substring(currentIndex, subSource.indexOf('<', currentIndex));
        if (!temp.equals("&nbsp;"))
            team3RankedLP = Byte.valueOf(subSource.substring(currentIndex, subSource.indexOf('<', currentIndex)));
        currentIndex = subSource.indexOf("#000", currentIndex) + "#000;\">".length();
        wlTeamRanked3[0] = subSource.substring(currentIndex, subSource.indexOf('<', currentIndex));
        currentIndex = subSource.indexOf("#000", currentIndex) + "#000;\">".length();
        wlTeamRanked3[1] = subSource.substring(currentIndex, subSource.indexOf('<', currentIndex));

        // 5v5 Solo Ranked
        currentIndex = subSource.indexOf('>', subSource.indexOf("personal_ratings_rating", currentIndex));       // <div class="personal_ratings_rating" style="margin-bottom: 0; font-size: 20px; height: 30px; font: bold 20px 'Trebuchet MS';">Seeding</div>
        soloRankedStatus = subSource.substring(currentIndex, subSource.indexOf('<', currentIndex));             //             ^                                                                                                                 ^
        currentIndex = subSource.indexOf("3px", currentIndex) + "3px;\">".length();
        temp = subSource.substring(currentIndex, subSource.indexOf('<', currentIndex));
        if (!temp.equals("&nbsp;"))
            team3RankedLP = Byte.valueOf(subSource.substring(currentIndex, subSource.indexOf('<', currentIndex)));
        currentIndex = subSource.indexOf("#000", currentIndex) + "#000;\">".length();
        wlSoloRanked[0] = subSource.substring(currentIndex, subSource.indexOf('<', currentIndex));
        currentIndex = subSource.indexOf("#000", currentIndex) + "#000;\">".length();
        wlSoloRanked[1] = subSource.substring(currentIndex, subSource.indexOf('<', currentIndex));

        // 5v5 Team Ranked
        currentIndex = subSource.indexOf('>', subSource.indexOf("personal_ratings_rating", currentIndex));       // <div class="personal_ratings_rating" style="margin-bottom: 0; font-size: 20px; height: 30px; font: bold 20px 'Trebuchet MS';">Seeding</div>
        team5RankedStatus = subSource.substring(currentIndex, subSource.indexOf('<', currentIndex));             //             ^                                                                                                                 ^
        currentIndex = subSource.indexOf("3px", currentIndex) + "3px;\">".length();
        temp = subSource.substring(currentIndex, subSource.indexOf('<', currentIndex));
        if (!temp.equals("&nbsp;"))
            team3RankedLP = Byte.valueOf(subSource.substring(currentIndex, subSource.indexOf('<', currentIndex)));
        currentIndex = subSource.indexOf("#000", currentIndex) + "#000;\">".length();
        wlTeamRanked5[0] = subSource.substring(currentIndex, subSource.indexOf('<', currentIndex));
        currentIndex = subSource.indexOf("#000", currentIndex) + "#000;\">".length();
        wlTeamRanked5[1] = subSource.substring(currentIndex, subSource.indexOf('<', currentIndex));

        // Normal
        currentIndex = subSource.indexOf("<div class=\"li", currentIndex);        // <div class="lifetime_stats_header">Normal 5v5</div>
        currentIndex = subSource.indexOf("lifetime_stats_val", currentIndex) + "lifetime_stats_val\" style=\"\">".length();
        wlNormal[0] = Integer.valueOf(subSource.substring(currentIndex, subSource.indexOf("<", currentIndex)));
        currentIndex = subSource.indexOf("lifetime_stats_val", currentIndex) + "lifetime_stats_val\" style=\"\">".length();
        wlNormal[1] = Integer.valueOf(subSource.substring(currentIndex, subSource.indexOf('<', currentIndex)));

    }

    public String getName() {
        return name;
    }

    public byte getLevel() {
        return level;
    }

    public ImageIcon getSummonerIcon() {
        return summonerIcon;
    }

    public byte[] getWlApm() {
        return wlApm;
    }

    public byte[] getWlAdc() {
        return wlAdc;
    }

    public byte[] getWlSupport() {
        return wlSupport;
    }

    public byte[] getWlJungle() {
        return wlJungle;
    }

    public byte[] getWlTop() {
        return wlTop;
    }

    public int[] getWlNormal() {
        return wlNormal;
    }

    public String[] getWlSoloRanked() {
        return wlSoloRanked;
    }

    public String getSoloRankedStatus() {
        return soloRankedStatus;
    }

    public byte getSoloRankedLP() {
        return soloRankedLP;
    }

    public String[] getWlTeamRanked3() {
        return wlTeamRanked3;
    }

    public String getTeam3RankedStatus() {
        return team3RankedStatus;
    }

    public byte getTeam3RankedLP() {
        return team3RankedLP;
    }

    public String[] getWlTeamRanked5() {
        return wlTeamRanked5;
    }

    public String getTeam5RankedStatus() {
        return team5RankedStatus;
    }

    public byte getTeam5RankedLP() {
        return team5RankedLP;
    }

    public List<Match> getLastMatches() {
        return lastMatches;
    }
}
