package com.netty.common;

import com.game.common.resource.I18NResource;
import com.game.common.service.WorldManager;
import com.game.equipment.constant.EquipmentPosition;
import com.game.equipment.constant.EquipmentType;
import com.game.equipment.resource.EquipmentResource;
import com.game.equipment.service.EquipmentManager;
import com.game.item.resource.ItemResource;
import com.game.item.service.ItemManager;
import com.game.npc.constant.NpcEnum;
import com.game.role.constant.RoleEnum;
import com.game.scene.constant.SceneType;
import com.game.skill.resource.SkillResource;
import com.game.skill.service.SkillManager;
import com.netty.proto.Message;
import com.netty.util.ProtocolParseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 〈一句话功能简述〉<br>
 * 〈协议枚举类〉
 *
 * @author KOLO
 * @create 2020/4/16
 * @since 1.0.0
 */
public enum ProtocolEnum {
    /** 响应服务端 */
    Response(100, Message.Response.class){
        @Override
        public void printMsg(ProtocolMsg msg) {
            Message.Response protocolObj = (Message.Response)ProtocolParseUtils.getProtocolObj(msg);
            if(protocolObj.getSuccess()){
                I18NResource i18nResource = WorldManager.getI18nResource(protocolObj.getResponseId());
                logger.info(i18nResource.getDesc());
            }else{
                logger.info(protocolObj.getAnswer());
            }
        }
    },

    /** 登录 */
    CM_LOGIN( 101, "登录", Message.Cm_Login.class){
        @Override
        public boolean checkParamNum(String[] args) {
            return args.length - 1 != Message.Cm_Login.class.getFields().length;
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
            if(protocolObj.getSuccess()){
                logger.info("恭喜你，登录成功！");
            }
        }
    },

    /** 注册 */
    Cm_Register( 103, "注册", Message.Cm_Register.class){
        @Override
        public boolean checkParamNum(String[] args) {
            return args.length - 1 != Message.Cm_Register.class.getFields().length;
        }

        @Override
        public ProtocolMsg getMessage(String[] args) {
            String accountId = StringUtils.trimAllWhitespace(args[1]);
            String nickName = StringUtils.trimAllWhitespace(args[2]);

            Message.Cm_Register message = Message.Cm_Register.newBuilder()
                    .setAccount(accountId).setNickName(nickName).build();
            return ProtocolMsg.valueOf(ProtocolEnum.Cm_Register.getId(), message.toByteArray());
        }
    },
    Sm_Register(104, Message.Sm_Register.class){
        @Override
        public void printMsg(ProtocolMsg msg) {
            Message.Sm_Register protocolObj = (Message.Sm_Register)ProtocolParseUtils.getProtocolObj(msg);
            if(protocolObj.getSuccess()){
                logger.info("恭喜你，注册成功！");
            }
        }
    },

    /** 创建角色 */
    Cm_CreateRole( 105, "角色", Message.Cm_CreateRole.class){
        @Override
        public boolean checkParamNum(String[] args) {
            return args.length - 1 != Message.Cm_CreateRole.class.getFields().length;
        }

        @Override
        public ProtocolMsg getMessage(String[] args) {
            String arg = args[1];
            arg = StringUtils.trimAllWhitespace(arg);
            int roleType = RoleEnum.getIdByRoleName(arg);
            if(roleType == 0){
                return null;
            }

            Message.Cm_CreateRole message = Message.Cm_CreateRole.newBuilder()
                    .setRoleType(roleType).build();
            return ProtocolMsg.valueOf(ProtocolEnum.Cm_CreateRole.getId(), message.toByteArray());
        }
    },
    Sm_CreateRole(106, Message.Sm_CreateRole.class){
        @Override
        public void printMsg(ProtocolMsg msg) {
            Message.Sm_CreateRole protocolObj = (Message.Sm_CreateRole)ProtocolParseUtils.getProtocolObj(msg);
            if(protocolObj.getSuccess()){
                logger.info("恭喜你，创建角色成功！");
            }
        }
    },

    /** 切图 */
    Cm_ChangeMap( 107, "MOVE", Message.Cm_ChangeMap.class){
        @Override
        public boolean checkParamNum(String[] args) {
            return args.length - 1 != Message.Cm_ChangeMap.class.getFields().length;
        }

        @Override
        public ProtocolMsg getMessage(String[] args) {
            String arg = args[1];
            arg = arg.trim();
            int mapId = SceneType.getMapIdByName(arg);
            if(mapId == 0){
                return null;
            }

            Message.Cm_ChangeMap message = Message.Cm_ChangeMap.newBuilder()
                    .setMapId(mapId).build();
            return ProtocolMsg.valueOf(ProtocolEnum.Cm_ChangeMap.getId(), message.toByteArray());
        }
    },
    Sm_ChangeMap(108, Message.Sm_ChangeMap.class){
        @Override
        public void printMsg(ProtocolMsg msg) {
            Message.Sm_ChangeMap protocolObj = (Message.Sm_ChangeMap)ProtocolParseUtils.getProtocolObj(msg);
            if(protocolObj.getSuccess()){
                logger.info("恭喜你，切图成功！");
            }
        }
    },

    /** 切图 */
    Cm_State( 109, "AOI", Message.Cm_State.class){
        @Override
        public boolean checkParamNum(String[] args) {
            return args.length - 1 != Message.Cm_State.class.getFields().length;
        }

        @Override
        public ProtocolMsg getMessage(String[] args) {
            Message.Cm_State message = Message.Cm_State.newBuilder().build();
            return ProtocolMsg.valueOf(ProtocolEnum.Cm_State.getId(), message.toByteArray());
        }
    },
    Sm_State(110, Message.Sm_State.class){
        @Override
        public void printMsg(ProtocolMsg msg) {
            Message.Sm_State protocolObj = (Message.Sm_State)ProtocolParseUtils.getProtocolObj(msg);
            StringBuilder sb = new StringBuilder();
            sb.append("你当前所在地图【").append(protocolObj.getMapName()).append("】");
            sb.append("\n玩家列表为：");
            protocolObj.getUserList().forEach(user -> sb.append(user.getNickName()).append("【").append(user.getRoleType()).append("】")
                    .append("【").append(user.getLiveState()).append("】\t"));
            sb.append("\nNPC列表为：");
            protocolObj.getNpcList().forEach(npc -> sb.append(npc.getNpcName()).append("\t"));
            sb.append("\n怪物列表为：");
            protocolObj.getMonsterList().forEach(monster -> sb.append(monster.getMonsterName()).append("【").append(monster.getCurrentHp()).append("/").append(monster.getMaxHp()).append("】")
                    .append("【").append(monster.getMonsterId()).append("】\t"));
            logger.info(sb.toString());
        }
    },

    /** 退出游戏 */
    Cm_Quit( 111, "退出", Message.Cm_Quit.class){
        @Override
        public boolean checkParamNum(String[] args) {
            return args.length - 1 != Message.Cm_Quit.class.getFields().length;
        }

        @Override
        public ProtocolMsg getMessage(String[] args) {
            Message.Cm_Quit message = Message.Cm_Quit.newBuilder().build();
            return ProtocolMsg.valueOf(ProtocolEnum.Cm_Quit.getId(), message.toByteArray());
        }
    },
    Sm_Quit(112, Message.Sm_Quit.class){
        @Override
        public void printMsg(ProtocolMsg msg) {
            Message.Sm_Quit protocolObj = (Message.Sm_Quit)ProtocolParseUtils.getProtocolObj(msg);
            if(protocolObj.getSuccess()){
                logger.info("退出游戏成功！");
            }

        }
    },


    /** 玩家对话NPC */
    Cm_Talk( 113, "对话", Message.Cm_Talk.class){
        @Override
        public boolean checkParamNum(String[] args) {
            return args.length - 1 != Message.Cm_Talk.class.getFields().length;
        }

        @Override
        public ProtocolMsg getMessage(String[] args) {
            String npcName = args[1];
            Integer npcId = NpcEnum.getNpcId(npcName.toUpperCase());
            if(npcId == null){
                return null;
            }

            Message.Cm_Talk message = Message.Cm_Talk.newBuilder().setNpcId(npcId).build();
            return ProtocolMsg.valueOf(ProtocolEnum.Cm_Talk.getId(), message.toByteArray());
        }
    },
    Sm_Talk(114, Message.Sm_Talk.class){
        @Override
        public void printMsg(ProtocolMsg msg) {
            Message.Sm_Talk protocolObj = (Message.Sm_Talk)ProtocolParseUtils.getProtocolObj(msg);
            logger.info(protocolObj.getMessage());
        }
    },

    /** 攻击怪物 */
    Cm_Attack( 115, "攻击", Message.Cm_Attack.class){
        @Override
        public boolean checkParamNum(String[] args) {
            return args.length - 1 != Message.Cm_Attack.class.getFields().length;
        }

        @Override
        public ProtocolMsg getMessage(String[] args) {
            int monsterId = Integer.parseInt(args[1]);

            Message.Cm_Attack message = Message.Cm_Attack.newBuilder().setMonsterId(monsterId).build();
            return ProtocolMsg.valueOf(ProtocolEnum.Cm_Attack.getId(), message.toByteArray());
        }
    },
    Sm_Attack(116, Message.Sm_Attack.class){
        @Override
        public void printMsg(ProtocolMsg msg) {
            Message.Sm_Attack protocolObj = (Message.Sm_Attack)ProtocolParseUtils.getProtocolObj(msg);
            StringBuilder sb = new StringBuilder();
            SkillResource skillResource = SkillManager.getSkillResource(protocolObj.getSkillId());
            sb.append("你使用了【").append(skillResource.getName()).append("】，对怪物【")
                    .append(protocolObj.getMonsterName()).append("】造成").append(protocolObj.getDamage())
                    .append("点伤害！");
            logger.info(sb.toString());
        }
    },

    /** 怪物被击杀 */
    Sm_MonsterDead(117, Message.Sm_MonsterDead.class){
        @Override
        public void printMsg(ProtocolMsg msg) {
            Message.Sm_MonsterDead protocolObj = (Message.Sm_MonsterDead)ProtocolParseUtils.getProtocolObj(msg);
            String sb = "玩家【" + protocolObj.getKiller() + "】已经击杀了怪物【" +
                    protocolObj.getMonster() + "】！";
            logger.info(sb);
        }
    },

    /** 击杀者奖励 */
    Sm_KillerReward(118, Message.Sm_KillerReward.class){
        @Override
        public void printMsg(ProtocolMsg msg) {
            Message.Sm_KillerReward protocolObj = (Message.Sm_KillerReward)ProtocolParseUtils.getProtocolObj(msg);
            StringBuilder sb = new StringBuilder();
            sb.append("恭喜你，击杀了怪物，得到奖励：\n");
            List<Message.Item> rewardList = protocolObj.getRewardList();
            rewardList.forEach(item -> sb.append("名称：").append(item.getItemName()).append(" 数量：").append(item.getItemNum()).append("\n"));

            logger.info(sb.toString());
        }
    },

    /** 查看背包 */
    Cm_BackPack( 119, "查看背包", Message.Cm_BackPack.class){
        @Override
        public boolean checkParamNum(String[] args) {
            return args.length - 1 != Message.Cm_BackPack.class.getFields().length;
        }

        @Override
        public ProtocolMsg getMessage(String[] args) {
            Message.Cm_BackPack message = Message.Cm_BackPack.newBuilder().build();
            return ProtocolMsg.valueOf(ProtocolEnum.Cm_BackPack.getId(), message.toByteArray());
        }
    },
    Sm_BackPack(120, Message.Sm_BackPack.class){
        @Override
        public void printMsg(ProtocolMsg msg) {
            Message.Sm_BackPack protocolObj = (Message.Sm_BackPack)ProtocolParseUtils.getProtocolObj(msg);
            StringBuilder sb = new StringBuilder();
            sb.append("你的背包信息为：");
            List<Message.Item> rewardList = protocolObj.getItemList();
            rewardList.forEach(item -> sb.append("\n道具ID：").append(item.getItemOnlyId()).append(" ,道具名称：").append(item.getItemName())
                    .append(" ,道具数量：").append(item.getItemNum()));

            logger.info(sb.toString());
        }
    },

    /** 使用道具 */
    Cm_UseItem( 121, "使用道具", Message.Cm_UseItem.class){
        @Override
        public boolean checkParamNum(String[] args) {
            return args.length - 1 != Message.Cm_UseItem.class.getFields().length;
        }

        @Override
        public ProtocolMsg getMessage(String[] args) {
            int objectRandomId = Integer.parseInt(args[1].trim());

            Message.Cm_UseItem message = Message.Cm_UseItem.newBuilder().setItemOnlyId(objectRandomId).build();
            return ProtocolMsg.valueOf(ProtocolEnum.Cm_UseItem.getId(), message.toByteArray());
        }
    },
    Sm_UseItem(122, Message.Sm_UseItem.class){
        @Override
        public void printMsg(ProtocolMsg msg) {
            Message.Sm_UseItem protocolObj = (Message.Sm_UseItem)ProtocolParseUtils.getProtocolObj(msg);
            logger.info(protocolObj.getResult());
        }
    },

    /** 查看已穿戴的装备 */
    Cm_Equipment( 123, "查看装备", Message.Cm_Equipment.class){
        @Override
        public boolean checkParamNum(String[] args) {
            return args.length - 1 != Message.Cm_Equipment.class.getFields().length;
        }

        @Override
        public ProtocolMsg getMessage(String[] args) {
            Message.Cm_Equipment message = Message.Cm_Equipment.newBuilder().build();
            return ProtocolMsg.valueOf(ProtocolEnum.Cm_Equipment.getId(), message.toByteArray());
        }
    },
    Sm_Equipment(124, Message.Sm_Equipment.class){
        @Override
        public void printMsg(ProtocolMsg msg) {
            Message.Sm_Equipment protocolObj = (Message.Sm_Equipment)ProtocolParseUtils.getProtocolObj(msg);
            Map<Integer, Message.Equipment> equipmentMap = protocolObj.getEquipmentMap();
            StringBuilder sb = new StringBuilder();
            sb.append("你已穿戴的装备信息为：\n");
            for (EquipmentPosition position : EquipmentPosition.values()) {
                sb.append(position.getName()).append("【");

                Message.Equipment abstractItem = equipmentMap.get(position.getPositionId());
                if (!Objects.isNull(abstractItem)) {
                    ItemResource itemResource = ItemManager.getResource(abstractItem.getItemId());
                    sb.append("道具ID：").append(abstractItem.getItemOnlyId()).append(" 名称：").append(itemResource.getName());
                    EquipmentResource equipmentResource = EquipmentManager.getResource(Integer.parseInt(itemResource.getAttrs()));
                    if(equipmentResource.getEquipmentType() == EquipmentType.WEAPON.getId()){
                        sb.append(" 耐久度为：").append(abstractItem.getDurability());
                    }
                } else {
                    sb.append(" 暂无 ");
                }
                sb.append("】\t");
            }

            logger.info( sb.toString() );
        }
    },


    /** 穿装备 */
    Cm_Wear( 125, "穿装备", Message.Cm_Wear.class){
        @Override
        public boolean checkParamNum(String[] args) {
            return args.length - 1 != Message.Cm_Wear.class.getFields().length;
        }

        @Override
        public ProtocolMsg getMessage(String[] args) {
            int equipmentId = Integer.parseInt(args[1].trim());

            Message.Cm_Wear message = Message.Cm_Wear.newBuilder().setEquipmentId(equipmentId).build();
            return ProtocolMsg.valueOf(ProtocolEnum.Cm_Wear.getId(), message.toByteArray());
        }
    },
    Sm_Wear(126, Message.Sm_Wear.class){
        @Override
        public void printMsg(ProtocolMsg msg) {
            Message.Sm_Wear protocolObj = (Message.Sm_Wear)ProtocolParseUtils.getProtocolObj(msg);
            if(protocolObj.getSuccess()){
                logger.info( "穿戴装备成功！");
            }
        }
    },

    /** 脱装备 */
    Cm_Deequipment( 127, "脱装备", Message.Cm_Deequipment.class){
        @Override
        public boolean checkParamNum(String[] args) {
            return args.length - 1 != Message.Cm_Deequipment.class.getFields().length;
        }

        @Override
        public ProtocolMsg getMessage(String[] args) {
            int equipmentId = Integer.parseInt(args[1].trim());

            Message.Cm_Deequipment message = Message.Cm_Deequipment.newBuilder().setEquipmentId(equipmentId).build();
            return ProtocolMsg.valueOf(ProtocolEnum.Cm_Deequipment.getId(), message.toByteArray());
        }
    },
    Sm_Deequipment(128, Message.Sm_Deequipment.class){
        @Override
        public void printMsg(ProtocolMsg msg) {
            Message.Sm_Deequipment protocolObj = (Message.Sm_Deequipment)ProtocolParseUtils.getProtocolObj(msg);
            if(protocolObj.getSuccess()){
                logger.info( "脱装备成功！");
            }
        }
    },

    /** 修理武器 */
    Cm_Repair( 129, "修理", Message.Cm_Repair.class){
        @Override
        public boolean checkParamNum(String[] args) {
            return args.length - 1 != Message.Cm_Repair.class.getFields().length;
        }

        @Override
        public ProtocolMsg getMessage(String[] args) {
            int objectOnlyId = Integer.parseInt(args[1].trim());

            Message.Cm_Repair message = Message.Cm_Repair.newBuilder().setEquipmentId(objectOnlyId).build();
            return ProtocolMsg.valueOf(ProtocolEnum.Cm_Repair.getId(), message.toByteArray());
        }
    },
    Sm_Repair(130, Message.Sm_Repair.class){
        @Override
        public void printMsg(ProtocolMsg msg) {
            Message.Sm_Repair protocolObj = (Message.Sm_Repair)ProtocolParseUtils.getProtocolObj(msg);
            if(protocolObj.getSuccess()){
                logger.info( "修理武器成功！");
            }
        }
    },

    ;

    private Integer id;

    private String order;

    private Class<?> clz;

    private static final Logger logger = LoggerFactory.getLogger(ProtocolEnum.class);

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
     */
    public void printMsg(ProtocolMsg msg){}

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