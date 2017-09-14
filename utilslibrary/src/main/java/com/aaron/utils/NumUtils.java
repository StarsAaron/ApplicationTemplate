package com.aaron.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * 数字处理工具
 * Created by Aaron on 2017/8/23.
 */

public class NumUtils {

//    ROUND_CEILING -------- 如果 BigDecimal 是正的，则做 ROUND_UP 操作；如果为负，则做 ROUND_DOWN 操作。
//    ROUND_DOWN ----- 从不在舍弃(即截断)的小数之前增加数字。
//    ROUND_FLOOR ----- 如果 BigDecimal 为正，则作 ROUND_UP ；如果为负，则作 ROUND_DOWN 。
//    ROUND_HALF_DOWN ----- 若舍弃部分> .5，则作 ROUND_UP；否则，作 ROUND_DOWN 。
//    ROUND_HALF_EVEN ----- 如果舍弃部分左边的数字为奇数，则作 ROUND_HALF_UP ；如果它为偶数，则作 ROUND_HALF_DOWN 。
//    ROUND_HALF_UP ----- 若舍弃部分>=.5，则作 ROUND_UP ；否则，作 ROUND_DOWN 。
//    ROUND_UNNECESSARY ----- 该“伪舍入模式”实际是指明所要求的操作必须是精确的，，因此不需要舍入操作。
//    ROUND_UP ----- 总是在非 0  舍弃小数(即截断)之前增加数字。

    /**
     * 保留n位小数
     * BigDecimal 四舍五入
     * @param num 需要格式化的数字
     * @param count 保留小数位个数
     * @return
     */
    public static float getFractionDigits(float num,int count){
        BigDecimal b = new BigDecimal(num);
        float f1 = b.setScale(count, BigDecimal.ROUND_HALF_UP).floatValue();//表明四舍五入，保留两位小数
        return f1;
    }

    /**
     * 保留n位小数
     * DecimalFormat 文本格式化
     * @param num 需要格式化的数字
     * @param count 保留小数位个数
     * @return
     */
    public static String getFractionDigits2(float num,int count){
        String str = "##0.";
        StringBuilder builder = new StringBuilder(str);
        for(int i=0;i<count;i++){
            builder.append("0");
        }
        DecimalFormat fnum = new DecimalFormat(builder.toString());//或###,###.##
        return fnum.format(num);
    }

    /**
     * 保留n位小数
     * NumberFormat 文本格式化
     * @param num 需要格式化的数字
     * @param count 保留小数位个数
     * @return
     */
    public static String getFractionDigits3(float num,int count){
        NumberFormat form = NumberFormat.getCurrencyInstance( );//建立货币格式化引用
        form.setMinimumIntegerDigits(1);  //整数位最少位数
        form.setMinimumFractionDigits(2); // 小数位最少位数
        form.setMaximumFractionDigits(count); // 小数位最多位数
        return form.format(num);
    }

    /**
     * 获取百分比数字
     * NumberFormat 文本格式化
     * @param num 需要格式化的数字
     * @param count 保留小数位个数
     * @return
     */
    public static String getPercentNum(BigDecimal num,int count){
        NumberFormat form = NumberFormat.getPercentInstance();//建立百分比格式化引用
        form.setMinimumIntegerDigits(1);  //整数位最少位数
        form.setMinimumFractionDigits(0); // 小数位最少位数
        form.setMaximumFractionDigits(count); // 小数位最多位数
        return form.format(num);
    }

    /**
     * 利息计算
     * @return
     */
    public static void compute(){
        NumberFormat currency = NumberFormat.getCurrencyInstance(); //建立货币格式化引用
        NumberFormat percent = NumberFormat.getPercentInstance();  //建立百分比格式化引用
        percent.setMaximumFractionDigits(3); //百分比小数点最多3位
        BigDecimal loanAmount = new BigDecimal("15000.48"); //贷款金额
        BigDecimal interestRate = new BigDecimal("0.008"); //利率
        BigDecimal interest = loanAmount.multiply(interestRate); //相乘，BigDecimal都是不可变的（immutable）的，在进行每一步运算时，都会产生一个新的对象，所以在做加减乘除运算时千万要保存操作后的值。
        System.out.println("贷款金额:\t" + currency.format(loanAmount));
        System.out.println("利率:\t" + percent.format(interestRate));
        System.out.println("利息:\t" + currency.format(interest));
    }

    /**
     * 注意BigDecimal构造函数，优先使用字符串构造函数
     */
    public static void attention(){
        BigDecimal aDouble =new BigDecimal(1.22);
        System.out.println("construct with a double value: " + aDouble);
        BigDecimal aString = new BigDecimal("1.22");
        System.out.println("construct with a String value: " + aString);
    }

    public static void main(String[] args){
        float t = 433.335666f;
        System.out.println(getFractionDigits(t,2));
        System.out.println(getFractionDigits2(t,2));
        System.out.println(getFractionDigits3(t,2));
        System.out.println(getPercentNum(new BigDecimal(t),2));
        compute();
        attention();

//        433.34
//        433.34
//        ￥433.34
//        43,333.57%
//        贷款金额:	￥15,000.48
//        利率:	0.8%
//        利息:	￥120.00
//        construct with a double value: 1.2199999999999999733546474089962430298328399658203125
//        construct with a String value: 1.22
    }

}
