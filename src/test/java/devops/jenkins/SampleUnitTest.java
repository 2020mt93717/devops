package devops.jenkins;

import java.beans.Transient;

import org.junit.Test;

import junit.framework.TestCase;

public class SampleUnitTest extends TestCase{

    @Test
    public void testSampleTest1() {
        
    }
    
    @Test
    public void testSampleTest2() {
        
    }
    
    @Test
    public void testSampleTest3s() {
        
    }
    @Test
    public String addTestResult(){
        System. out. println("addtestREsult");
        return null;
        
    }
    @Test
    public String subTestResult(){
        return null;
        
    }    
     std::cout << "\t----String class functions----";  
    std::cout << "\n\t1] getValue() = " << s1.getValue();  
    std::cout << "\n\t2] charAt() = " << s1.charAt(5);  
    std::cout << "\n\t3] compareTo() = " << s1.compareTo("java program");  
    std::cout << "\n\t4] endsWith() = " << s1.endsWith("ming");  
    std::cout << "\n\t5] equals() = " << s1.equals("java programming");  
    std::cout << "\n\t6] equalsIgnoreCase() = " << s1.equalsIgnoreCase("JAVA PROGRAMMING");  
    char a[100]="";  
    s1.getChars(1, 14, a);  
    std::cout << "\n\t7] getChars() = " << a;  
  
  
    // change the string using setValue() function  
    s1.setValue("The horse can run faster than human being");  
    std::cout << "\n\n\tNow string is '" << s1.getValue()<<"'";  
    std::cout << "\n\n\t8] indexOf() = " << s1.indexOf("horse");  
    std::cout << "\n\t9] lastIndexOf() = " << s1.lastIndexOf("faster");  
    std::cout << "\n\t10] length() = " << s1.length();  
    std::cout << "\n\t11] replace() = " << s1.replace("horse", "dog");  
  
    char mystr2[] = "The Elephant is bigger than ant";  
    String s2(mystr2);  
    std::cout << "\n\n\tNow string is '" << s2.getValue() << "'";  
    std::cout << "\n\n\t12] Reverse() = " << s2.reverse();  
    // reverse the string for further operations  
    s2.reverse();  
  
    std::cout << "\n\t13] startsWith() = " << s2.startsWith("The");  
    std::cout << "\n\t14] substring() = " << s2.substring(13);  
    std::cout << "\n\t15] toLowerCase() = " << s2.toLowerCase();  
    std::cout << "\n\t16] toUpperCase() = " << s2.toUpperCase();  
  
    char mystr3[] = "            C/C++ Programming                 ";  
    String s3(mystr3);  
    std::cout << "\n\n\tNow string is '" << s3.getValue() << "'";  
    std::cout << "\n\n\t17] trim() = " << s3.trim();  
  
    // Character class functions  
    std::cout << "\n\n\t----Character class functions----";  
    std::cout << "\n\t1] isLetter() = " << Character::isLetter('T');  
    std::cout << "\n\t2] isDigit() = " << Character::isDigit('6');  
    std::cout << "\n\t3] isUpperCase() = " << Character::isUpperCase('c');  
    std::cout << "\n\t4] isLowerCase() = " << Character::isLowerCase('W');  
    std::cout << "\n\t5] isWhiteSpace() = " << Character::isWhitespace('c');  
    std::cout << "\n\t   isWhiteSpace() =  " << Character::isWhitespace(' ');  
  
    String s4("C:\\Visual Studio Projects\\My String Functions\\My String Functions\\My String Functions.cpp");  
    std::cout << "\n\n\tFilename = " << s4.substring(s4.lastIndexOf("\\") + 1);  
}
