package htmlfuzzing;
import htmlfuzzing.spi.Fuzzer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Random;


public class TagRemover implements Fuzzer{
    @Override
    public void fuzz(String htmlstr){
        String[] tags = new String[] {"p","/p","head","/head", "tile" ,"/tile",
                "img","/img", "div","/div"};
        Random idx = new Random();
        String selectedTag = tags[idx.nextInt(10)];
        Document doc = Jsoup.parse(htmlstr);
        Elements p = doc.getElementsByTag(selectedTag);
        p.removeAttr(selectedTag);
        return;
    }
}
