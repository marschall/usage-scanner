package com.github.marschall.usagescanner;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.TERMINATE;
import static java.nio.file.FileVisitResult.SKIP_SUBTREE;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

class UsageCollector {

  private Set<String> unusedMetaData;

  private Set<String> usedMetaData;

  private static final Set<String> IGNORED_FOLDERS = new HashSet<>(Arrays.asList("target", "resources", ".settings"));

  UsageCollector(Set<String> metaData) {
    this.unusedMetaData = metaData;
    this.usedMetaData = new HashSet<>();
  }

  Set<String> scanFolders(Collection<Path> folders) throws IOException {
    for (Path folder : folders) {
      this.scanFolder(folder);
    }
    return Collections.unmodifiableSet(this.unusedMetaData);
  }

  void scanFolder(Path folder) throws IOException {
    Files.walkFileTree(folder, new SimpleFileVisitor<Path>(){

      @Override
      public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        String fileName = dir.getFileName().toString();
        return IGNORED_FOLDERS.contains(fileName) ? SKIP_SUBTREE : CONTINUE;
      }

      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (!file.getFileName().toString().endsWith(".java")) {
          return CONTINUE;
        }
        scanFile(file);
        return unusedMetaData.isEmpty() ? TERMINATE : CONTINUE;
      }
    });
  }


  void scanFile(Path file) throws IOException {
    try (BufferedReader reader = Files.newBufferedReader(file)) {
      String line = reader.readLine();
      while (line != null) {
        Iterator<String> iterator = unusedMetaData.iterator();
        while (iterator.hasNext()) {
          String metaData = iterator.next();
          if (line.contains(metaData)) {
            iterator.remove();
            usedMetaData.add(line);
          }
        }
        if (unusedMetaData.isEmpty()) {
          return;
        }
        line = reader.readLine();
      }
    }

  }

}
