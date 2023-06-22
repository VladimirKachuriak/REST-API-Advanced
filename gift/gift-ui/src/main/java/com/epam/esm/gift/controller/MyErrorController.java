package com.epam.esm.gift.controller;

import com.epam.esm.gift.model.ErrorResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyErrorController implements ErrorController {
    @GetMapping("/error")
    public ResponseEntity<ErrorResponse> renderErrorPage(HttpServletRequest httpRequest) {
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);
        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Http Error Code: 400. Bad Request";
                break;
            }
            case 401: {
                errorMsg = "Http Error Code: 401. Unauthorized";
                break;
            }
            case 404: {
                errorMsg = "Http Error Code: 404. Resource not found";
                break;
            }
            case 500: {
                errorMsg = "Http Error Code: 500. Internal Server Errorsdaf";
                break;
            }
        }
        ErrorResponse errorResponse = new ErrorResponse(errorMsg, httpErrorCode);

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatusCode.valueOf(httpErrorCode));
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    }
}