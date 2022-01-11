package com.cas.controller;

import com.cas.config.advice.BusinessException;
import com.cas.config.advice.CallResultMsg;
import com.cas.config.advice.UniformResponseHandler;
import com.cas.enums.CodeAndMsg;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/1/10 4:13 下午
 * @desc
 */
@RestController
public class TestController {

    @GetMapping("/success")
    public CallResultMsg testObjectReturn(){
        Map<String, Integer> map = new HashMap();
        map.put("qingfen", 16);
        map.put("lantian", 17);
        map.put("baiyun", 18);
        return new UniformResponseHandler<Map>().sendSuccessResponse(map);
    }

    @GetMapping("/fail/{x}")
    public int testExceptionResturn(@PathVariable int x){
        if (0 < x && x < 10){
            throw new BusinessException(CodeAndMsg.FAIL);
        } else {
            return  1/0;
        }
    }


}
