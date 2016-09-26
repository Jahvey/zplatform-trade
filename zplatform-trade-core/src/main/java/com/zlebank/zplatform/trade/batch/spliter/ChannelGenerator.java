/* 
 * ChannelGenerator.java  
 * 
 * version v1.3
 *
 * 2016年3月7日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.batch.spliter;

/**
 * 渠道生成器
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年3月7日 上午10:10:08
 * @since 
 */
public class ChannelGenerator {
    private static  ChannelGenerator instance = new ChannelGenerator();
    
    public static ChannelGenerator getInstance() {
        return instance;
    }

    private  ChannelGenerator(){}
    
    /**
     * 得到相应的渠道
     * @param channel 生成渠道所需要的条件
     * @return 渠道代码
     */
    public String getChannel(ChannelCondition channel) {
        String channelCode = null;
        if (channel != null) {
            if ("01".equals(channel.getBankType())) {
                channelCode = "10000001";
            }
            if ("02".equals(channel.getBankType())) {
                channelCode = "10000002";
            }
        } else {
            return defaultChannel();
        }
        return channelCode;
    }

    /**
     * 默认渠道
     * @return 默认渠道
     */
    private String defaultChannel() {
        return "10000001";
    }
}
