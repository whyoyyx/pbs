package com.shouyingbao.pbs.common.pay.weixin.service;

import com.shouyingbao.pbs.common.pay.weixin.model.ScanPayReqData;
import com.shouyingbao.pbs.common.pay.weixin.util.Configure;

/**
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 16:03
 */
public class ScanPayService extends BaseService{

    public ScanPayService() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        super(Configure.PAY_API);
    }

    /**
     * 请求支付服务
     * @param scanPayReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public String request(ScanPayReqData scanPayReqData,Configure configure) throws Exception {

        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        String responseString = sendPost(scanPayReqData,configure);

        return responseString;
    }
}
