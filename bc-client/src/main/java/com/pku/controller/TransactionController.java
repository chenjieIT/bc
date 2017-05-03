package com.pku.controller;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.pku.bean.Transiction;
import com.pku.multithread.Sign;
import com.pku.rabbitmqAPI.MqProductor;

@Controller  
@RequestMapping("/transaction")
public class TransactionController {
	private static Logger logger = LoggerFactory.getLogger(TransactionController.class);
	@Autowired
    private Properties remoteConfigs;  
	
	@ResponseBody
	@RequestMapping("/receive")  
    public String receive(HttpServletRequest request, Transiction transiction, Model model){  
		logger.error("a request to receive...");
		if(transiction == null){
			return "failed";
		}
		MqProductor mqProductor = MqProductor.createProductor();
		try {
			String signInfo = Sign.sign(JSON.toJSONString(transiction));
			transiction.setSignInfo(signInfo);
			mqProductor.sendMessageToQueue(JSON.toJSONString(transiction));
		} catch (Exception e) {
			logger.debug("failed to get message...");
			e.printStackTrace();
		} 
        model.addAttribute("is_ok", true);  
        return "ok";
    }
    
    @ResponseBody
	@RequestMapping("/test")
    public String test(HttpServletRequest request, Model model){
    	logger.debug(remoteConfigs.getProperty("author"));
    	logger.debug(remoteConfigs.getProperty("project.name"));
    	return "ok";
    }
	
}
