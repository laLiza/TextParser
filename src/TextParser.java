import java.text.ParseException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser
{
    static String ParseBracketExpr(String exp ){
        String line = exp;
        String pattern = "\\s*\\(\\s*([^\\(\\)]+?)\\s*\\)\\s*";// скобках но без скобок внутри
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(line);
        String res ;
        while (m.find( )) {
            res =DirectorParser(m.group(1));
            exp = new StringBuilder(line).replace(m.start(0),m.end(0), " " + res + " ").toString();
            exp=  ParseBracketExpr(exp);
        }
        exp = DirectorParser(exp);
        return exp;
    }
    static String MulDevParser(String exp){
        String mul = "[*|/]";
        Pattern rm = Pattern.compile(mul);
        Matcher mm;

        String num = "\\(*\\s*([\\d]+(\\.\\d+)?)\\s*\\)*";
        String pattern = num + mul + num;
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(exp);
        String a,b,c;
        c = "err";
        int res = 100000;
        while (m.find( )) {
            // System.out.println("Found value: " + m.group());
            a = m.group(1);
            mm = rm.matcher(m.group());
            if(mm.find()) {
                c = mm.group();
            }
            b = m.group(3);
            if (Objects.equals(c, "*")) {
                res = Integer.parseInt(a) * Integer.parseInt(b);
            }
            if (Objects.equals(c, "/")) {
                res = Integer.parseInt(a) / Integer.parseInt(b);
            }
            exp = new StringBuilder(exp).replace(m.start(),m.end(),  " "+String.valueOf(res)+" ").toString();
        }
        return exp;
    }
    static String DevParser(String exp){
        String dev = "[/]";
        String num = "\\s*([\\d]+(\\.\\d+)?)\\s*";
        String pattern = num + dev + num;
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(exp);
        String a,b;
        int res;
        while (m.find( )) {
            a = m.group(1);
            b = m.group(3);
            res = Integer.parseInt(a) / Integer.parseInt(b);
            exp = new StringBuilder(exp).replace(m.start(1),m.end(3), " "+ String.valueOf(res)+" ").toString();
        }
        return exp;
    }
    static String PlusMinusParser(String exp){

        String mul = "[+|-]";
        Pattern rm = Pattern.compile(mul);
        Matcher mm;


        String num = "\\s*([\\d]+(\\.\\d+)?)\\s*";
        String pattern = num + mul + num;
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(exp);
        String a,b,c;
        c = "err";
        int res = 100000;
        while (m.find( )) {
            // System.out.println("Found value: " + m.group());
            a = m.group(1);
            mm = rm.matcher(m.group());
            if(mm.find()) {
                c = mm.group();
            }
            b = m.group(3);
            if (Objects.equals(c, "+")) {
                res = Integer.parseInt(a) + Integer.parseInt(b);
            }
            if (Objects.equals(c, "-")) {
                res = Integer.parseInt(a) - Integer.parseInt(b);
            }
            exp = new StringBuilder(exp).replace(m.start(),m.end(),  String.valueOf(res)).toString();
        }
        return exp;
    }
    static String MinusParser(String exp){
        String minus = "[-]";
        String num = "\\s*([\\d]+(\\.\\d+)?)\\s*";
        String pattern = num + minus + num;
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(exp);
        String a,b;
        int res;
        while (m.find( )) {
            a = m.group(1);
            b = m.group(3);
            res = Integer.parseInt(a) - Integer.parseInt(b);
            exp = new StringBuilder(exp).replace(m.start(1),m.end(3),  String.valueOf(res)).toString();
        }
        return exp;
    }
    static String DirectorParser(String exp){


        exp = MulDevParser(exp);
        exp = PlusMinusParser(exp);
        return exp;

    }
    public static void main( String args[] ) {
        String exp = " Proga (10*(3+(2*1))/2)*(((3)+(1)))     5500 6+8 4 77 3000 ! OK?"; //(10*(3+2)*2)
        exp = ParseBracketExpr(exp);
        System.out.println(exp);
    }
}
