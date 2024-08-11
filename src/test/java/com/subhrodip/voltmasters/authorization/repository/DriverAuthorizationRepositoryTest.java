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
