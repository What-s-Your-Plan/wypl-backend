# OpenWeather Client

<div align="center">

<img src="./docs/wypl-openweathermap.png" alt="wypl-logo"  width="800"/>

![Java](https://img.shields.io/badge/Java-17-orange?logo=java)

해당 모듈은 [OpenWeatherMap](https://openweathermap.org/)의 API를 활용하여 날씨 정보를 조회합니다.

</div>

# Dependencies

아래와 같이 해당 모듈을 추가할 수 있습니다.

```groovy
dependencies {
    implementation project(':client:openweather-client')
}
```

# Function

해당 모듈의 주요 기능입니다.

## Fetch Weather - 날씨 조회

`OpenWeatherResponse fetchWeather(OpenWeatherCond cond)`

`fetchWeather` 메서드는 지정된 조건에 따라 OpenWeatherMap API를 호출하여 날씨 정보를 조회합니다. 조회된 날씨 정보는 `OpenWeatherResponse` 객체로 반환됩니다.

### Params

- `OpenWeatherCond cond`: 날씨 조회 조건을 담고 있는 객체로, 다음과 같은 필드를 포함합니다:
    - `city`: 조회할 도시 이름
    - `isLangKr`: 결과 언어를 한국어로 설정할지 여부 (true일 경우 한국어)
    - `isMetric`: 온도 단위를 섭씨로 설정할지 여부 (true일 경우 섭씨)

### Return

- `OpenWeatherResponse`: API에서 응답받은 날씨 정보를 포함한 객체. 이 객체에는 현재 날씨 정보가 포함되어 있습니다.

### 예외 처리

- `OpenWeatherException`: API 호출 중 오류가 발생한 경우 예외가 발생합니다. 예외는 두 가지 유형이 있습니다:
    - `OpenWeatherErrorCode.INTERNAL_SERVER_ERROR`: 서버 측 오류 (5xx 오류) 발생 시 발생하는 예외입니다.
    - `OpenWeatherErrorCode.INVALID_OPEN_WEATHER_REQUEST`: 클라이언트 요청이 잘못된 경우 (2xx 이외의 응답 코드) 발생하는 예외입니다.

### Example

```java
@Autowired
private OpenWeatherClientImpl openWeatherClient;

public void getWeatherExample() {
    OpenWeatherCond cond = new OpenWeatherCond("Seoul", true, true);
    try {
        OpenWeatherResponse response = openWeatherClient.fetchWeather(cond);
        System.out.println("Weather Data: " + response);
    } catch (OpenWeatherException e) {
        System.err.println("Failed to fetch weather data: " + e.getErrorCode());
    }
}
```

위 예제에서는 fetchWeather 메서드를 호출하여 서울의 날씨를 조회하고, 결과를 콘솔에 출력합니다. 예외가 발생할 경우 에러 코드와 함께 오류 메시지를 출력합니다.

# Properties 설정

OpenWeatherMap 클라이언트를 사용하기 위해 필요한 환경 변수 설정입니다. OpenWeatherMap API에 접근하여 날씨 데이터를 가져오기 위해 아래 두 가지 설정이 필요합니다.

- `key`: OpenWeatherMap API에 접근하기 위한 Access Key입니다.
- `base-url`: OpenWeatherMap API의 기본 URL로, API 요청 시 사용하는 엔드포인트의 기본 주소입니다.