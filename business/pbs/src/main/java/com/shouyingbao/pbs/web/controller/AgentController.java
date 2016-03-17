package com.shouyingbao.pbs.web.controller;

import com.shouyingbao.pbs.Exception.UserNotFoundException;
import com.shouyingbao.pbs.constants.ConstantEnum;
import com.shouyingbao.pbs.core.bean.ResponseData;
import com.shouyingbao.pbs.core.common.util.DateUtil;
import com.shouyingbao.pbs.entity.Agent;
import com.shouyingbao.pbs.service.AgentService;
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
 * 2016/3/17 14:53
 **/
@Controller
@RequestMapping("/agent")
public class AgentController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AgentController.class);

    @Autowired
    AgentService agentService;

    @RequestMapping("/list")
    public String list(ModelMap model, @RequestBody Map<String, Object> map) {
        LOGGER.info("list:map={}", map);
        try {
            Integer currpage = Integer.valueOf(map.get("currpage").toString());
            List<Agent> areaList = agentService.selectListByPage(map, currpage, ConstantEnum.LIST_PAGE_SIZE.getCodeInt());
            Integer pageTotal = agentService.selectListCount(map);
            model.addAttribute("rowCount", getRowCount(pageTotal));
            model.addAttribute("currpage", currpage);
            model.addAttribute("list", areaList);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return "agent/list";
    }

    @RequestMapping("/insert")
    @ResponseBody
    public ResponseData insert(@RequestBody Agent agent) {
        LOGGER.info("insert:agent={}", agent);
        try {
            agent.setCreateAt(DateUtil.getCurrDateTime());
            agent.setCreateBy(getUser().getId());
            agentService.insert(agent);
            return ResponseData.success();
        } catch (UserNotFoundException e) {
            return ResponseData.failure(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_INSERT_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_INSERT_FAIL.getValueStr());
        }

    }

    @RequestMapping("/update")
    @ResponseBody
    public ResponseData update(@RequestBody Agent agent) {
        LOGGER.info("update:agent={}", agent);
        try {
            agent.setUpdateAt(DateUtil.getCurrDateTime());
            agent.setUpdateBy(getUser().getId());
            agentService.update(agent);
            return ResponseData.success();
        } catch (UserNotFoundException e) {
            return ResponseData.failure(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_INSERT_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_INSERT_FAIL.getValueStr());
        }

    }
}