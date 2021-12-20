import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MathExprParserTest {

     String exp = "my unit test ( 1+1 )*(10)";
     @Before
     public void SetUp(){

     }
     @Test
     public void parseString() {
         String res = "my unit test 20.0";
         Assert.assertEquals(res, MathParser.ParseString(exp));
     }
    @Test
    public void AlgebraicProcessor_2plus2is4() {
        Assert.assertEquals(MathParser.ParseString("2+2")," 4.0");
    }

    @Test
    public void AlgebraicProcessor_2minus3isMinus1() {
        Assert.assertEquals(MathParser.ParseString("2-3")," -1.0");
    }

    @Test
    public void AlgebraicProcessor_2multiply3is6() {
        Assert.assertEquals(MathParser.ParseString("2*3")," 6.0");
    }

    @Test
    public void AlgebraicProcessor_6divide3is2() {
        Assert.assertEquals(MathParser.ParseString("6/3")," 2.0");
    }

    @Test
    public void AlgebraicProcessor_bracketsMultiply() {
        Assert.assertEquals(MathParser.ParseString("(2+2)*3")," 12.0");
    }

    @Test
    public void AlgebraicProcessor_bracketsDivide() {
        Assert.assertEquals(MathParser.ParseString("(2+7)/3")," 3.0");
    }


}