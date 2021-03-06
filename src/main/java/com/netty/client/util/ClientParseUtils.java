package com.netty.client.util;

import com.netty.common.ProtocolEnum;
import com.netty.common.ProtocolMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 〈一句话功能简述〉<br> 
 * 〈客户端解析工具〉
 *
 * @author 坤龙
 * @create 2020/4/1
 * @since 1.0.0
 */
public class ClientParseUtils {

    private static final Logger logger = LoggerFactory.getLogger(ClientParseUtils.class);

    /**
     * 解析字符串，得到对应的协议对象
     *
     */
    public static ProtocolMsg parseStr(String order){
        order = order.trim();
        if(StringUtils.isEmpty(order)){
            return null;
        }

        String[] strs = order.split("\\s+");
        if(!ProtocolEnum.isAvailable(strs[0].toUpperCase())){
            logger.info("没有该命令：{}，请重新输入！", strs[0]);
            return null;
        }

        ProtocolEnum option = ProtocolEnum.getOptionByName(strs[0].toUpperCase());
        if(option.checkParamNum(strs)){
            logger.info("命令的参数数量不符合规范，请重新输入！");
            return null;
        }

        ProtocolMsg message = option.getMessage(strs);
        if(message == null){
            logger.info("命名的参数不符合规范，请重新输入！");
            return null;
        }
        return message;
    }

}