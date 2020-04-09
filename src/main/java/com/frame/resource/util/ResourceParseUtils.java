/**
 * FileName: ResourceParseUtils
 * Author:   坤龙
 * Date:     2020/4/7 22:22
 * Description: 配置表解析工具
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.frame.resource.util;

import com.frame.resource.AbstractResource;
import com.frame.resource.constant.FieldEnum;
import com.frame.resource.handler.ResourceCacheHandler;
import com.frame.resource.handler.ResourceScanHandler;
import com.frame.resource.model.FieldObj;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 〈一句话功能简述〉<br>
 * 〈配置表解析工具〉
 *
 * @author 坤龙
 * @create 2020/4/7
 * @since 1.0.0
 */
public class ResourceParseUtils {

    private static final Logger logger = LoggerFactory.getLogger(ResourceParseUtils.class);

    private static final String SERVER_MARK = "SERVER";

    private static final String END_MARK = "End";

    /**
     * 解析单个Excel文件
     *
     * @param workBook
     */
    public static void parseWorkBook(XSSFWorkbook workBook) {
        int sheets = workBook.getNumberOfSheets();
        for (int i = 0; i < sheets; i++) {
            parseSheet(workBook.getSheetAt(i));
        }
    }

    /**
     * 解析单个工作表
     *
     * @param sheet
     */
    private static void parseSheet(XSSFSheet sheet) {
        String resourceName = sheet.getRow(0).getCell(0).getStringCellValue();
        Class<?> resourceClass = ResourceScanHandler.getResourceClass(resourceName.toUpperCase());
        int serverRow = getServerRow(sheet);
        Set<FieldObj> readerSet = getFiledInfo(sheet, serverRow);
        List list = parseData(sheet, resourceClass, serverRow, readerSet);
        cacheData(list);
    }

    /**
     * 缓存数据
     *
     * @param list
     */
    private static void cacheData(List list) {
        for (int i = 0; i < list.size(); i++) {
            ResourceCacheHandler.addResource(list.get(i).getClass(), (AbstractResource) list.get(i));
        }
    }

    /**
     * 解析数据
     *
     * @param sheet
     * @param resourceClass
     * @param serverRow
     * @param readerSet
     * @return
     */
    private static List parseData(XSSFSheet sheet, Class<?> resourceClass, int serverRow, Set<FieldObj> readerSet) {
        List list = new ArrayList();

        boolean parseState = true;
        int parseIndex = serverRow + 1;
        while (parseState) {
            // 单行数据
            XSSFRow xssfRow = sheet.getRow(parseIndex);
            ++parseIndex;

            XSSFCell cell = xssfRow.getCell(0);
            if (xssfRow.getCell(0) != null && ResourceParseUtils.END_MARK.equals(cell.getStringCellValue())) {
                parseState = false;
            }

            // 创建一个对象
            Object instance = null;
            try {
                instance = resourceClass.getConstructor().newInstance();
                for (FieldObj obj : readerSet) {
                    // 单个数据
                    XSSFCell rowCell = xssfRow.getCell(obj.getIndex());

                    // 根据类型解析出对应的真实数据
                    FieldEnum type = FieldEnum.getType(obj.getType());
                    Object parseData = type.parseData(rowCell);

                    // 把数据放入对象中
                    Field field = resourceClass.getDeclaredField(obj.getFieldName());
                    field.setAccessible(true);
                    field.set(instance, parseData);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            list.add(instance);
        }
        return list;
    }

    /**
     * 获取需要读取字段的信息
     *
     * @param sheet
     * @param serverIndex
     * @return
     */
    private static Set<FieldObj> getFiledInfo(XSSFSheet sheet, int serverIndex) {
        Set<FieldObj> readerSet = new HashSet<FieldObj>();
        // 服务端定义那一行
        XSSFRow serverRow = sheet.getRow(serverIndex);
        XSSFRow typeRow = sheet.getRow(serverIndex - 1);

        // 最后一列的编号
        short lastCellNum = serverRow.getLastCellNum();
        // 缓存 Map < 列数 ， 字段类型（枚举） >
        for (int index = 1; index < lastCellNum; index++) {
            XSSFCell tempCell = serverRow.getCell(index);
            if (tempCell != null && tempCell.getStringCellValue().trim() != null) {
                String type = typeRow.getCell(index).getStringCellValue().toUpperCase();
                String fieldName = serverRow.getCell(index).getStringCellValue();
                readerSet.add(FieldObj.valueOf(index, fieldName, type));
            }
        }
        return readerSet;
    }

    /**
     * 服务端标识所在行
     *
     * @param sheet
     * @return
     */
    private static int getServerRow(XSSFSheet sheet) {
        boolean state = true;
        int i = 0;  // 服务端读取字段的行数
        while (state) {
            String serverIndex = "";
            XSSFCell cell = sheet.getRow(i).getCell(0);
            if (cell != null) {
                serverIndex = cell.getStringCellValue().toUpperCase();
            }
            if (serverIndex.equals(ResourceParseUtils.SERVER_MARK)) {
                state = false;
            } else {
                ++i;
            }
        }
        return i;
    }

}