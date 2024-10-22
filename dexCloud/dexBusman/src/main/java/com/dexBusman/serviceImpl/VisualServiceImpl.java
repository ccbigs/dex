package com.dexBusman.serviceImpl;


import com.dexBusman.dao.VisualDao;
import com.dexBusman.service.VisualService;
import domain.ordersPojo;
import domain.warePojo;
import dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qo.order_add_warePojo;
import utils.JudgeTime;
import utils.MapValue;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 左先生
 * @date: 2021-07-17 11:22
 * @description:逻辑类
 */
@Service
public class VisualServiceImpl implements VisualService {
    @Autowired
    private VisualDao visualdao;

    /*第一张表图*/
    @Override
    public VSalesVolume salesVolume(int busman_id) throws ParseException {
        VSalesVolume vSalesVolume = new VSalesVolume();
        List<ordersPojo> ordersPojos = visualdao.salesVolume(busman_id);

        /*存放全部销售额度*/
        Double[] sale1=new Double[]{0.0,0.0,0.0,0.0};
        Double[] sale2=new Double[]{0.0,0.0,0.0,0.0};

        for (ordersPojo order : ordersPojos) {
            /*获得季度，分别是1，2，3，4*/
            int quarter = JudgeTime.JudgeQuarter(order.getOrders_date());

            if (quarter == 1){
                sale1[0]+=order.getOrders_totalprice().doubleValue();
                if ("已签收".equals(order.getLogistics_status())){
                    sale2[0]+=order.getOrders_totalprice().doubleValue();
                }
            }else if (quarter == 2){
                sale1[1]+=order.getOrders_totalprice().doubleValue();
                if ("已签收".equals(order.getLogistics_status())){
                    sale2[1]+=order.getOrders_totalprice().doubleValue();
                }
            }else if (quarter == 3){
                sale1[2]+=order.getOrders_totalprice().doubleValue();
                if ("已签收".equals(order.getLogistics_status())){
                    sale2[2]+=order.getOrders_totalprice().doubleValue();
                }
            }else{
                sale1[3]+=order.getOrders_totalprice().doubleValue();
                if ("已签收".equals(order.getLogistics_status())){
                    sale2[3]+=order.getOrders_totalprice().doubleValue();
                }
            }
        }


       vSalesVolume.setSale1(sale1);
       vSalesVolume.setSale2(sale2);
       vSalesVolume.setCode(200);
       vSalesVolume.setSize(2);

        return vSalesVolume;
    }

    /*返回第二章图片中普通订单销售数据和秒杀订单数据*/
    @Override
    public List<VTwoVolume> twoVolume(int busman_id) {
        List<VTwoVolume> vTwoVolume = new ArrayList<>();

        vTwoVolume.add(new VTwoVolume("网红下单金额",visualdao.getOrderSaleByBusman(busman_id)));
        vTwoVolume.add(new VTwoVolume("秒杀订单金额",visualdao.getBonusOrderSaleByBusman(busman_id)));

        return vTwoVolume;
    }

    @Override
    public VThreeVolume threeData(int busman_id) {
        List<ordersPojo> ordersPojos = visualdao.salesVolume(busman_id);
        List<warePojo> wareByBusman = visualdao.getWareByBusman(busman_id);
        /*获得总订单数，包括秒杀订单和普通购买订单*/

        VThreeVolume vThreeVolume = new VThreeVolume();

        double allSale= 0.0;

        /*得出总销售额度，得出售出数量*/
        for (ordersPojo ordersPojo : ordersPojos) {
            if ("已签收".equals(ordersPojo.getLogistics_status())){
                allSale+=ordersPojo.getOrders_totalprice().doubleValue();
            }
        }

        /*获得销售量*/
        vThreeVolume.setAllSaleNumber(visualdao.getAllSaleNumber(busman_id));
        vThreeVolume.setListings(wareByBusman.size());
        vThreeVolume.setAllSale(allSale);
        /*添加总订单数*/
        vThreeVolume.setOrderNumber(visualdao.getBonusOrderTotalByBusman(busman_id)+visualdao.getOrdersTotalByBusman(busman_id));
        vThreeVolume.setCode(200);
        vThreeVolume.setSize(4);

        return vThreeVolume;
    }

    /*第五章表的数据处理*/
    @Override
    public List<VFiveVolume> fiveData(int busman_id) throws ParseException {
        List<warePojo> wareByBusman = visualdao.getWareByBusman(busman_id);
        /*返回全部的订单信息*/
        List<ordersPojo> orders = visualdao.getOrderByFive(busman_id);
        List<VFiveVolume> vFives = new ArrayList<>();
        /*
        * 先遍历将商品库存数量都减为0，然后再建返回值列表，列表中填入所有存在的商品，目前商品只赋值名字
        * */
        for (warePojo warePojo : wareByBusman) {
            VFiveVolume vFiveVolume = new VFiveVolume();

            vFiveVolume.setWare_id(warePojo.getId());
            vFiveVolume.setWare_name(warePojo.getWare_name());

            vFives.add(vFiveVolume);
        }
        /*
         * 先获取商品的数量，然后根据商品的id进行判断，进行接收
         * */
        for (ordersPojo order : orders) {
            for (VFiveVolume vFive : vFives) {
                if (order.getWare_id() == vFive.getWare_id()){
                    /**
                     * @author: 左先生
                     * @date: 2021-07-17 14:43
                     * @description:调用judge类的一个方法，来判断订单里面的日期到今天日期的时间，返回值为1或0- -6之间的数字
                     * 返回值为1，表示该类不存在于七天之前
                     */
                    if (JudgeTime.JudgeSevenDay(order.getOrders_date()) == 1){
                        continue;
                    }else {
                        Integer[] ware_number = vFive.getWare_number();
                        ware_number[JudgeTime.JudgeSevenDay(order.getOrders_date())+6]+=order.getOrders_number();
                        vFive.setWare_number(ware_number);
                    }
                }
            }

        }

        /*
         * @author: 左先生
         * @date: 2021-07-17 15:04
         * @description:返回最后的结果如下：
         * 小米电视6 至尊版 75英寸:0,0,0,0,0,0,0,
            荣耀50:0,0,0,0,0,0,0,
            荣耀笔记本14:0,0,0,0,0,0,4,
            真我X7 Pro 至尊版:0,0,0,0,0,0,4,
            真我X50 5G冰川:0,0,0,0,0,0,0,
            真我Buds Air 2 Neo:0,0,0,0,0,0,1,
            真我 Buds Air 2动感黑:0,0,0,0,0,0,4,
            撒撒速1:0,0,4,0,0,0,0,
         */

        return vFives;
    }

    /*
     * @author: 左先生
     * @date: 2021-07-17 16:17
     * @description:第六章表的数据处理，返回商品的库存数量，需要注意的是，返回的商品的状态应该是售卖中
     */
    @Override
    public List<VSixVolume> sixData(int busman_id) {
        List<warePojo> wares = visualdao.getWareByBusman(busman_id);
        List<VSixVolume> vSixVolumes = new ArrayList<>();

        for (warePojo ware : wares) {
                VSixVolume vSixVolume = new VSixVolume();
                vSixVolume.setName(ware.getWare_name());
                vSixVolume.setValue(ware.getWare_number());

                /*装填*/
                vSixVolumes.add(vSixVolume);
        }

        return vSixVolumes;
    }

    /*
     * @author: 左先生
     * @date: 2021-07-18 11:41
     * @description:中国地图的数据处理
     */
    @Override
    public List<VFourVolume> fourData(int busman_id) {
        List<order_add_warePojo> orderAddressWare = visualdao.getOrderAddressWare(busman_id);
        /*返回初始化的地图信息列表*/
        ArrayList<VFourVolume> vFours = MapValue.initFiveVolume();
        /*两层循环，进行地区的对比，将订单的总价格填入Vfour中*/
        for (order_add_warePojo OAW : orderAddressWare) {
            OAW.setLocal(MapValue.DataBasicToEchartsData(OAW.getLocal()));
            for (VFourVolume vFour : vFours) {
                if (vFour.getName().equals(OAW.getLocal())){
                    vFour.setValue(vFour.getValue()+OAW.getOrders_totalprice());
                }
            }
        }
        return vFours;
    }




}
