package htmlfuzzing;
import htmlfuzzing.spi.Fuzzer;

import java.util.*;

public class FuzzingService {
    private static FuzzingService service;
    private ServiceLoader<Fuzzer> loader;
    private static final int combinedNum = 3;
    private List<String> services;

    private FuzzingService() {

        loader = ServiceLoader.load(Fuzzer.class);
        services = new ArrayList<>();

    }

    private void loadServices() {
        while (loader.iterator().hasNext()) {
            System.out.println(loader.iterator().next().toString());
            services.add(loader.iterator().next().toString());
        }
    }

    public static synchronized FuzzingService getInstance() {
        if (service == null) {
            service = new FuzzingService();
        }
        return service;
    }

    private Fuzzer getTagInserter(){
//        for (String s : services) {
//            if (s.equals("htmlfuzzing.ScriptTagInserter")) {
//                return
//            }
//        }
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

    public void singleModification(String htmlstr){
        Fuzzer remove = getTagRemover();
        Fuzzer replace = getTagReplacer();
        Fuzzer[] ObjArray = {remove, replace};
        Random rand = new Random();
        int rdIndex = rand.nextInt(2);
        ObjArray[rdIndex].fuzz(htmlstr);
        //return ObjArray[rdIndex].fuzz(htmlstr);                 //randomly pick a ServiceProvider to run fuzz method.
    }

    public void combinedModification(String str){
        Fuzzer remove = getTagRemover();
        Fuzzer replace = getTagReplacer();
        Fuzzer[] ObjArray = {remove, replace};
        Random rand = new Random();
        String res = str;
        for (int i = 0; i < combinedNum; ++i) {
            int rdIndex = rand.nextInt(2);
            ObjArray[rdIndex].fuzz(res);             //randomly pick a ServiceProvider to run fuzz method.
        }
        return ;
    }

    public void insertHTML(String str){

        getTagInserter().fuzz(str);
    }
}
