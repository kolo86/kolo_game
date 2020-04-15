package com.game.drop.util;

import java.util.Random;

/**
 * 〈一句话功能简述〉<br>
 * 〈掉落工具〉
 *
 * @author KOLO
 * @create 2020/4/13
 * @since 1.0.0
 */
public class DropUtils {

    private static final int MAX_PROB = 10000;

    /**
     * 查看道具被命中的数量
     *
     * @param dropProb
     * @return
     */
    public static int hitNum(double dropProb){
        // 分子
        int molecule = (int) dropProb * MAX_PROB;
        int hitNum = molecule / MAX_PROB;

        int less = molecule - hitNum * MAX_PROB;

        Random random = new Random();
        int randomInt = random.nextInt(DropUtils.MAX_PROB);
        if(less >= randomInt){
            return hitNum + 1;
        }
        return hitNum;
    }

}