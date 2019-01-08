package zhaohe.study.mybatis.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import zhaohe.study.mybatis.domain.City;
import zhaohe.study.mybatis.service.CityService;

@RestController
@RequestMapping
public class CityController {

	@Autowired
	private CityService cityService;
	@RequestMapping("/api/city")
	public void findOneCity(){
		City city = cityService.findCityByName("beijing");
		System.out.println(city.toString());
		city = cityService.findCity(city);
		System.out.println(city.toString());
	}

	//http://127.0.0.1:8080/api/city2  JSON:{"cityName":"beijing"}
	@RequestMapping(value = "/api/city1", method = RequestMethod.POST)
	public @ResponseBody void findOneCity1(@RequestBody Map<String, Object> param) {
		City city = cityService.findCityByName(param.get("cityName").toString());
		System.out.println(city.toString());
		
	}
	//http://127.0.0.1:8080/api/city2?cityName=beijing
	@RequestMapping(value = "/api/city2", method = RequestMethod.POST)
	public @ResponseBody void findOneCity2(@RequestParam("cityName") String param) {
		City city = cityService.findCityByName(param);
		System.out.println(city.toString());
	}
	//http://localhost:8080/api/city3/beijing
	@RequestMapping(value = "/api/city3/{param}", method = RequestMethod.POST)
	public @ResponseBody void findOneCity3(@PathVariable String param) {
		City city = cityService.findCityByName(param);
		System.out.println(city);
	}
}
