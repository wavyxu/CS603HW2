package htmlfuzzing;
import htmlfuzzing.spi.Fuzzer;
import java.util.Random;
import java.util.ServiceLoader;
public class FuzzingService {
    private static FuzzingService service;
    private ServiceLoader<Fuzzer> loader;
    private static final int combinedNum = 3;

    private FuzzingService() {
        loader = ServiceLoader.load(Fuzzer.class);
    }

    public static synchronized FuzzingService getInstance() {
        if (service == null) {
            service = new FuzzingService();
        }
        return service;
    }

    private Fuzzer getTagInserter(){
        for (Fuzzer fu : loader) {
            if (fu.getClass().getName().equals("htmlfuzzing.ScriptTagInserter"))
                return fu;
        }
        return null;
    }

    private Fuzzer getTagRemover(){
        for (Fuzzer fu : loader) {
            if (fu.getClass().getName().equals("htmlfuzzing.TagRemover"))
                return fu;
        }
        return null;
    }

    private Fuzzer getTagReplacer(){
        for (Fuzzer fu : loader) {
            if (fu.getClass().getName().equals("htmlfuzzing.TagReplacer"))
                return fu;
        }
        return null;
    }

    public String singleModification(String str){
        Fuzzer remove = getTagRemover();
        Fuzzer replace = getTagReplacer();
        Fuzzer[] ObjArray = {remove, replace};
        Random rand = new Random();
        int rdIndex = rand.nextInt(2);
        return ObjArray[rdIndex].fuzz(str);                 //randomly pick a ServiceProvider to run fuzz method.
    }

    public String combinedModification(String str){
        Fuzzer remove = getTagRemover();
        Fuzzer replace = getTagReplacer();
        Fuzzer[] ObjArray = {remove, replace};
        Random rand = new Random();
        String res = str;
        for (int i = 0; i < combinedNum; ++i) {
            int rdIndex = rand.nextInt(2);
            res = ObjArray[rdIndex].fuzz(res);             //randomly pick a ServiceProvider to run fuzz method.
        }
        return res;
    }

    public String insertHTML(String str){
        return getTagInserter().fuzz(str);
    }
}
