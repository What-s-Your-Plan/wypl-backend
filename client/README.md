# What's Your Plan! - Client Module

<div align="center">

<img src="https://github.com/user-attachments/assets/490f065e-7366-4072-8691-949d774432d8" alt="wypl-logo"  width="800"/>

저장소, 도메인 외 시스템에서 필요한 모듈들은 이 계층에 속하게 됩니다. 독립 모듈 계층은 시스템에도 전혀 관여되지 않았다면, 이 계층은 시스템과 연관이 있는 모듈을 말합니다.

</div>

# Modules

## AWS S3 Client

[AWS S3 Client](https://github.com/What-s-Your-Plan/wypl-backend/tree/main/client/aws-s3-client)
모듈은 [AWS S3](https://aws.amazon.com/ko/s3/)를 활용하여 파일을 관리합니다.

## Google OAuth Client

## OpenWeather Client

[OpenWeather Client](https://github.com/What-s-Your-Plan/wypl-backend/tree/main/client/openweather-client)모듈의 API를 활용하여
날씨 정보를 조회합니다.

# Rules

해당 계층은 "어플리케이션, 도메인 비즈니스"를 모른다는 원칙을 가집니다.
시스템 전체적인 기능을 서포터하기 위한 기능 모듈이 만들어질 수 있으며, 프로젝트 안의 어떠한 실행 가능한 애플리케이션에서도 독립 사용 가능한 모듈이 위치되어야 하므로 도메인 계층을 의존하지 않습니다.

|              | Application | Client | Domain | Common |
|:------------:|:-----------:|:------:|:------:|:------:|  
| 사용 가능한 모듈 여부 |      X      |   O    |   X    |   O    | 

## Naming

해당 모듈에는 다음과 같은 네이밍 규칙을 가지고

1. `core-web`
   웹설정을 사용하는 프로젝트에서 사용할 수 있는 모듈입니다. 주로 `Web Filter`을 이용한 보안, 로깅 등으로 활용되면서, 웹에 대한 필수적인 공통 설정을 합니다.

2. `{provider}-{function}-client`
   외부의 시스템과 통신을 책임지는 모듈이며 각 외부 시스템과 기능별로 따로 모듈을 만들었습니다. 비즈니스와 관계없이 요청에 응답할 수 있는 사용성을 제공하고, 요청에 대한 설명과 스펙을 책임집니다.
   [Application](https://github.com/What-s-Your-Plan/wypl-backend/tree/main/application)모듈에서는 사용하는 외부 시스템 모듈만 사용하게 됩니다.