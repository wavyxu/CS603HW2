package htmlfuzzing;
import htmlfuzzing.spi.Fuzzer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TagReplacer implements Fuzzer{
    @Override
    public void fuzz(String htmlstr){
        Document doc = Jsoup.parse(htmlstr);
        Elements p = doc.getElementsByTag("p");
        p.attr("h1");
        return;
    }
}
