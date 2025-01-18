# Image Controller

## Upload Image Request

```mermaid
flowchart TD

Request("Client - 이미지 업로드 요청")
Service("Image Service - 이미지 저장")
Validate("Image Service - 이미지 확장자 검증")
ImageMagic("ImageMagick - 이미지 압축")
Upload("Aws S3 Storage Service - 파일 업로드")
Remove("Image Remove Utils - 로컬 이미지 삭제")


201("201 - Created")
400("400 - Bad Request")
500("500 - Internal Server Error")

Request --> Service --> Validate
Validate -- 잘못된 확장자인 경우 --> 400
Validate --> ImageMagic
ImageMagic -- 올바르지 않은 파일 경로 --> 500
ImageMagic -- 존재하지 않는 명령어 --> 500
ImageMagic -- 부모 프로세스 죽음 --> 500
ImageMagic --> Upload --> Remove
Remove -- WAS에 업로드 된 이미지 삭제 실패 --> 500
Remove --> 201
```

## Delete Image Request