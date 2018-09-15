package htmlfuzzing;
import htmlfuzzing.spi.Fuzzer;
public class ScriptTagInserter implements Fuzzer{
    @Override
    public void fuzz(String str){
        String extraStr = "<script type=\"text/javascript\">\n" +
                "document.write(\"Hello World!\")\n" +
                "</script>";
        String res = new StringBuilder(str).insert(3 % str.length(), extraStr).toString();
        return ;
    }
}
