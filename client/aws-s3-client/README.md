# AWS S3 Client

<div align="center">

<img src="./docs/wypl-s3.png" alt="wypl-logo"  width="800"/>

![Java](https://img.shields.io/badge/Java-17-orange?logo=java)
![AWS S3](https://img.shields.io/badge/Amazon-S3-569A31?logo=amazons3)

해당 모듈은 [AWS S3](https://aws.amazon.com/ko/s3/)를 활용하여 파일을 관리합니다.

</div>

# Dependencies

아래와 같이 해당 모듈을 추가할 수 있습니다.

```groovy
dependencies {
    implementation project(':client:aws-s3-client')
}
```

# Function

해당 모듈의 주요 기능입니다.

## File Upload

`public String fileUpload(final File file)` 메서드를 사용하여 파일을 `AWS S3`에 업로드 할 수 있습니다. 업로드가 완료되면 업로드된 파일의 경로(URL)을 반환합니다.

## File Remove

`public void deleteFiles(List<String> fimeNames)` 파일의 이름을 통해 `AWS S3`에서 여러 파일을 삭제할 수 있습니다. 파일의 이름이 공백이거나 `NULL`이면 예외를
발생합니다.

# Properties

AWS S3 클라이언트를 사용하기 위해 필요한 환경 변수 설정입니다. 아래 세 가지 주요 설정이 필요합니다.

- `accessKey`: AWS Access Key ID로, AWS 계정의 인증을 위해 사용됩니다.
- `secretKey`: AWS Secret Access Key로, `accessKey`와 함께 사용하여 AWS 계정 인증을 완료합니다.
- `bucket`: 파일을 저장할 AWS S3의 버킷 이름입니다.