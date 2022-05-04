package com.cydeo.utilities;

public class Utils {

    public static int StringtoInt(String string){
String string2="";
        for (char c : string.toCharArray()) {

            if (c>=48 && c<=57){
                string2+=c;
            }



        }

        int a=Integer.parseInt(string2);

        return a;
    }
}
