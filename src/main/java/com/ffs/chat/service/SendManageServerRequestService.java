package com.ffs.chat.service;

import com.ffs.chat.dto.MatchingResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendManageServerRequestService {
    private static final String HTTP_PREFIX = "http://";
    private static final String CHECK_MATCHING_API =  "/matching/info?memberId=%s&employeeId=%s";

    private final RestTemplate restTemplate;

    @Value("${manage-server.address}")
    private String manageServerAddress;

    @Value("${manage-server.port}")
    private String manageServerPort;

    private String manageServerUrl;

    @PostConstruct
    public void RequiredArgsConstructor(){
        manageServerUrl = HTTP_PREFIX + manageServerAddress +":"+ manageServerPort;
    }

    public MatchingResult checkMemberMatchingWithEmployee(Long userId, Long targetUserId) {
        String url = manageServerUrl + String.format(CHECK_MATCHING_API, userId, targetUserId);

        HttpHeaders header = getHttpHeader();
        ResponseEntity<MatchingResult> response = restTemplate.exchange
                (url, HttpMethod.GET, new HttpEntity<String>(header), MatchingResult.class);

        if(!response.getStatusCode().equals(HttpStatus.OK) || !response.hasBody()) {
            log.info("Fail to check member matching info. userId={}, targetUserId={}", userId, targetUserId);
            return null;
        }

        return response.getBody();
    }

    private HttpHeaders getHttpHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}

