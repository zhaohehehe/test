package com.zhaohe.anno.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.zhaohe.anno.domain.ListObj;
import com.zhaohe.anno.service.TestService;

@Controller
@RequestMapping(path = "/service")
@SessionAttributes(value = { "objs" }, types = { Integer.class })
public class TestServiceController {
	@Resource
	private TestService testService;

	@RequestMapping(value = "control01")
	public void control01() {
		testService.show();
	}

	@RequestMapping("/control02")
	public void control02() {
		testService.show();
		this.show();
	}

	private void show() {
		System.out.println("控制器中附加的show()");
	}

	@RequestMapping(value = "/control03")
	public ModelAndView control03() {
		ModelAndView view = new ModelAndView();
		List<ListObj> list = new ArrayList<ListObj>();
		ListObj obj1 = new ListObj();
		obj1.setId("1");
		obj1.setName("a");
		ListObj obj2 = new ListObj();
		obj2.setId("2");
		obj2.setName("b");
		list.add(obj1);
		list.add(obj2);
		view.addObject("list", list);
		view.setViewName("jsps/test");
		return view;
	}

	@RequestMapping(value = "/control04", produces = "application/json;charset=UTF-8", method = { RequestMethod.POST })
	public @ResponseBody String control04() {
		ListObj obj2 = new ListObj();
		obj2.setId("2");
		obj2.setName("b");
		Gson gson = new Gson();
		return gson.toJson(obj2);
	}

	@RequestMapping(value = "/control05/{id}", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.GET })
	@ResponseBody
	public String control05(@PathVariable String id) {
		System.out.println("@PathVariable id = " + id);
		return id;
	}

	@RequestMapping(value = "/control06", produces = "application/json;charset=utf-8", method = { RequestMethod.POST })
	@ResponseBody
	public ListObj control06(@RequestBody Map<String, ?> map) {
		System.out.println("000000000000000000000000");
		ListObj obj2 = new ListObj();
		obj2.setId(map.get("id") + "new");
		obj2.setName(map.get("name") + "new");
		return obj2;
	}

	@RequestMapping(value = "/control061", produces = "application/json;charset=utf-8", method = { RequestMethod.POST })
	@ResponseBody
	public ListObj control061(@RequestBody ListObj obj) {
		System.out.println(obj);
		return obj;
	}

	@RequestMapping(value = "/control07", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	public ListObj control07(@RequestParam("id") String id, @RequestParam("name") String name) {
		ListObj obj2 = new ListObj();
		obj2.setId(id + "new");
		obj2.setName(name + "new");
		return obj2;
	}

	/**
	 * 每次执行请求之前先执行@ModelAttribute
	 * 
	 * @param model
	 * @return
	 */
	@ModelAttribute("obj")
	public ListObj controlAdd(Model model) {
		ListObj obj2 = new ListObj();
		obj2.setId("0000");
		obj2.setName("0000");
		System.out.println("===============@ModelAttribute==============================");
		return obj2;
	}

	/**
	 * test.jsp取的是@ModelAttribute("obj")和@ModelAttribute("listobj")
	 * 和@SessionAttributes(value={"objs"},types={Integer.class}) 如果二者值不一样会怎样？
	 * 
	 * @param obj2
	 * @return
	 */
	@RequestMapping(value = "/control08")
	public ModelAndView control08(@ModelAttribute("listobj") ListObj obj2, ModelMap model) {
		ModelAndView view = new ModelAndView();
		obj2.setId("1111");
		obj2.setName("1111");
		view.addObject(obj2);
		view.setViewName("jsps/test");
		System.out.println("===============参数 @ModelAttribute==============================");
		ListObj objs = new ListObj();
		objs.setId("2222");
		objs.setName("2222");
		model.addAttribute("objs", objs);
		return view;
	}

	/*
	 * 待定： 1.@RequestHeader和@CookieValue 2.redirect和forward
	 */
	@RequestMapping(value = "/controlUnused...")
	public void controlUnused(@RequestHeader("Accept-Encoding") String acceptEnc) {
		System.out.println("Accept Encoding : " + acceptEnc);
	}
}
