package com.goldenasia.lottery.util;

/**
 * Created by ACE-PC on 2016/6/20.
 */
public class CurrencyTest {
    private static final String[] NUMBERS = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
    private static final String[] BIG_POS = {"","万","亿"};
    private static final String[] SMALL_POS = {"","拾","佰","仟"};
    private static final String[] UNITS = {"角","分","厘"};
    private static final String YUAN = "圆";
    private static final String YUAN_ZHENG = "圆整";
    private static final String[] SMALL_O = {"零仟","零佰","零拾"};
    private static final String[] BIG_O = {"零亿","零万","零圆"};
    private static final String[] BIG_T = {"亿","万","圆"};
    private static final String BILL = "亿";
    private static final String ZERO = "零";
    private static final String DOUBLE_ZERO = "零零";

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(toCurrency("100000000001.203"));
    }

    public static String toCurrency(String d){
        if(d == null){
            return "";
        }
        String s = null;
        String high = null;
        String low = null;
        d = d.trim();
        d = d.replaceAll("\\s+", "");
        if(d.contains(".")){
            high = d.substring(0,d.indexOf("."));
            low = d.substring(d.indexOf(".")+1);
        }else{
            high = d;
        }
        high = toHigh(high);
        low = toLow(low);
        if(!isEmpty(low) && !isEmpty(high)){
            s = high + YUAN + low;
        }else if(!isEmpty(high) && isEmpty(low) ){
            s = high + YUAN_ZHENG ;
        }else if(isEmpty(high) && !isEmpty(low)){
            while(true){
                if(low.startsWith(NUMBERS[0])){
                    low = low.substring(2);
                }else{
                    break;
                }
            }
            s = low;
        }
        return s;
    }

    private static String toHigh(String high) {
        if(high == null){
            return null;
        }
        high = high.trim();
        if(high.length() < 10 && Integer.parseInt(high) == 0){
            return null;
        }

        StringBuffer data = new StringBuffer(high);
        data = data.reverse();
        high = data.toString();
        boolean b = false;
        boolean m = false;
        boolean f = false;
        StringBuffer rs = new StringBuffer();
        if(high.length() > 8){
            String[] billions = getBillions(high);
            for(int i = 0 ; i < billions.length ; i++){
                if(isEmpty(billions[i])){
                    break;
                }
                if(i > 0){
                    b = true;
                }
                if(b){
                    rs.append(BILL);
                }
                if(m && f){
                    rs.append(BILL);
                }
                String[] millions = getMillions(billions[i]);
                if(millions != null){
                    for(int j = 0 ; j < millions.length ; j++){
                        if(isEmpty(millions[j])){
                            break;
                        }
                        if(b && j > 0){
                            m = true;
                        }
                        if(Integer.parseInt(millions[j]) == 0){
                            f = false;
                            continue;
                        }else{
                            f = true;
                        }
                        if(b && m){
                            rs.append(BILL);
                        }
                        rs.append(BIG_POS[j]);
                        for(int n = 0  ; n < millions[j].length() ; n++){
                            rs.append(SMALL_POS[n]).append(NUMBERS[Integer.parseInt(millions[j].charAt(n)+"")]);
                        }
                    }
                }else{
                    for(int n = 0  ; n < billions[i].length() ; n++){
                        rs.append(SMALL_POS[n]).append(NUMBERS[Integer.parseInt(billions[i].charAt(n)+"")]);
                    }
                }


            }
        }else if(high.length() > 4) {
            String[] millions = getMillions(high);
            for(int j = 0 ; j < millions.length ; j++){
                if(isEmpty(millions[j])){
                    break;
                }
                rs.append(BIG_POS[j]);
                for(int n = 0  ; n < millions[j].length() ; n++){
                    rs.append(SMALL_POS[n]).append(NUMBERS[Integer.parseInt(millions[j].charAt(n)+"")]);
                }
            }
        }else{
            for(int n = 0  ; n < high.length() ; n++){
                rs.append(SMALL_POS[n]).append(NUMBERS[Integer.parseInt(high.charAt(n)+"")]);
            }
        }

        rs = rs.reverse();

        //return rs.toString();
        return getResult(rs.toString());
    }

    private static String getResult(String s) {
        int l = s.length();
        if(s == null){
            return null;
        }
        for(int i = 0 ; i < SMALL_O.length ; i++){
            s = s.replaceAll(SMALL_O[i], ZERO);
        }
        s = s.replaceAll(DOUBLE_ZERO, ZERO);
        for(int i = 0 ; i < BIG_O.length ; i++){
            s = s.replaceAll(BIG_O[i], BIG_T[i]);
        }

        if(l != s.length()){
            s = getResult(s);
        }
        return s;
    }

    private static String[] getMillions(String high) {
        if(high.length() > 4){
            int l = high.length()/4+1;
            String[] billions = new String[l];
            int index = 0;
            int st = 0;
            int en = 4;
            while(true){
                if(high.length() > 4 ){
                    billions[index] = high.substring(st,en);
                    high = high.substring(en);
                }else{
                    billions[index] = high;
                    break;
                }
                index++;
            }
            return billions;
        }
        return null;
    }

    private static boolean isEmpty(String s){
        return s == null || "".equals(s.trim());
    }
    private static String[] getBillions(String high) {
        if(high.length() > 8){
            int l = high.length()/8+1;
            String[] billions = new String[l];
            int index = 0;
            int st = 0;
            int en = 8;
            while(true){
                if(high.length() > 8 ){
                    billions[index] = high.substring(st,en);
                    high = high.substring(en);
                }else{
                    billions[index] = high;
                    break;
                }
                index++;
            }
            return billions;
        }
        return null;
    }

    private static String toLow(String low) {
        if(low == null){
            return null;
        }
        StringBuffer rs = new StringBuffer();
        for(int i = 0 ; i < low.length() ; i++){
            rs.append(NUMBERS[Integer.parseInt(low.charAt(i)+"")]).append(UNITS[i]);
        }
        return rs.toString();
    }


}
