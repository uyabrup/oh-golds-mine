package vn.luis.goldsmine.util;

public class Function {
    
    /* Check Digit */
	public String checkDigit(int number){
        return number<=9?"0"+number:String.valueOf(number);
    }
	
}
