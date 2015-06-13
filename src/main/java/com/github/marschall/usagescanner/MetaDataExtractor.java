package com.github.marschall.usagescanner;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class MetaDataExtractor {

  private Set<String> suffixes;

  MetaDataExtractor() {
    this.suffixes = new HashSet<>(Arrays.asList("GTO", "ROGTO", "GDAO", "ROGDAO", "MetaData", "GPK"));
  }

  Set<String> extractMetaData(Path path) throws IOException {
    Set<String> metaData = new HashSet<>();
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, "*.{java,class}")) {
      for (Path each : stream) {
        String fileName = each.getFileName().toString();
        int smallestSuffixIndex = Integer.MAX_VALUE;
        for (String suffix : this.suffixes) {
          int suffixIndex = fileName.indexOf(suffix);
          if (suffixIndex != -1 && suffixIndex < smallestSuffixIndex) {
            smallestSuffixIndex = suffixIndex;
          }
        }
        if (smallestSuffixIndex < Integer.MAX_VALUE) {
          metaData.add(fileName.substring(0, smallestSuffixIndex));
        }
      }
    }
    return metaData;
  }

}
