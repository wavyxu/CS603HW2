package htmlfuzzing;
import htmlfuzzing.spi.Fuzzer;
public class TagRemover implements Fuzzer{
    @Override
    public String fuzz(String s){
        String res = s.replaceFirst("<[^>]+>",""); //remove the first tag
        //String res = s.replaceFirst("","");
        return res;
    }
}
