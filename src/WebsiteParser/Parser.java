package WebsiteParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created with IntelliJ IDEA.
 * User: Jason
 * Date: 10.05.13
 * Time: 22:20
 * To change this template use File | Settings | File Templates.
 */
class Parser {
    private String source;
    private String urlS = "";
    private String urlExtS = "";

    public Parser(String url) {
        this.urlS = url;
    }

    public String getUrlExt() {
        return urlExtS;
    }

    public void setUrlExt(String urlExt) {
        this.urlExtS = urlExt;
    }

    public void init() {
        try {
            URL url = new URL(urlS + urlExtS);
            URLConnection conn = url.openConnection();
            String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; de; rv:1.8.1.12) Gecko/20080201 Firefox/2.0.0.12";
            conn.addRequestProperty("User-Agent", userAgent);

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String str;
            StringBuilder builder = new StringBuilder(1024);
            while ((str = in.readLine()) != null) {
                builder.append(str);
            }
            in.close();
            source = builder.toString();
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
            System.err.println("Could not establish connection!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.err.println("Could not establish connection!");
        }
    }

    public String getSource() {
        return source;
    }
}
