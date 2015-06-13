package com.github.marschall.usagescanner;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public class FindUnusedMetaData {

  public static void main(String[] args) throws IOException {
    Path sourcePath = Paths.get("java/classes");
    MetaDataExtractor extractor = new MetaDataExtractor();
    Set<String> metaData = extractor.extractMetaData(sourcePath);

    UsageCollector collector = new UsageCollector(metaData);
    Collection<Path> projectRoots = Arrays.asList(Paths.get("project1"), Paths.get("project1"));
    Set<String> unusedMetaData = collector.scanFolders(projectRoots);

    System.out.println("Unused MetaData:");
    for (String each : unusedMetaData) {
      System.out.println(each);
    }
  }

}
