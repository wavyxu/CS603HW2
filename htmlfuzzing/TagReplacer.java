package htmlfuzzing;
import htmlfuzzing.spi.Fuzzer;
public class TagReplacer implements Fuzzer{
    @Override
    public String fuzz(String s){
        String res = s.replaceFirst("<[^>]+>","<script>"); //repalce the first tag to <script>
        //String res = s.replaceFirst("</p>","<script>");
        return res;
    }
}
