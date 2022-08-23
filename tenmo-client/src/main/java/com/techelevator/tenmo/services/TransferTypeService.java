package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.TransferType;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferTypeService {
    private final String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public TransferTypeService(String baseUrl) {
        this.baseUrl = baseUrl + "transfer/";
    }

    public TransferType getTransferType(AuthenticatedUser authenticatedUser, String description) {
        TransferType transferType = null;

        try {
            String url = baseUrl + "/transfertype/filter?description=" + description;
            transferType = restTemplate.exchange(url, HttpMethod.GET, makeEntity(authenticatedUser),
                           TransferType.class).getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return transferType;
    }


    public TransferType getTransferTypeFromId(AuthenticatedUser authenticatedUser, int transferTypeId) {
        TransferType transferType = null;

        try {
            String url = baseUrl + "transfertype/" + transferTypeId;
            transferType = restTemplate.exchange(url, HttpMethod.GET,
                           makeEntity(authenticatedUser), TransferType.class).getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return transferType;
    }




        private HttpEntity makeEntity(AuthenticatedUser authenticatedUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
}
