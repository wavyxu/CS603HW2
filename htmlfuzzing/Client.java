package htmlfuzzing;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.jsoup.safety.Whitelist;
import java.io.File;
public class Client {
    private static final int TESTNUM = 10;

    public static void creation(FuzzingService fuzzServ){
        //single modification
        for (int i = 0; i < TESTNUM; ++i){
            try {
                String html = "<html> " +
                        "<head><title id = \"cs\" + >CS603 HW2</title></head>" +
                        "<body><p>This is the body of HTML.</p></body>" +
                        "</html>";
                System.out.println("Before fuzz single modification");

                Document doc = Jsoup.parse(html);
                Element title = doc.getElementById("cs");
                //System.out.println(title);
                //System.out.println(doc.title());
                String fuzzedHtml = fuzzServ.singleModification(html);
                doc = Jsoup.parse(fuzzedHtml);
                System.out.println("After fuzz single modification");
                //System.out.println(doc.body().parent());
                System.out.println(doc.title());
                System.out.println("---------------------------------");
            }catch(Exception e){
                e.getStackTrace();
            }
        }

        //combined modification
        for (int i = 0; i < TESTNUM; ++i){
            try {
                String html = "<html> " +
                        "<head><title id = \"cs\" + >CS603 HW2</title></head>" +
                        "<body><p>This is the body of HTML.</p></body>" +
                        "</html>";
                String fuzzedHtml = fuzzServ.combinedModification(html);
                Document doc = Jsoup.parse(fuzzedHtml);
                System.out.println(doc.title());
            }catch(Exception e){
                e.getStackTrace();
            }
        }
        System.out.println("*************Creation Success!!!******************");
    }

    public static void clean(FuzzingService service){
        for (int i = 0; i < TESTNUM; ++i){
            try {
                String unsafe = "<p><a href='http://example.com/' onclick='stealCookies()'>Link</a></p>";
                String fuzzedHtml = service.insertHTML(unsafe);
                String safe = Jsoup.clean(fuzzedHtml, Whitelist.basic());
            }catch(Exception e){
                e.getStackTrace();
            }
        }
        System.out.println("*************Clean Success!!!******************");
    }

    public static void main(String[] args) {
        FuzzingService fuzzServ = FuzzingService.getInstance();
        creation(fuzzServ);
        clean(fuzzServ);
    }

}
