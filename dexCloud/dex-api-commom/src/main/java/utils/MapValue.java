package utils;



import dto.VFourVolume;

import java.util.ArrayList;

/**
 * @author:左定权
 * @date:日期:2021-07-17-时间:16:44
 * @message:种花家地图的数据
 */
public class MapValue {

    public static String[] DataBasicMap={
        "北京市","天津市", "河北省", "山西省", "内蒙古自治区", "辽宁省", "吉林省", "黑龙江省", "上海市", "江苏省",
        "浙江省", "安徽省", "福建省", "江西省", "山东省", "河南省", "湖北省", "湖南省", "广东省", "广西壮族自治区",
        "海南省", "重庆市", "四川省", "贵州省", "云南省", "西藏自治区", "陕西省", "甘肃省", "青海省", "宁夏回族自治区",
        "新疆维吾尔自治区", "台湾省", "香港特别行政区", "澳门特别行政区"};

    public static String[] EchartsMaps={
        "北京", "天津","河北","山西","内蒙古","辽宁","吉林","黑龙江","上海","江苏",
        "浙江","安徽", "福建", "江西","山东","河南", "湖北", "湖南","广东", "广西",
        "海南", "重庆", "四川","贵州","云南","西藏", "陕西","甘肃", "青海","宁夏",
        "新疆", "台湾", "香港", "澳门", "南海诸岛",
    };

    /*
     * @author: 左先生
     * @date: 2021-07-17 17:24
     * @description:序列化出来一个原始的地图数据列表，包括name:h和Value:。
     */
    public static ArrayList<VFourVolume> initFiveVolume(){
        ArrayList<VFourVolume> vFourVolumes = new ArrayList<>();
        for (String echartsMap : EchartsMaps) {
            VFourVolume vFour = new VFourVolume();
            vFour.setName(echartsMap);

            vFourVolumes.add(vFour);
        }

        return vFourVolumes;
    }

    /*
     * @author: 左先生
     * @date: 2021-07-18 11:49
     * @description:将数据库中的地图信息转化为echarts中的地图信息，方便于和序列化出来的地图进行比较
     * 使用containts方法，来判断订单地址中是否包含省份信息
     */
    public static String DataBasicToEchartsData(String local){
        String name=null;
        for (int i = 0; i <DataBasicMap.length ; i++) {
            if (local.contains(DataBasicMap[i])){
                name=EchartsMaps[i];
                break;
            }
        }

        return name;
    }
}
