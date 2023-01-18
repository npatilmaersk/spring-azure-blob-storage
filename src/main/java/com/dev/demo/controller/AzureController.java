package com.dev.demo.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dev.demo.service.AzureBlobService;

@RestController
@RequestMapping("/files")
public class AzureController {

	@Autowired
	private AzureBlobService azureBlobService;

	@PostMapping
	public ResponseEntity<String> upload(@RequestParam MultipartFile file) throws IOException {

		String fileName = azureBlobService.upload(file);
		return ResponseEntity.ok(fileName);
	}

	@GetMapping("/getAllBlobs")
	public ResponseEntity<List<String>> getAllBlobs() {

		List<String> items = azureBlobService.listBlobs();
		return ResponseEntity.ok(items);
	}

	@GetMapping("/url")
	public ResponseEntity<String> getBlobByName(@RequestParam(value = "file") String file) throws URISyntaxException {
		var bytes = azureBlobService.getFile(file);
		return ResponseEntity.ok(file);
	}

	@GetMapping(path = "/downloads")
	public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam(value = "fileName") String fileName) throws IOException, URISyntaxException {
		byte[] data = azureBlobService.getFile(fileName);//DDD-Telikos.pptx
		ByteArrayResource resource = new ByteArrayResource(data);
		String blobUrl = azureBlobService.getBlobUrl("storage-service",fileName);
		return ResponseEntity
				.ok()
				.contentLength(data.length)
				.header("Content-type", "application/octet-stream")
				.header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
				.body(resource);

	}

	@GetMapping(path = "/downloads/fileurl")
	public ResponseEntity<String> downloadFileUrl(@RequestParam(value = "fileName") String fileName) throws IOException, URISyntaxException {
		String blobUrl = azureBlobService.getBlobUrl("storage-service",fileName);
		return ResponseEntity
				.ok(blobUrl);

	}

	@DeleteMapping("/delete")
	public ResponseEntity<Boolean> delete(@RequestParam(value = "fileName") String fileName) {
		var result = azureBlobService.deleteBlob(fileName);
		return ResponseEntity.ok(result);
	}

	/*@GetMapping("/download")
	public ResponseEntity<Resource> getFile(@RequestParam String fileName) throws URISyntaxException {

		ByteArrayResource resource = new ByteArrayResource(azureBlobService.getFile(fileName));

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).headers(headers).body(resource);
	}*/
}
