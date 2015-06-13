package com.github.marschall.usagescanner;

import static org.junit.Assert.*;
import static com.github.marschall.usagescanner.FindUnusedMetaData.toTableName;

import org.junit.Test;

public class FindUnusedMetaDataTest {

  @Test
  public void testToTableName() {
    assertEquals("T_ACME_TABLE", toTableName("TAcmeTable"));
  }

}
