package htmlfuzzing;
import htmlfuzzing.spi.Fuzzer;

import java.util.*;

public class FuzzingService {
    private static FuzzingService service;
    private ServiceLoader<Fuzzer> loader;
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
        Random idx = new Random();

        if ((idx.nextInt(1000) & 1) == 0) {
            Fuzzer fuzzer  = getTagRemover();
            fuzzer.fuzz(htmlstr);
        } else {
            Fuzzer fuzzer = getTagReplacer();
            fuzzer.fuzz(htmlstr);
        }
    }

    public void combinedModification(String htmlstr){
        Random count = new Random();
        for (int i = 0; i < count.nextInt(10); i++) {
            Fuzzer fuzzer = getTagReplacer();
            fuzzer.fuzz(htmlstr);
        }

    }

    public void insertHTML(String htmlstr){
        getTagInserter().fuzz(htmlstr);
    }
}
