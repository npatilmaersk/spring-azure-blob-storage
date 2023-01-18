package com.dev.demo.config;

import com.azure.storage.common.StorageSharedKeyCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

import java.util.Locale;

@Configuration
public class AzureBlobConfig {

//	@Value("${azure.storage.blob-endpoint}")
//	private String connectionString;
//
//	@Value("${azure.storage.container-name}")
//	private String containerName;

//	@Bean
//	public BlobServiceClient clobServiceClient() {
//
//		BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString)
//				.buildClient();
//
//		return blobServiceClient;
//
//	}
//
//	@Bean
//	public BlobContainerClient blobContainerClient() {
//
//		BlobContainerClient blobContainerClient = clobServiceClient().getBlobContainerClient(containerName);
//
//		return blobContainerClient;
//
//	}

	@Value("${azure.storage.account-name}")
	private String accountName;

	@Value("${azure.storage.account-key}")
	private String accountKey;

	@Bean
	public BlobServiceClient getBlobServiceClient() {
		return new BlobServiceClientBuilder()
				.endpoint(String.format(Locale.ROOT, "https://%s.blob.core.windows.net", accountName))
				.credential(new StorageSharedKeyCredential(accountName, accountKey)).buildClient();
	}

}