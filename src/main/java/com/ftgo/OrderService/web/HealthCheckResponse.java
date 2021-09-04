package com.ftgo.OrderService.web;

import lombok.Data;

/**
 * Response of health check.
 */

@Data
public class HealthCheckResponse {
    private String message = "OK";
}
