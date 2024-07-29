package com.bidding.crew.report;

import com.bidding.crew.flight.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v0/setup")
public class SetupController {

    private final RestTemplate restTemplate;

    @Autowired
    public SetupController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/initialize")
    public ResponseEntity<String> initializeApplication(@RequestParam("file") MultipartFile file) throws IOException {
        // Przygotowanie danych do przesłania pliku
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new MultipartInputStreamFileResource(file.getBytes(), file.getOriginalFilename()));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Wywołanie endpointu do przesłania pliku
        restTemplate.postForEntity("http://localhost:8080/api/v0/flights/upload", requestEntity, String.class);

        ReportRequest reportRequest = new ReportRequest();
        // Ustawienie danych dla raportu (przykładowe dane, dostosuj do swoich potrzeb)
        restTemplate.postForEntity("http://localhost:8080/api/v0/reports", reportRequest, ReportResponse.class);

        return ResponseEntity.ok("Initialization started");
    }

    private static class MultipartInputStreamFileResource extends ByteArrayResource {
        private final String filename;

        MultipartInputStreamFileResource(byte[] byteArray, String filename) {
            super(byteArray);
            this.filename = filename;
        }

        @Override
        public String getFilename() {
            return this.filename;
        }
    }
}

