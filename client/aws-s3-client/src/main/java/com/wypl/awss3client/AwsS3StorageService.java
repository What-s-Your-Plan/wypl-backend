package com.wypl.awss3client;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AwsS3StorageService {

	private final AmazonS3Client amazonS3Client;

	private final AwsS3Properties awsS3Properties;

	/**
	 * 파일을 <a href="https://aws.amazon.com/ko/s3/">AWS S3</a>에 업로드한 후 업로드된 파일의 경로를 반환합니다.
	 *
	 * @param file AWS S3에 업로드할 파일
	 * @return AWS S3에 업로드된 파일의 경로
	 */
	public String fileUpload(final File file) {
		amazonS3Client.putObject(awsS3Properties.getBucket(), file.getName(), file);
		return amazonS3Client.getUrl(awsS3Properties.getBucket(), file.getName()).toString();
	}

	/**
	 *	파일의 이름을 가지고 AWS S3에서 파일을 삭제합니다.<p>
	 *	</br>
	 *    {@link  com.amazonaws.services.s3.model.AmazonS3Exception}<p>
	 *	1. 파일의 이름이 공백이거나 `NULL`이면 예외를 던진다.
	 *
	 * @param fileNames AWS S3에서 삭제할 파일 이름
	 */
	public void filesRemove(List<String> fileNames) {
		List<DeleteObjectsRequest.KeyVersion> list = fileNames.stream()
				.map(DeleteObjectsRequest.KeyVersion::new)
				.toList();
		DeleteObjectsRequest deleteObjectRequest = new DeleteObjectsRequest(awsS3Properties.getBucket());
		deleteObjectRequest.setKeys(list);
		amazonS3Client.deleteObjects(deleteObjectRequest);
	}
}
