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

package com.subhrodip.voltmasters.authorization.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StationAuthorizationRepositoryTest {

  private StationAuthorizationRepository repository;

  @BeforeEach
  public void setUp() {
    repository = new StationAuthorizationRepository();
  }

  @Test
  public void exists() {
    assertTrue(repository.exists(UUID.fromString("59419c4f-b8b8-4def-b460-f8c11ae426ea")));
    assertFalse(repository.exists(UUID.fromString("00000000-0000-0000-0000-000000000000")));
  }

  @Test
  public void isAllowedToCharge() {
    assertTrue(
        repository.isAllowedToCharge(UUID.fromString("59419c4f-b8b8-4def-b460-f8c11ae426ea")));
    assertFalse(
        repository.isAllowedToCharge(UUID.fromString("80ba0cee-e458-49e3-8fed-ee2524a4b943")));
    assertFalse(
        repository.isAllowedToCharge(UUID.fromString("00000000-0000-0000-0000-000000000000")));
  }

  @Test
  public void addIdentifier() {
    UUID newId = UUID.randomUUID();
    repository.addIdentifier(newId, true);
    assertTrue(repository.exists(newId));
    assertTrue(repository.isAllowedToCharge(newId));
  }

  @Test
  public void removeIdentifier() {
    UUID newId = UUID.randomUUID();
    repository.addIdentifier(newId, true);
    repository.removeIdentifier(newId);
    assertFalse(repository.exists(newId));
  }

  @Test
  public void updateIdentifier() {
    UUID newId = UUID.randomUUID();
    repository.addIdentifier(newId, true);
    repository.updateIdentifier(newId, false);
    assertFalse(repository.isAllowedToCharge(newId));
  }
}
