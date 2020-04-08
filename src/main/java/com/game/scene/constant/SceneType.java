package com.game.scene.constant;

import com.game.scene.AbstractMapHandler;
import com.game.scene.handler.CastleMapHandler;
import com.game.scene.handler.ForestMapHandler;
import com.game.scene.handler.MainCityMapHandler;
import com.game.scene.handler.VillageMapHandler;

import java.util.*;

/**
 * 〈一句话功能简述〉<br> 
 * 〈场景类型〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
public enum SceneType {

    /**未进入地图*/
    NONE(0,"未进入地图"),
    /** 起始之地 */
    MAINCITY(101, "起始之地", new MainCityMapHandler(), Collections.singletonList(102)),
    /** 村子 */
    VILLAGE(102,"村子",new VillageMapHandler(),Arrays.asList(101,103,104)),
    /** 森林 */
    FOREST(103,"森林", new ForestMapHandler(), Collections.singletonList(102)),
    /** 城堡 */
    CASTLE(104,"城堡", new CastleMapHandler(), Collections.singletonList(102))
    ;

    /** 地图ID */
    private int mapId;
    /** 地图名 */
    private String mapName;
    /** 地图处理器 */
    private AbstractMapHandler handler;
    /** 可以进来的地图类型 */
    private List<Integer> sceneTypeList = new ArrayList<>();
    /** Map < 场景名 ， 场景ID > */
    private static final Map<String, Integer> NAME_ID_MAP = new HashMap<>();
    /** Map < 场景ID， 场景对象 > */
    private static final Map<Integer, SceneType> ID_SCENE_MAP = new HashMap<>();

    static{
        for(SceneType type : values()){
            NAME_ID_MAP.put(type.getMapName(), type.getMapId());
            ID_SCENE_MAP.put(type.getMapId(), type);
        }
    }

    SceneType(int mapId, String mapName){
        this.mapId = mapId;
        this.mapName = mapName;
    }

    SceneType(int mapId, String mapName, AbstractMapHandler handler, List<Integer> sceneTypeList){
        this.mapId = mapId;
        this.mapName = mapName;
        this.handler = handler;
        this.sceneTypeList = sceneTypeList;
    }

    /**
     * 通过场景名称获取场景ID
     *
     * @param name 场面名
     * @return  场景id
     */
    public static int getMapIdByName(String name){
        return NAME_ID_MAP.getOrDefault(name, 0);
    }

    /**
     * 通过地图ID获取地图信息
     *
     * @param mapId 地图ID
     * @return  地图信息
     */
    public static SceneType getSceneById(int mapId){
        return ID_SCENE_MAP.get(mapId);
    }

    public int getMapId() {
        return mapId;
    }

    public String getMapName() {
        return mapName;
    }

    public AbstractMapHandler getHandler() {
        return handler;
    }

    public void setHandler(AbstractMapHandler handler) {
        this.handler = handler;
    }

    public List<Integer> getSceneTypeList() {
        return sceneTypeList;
    }

}