package LeagueStats;

/**
 * Created with IntelliJ IDEA.
 * User: Jason
 * Date: 12.05.13
 * Time: 14:03
 * To change this template use File | Settings | File Templates.
 */
public class Champion {
    String name;
    String title;
    byte idn;       // ID alphabetical sorted
    byte idt;       // ID time sorted
    byte[] badagainst = new byte[10];
    byte[] goodagainst = new byte[10];
    byte[] goodwith = new byte[10];

    public Champion(String name, String title, byte idn, byte idt, byte[] badagainst, byte[] goodagainst, byte[] goodwith) {
        this.name = name;
        this.title = title;
        this.idn = idn;
        this.idt = idt;
        this.badagainst = badagainst;
        this.goodagainst = goodagainst;
        this.goodwith = goodwith;
    }

    public Champion(String name, String title, byte idn, byte idt) {
        this.name = name;
        this.title = title;
        this.idn = idn;
        this.idt = idt;
    }
}
