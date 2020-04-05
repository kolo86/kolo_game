/**
 * FileName: SceneType
 * Author:   坤龙
 * Date:     2020/4/2 14:56
 * Description: 场景类型
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
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

    NONE(0,"未进入地图"),
    MAINCITY(101, "起始之地", new MainCityMapHandler(), Arrays.asList(102)),
    VILLAGE(102,"村子",new VillageMapHandler(),Arrays.asList(101,103,104)),
    FOREST(103,"森林", new ForestMapHandler(),Arrays.asList(102)),
    CASTLE(104,"城堡", new CastleMapHandler(), Arrays.asList(102))
    ;

    private int mapId;

    private String mapName;

    private AbstractMapHandler handler;

    // 可以进来的地图类型
    private List<Integer> sceneTypeList = new ArrayList<Integer>();

    // Map < 场景名 ， 场景ID >
    private static final Map<String, Integer> nameToId = new HashMap<String, Integer>();
    // Map < 场景ID， 场景对象 >
    private static final Map<Integer, SceneType> idToScene = new HashMap<Integer,SceneType>();

    static{
        for(SceneType type : values()){
            nameToId.put(type.getMapName(), type.getMapId());
            idToScene.put(type.getMapId(), type);
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
     * @param name
     * @return
     */
    public static int getMapIdByName(String name){
        return nameToId.getOrDefault(name, 0);
    }

    /**
     * 通过地图ID获取地图信息
     *
     * @param mapId
     * @return
     */
    public static SceneType getSceneById(int mapId){
        return idToScene.get(mapId);
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
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

    public void setSceneTypeList(List<Integer> sceneTypeList) {
        this.sceneTypeList = sceneTypeList;
    }
}