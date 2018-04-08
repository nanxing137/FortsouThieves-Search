package src.net.bittreasury.fts.service.impl;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Main {

    /**
     * 请完成下面这个函数，实现题目要求的功能
     * 当然，你也可以不按照这个模板来作答，完全按照自己的想法来
     *
     * @param a 贷款金额
     * @param i 年利率
     * @param n 贷款期限
     * @return 月还款额，保留2位小数，向上舍入
     */
    static String calculate(int a, double i, int n) {
    	//a金额
    	//年利率
    	//n期限
    	Double ddd =(a*(1+i)*Math.pow((1+i), n)) / (Math.pow((1+1), (n-1)));
    	
    	
    	return String.format("%.2f", ddd).toString();


    }

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String res;
       
        //请出入贷款金额(正整数):;
        int _a;
        _a = Integer.parseInt(in.nextLine().trim());

        //请输入年利率(如:4.35)%:;
        double _i;
        _i = Double.parseDouble(in.nextLine().trim());
                
       //请输入贷款期限多少年:;
        int _n;
        _n = Integer.parseInt(in.nextLine().trim());
  
        res = calculate(_a, _i/12, _n*12);
        System.out.println(res);
    }
}