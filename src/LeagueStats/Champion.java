package LeagueStats;

/**
 * Created with IntelliJ IDEA.
 * User: Jason
 * Date: 12.05.13
 * Time: 14:03
 * To change this template use File | Settings | File Templates.
 */
public class Champion {
    private final String name;
    private final String title;
    private byte idn;       // ID alphabetical sorted
    private byte[] badagainst;
    private byte[] goodagainst;
    private byte[] goodwith;

    public Champion(String name, String title) {
        this.name = name;
        this.title = title;

        badagainst = new byte[10];
        goodagainst = new byte[10];
        goodwith = new byte[10];
    }

    public byte[] getBadAgainst() {
        return badagainst;
    }

    public void setBadAgainst(byte[] badagainst) {
        this.badagainst = badagainst;
    }

    public byte[] getGoodAgainst() {
        return goodagainst;
    }

    public void setGoodAgainst(byte[] goodagainst) {
        this.goodagainst = goodagainst;
    }

    public byte[] getGoodWith() {
        return goodwith;
    }

    public void setGoodWith(byte[] goodwith) {
        this.goodwith = goodwith;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public byte getIdn() {
        return idn;
    }

    public void setIdn(byte idn) {
        this.idn = idn;
    }
}
