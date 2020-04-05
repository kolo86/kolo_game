/**
 * FileName: OptionConstant
 * Author:   坤龙
 * Date:     2020/4/1 14:34
 * Description: 玩家操作常量
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.netty.client.constant;

import com.game.role.constant.RoleEnum;
import com.game.scene.constant.SceneType;
import com.netty.proto.Message;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈玩家操作常量〉
 *
 * @author 坤龙
 * @create 2020/4/1
 * @since 1.0.0
 */
public enum OptionEnum {

    REGISTER(1, "注册") {
        @Override
        public boolean checkParamNum(String[] args) {
            return args.length - 1 != Message.Register.class.getFields().length;
        }

        @Override
        public Message.Option getMessage(String[] args) {
            Message.Option message = Message.Option.newBuilder()
                    .setOptionType(Message.Option.OptionType.REGISTER)
                    .setRegister(Message.Register.newBuilder()
                            .setAccount(StringUtils.trimAllWhitespace(args[1]))
                            .setNickName(StringUtils.trimAllWhitespace(args[2])).build())
                    .build();
            return message;
        }
    },
    LOGIN(2,"登录"){
        @Override
        public boolean checkParamNum(String[] args) {
            return args.length - 1 != Message.Login.class.getFields().length;
        }

        @Override
        public Message.Option getMessage(String[] args) {
            String accountId = StringUtils.trimAllWhitespace(args[1]);

            Message.Option message = Message.Option.newBuilder()
                    .setOptionType(Message.Option.OptionType.LOGIN)
                    .setLogin(Message.Login.newBuilder()
                            .setAccount(accountId).build())
                    .build();
            return message;
        }
    },
    CREATE_ROLE(3,"角色"){
        @Override
        public boolean checkParamNum(String[] args) {
            return args.length - 1 != Message.CreateRole.class.getFields().length;
        }

        @Override
        public Message.Option getMessage(String[] args) {
            String arg = args[1];
            arg = StringUtils.trimAllWhitespace(arg);
            int roleType = RoleEnum.getIdByRoleName(arg);
            if(roleType == 0){
                return null;
            }

            Message.Option message = Message.Option.newBuilder()
                    .setOptionType(Message.Option.OptionType.ROLE)
                    .setCreatRole(Message.CreateRole.newBuilder().setRoleType(roleType).build())
                    .build();
            return message;
        }
    },
    CHANGE_MAP(4,"MOVE"){
        @Override
        public boolean checkParamNum(String[] args) {
            return args.length - 1 != Message.ChangeMap.class.getFields().length;
        }

        @Override
        public Message.Option getMessage(String[] args) {
            String arg = args[1];
            arg = arg.trim();
            int mapId = SceneType.getMapIdByName(arg);
            if(mapId == 0){
                return null;
            }

            Message.Option message = Message.Option.newBuilder()
                    .setOptionType(Message.Option.OptionType.CHANGE_MAP).
                    setChangeMap(Message.ChangeMap.newBuilder()
                            .setMapId(mapId).build())
                    .build();
            return message;
        }
    },
    STATE(5,"AOI"){
        @Override
        public boolean checkParamNum(String[] args) {
            return args.length - 1 != Message.State.class.getFields().length;
        }

        @Override
        public Message.Option getMessage(String[] args) {
            Message.Option message = Message.Option.newBuilder()
                    .setOptionType(Message.Option.OptionType.STATE).
                    setState(Message.State.newBuilder()
                            .build())
                    .build();
            return message;
        }
    }

    ;
    private int id;
    private String optionName;

    // Map< 操作ID， 操作命令 >
    private static final Map<Integer, String> idToName = new HashMap<Integer, String>();
    // Map< 操作命令 ， 操作对象 >
    private static final Map<String, OptionEnum> nameToOption = new HashMap<String, OptionEnum>();
    static{
        for( OptionEnum option: values() ){
            idToName.put(option.getId(), option.getOptionName());
            nameToOption.put(option.getOptionName(), option);
        }
    }

    /**
     * 是否有效的操作命令
     *
     * @param order
     * @return
     */
    public static boolean isAvailable(String order){
        return idToName.values().contains(order);
    }

    /**
     * 根据操作命令得到操作对象
     *
     * @param optionName
     * @return
     */
    public static OptionEnum getOptionByName(String optionName){
        return nameToOption.get(optionName);
    }

    /**
     * 根据不同的操作类型来获取对应的协议对象
     *
     * @return
     */
    public abstract Message.Option getMessage(String[] args);

    /**
     * 检查参数的数量
     *
     * @param args
     * @return 若参数数量不对，返回false
     */
    public abstract boolean checkParamNum(String[] args);

    OptionEnum(int id, String optionName){
        this.id = id;
        this.optionName = optionName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }
}