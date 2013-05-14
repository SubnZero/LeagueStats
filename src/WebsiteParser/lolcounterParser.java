package WebsiteParser;

import LeagueStats.Champion;
import LeagueStats.LeagueStats;

import static LeagueStats.LeagueStats.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jason
 * Date: 11.05.13
 * Time: 10:18
 * To change this template use File | Settings | File Templates.
 */
public class lolcounterParser {
    private final Parser p;

    public lolcounterParser() {
        p = new Parser("http://www.lolcounter.com");
    }

    public byte[] getBadAgainst(Champion c) {
        byte[] badAgainst = new byte[10];
        String urlExt = "/champ/" + c.getName().toLowerCase().replaceAll("['. ]", "");
        p.setUrlExt(urlExt);
        p.init();
        String source = p.getSource();
        if (source == null) return null;
        int iChampStart = 0;
        int iChampEnd;


        int istart = source.indexOf("\"#counterPickModal\"") + "\"#counterPickModal\" href=\"#\">(Add more)</a></div>".length();
        int iend = source.indexOf("\"#badPickModal\"");
        String countersource = source.substring(istart, iend);

        for (int i = 0; i < 10; i++) {
            iChampStart = countersource.indexOf("<h4>", iChampStart) + "<h4>         ".length();
            iChampEnd = countersource.indexOf('<', iChampStart) - "        ".length();
            badAgainst[i] = (byte) champions.getIndexByName(countersource.substring(iChampStart, iChampEnd));
        }

        return badAgainst;
    }
}
