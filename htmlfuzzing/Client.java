package htmlfuzzing;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.safety.Whitelist;
public class Client {

    private static final int TESTNUM = 50;

    public static void creation(FuzzingService service){

        System.out.println("-------- each loop with a single modification  ----------");
        for (int i = 0; i < TESTNUM; ++i){
            try {
                String htmlstr = "<html>" +
                        "<head><title>CS603 HW2</title></head><body>" +
                        "<div>" +
                        "<p id = \"1\">Sentence 1.</p> " +
                        "<p id = \"2\">Sentence 2.</p> " +
                        "<img src = \"./picture1.jpg\">" +
                        "</img>" +
                        "</div>" +
                        "</body></html>";
                //Document doc = Jsoup.parse(htmlstr);

                //System.out.println(doc.title());
                service.singleModification(htmlstr);
                System.out.println("single modification : " + i);
                //doc = Jsoup.parse(htmlstr);
            }catch(Exception e){
                e.getStackTrace();
            }
        }

        System.out.println("-------- each loop with a combined modification  ----------");
        for (int i = 0; i < TESTNUM; ++i){
            try {
                String htmlstr = "<html>" +
                        "<head><title>CS603 HW2</title></head><body>" +
                        "<div>" +
                        "<p id = \"1\">Sentence 1.</p> " +
                        "<p id = \"2\">Sentence 2.</p> " +
                        "<img src = \"./picture1.jpg\">" +
                        "</img>" +
                        "</div>" +
                        "</body></html>";
                service.combinedModification(htmlstr);
                System.out.println("combined modification : " + i);
            }catch(Exception e){
                e.getStackTrace();
            }
        }
    }

    public static void clean(FuzzingService service){
            try {
                System.out.println("-------- before clean  ----------");
                String src = "<p><a id = \"1\" href='/photo.jpg'>picture</a></p>";
                System.out.println(src);
                service.insertHTML(src);
                String target = Jsoup.clean(src, Whitelist.basic());
                Document cleaned = Jsoup.parse(target);
                Element url = cleaned.getElementById("1");
                System.out.println("-------- after begin  ----------");
                System.out.println(target);
            }catch(Exception e){
                e.getStackTrace();
            }

    }
    public static void main(String[] args) {
        FuzzingService fuzzService = FuzzingService.getInstance();
        creation(fuzzService);
        clean(fuzzService);
    }

}
