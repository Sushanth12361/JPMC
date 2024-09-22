package com.example.jpmorgantest.presentation.detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import com.example.jpmorgantest.R
import com.example.jpmorgantest.domain.usecase.entities.WeatherDetail
import com.example.jpmorgantest.ui.theme.JPMorganTestTheme
import com.example.jpmorgantest.util.extension.convertUnixTimestampToCentralTime
import com.example.jpmorgantest.util.extension.formatImageUrl
import com.example.jpmorgantest.util.extension.orEmpty

@Composable
fun DetailContent(
    detail: WeatherDetail?
) {
    requireNotNull(detail)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.Normal100)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.Normal100))
    ) {
        // City name
        Text(
            text = detail.cityName,
            style = MaterialTheme.typography.headlineMedium
        )

        // Temperature
        Text(
            text = stringResource(R.string.desc_temperature, detail.temperature.orEmpty()),
            style = MaterialTheme.typography.headlineMedium
        )

        // Weather description
        Text(
            text = detail.weatherDescription,
            style = MaterialTheme.typography.headlineSmall
        )

        // Humidity and pressure
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.desc_humidity, detail.humidity.orEmpty()),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(R.string.desc_pressure, detail.pressure.orEmpty()),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Wind speed
        Text(
            text = stringResource(R.string.desc_wind_speed, detail.windSpeed.orEmpty()),
            style = MaterialTheme.typography.bodyMedium
        )

        // Sunrise and sunset times
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(
                    R.string.desc_sunrise,
                    detail.sunrise.convertUnixTimestampToCentralTime()
                ),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(
                    R.string.desc_sunset,
                    detail.sunrise.convertUnixTimestampToCentralTime()
                ),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Weather icon
        Image(
            painter = rememberImagePainter(detail.icon.formatImageUrl()),
            contentDescription = stringResource(
                R.string.desc_type_img_weather,
                detail.weatherDescription
            ),
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.ImageNormal))
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
@Preview(name = "Light Theme", showBackground = true)
fun DetailContent() {
    JPMorganTestTheme {
        DetailContent(
            detail = WeatherDetail(
                cityName = "Fairfield",
                temperature = 298.43,
                weatherDescription = "clear sky",
                humidity = 64,
                pressure = 1019,
                windSpeed = 2.58,
                sunrise = 1696330297L,
                sunset = 1696372375L,
                icon = "01d"
            )
        )
    }
}
