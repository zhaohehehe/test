package zhaohe.study.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import zhaohe.study.mybatis.domain.City;

@Mapper
public interface CityMapper {
    City findByName(@Param("cityName") String cityName);
    City findCity(@Param("city") City city);
}
