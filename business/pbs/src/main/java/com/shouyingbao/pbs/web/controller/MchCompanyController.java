package com.shouyingbao.pbs.web.controller;

import com.shouyingbao.pbs.Exception.UserNotFoundException;
import com.shouyingbao.pbs.constants.ConstantEnum;
import com.shouyingbao.pbs.core.bean.ResponseData;
import com.shouyingbao.pbs.core.common.util.DateUtil;
import com.shouyingbao.pbs.entity.MchCompany;
import com.shouyingbao.pbs.service.MchCompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/17 11:05
 **/
@Controller
@RequestMapping("/mchCompany")
public class MchCompanyController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(MchCompanyController.class);

    @Autowired
    MchCompanyService mchCompanyService;


    @RequestMapping(value = "/search")
    public String search() {
        return "/mchCompany/mchCompany";
    }

    @RequestMapping("/list")
    public String list(ModelMap model,@RequestBody Map<String,Object> map){
        LOGGER.info("list:map={}", map);
        try {
            Integer currpage = Integer.valueOf(map.get("currpage").toString());
            List<MchCompany> mchCompanyList = mchCompanyService.selectListByPage(map, currpage, ConstantEnum.LIST_PAGE_SIZE.getCodeInt());
            Integer totalCount = mchCompanyService.selectListCount(map);
            model.addAttribute("rowCount", getRowCount(totalCount));
            model.addAttribute("totalCount", totalCount);
            model.addAttribute("currpage", currpage);
            model.addAttribute("list", mchCompanyList);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return "mchCompany/list";
    }


    @RequestMapping("/edit")
    public String edit(ModelMap modelMap, Integer id) {
        MchCompany mchCompany= null;
        if (id != null && id > 0) {
            mchCompany = mchCompanyService.selectById(id);
        }
        modelMap.addAttribute("entity", mchCompany);
        return "mchCompany/edit";
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResponseData save( MchCompany mchCompany){
        LOGGER.info("insert:mchCompany={}",mchCompany);
        try {
            if (mchCompany.getId() == null) {
                mchCompany.setCreateAt(DateUtil.getCurrDateTime());
                mchCompany.setCreateBy(getUser().getId());
                mchCompanyService.insert(mchCompany);
            } else {
                mchCompany.setUpdateAt(DateUtil.getCurrDateTime());
                mchCompany.setUpdateBy(getUser().getId());
                mchCompanyService.update(mchCompany);
            }
            return ResponseData.success();
        }catch (UserNotFoundException e){
            return ResponseData.failure(e.getCode(),e.getMessage());
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_INSERT_FAIL.getCodeStr(),ConstantEnum.EXCEPTION_INSERT_FAIL.getValueStr());
        }

    }


    @RequestMapping("/cance")
    @ResponseBody
    public ResponseData cance(Integer id) {
        LOGGER.info("cance:id={}", id);
        try {
            MchCompany mchCompany = mchCompanyService.selectById(id);
            mchCompany.setUpdateAt(DateUtil.getCurrDateTime());
            mchCompany.setUpdateBy(getUser().getId());
            mchCompany.setIsDelete(ConstantEnum.IS_DELETE_1.getCodeByte());
            mchCompanyService.update(mchCompany);
            return ResponseData.success();
        } catch (UserNotFoundException e) {
            return ResponseData.failure(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_CANCE_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_CANCE_FAIL.getValueStr());
        }

    }
}
