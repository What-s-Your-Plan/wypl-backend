# What's Your Plan! - Application Module

<div align="center">

<img src="https://github.com/user-attachments/assets/490f065e-7366-4072-8691-949d774432d8" alt="wypl-logo"  width="800"/>

해당 서버는 이미지를 업로드 및 확장자 변환, 압축을 담당합니다.

</div>

## What's Your Plan! - Core

[Core Server](https://github.com/What-s-Your-Plan/wypl-backend/tree/main/application/wypl-core)은 백엔드 서버의 전반적인 기능들을
담당합니다.

## What's Your Plan! - Image

[Image Server](https://github.com/What-s-Your-Plan/wypl-backend/tree/main/application/wypl-image)은 이미지를 업로드 및 확장자 변환,
압축을 담당합니다.

## What's Your Plan! - Notification

[Notification Server](https://github.com/What-s-Your-Plan/wypl-backend/tree/main/application/wypl-notification)은 알림을
담당합니다.

# Rules

하위 설계 했던 모듈들을 조립하여 서비스 비즈니스를 완성시킵니다.

|              | Application | Client | Domain | Common |
|:------------:|:-----------:|:------:|:------:|:------:|  
| 사용 가능한 모듈 여부 |      X      |   O    |   O    |   O    | 

> 기본적으로 Application 모듈을 사용할 수 없지만 공통적인 로직(`RestControllerAdvice`의 경우)는 예외로 설정하였습니다.