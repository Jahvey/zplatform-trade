/* 
 * LogUtil.java  
 * 
 * version TODO
 *
 * 2015年7月7日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.utils;

/**
 * Class Description
 *
 * @author yangying
 * @version
 * @date 2015年7月7日 上午10:21:55
 * @since
 */
public class LogUtil {

    private final static int MAX_COL = 16;

    public static String formatLogHex(byte[] data) {
        StringBuilder sb = new StringBuilder();

        String str = new String(data);
        int total = data.length;
        int strTotal = str.length();
        int row = (total % MAX_COL == 0)
                ? (total / MAX_COL)
                : (total / MAX_COL + 1);
        int strRow = (strTotal % MAX_COL == 0)
                ? (strTotal / MAX_COL)
                : (strTotal / MAX_COL + 1);
        String[] head = new String[row];
        String[] leftStr = new String[row];
        String[] rightStr = new String[strRow];
        for (int i = 0; i < row; i++) {
            head[i] = String.format("%1$04dh:", i);
            StringBuilder _left = new StringBuilder();

            int last = (i == (row - 1)) ? total : ((i + 1) * MAX_COL);
            for (int j = i * MAX_COL; j < last; j++) {
                _left.append(String.format("%1$02X", data[j]));
                if (j != last - 1) {
                    _left.append(" ");
                }
            }
            leftStr[i] = _left.toString();
        }

        for (int i = 0; i < strRow; i++) {
            if (i == strRow - 1) {
                rightStr[i] = str.substring(i * MAX_COL);
                break;
            }
            rightStr[i] = str.substring(i * MAX_COL, (i + 1) * MAX_COL);
        }

        for (int i = 0; i < row; i++) {
            sb.append(head[i]).append(leftStr[i]);
            if (i < strRow) {
                sb.append(";").append(rightStr[i]);
            }
            if (i < row - 1)
                sb.append("\n");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String s = "0020C0 00 00 05 78 FF 63 C7 78 01 CC B0 14 2A 24 CC 05 68 AC 58 CF 80 F3 CF BF 57 16 35 8B F2 F8 8F"
                .replaceAll(" ", "");
        byte[] b = LogUtil.hexStr2hexByte(s);
        System.out.println(LogUtil.formatLogHex(b));
    }

    public static byte[] int2byte(int n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n >> 24);
        b[1] = (byte) (n >> 16);
        b[2] = (byte) (n >> 8);
        b[3] = (byte) n;
        return b;
    }

    public static byte[] hexStr2hexByte(String orgHexStr) {
        if ((orgHexStr == null) || (orgHexStr.trim() == ""))
            return null;
        int len = orgHexStr.trim().length();
        if (len % 2 != 0)
            return null;
        byte[] ret = new byte[len / 2];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = Integer.valueOf(orgHexStr.substring(i * 2, i * 2 + 2), 16)
                    .byteValue();
        }
        return ret;
    }
}
