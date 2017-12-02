package org.sekill.web;


import org.sekill.dto.Exposer;
import org.sekill.dto.SeckillExecution;
import org.sekill.dto.SeckillResult;
import org.sekill.entity.Seckill;
import org.sekill.enums.SeckillStateEnum;
import org.sekill.exception.RepeatKillException;
import org.sekill.exception.SeckillCloseException;
import org.sekill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/seckill")
public class SecikllController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value="/list", method = RequestMethod.GET)
    public String list(Model model){
        //列表页
        List<Seckill> list = seckillService.getSeckllList();
        System.out.println("xxxxxxxxxxxxxxxxxxxxx");
        for(Seckill seckill :list){
            System.out.println(seckill);
        }
        model.addAttribute("list",list);
        //list.jsp + model = ModelAndView
        return "list";
    }

    @RequestMapping(value="/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model){

        if(seckillId == null){
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if(seckill == null){
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill", seckill);
        return "detail";
    }

    //ajax json
    @RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST,
    produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result  = new SeckillResult<Exposer>(true, exposer);
        }catch (Exception e){
            result  = new SeckillResult<Exposer>(false, e.getMessage());
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "killPhone", required = false) Long userPhone){
        if(userPhone == null){
            return new SeckillResult<SeckillExecution>(false, "未注册");
        }
        SeckillResult<SeckillExecution> result;
        try{
           SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId,userPhone,md5);
           result = new SeckillResult<SeckillExecution>(true, seckillExecution);
        }catch (SeckillCloseException e){
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.END);
            result  = new SeckillResult<SeckillExecution>(false, execution);
        }catch (RepeatKillException e){
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
            result  = new SeckillResult<SeckillExecution>(false, execution);
        }catch (Exception e){
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
            result  = new SeckillResult<SeckillExecution>(false, execution);
        }
        return result;
    }

    @RequestMapping(value = "/time/now", method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Long> time(){
        Date now = new Date();
        return new SeckillResult<Long>(true, now.getTime());
    }


}
