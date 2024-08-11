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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DriverAuthorizationRepositoryTest {

  private DriverAuthorizationRepository repository;

  @BeforeEach
  public void setUp() {
    repository = new DriverAuthorizationRepository();
  }

  @Test
  public void testExists() {
    assertTrue(repository.exists("257a19ed-4bff-4e32-b692-7da2e3860ef3"));
    assertFalse(repository.exists("non-existent-id"));
  }

  @Test
  public void testIsAllowedToCharge() {
    assertTrue(repository.isAllowedToCharge("257a19ed-4bff-4e32-b692-7da2e3860ef3"));
    assertFalse(repository.isAllowedToCharge("0fe9dce6-fbae-4da8-be90-392a53c0860b"));
    assertFalse(repository.isAllowedToCharge("non-existent-id"));
  }

  @Test
  public void testAddIdentifier() {
    repository.addIdentifier("new-id", true);
    assertTrue(repository.exists("new-id"));
    assertTrue(repository.isAllowedToCharge("new-id"));
  }

  @Test
  public void testRemoveIdentifier() {
    repository.addIdentifier("new-id", true);
    repository.removeIdentifier("new-id");
    assertFalse(repository.exists("new-id"));
  }

  @Test
  public void testUpdateIdentifier() {
    repository.addIdentifier("new-id", true);
    repository.updateIdentifier("new-id", false);
    assertFalse(repository.isAllowedToCharge("new-id"));
  }
}
