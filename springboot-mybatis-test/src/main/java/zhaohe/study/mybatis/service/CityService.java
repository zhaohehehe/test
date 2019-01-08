package zhaohe.study.mybatis.service;

import zhaohe.study.mybatis.domain.City;


public interface CityService {

    City findCityByName(String cityName);

	City findCity(City city);
}
