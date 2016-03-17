package service.unit;

import base.BaseTest;
import com.shouyingbao.pbs.common.pay.weixin.model.ScanPayReqData;
import com.shouyingbao.pbs.common.pay.weixin.service.ScanPayService;
import com.shouyingbao.pbs.common.pay.weixin.util.Configure;
import com.shouyingbao.pbs.unit.WeixinConfigUnit;
import com.shouyingbao.pbs.unit.WeixinPayUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;

import java.net.URLEncoder;

/**
 * kejun
 * 2016/3/8 17:13
 **/
public class WeixinUnitTest extends BaseTest{

    @Autowired
    @Qualifier("weixinPayUnit")
    WeixinPayUnit weixinPayUnit;

    @Autowired
    @Qualifier("weixinConfigUnit")
    WeixinConfigUnit weixinConfigUnit;

//    @Test
    public void scanPayTest(){
        try {
            Configure configure = weixinConfigUnit.initConfigure(null,0);
            String  xmString = new String("闪购".toString().getBytes("UTF-8"));
            String body = URLEncoder.encode(xmString, "UTF-8");
            ScanPayReqData scanPayReqData = new ScanPayReqData("123",body,"", "",
                    "12312312313",1, "1","","","","",configure);
            ScanPayService scanPayService = new ScanPayService();
            String result = scanPayService.request(scanPayReqData, configure);
            System.err.println("result=" + result);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void queryOrderTest(){
        weixinPayUnit.scanPayQueryOrder(null, "0031583473408161345", 1);
    }

//    @Test
    public void reverseOrderTest(){
        weixinPayUnit.reverseOrder("0030814341376164130",1);
    }
}