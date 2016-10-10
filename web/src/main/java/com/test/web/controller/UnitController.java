package com.test.web.controller;

import com.test.data.domain.Unit;
import com.test.data.domain.User;
import com.test.data.model.UnitQo;
import com.test.data.repositories.UserRepository;
import com.test.data.services.UnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequestMapping("/unit")
public class UnitController {
    private static Logger logger = LoggerFactory.getLogger(UnitController.class);

    @Autowired
    private UnitService unitService;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/index")
    public String index(ModelMap model, Principal user) throws Exception{
        model.addAttribute("user", user);
        return "unit/index";
    }

    @RequestMapping(value="/userlist/{id}")
    public String userlist(ModelMap model,@PathVariable Long id) {
        Iterable<User> users = userRepository.getUsersByUnitId(id);
        model.addAttribute("users",users);
        return "unit/ulist";
    }

    @RequestMapping(value="/{id}")
    public String show(ModelMap model,@PathVariable Long id) {
        Unit unit = unitService.findById(id);
        model.addAttribute("unit",unit);
        return "unit/show";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Page<Unit> getList(UnitQo unitQo) {
        try {
            return unitService.findPage(unitQo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/new")
    public String create(){
        return "unit/new";
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(Unit unit) throws Exception{
        unitService.save(unit);
        logger.info("新增->ID="+unit.getId());
        return "1";
    }

    @RequestMapping(value="/edit/{id}")
    public String update(ModelMap model,@PathVariable Long id){
        Unit unit = unitService.findById(id);
        model.addAttribute("unit",unit);
        return "unit/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value="/update")
    @ResponseBody
    public String update(Unit unit) throws Exception{
        unitService.save(unit);
        logger.info("修改->ID="+unit.getId());
        return "1";
    }

    @RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable Long id) throws Exception{
        unitService.delete(id);
        logger.info("删除->ID="+id);
        return "1";
    }

}
