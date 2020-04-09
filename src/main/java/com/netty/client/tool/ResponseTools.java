/**
 * FileName: ResponseTools
 * Author:   坤龙
 * Date:     2020/4/2 20:41
 * Description: 响应工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.netty.client.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 〈一句话功能简述〉<br> 
 * 〈响应工具类〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
public class ResponseTools{

    private static final Logger logger = LoggerFactory.getLogger(ResponseTools.class);

    /**
     * 根据服务端返回的提示，进一步提示玩家下一步的操作
     *
     * @param answer
     */
    public static void specialHandler(String answer){
        if(answer.contains("创建一个角色")){
            logger.info("\n【创建角色】\n" +
                    "\t请输入：角色 英雄 | 村民 | 怪物\n" +
                    "\t  例子：角色 怪物");
        } else if(answer.contains("当前地图") ||
                    answer.contains("直接去到地图")){
            logger.info("\n【切换地图】\n" +
                    "\t请输入：MOVE 起始之地 |  村子 | 森林 | 城堡\n" +
                    "\t  例子：MOVE 村子\n" +
                    "\n" +
                    "【查看当前场景中所有实体信息】\n" +
                    "\t请输入：AOI\n" +
                    "\t  例子：AOI\n" +
                    "\n" +
                    "【对话NPC】\n" +
                    "\t请输入：对话 村民NPC\n" +
                    "\t  例子：对话 村民NPC\n" +
                    "\n" +
                    "【攻击怪物】\n" +
                    "\t请输入：攻击 怪物编号\n" +
                    "\t  例子：攻击 1001\n" +
                    "\n" +
                    "【退出游戏】\n" +
                    "\t请输入：退出\n" +
                    "\t  例子：退出");
        } else if(answer.contains("没有创建账号")){
            logger.info("\n请输入你的功能：\n" +
                    "【注册账号】\n" +
                    "\t请输入：注册 账号 昵称\n" +
                    "\t  例子：注册 kolo 孙悟空\n" +
                    "\n" +
                    "【登录游戏】\n" +
                    "\t请输入：登录 账号\n" +
                    "\t  例子：登录 kolo");
        }
    }

}