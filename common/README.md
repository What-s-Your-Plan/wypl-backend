# What's Your Plan! - Common Module

<div align="center">

<img src="https://github.com/user-attachments/assets/490f065e-7366-4072-8691-949d774432d8" alt="wypl-logo"  width="800"/>

저장소, 도메인 외 시스템에서 필요한 모듈들은 이 계층에 속하게 됩니다.
독립 모듈 계층은 시스템에도 전혀 관여되지 않았다면, 이 계층은 시스템과 연관이 있는 모듈을 말합니다.

</div>

# Rules

- Type, Utils 등을 정의한다.
- 가능하면 사용하지 않는다.

|              | Application | Client | Domain | Common |
|:------------:|:-----------:|:------:|:------:|:------:|  
| 사용 가능한 모듈 여부 |      X      |   X    |   X    |   x    |

외부의 의존 관계도 갖지 않습니다.

```groovy
dependencies {
}
```

순수한 Java Class만 정의할 수 있으며, 생성된 모듈에서는 시스템에서 많이 쓰이는 Type과 기본적인 Utils Class만 배치하였습니다.