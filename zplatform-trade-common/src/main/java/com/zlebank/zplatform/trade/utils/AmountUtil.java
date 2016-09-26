package com.zlebank.zplatform.trade.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author jin.z
 * @ 金额格式转换.
 */
public class AmountUtil {

	private final static String AMOUNT_PATTERN = "^(0(\\.\\d{0,2})?|([1-9]+[0]*)+(\\.\\d{0,2})?)$";

	/**
	 * 验证金额是否合法
	 * 
	 * @param amountStr
	 * @return
	 */
	public static boolean checkAmount(String amountStr) {
		boolean ret = false;
		try {
			Pattern p = Pattern.compile(AMOUNT_PATTERN);
			Matcher m = p.matcher(amountStr);
			ret = m.matches();
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}

	/**
	 * 将字符串金额转成长整型
	 * 
	 * @param amountStr
	 * @return
	 */
	public static Long toLongAmount(String amountStr) {
		if (checkAmount(amountStr)) {
			return new BigDecimal(amountStr).multiply(new BigDecimal(100))
					.longValue();
		}
		return 0L;
	}
	
	/**
	 * 计算金额精度(四舍五入)
	 * 
	 * @param amount;
	 * @return
	 */
	public static long roundAmount(BigDecimal amount){
		return roundAmount(amount,1);
	}

	/**
	 * 计算金额精度
	 * 
	 * @param amount
	 * @param type
	 *            1-四舍五入;2-五舍五一入; 3-进位;4-舍位;默认四舍五入;
	 * @return
	 */
	public static long roundAmount(BigDecimal amount, int type) {
		
		BigDecimal tmp = null;

		switch (type) {
		case 1:
			tmp = amount.setScale(0, BigDecimal.ROUND_HALF_UP);
			break;
		case 2:
			tmp = amount.setScale(0, BigDecimal.ROUND_HALF_DOWN);
			break;
		case 3:
			tmp = amount.setScale(0, BigDecimal.ROUND_UP);
			break;
		case 4:
			tmp = amount.setScale(0, BigDecimal.ROUND_DOWN);
			break;
		default:
			tmp = amount.setScale(0, BigDecimal.ROUND_HALF_UP);
		}

		return tmp.longValue();
	}

	/**
	 * 金额转BigDecimal
	 * 
	 * @param amount
	 * @return
	 */
	public static BigDecimal toBigDecimalAmount(Long amount) {
		Long tmpAmount = amount;
		if (tmpAmount == null) {
			tmpAmount = 0L;
		}
		return new BigDecimal(tmpAmount).divide(new BigDecimal(100), 2,
				BigDecimal.ROUND_HALF_UP);

	}
	
	/**
	 * 金额转BigDecimal
	 * 
	 * @param amount
	 * @return
	 */
	public static BigDecimal toBigDecimalAmountStr(BigDecimal amount) {
		if (amount == null) {
			amount = new BigDecimal(0);
		}
		return amount.divide(new BigDecimal(100), 2,
				BigDecimal.ROUND_HALF_UP);

	}
	

	/**
	 * 格式化显示BigDecimal型金额
	 * 
	 * @param amount
	 * @return
	 */
	public static String numberFormat(BigDecimal amount) {
		NumberFormat formatter = new DecimalFormat("##0.00");
		if (amount == null) {
			return "NULL";
		}
		return formatter.format(amount.doubleValue());
	}

	/**
	 * 格式化显示Long型金额
	 * 
	 * @param amount
	 * @return
	 */
	public static String numberFormat(Long amount) {
		NumberFormat formatter = new DecimalFormat("#,###,##0.00");
		if (amount == null) {
			return "NULL";
		}

		BigDecimal tmp = toBigDecimalAmount(amount);

		return formatter.format(tmp.doubleValue());
	}
	
	/**
	 * 格式化显示Long型金额不带逗号
	 * 
	 * @param amount
	 * @return
	 */
	public static String numberDefaultFormat(Long amount) {
		NumberFormat formatter = new DecimalFormat("###.##");
		if (amount == null) {
			return "NULL";
		}

		BigDecimal tmp = toBigDecimalAmount(amount);

		return formatter.format(tmp.doubleValue());
	}
	
	
	/**
	 * 格式化显示Long型金额不带逗号,带两位小数
	 * 
	 * @param amount
	 * @return
	 */
	public static String numberFormatDecimal(Long amount) {
		NumberFormat formatter = new DecimalFormat("##0.00");
		if (amount == null) {
			return "NULL";
		}
		BigDecimal tmp = toBigDecimalAmount(amount);
		return formatter.format(tmp.doubleValue());
	}
	

	public static void main(String[] args) {
		BigDecimal i = new BigDecimal(3.4);
/*
		System.out.println("四舍五入（"+i.doubleValue()+"）:" + AmountUtil.roundAmount(i, 1));
		System.out.println("五舍五一入（"+i.doubleValue()+"）:" + AmountUtil.roundAmount(i, 2));
		System.out.println("进位（"+i.doubleValue()+"）:" + AmountUtil.roundAmount(i, 3));
		System.out.println("舍位（"+i.doubleValue()+"）:" + AmountUtil.roundAmount(i, 4));
		System.out.println("默认（"+i.doubleValue()+"）:" + AmountUtil.roundAmount(i, 5));
		System.out.println("**********************************************************");
		i = new BigDecimal(3.5);

		System.out.println("四舍五入（"+i.doubleValue()+"）:" + AmountUtil.roundAmount(i, 1));
		System.out.println("五舍五一入（"+i.doubleValue()+"）:" + AmountUtil.roundAmount(i, 2));
		System.out.println("进位（"+i.doubleValue()+"）:" + AmountUtil.roundAmount(i, 3));
		System.out.println("舍位（"+i.doubleValue()+"）:" + AmountUtil.roundAmount(i, 4));
		System.out.println("默认（"+i.doubleValue()+"）:" + AmountUtil.roundAmount(i, 5));
		System.out.println("**********************************************************");
		i = new BigDecimal(3.51);

		System.out.println("四舍五入（"+i.doubleValue()+"）:" + AmountUtil.roundAmount(i, 1));
		System.out.println("五舍五一入（"+i.doubleValue()+"）:" + AmountUtil.roundAmount(i, 2));
		System.out.println("进位（"+i.doubleValue()+"）:" + AmountUtil.roundAmount(i, 3));
		System.out.println("舍位（"+i.doubleValue()+"）:" + AmountUtil.roundAmount(i, 4));
		System.out.println("默认（"+i.doubleValue()+"）:" + AmountUtil.roundAmount(i, 5));
		System.out.println("**********************************************************");*/
		
		System.out.println(AmountUtil.checkAmount("100.1"));
		
	}
}
