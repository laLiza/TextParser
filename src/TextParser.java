import java.text.ParseException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser
{
    static String ParseBracketExpr(String exp ){
        String patternStr = "\\s*\\(\\s*([^\\(\\)]+?)\\s*\\)\\s*";// скобках но без скобок внутри
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(exp);
        String res ;
        while (matcher.find( )) {
            res =DirectorParser(matcher.group(1));
            exp = new StringBuilder(exp).replace(matcher.start(0),matcher.end(0), " " + res + " ").toString();
            exp=  ParseBracketExpr(exp);
        }
        exp = DirectorParser(exp);
        return exp;
    }
    static String MulDevParser(String exp){
        String firstNum,secondNum,token;
        token = "";
        int res = 0;

        String operator = "[*|/]";
        Pattern operatorPattern = Pattern.compile(operator);
        Matcher operatorMatcher;

        String num = "\\(*\\s*([\\d]+(\\.\\d+)?)\\s*\\)*";
        String patternStr = num + operator + num;
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(exp);

        while (matcher.find( )) {
            firstNum = matcher.group(1);
            operatorMatcher = operatorPattern.matcher(matcher.group());
            if(operatorMatcher.find()) {
                token = operatorMatcher.group();
            }
            secondNum = matcher.group(3);
            if (Objects.equals(token, "*")) {
                res = Integer.parseInt(firstNum) * Integer.parseInt(secondNum);
            }
            if (Objects.equals(token, "/")) {
                res = Integer.parseInt(firstNum) / Integer.parseInt(secondNum);
            }
            exp = new StringBuilder(exp).replace(matcher.start(),matcher.end(),  " "+String.valueOf(res)+" ").toString();
        }
        return exp;
    }

    static String PlusMinusParser(String exp){

        String firstNum,secondNum,token;
        token = "";
        int res = 0;

        String operator = "[+|-]";
        Pattern operatorPattern = Pattern.compile(operator);
        Matcher operatorMatcher;

        String num = "\\(*\\s*([\\d]+(\\.\\d+)?)\\s*\\)*";
        String patternStr = num + operator + num;
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(exp);

        while (matcher.find( )) {
            firstNum = matcher.group(1);
            operatorMatcher = operatorPattern.matcher(matcher.group());
            if(operatorMatcher.find()) {
                token = operatorMatcher.group();
            }
            secondNum = matcher.group(3);
            if (Objects.equals(token, "+")) {
                res = Integer.parseInt(firstNum) + Integer.parseInt(secondNum);
            }
            if (Objects.equals(token, "-")) {
                res = Integer.parseInt(firstNum) - Integer.parseInt(secondNum);
            }
            exp = new StringBuilder(exp).replace(matcher.start(),matcher.end(),  " "+String.valueOf(res)+" ").toString();
        }
        return exp;
    }

    static String DirectorParser(String exp){
        exp = MulDevParser(exp);
        exp = PlusMinusParser(exp);
        return exp;
    }
    public static void main( String args[] ) {
        String exp = " Proga (10*(3+(2*1))/2)*(((3)+(1))) 5500 6+8 4kcdsll 78887 3000 ! OK?"; //(10*(3+2)*2)
        exp = ParseBracketExpr(exp);
        System.out.println(exp);
    }
}
