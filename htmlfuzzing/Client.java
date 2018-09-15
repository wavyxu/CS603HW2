package htmlfuzzing;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.safety.Whitelist;
public class Client {

    private static final int TESTNUM = 100;

    public static void creation(FuzzingService service){
        //single modification
        System.out.println("-------- creation begin ----------");
        for (int i = 0; i < TESTNUM; ++i){
            try {
                String htmlstr = "<html><head><title>CS603 HW2</title></head><body>" +
                        "<p id = \"1\">Sentence 1.</p> " +
                        "<p id = \"2\">Sentence 2.</p> " +
                        "</body></html>";
                Document doc = Jsoup.parse(htmlstr);
                System.out.println("Before fuzz single modification");
                System.out.println(doc.title());
                service.singleModification(htmlstr);
                doc = Jsoup.parse(htmlstr);
                System.out.println("After fuzz single modification");
                System.out.println(doc.title());

                System.out.println("---------------------------------");
            }catch(Exception e){
                e.getStackTrace();
            }
        }

        //combined modification
        for (int i = 0; i < TESTNUM; ++i){
            try {
                String htmlstr = "<html><head><title>First parse</title></head>" + "<body><p>Parsed HTML into a doc.</p></body></html>";
                service.combinedModification(htmlstr);
                Document doc = Jsoup.parse(htmlstr);
            }catch(Exception e){
                e.getStackTrace();
            }
        }
    }

    public static void TestClean(FuzzingService service){
        System.out.println("-------- clean begin  ----------");
        for (int i = 0; i < TESTNUM; ++i){
            try {
                String unsafe = "<p><a id = \"1\" href='/photo.jpg'>picture</a></p>";
                service.insertHTML(unsafe);
                String safe = Jsoup.clean(unsafe, Whitelist.basic());
                Document cleaned = Jsoup.parse(safe);
                Element url = cleaned.getElementById("1");
                System.out.println(url);
            }catch(Exception e){
                e.getStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        FuzzingService fuzzService = FuzzingService.getInstance();
        creation(fuzzService);
        TestClean(fuzzService);
    }

}
