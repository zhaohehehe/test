package zhaohe.study.mybatis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zhaohe.study.mybatis.domain.City;
import zhaohe.study.mybatis.mapper.CityMapper;
import zhaohe.study.mybatis.service.CityService;

@Service("CityService")
public class CityServiceImpl implements CityService {

    @Autowired
    private CityMapper cityMapper;

    public City findCityByName(String cityName) {
        return cityMapper.findByName(cityName);
    }

	@Override
	public City findCity(City city) {
		return  cityMapper.findCity(city);
	}

}
