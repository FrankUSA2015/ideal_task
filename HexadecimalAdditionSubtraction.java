import java.util.Locale;

/**
 * @author 葛鑫
 * @Description 计算16进制的算法
 * @create 2021-08-10 23:11
 */

public class HexadecimalAdditionSubtraction {

    private static int ConversionOfCharactersNumbers(char aa){

        if(aa>='0'&&aa<='9'){
            return aa-'0';
        }else{
            return aa-'a'+10;
        }
    }

    private static char ConversionCharacters(int aa){
        if(aa>=0&&aa<=9){
            char result = (char) ('0'+aa);
            return result;
        }else {
            return (char) ('a'+(aa-10));
        }
    }
    public static String  subtractionNumber(String start,String end){

        StringBuffer sb = new StringBuffer();
        start=start.toLowerCase();
        end=end.toLowerCase();
        char[] start_char = start.toCharArray();
        char[] end_char = end.toCharArray();
        int flag=0;
        for(int i=start.length()-1;i>=0;i--){
            int start_num = ConversionOfCharactersNumbers(start_char[i]);
            int end_num = ConversionOfCharactersNumbers(end_char[i]);
            int difference = end_num-start_num-flag;
            if(difference>0){
                sb.insert(0,ConversionCharacters(difference));
                flag=0;
            }else {
                difference = difference+16;
                sb.insert(0,ConversionCharacters(difference));
                flag=1;
            }
        }
        return sb.toString();
    }
}
