package com.rapipay.qrsicici.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class Util {

    public static String failureResponse() {
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("Message","Invalid");
            jsonObject.put("ActCode","1");
            String rootElement="XML";
            return  "<"+rootElement+">" + XML.toString(jsonObject) + "</"+rootElement+">";

        } catch (JSONException e) {
            return "";
        }
    }

}
