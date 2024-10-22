import com.dexBusman.dexBusmanMain;
import com.dexBusman.service.VisualService;
import dto.VSixVolume;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-09-26-时间:9:59
 * @message:
 */
@SpringBootTest(classes = dexBusmanMain.class)
public class VisualTest {
    @Autowired
    private VisualService visualService;

    @Test
    public void six(){
        List<VSixVolume> vSixVolumes = visualService.sixData(2);
        for (VSixVolume vSixVolume : vSixVolumes) {
            System.out.println(vSixVolume);
        }
    }
}
