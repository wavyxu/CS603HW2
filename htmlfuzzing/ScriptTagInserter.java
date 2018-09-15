package htmlfuzzing;
import htmlfuzzing.spi.Fuzzer;
public class ScriptTagInserter implements Fuzzer{
    @Override
    public String fuzz(String str){
        String extraStr = "<script type=\"text/javascript\">\n" +
                "document.write(\"CS603 HW2!\")\n" +
                "</script>";
        String res = new StringBuilder(str).insert(3 % str.length(), extraStr).toString();
        return res;
    }
}
