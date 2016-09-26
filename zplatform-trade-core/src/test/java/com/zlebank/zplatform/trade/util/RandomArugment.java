package com.zlebank.zplatform.trade.util;

import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;

public class RandomArugment {
    /**
     * Generate a random number.It length is specific by parameter length
     * 
     * @param length
     * @return
     */
    public static String randomNumber(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
    /**
     * Generate a random acc name which start by "accName"
     * 
     * @return
     */
    public static String randomAccName() {
        Random random = new Random();
        int length = random.nextInt(10);
        String name = RandomStringUtils.random(length);

        return "accName" + name;
    }
    /**
     * Generate a random boolean value
     * @return
     */
    public static boolean randomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }
}
