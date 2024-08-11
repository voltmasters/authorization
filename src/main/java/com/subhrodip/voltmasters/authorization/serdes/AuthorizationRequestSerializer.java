/*
 * authorization - Authorization Service for authenticating requests from Charging Stations
 * Copyright © 2024 Subhrodip Mohanta (contact@subhrodip.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.subhrodip.voltmasters.authorization.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.subhrodip.voltmasters.authorization.model.AuthorizationRequest;
import java.util.Map;
import org.apache.kafka.common.serialization.Serializer;

public class AuthorizationRequestSerializer implements Serializer<AuthorizationRequest> {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {}

  @Override
  public byte[] serialize(String topic, AuthorizationRequest data) {
    try {
      return objectMapper.writeValueAsBytes(data);
    } catch (Exception e) {
      throw new RuntimeException("Error serializing AuthorizationRequest", e);
    }
  }

  @Override
  public void close() {}
}
