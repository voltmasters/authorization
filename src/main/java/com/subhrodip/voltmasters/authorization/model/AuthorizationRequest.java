package com.subhrodip.voltmasters.authorization.model;

import java.util.UUID;

public record AuthorizationRequest(UUID stationUuid, DriverIdentifier driverIdentifier) {}
