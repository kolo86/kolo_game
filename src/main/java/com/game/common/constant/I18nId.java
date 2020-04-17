package com.game.common.constant;

/**
 * 〈一句话功能简述〉<br>
 * 〈提示语ID〉
 *
 * @author KOLO
 * @create 2020/4/16
 * @since 1.0.0
 */
public interface I18nId {
    /** 账号被注册 */
    int ACCOUNT_HAS_BEEN_REGISTERED = 1001;
    /** 恭喜你，注册成功 */
    int REGISTER_WAS_SUCCESSFUL = 1002;
    /** 怪物不存在 */
    int MONSTER_DOES_NOT_EXIST = 1003;
    /** 技能无法使用 */
    int SKILL_UNAVAILABLE = 1004;
    /** 你当前没有这个装备，无法穿戴！ */
    int EQUIPMENT_AT_PRESENT = 1005;
    /** 当前道具不是装备类型，无法穿戴！ */
    int CURRENT_ITEM_IS_NOT_EQUIPMENT_TYPE = 1006;
    /** 当前装备的耐久度为0，无法穿戴！ */
    int CURRENT_EQUIPMENT_IS_ZERO = 1007;
    /** 你当前没有穿戴该道具！ */
    int YOU_ARE_NOT_WEARING_THIS_ITEM_AT_PRESENT = 1008;
    /** 你当前武器耐久度为0，无法使用，请修理后再重新使用！ */
    int REPAIR_THE_WEAPON_AND_REUSE_IT = 1009;
    /** 修复武器完成 */
    int REPAIR_WEAPON_COMPLETE = 1010;
    /** 修复的道具不存在  */
    int REPAIRED_ITEM_DOES_NOT_EXIST = 1011;
    /** 修复的道具不是装备类型 */
    int ITEM_REPAIRED_IS_NOT_EQUIPMENT_TYPE = 1012;
    /** 扣除道具失败 */
    int FAILED_TO_DEDUCT_PROPS = 1013;
    /** 你的背包中没有该道具 */
    int THERE_IS_NO_SUCH_ITEM_IN_THE_BACKPACK = 1014;
    /** 当前道具是装备类型，应该使用穿戴装备命令！ */
    int USE_WEARABLE_EQUIPMENT_COMMAND = 1015;
    /** 登录游戏成功 */
    int LOGIN_IN_SUCCESSFULLY = 1016;
    /** 未创建该账号 */
    int THE_ACCOUNT_HAS_NOT_BEEN_CREATED = 1017;
    /** 不存在该NPC */
    int THE_NPC_DOES_NOT_EXIST = 1018;
    /** 妲己：快来创建一个角色和我玩耍吧！ */
    int PLEASE_CREATE_A_ROLE = 1019;
    /** 请登录 */
    int PLEASE_LOGIN = 1020;
    /** 不能再创建角色 */
    int CANNOT_CREATE_ROLE = 1021;
    /** 已在目标地图 */
    int YOU_ARE_ON_THE_TARGET_MAP = 1022;
    /** 你不能直接去到该地图 */
    int DIRECTLY_CHANGE_THE_MAP_ERROR = 1023;
    /** 切换地图成功！ */
    int SWITCH_MAP_SUCCESSFULLY = 1024;
    /** 退出游戏成功！ */
    int QUIT_THE_GAME_SUCCESSFULLY = 1025;

}