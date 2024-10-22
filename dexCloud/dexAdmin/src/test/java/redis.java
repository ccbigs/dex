
import com.dex.controller.AdminDataPanleController;
import com.dex.dao.BonusDao;
import com.dex.dao.AdminDataPanleDao;
import com.dex.dao.InformationDao;
import com.dex.dexAdminMain;

import com.dex.manager.ESWareMag;

import com.dex.manager.adminDataPanleMag;
import com.dex.service.AdminDataPanleService;
import com.dex.service.ElasticSearchService;
import com.dex.service.InformationService;
import com.dex.service.WareService;
import com.dex.serviceImpl.BonusServiceImpl;
import domain.bonusOrderPojo;
import domain.infoPojo;
import domain.ordersPojo;
import domain.warePojo;
import dto.AOneVolume;
import dto.AThreeVolume;
import dto.ESAdminMsgDTO;
import dto.ESWareDTO;
import io.jsonwebtoken.Claims;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.redis.core.RedisTemplate;
import utils.getTimeNow;
import utils.jwtUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:10:33
 * @message:
 */
@SpringBootTest(classes = dexAdminMain.class)
public class redis {

    @Resource
    private InformationDao informationDao;

    @Autowired
    private ESWareMag esWareMag;

    @Autowired
    private WareService wareService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BonusServiceImpl bonusService;

    @Autowired
    private BonusDao bonusDao;

    @Resource
    private AdminDataPanleDao adminDataPanleDao;

    @Autowired
    private AdminDataPanleService adminDataPanleService;

    @Resource
    private adminDataPanleMag adminDataPanleMag;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private ElasticSearchService elasticSearchService;

    @Test
    public void dd() throws ParseException {
        List<infoPojo> dd = informationDao.researchInformationByTitle("有赞");
        for (infoPojo infoPojo : dd) {
            System.out.println(infoPojo);
        }
    }

    @Test
    public void test() {
        redisTemplate.opsForValue().set("AllBusman",3);
        redisTemplate.opsForValue().set("AllOrder",27);

        redisTemplate.opsForValue().set("AllComment",3);
        redisTemplate.opsForValue().set("AllSumTurnover",138701.39);
        redisTemplate.opsForValue().set("AllActuallyTurnover",83362.60);
        redisTemplate.opsForValue().set("LimitQutient",6);
        redisTemplate.opsForValue().set("AllQutientTurnover",13000.00);

        System.out.println(redisTemplate.opsForValue().get("AllUser"));
    }

    @Test
    void a() throws ParseException {
        List<bonusOrderPojo> bonusOrderPojos = bonusDao.adminGetAllBonusOrder();
        System.out.println(bonusOrderPojos);
    }


    @Test
    void b(){
        List<ordersPojo> sevenDayOrder = adminDataPanleDao.getSevenDayOrder(getTimeNow.getDayAddYMS(1), getTimeNow.getDayAddYMS(-6));
        for (ordersPojo ordersPojo : sevenDayOrder) {
            System.out.println(ordersPojo);
        }
    }

    @Test
    void c(){
        List<AOneVolume> aOneVolumes = adminDataPanleService.DexOnePanleData();
        for (AOneVolume aOneVolume : aOneVolumes) {
            System.out.println(aOneVolume);
        }
    }

    @Test
    void d() throws ParseException {
        elasticSearchService.adminSynchronizationWareData("1");
        elasticSearchService.adminSynchronizationBonusOrderData("1");
        elasticSearchService.adminSynchronizationOrderData("1");
    }





    @Test
    public void createIndex() throws ParseException {
        List<warePojo> warePojos = wareService.adminGetAllWare();

        BeanUtils.copyProperties(warePojos,esWareMag);
//        for (warePojo ware : warePojos) {
//            System.out.println(ware);
//            ESWareDTO esWareDTO = new ESWareDTO();
//            esWareDTO.setId(ware.getId());
//            esWareDTO.setBusman_id(ware.getBusman_id());
//            esWareDTO.setWare_name(ware.getWare_name());
//            esWareDTO.setWare_totalnumber(ware.getWare_totalnumber());
//            esWareDTO.setWare_number(ware.getWare_number());
//            esWareDTO.setWare_describe(ware.getWare_describe());
//            esWareDTO.setWare_connect(ware.getWare_connect());
//            esWareDTO.setWare_price(ware.getWare_price());
//            esWareDTO.setWare_img1(ware.getWare_img1());
//            esWareDTO.setWare_img2(ware.getWare_img2());
//            esWareDTO.setWare_img3(ware.getWare_img3());
//            esWareDTO.setWare_img4(ware.getWare_img4());
//            esWareDTO.setWare_img5(ware.getWare_img5());
//            esWareDTO.setWare_state(ware.getWare_state());
//            esWareDTO.setWare_active(ware.getWare_active());
//            esWareDTO.setWare_date(getTimeNow.CheckStringDateToDate(ware.getWare_date()));
//            esWareDTO.setCategorie_name(ware.getCategorie_name());
//            esWareDTO.setCategorie_id(ware.getCategorie_id());
//
//            esWareDTO.setWare_activeDiscount(ware.getWare_activeDiscount());
//            esWareDTO.setWare_activeFullReduction(ware.getWare_activeFullReduction());
//            esWareDTO.setWare_activeFullPrice(ware.getWare_activeFullPrice());
//
//            esWareDTO.setWare_bonus(ware.getWare_bonus());
//            esWareDTO.setWare_bonusmoney(ware.getWare_bonusmoney());
//            esWareMag.save(esWareDTO);
//        }



    }


    @Test
    public void findById(){

        /*redisTemplate.opsForList().rightPushAll("essearchnode",
                new ESAdminMsgDTO("yellow","open","ware","lhjUy1psQ4C1nWR56ek-Aw",10),
                new ESAdminMsgDTO("yellow","open","orders","lhjUy1psQ4C1nWR56ek-Aw",57),
                new ESAdminMsgDTO("yellow","open","bonusorder","lhjUy1psQ4C1nWR56ek-Aw",25)
        );
        System.out.println(redisTemplate.hasKey("essearchnode"));
*/
    }

    @Test
    public void f(){
        List<ESAdminMsgDTO> essearchnode = (List<ESAdminMsgDTO>) redisTemplate.opsForList().range("essearchnode", 0,-1);
        for (ESAdminMsgDTO esAdminMsgDTO : essearchnode) {
            System.out.println(esAdminMsgDTO);
        }

    }

}
