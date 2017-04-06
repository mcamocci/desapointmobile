package com.desapoint.desapoint.toolsUtilities;

/**
 * Created by root on 4/4/17.
 */
public class UsefullyFunctions {

    public static String getPhone(String phone){

        final String code="+255";
        String returned="";
        if(phone.charAt(0)=='0'){
            returned=code.concat(phone.substring(1,phone.length()));
        }else if(phone.charAt(0)=='2' && phone.charAt(3)=='0'){
            returned=code.concat(phone.substring(4,phone.length()));
        }else if(phone.charAt(0)=='+' && phone.charAt(4)=='0'){
            returned=code.concat(phone.substring(5,phone.length()));
        }else if(phone.charAt(0)=='2'){
            returned="+".concat(phone);
        }else if(phone.charAt(0)=='+'){
            returned=phone;
        }
        return returned;
    }
}
