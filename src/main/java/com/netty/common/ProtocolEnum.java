package com.netty.common;

import com.netty.proto.Message;
import com.netty.util.ProtocolParseUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈协议枚举类〉
 *
 * @author KOLO
 * @create 2020/4/16
 * @since 1.0.0
 */
public enum ProtocolEnum {
    /** 登录 */
    CM_LOGIN( 101, "登录", Message.Cm_Login.class){
        @Override
        public boolean checkParamNum(String[] args) {
            return args.length - 1 != Message.Login.class.getFields().length;
        }

        @Override
        public ProtocolMsg getMessage(String[] args) {
            String accountId = StringUtils.trimAllWhitespace(args[1]);

            Message.Cm_Login message = Message.Cm_Login.newBuilder()
                    .setAccount(accountId).build();
            return ProtocolMsg.valueOf(ProtocolEnum.CM_LOGIN.getId(), message.toByteArray());
        }
    },
    SM_LOGIN(102, Message.Sm_Login.class){
        @Override
        public void printMsg(ProtocolMsg msg) {
            Message.Sm_Login protocolObj = (Message.Sm_Login)ProtocolParseUtils.getProtocolObj(msg);
            System.out.println("玩家登录信息 ： " + protocolObj.getSuccess() );
        }
    },

    ;

    private Integer id;

    private String order;

    private Class<?> clz;

    /** Map< 协议ID， 协议字节码 ></> */
    private static final Map<Integer, Class<?>> ID_CLASS_MAP = new HashMap<>();
    /** Map< 协议ID， 协议对象></> */
    private static final Map<Integer, ProtocolEnum> ID_PROTOCOL_MAP = new HashMap<>();
    /** Map< 操作命令， 协议字节码 ></> */
    private static final Map<String, Class<?>> NAME_CLASS_MAP = new HashMap<>();
    /** Map< 操作命令， 协议对象 ></> */
    private static final Map<String, ProtocolEnum> NAME_PROTOCOL_MAP = new HashMap<>();

    static{
        for(ProtocolEnum protocolEnum : values()){
            ID_CLASS_MAP.put(protocolEnum.getId(), protocolEnum.getClz());
            ID_PROTOCOL_MAP.put(protocolEnum.getId(), protocolEnum);

            if(!StringUtils.isEmpty(protocolEnum.getOrder())){
                NAME_CLASS_MAP.put(protocolEnum.getOrder(), protocolEnum.getClz());
                NAME_PROTOCOL_MAP.put(protocolEnum.getOrder(), protocolEnum);
            }
        }
    }

    ProtocolEnum(int id, Class<?> clz) {
        this.id = id;
        this.clz = clz;
    }

    ProtocolEnum(int id, String order, Class<?> clz){
        this.id = id;
        this.order = order;
        this.clz = clz;
    }

    /**
     * 检查参数个数
     *
     */
    public boolean checkParamNum(String[] args){
        return false;
    }

    /**
     * 打印消息
     *
     * @param msg
     */
    public void printMsg(ProtocolMsg msg){};

    /**
     * 是否有效的操作命令
     *
     */
    public static boolean isAvailable(String order){
        return NAME_CLASS_MAP.containsKey(order);
    }

    /**
     * 根据不同的操作类型来获取对应的协议对象
     *
     */
    public ProtocolMsg getMessage(String[] args){
        return null;
    }

    /**
     * 根据操作命令得到操作对象
     *
     */
    public static ProtocolEnum getOptionByName(String optionName){
        return NAME_PROTOCOL_MAP.get(optionName);
    }

    /**
     * 根据协议ID获取协议类型
     *
     * @param code
     * @return
     */
    public static ProtocolEnum getProtocol(int code){
        return ID_PROTOCOL_MAP.get(code);
    }

    /**
     * 根据协议ID获取协议字节码
     *
     */
    public static Class<?> getProtocolType(int code){
        return ID_CLASS_MAP.get(code);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    public String getOrder() {
        return order;
    }
}