package WebsiteParser;

import LeagueStats.LeagueStats;

/**
 * Created with IntelliJ IDEA.
 * User: Jason
 * Date: 11.05.13
 * Time: 10:18
 * To change this template use File | Settings | File Templates.
 */
public class lolcounterParser {
    private Parser p = null;


    public lolcounterParser() {
        p = new Parser("http://www.lolcounter.com");
    }

    public int[] getCounters(int champ) {
        int[] counters = new int[10];
        String urlExt = "/champ/" + LeagueStats.champions.get(champ).toLowerCase().replaceAll("['. ]", "");
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
            counters[i] = LeagueStats.champions.indexOf(countersource.substring(iChampStart, iChampEnd));
            System.out.println(countersource.substring(iChampStart, iChampEnd));
        }

        return counters;
    }
}
