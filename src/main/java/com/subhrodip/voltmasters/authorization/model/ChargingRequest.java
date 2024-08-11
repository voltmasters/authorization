package com.subhrodip.voltmasters.authorization.model;

import java.util.UUID;

public record ChargingRequest(UUID requestId, AuthorizationRequest authorizationRequest) {}
