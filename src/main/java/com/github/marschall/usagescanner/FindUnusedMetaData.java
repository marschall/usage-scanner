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
      System.out.println(toTableName(each));
    }
  }

  static String toTableName(String s) {
    int length = s.length();
    StringBuilder buffer = new StringBuilder(length + 5);
    for (int i = 0; i < length; i++) {
      char c = s.charAt(i);
      if (c >= 'A' && c <= 'Z') {
        if (i > 0) {
          buffer.append('_');
        }
        buffer.append(c);
      } else {
        buffer.append((char) (c - 32));
      }
    }
    return buffer.toString();
  }

}
