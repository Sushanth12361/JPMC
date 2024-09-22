package com.example.jpmorgantest.data.sources.search.mapper;

import androidx.annotation.NonNull;

import com.example.jpmorgantest.data.sources.search.entities.WeatherDetailResponse;
import com.example.jpmorgantest.domain.usecase.entities.WeatherDetail;
import com.example.jpmorgantest.util.constants.ConstantsKt;

import javax.inject.Inject;

public class SearchResultMapperImpl implements SearchResultMapper {
    @Inject
    public SearchResultMapperImpl(){}

    @NonNull
    @Override
    public WeatherDetail fromResponseToDomain(@NonNull WeatherDetailResponse info) {
        return new WeatherDetail(
                info.getName(),
                info.getMain().getFeelsLike(),
                info.getWeather().isEmpty() ? ConstantsKt.EMPTY_STRING : info.getWeather().get(ConstantsKt.ZERO).getDescription(),
                info.getMain().getHumidity(),
                info.getMain().getPressure(),
                info.getWind().getSpeed(),
                info.getSys().getSunrise(),
                info.getSys().getSunset(),
                info.getWeather().isEmpty() ? ConstantsKt.EMPTY_STRING : info.getWeather().get(ConstantsKt.ZERO).getIcon()
        );
    }
}
