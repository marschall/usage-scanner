package com.github.marschall.usagescanner;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MetaDataExtractorTest {

  @Rule
  public final FileSystemRule rule = new FileSystemRule();

  private MetaDataExtractor extractor;

  @Before
  public void setUp() {
    extractor = new MetaDataExtractor();
  }

  @Test
  public void extractMetaDataFromSources() throws IOException {

    FileSystem fileSystem = this.rule.getFileSystem();
    Path classes = Files.createDirectory(fileSystem.getPath("classes"));

    Files.createFile(classes.resolve("AcmeTransactionGTO.java"));
    Files.createFile(classes.resolve("AcmeTransactionROGTO.java"));
    Files.createFile(classes.resolve("AcmeTransactionGDAO.java"));
    Files.createFile(classes.resolve("AcmeTransactionROGDAO.java"));
    Files.createFile(classes.resolve("AcmeTransactionMetaData.java"));
    Files.createFile(classes.resolve("AcmeTransactionGPK.java"));
    Files.createFile(classes.resolve("AccountGTO.java"));
    Files.createFile(classes.resolve("AccountROGTO.java"));
    Files.createFile(classes.resolve("AccountGDAO.java"));
    Files.createFile(classes.resolve("AccountROGDAO.java"));
    Files.createFile(classes.resolve("AccountMetaData.java"));
    Files.createFile(classes.resolve("AccountGPK.java"));

    Set<String> metaData = this.extractor.extractMetaData(classes);
    assertEquals(new HashSet<>(Arrays.asList("AcmeTransaction", "Account")), metaData);
  }

  @Test
  public void extractMetaDataFromClasse() throws IOException {

    FileSystem fileSystem = this.rule.getFileSystem();
    Path classes = Files.createDirectory(fileSystem.getPath("classes"));

    Files.createFile(classes.resolve("AcmeTransactionGTO.class"));
    Files.createFile(classes.resolve("AcmeTransactionROGTO.class"));
    Files.createFile(classes.resolve("AcmeTransactionGDAO.class"));
    Files.createFile(classes.resolve("AcmeTransactionROGDAO.class"));
    Files.createFile(classes.resolve("AcmeTransactionMetaData.class"));
    Files.createFile(classes.resolve("AcmeTransactionGPK.class"));
    Files.createFile(classes.resolve("AccountGTO.class"));
    Files.createFile(classes.resolve("AccountROGTO.class"));
    Files.createFile(classes.resolve("AccountGDAO.class"));
    Files.createFile(classes.resolve("AccountROGDAO.class"));
    Files.createFile(classes.resolve("AccountMetaData.class"));
    Files.createFile(classes.resolve("AccountGPK.class"));

    Set<String> metaData = this.extractor.extractMetaData(classes);
    assertEquals(new HashSet<>(Arrays.asList("AcmeTransaction", "Account")), metaData);
  }

}
