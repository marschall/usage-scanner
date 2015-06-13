package com.github.marschall.usagescanner;

import java.nio.file.FileSystem;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import com.github.marschall.memoryfilesystem.MemoryFileSystemBuilder;

final class FileSystemRule implements TestRule {

  private FileSystem fileSystem;

  FileSystem getFileSystem() {
    return this.fileSystem;
  }

  @Override
  public Statement apply(final Statement base, Description description) {
    return new Statement() {

      @Override
      public void evaluate() throws Throwable {
        fileSystem = MemoryFileSystemBuilder.newEmpty().build("name");
        try {
          base.evaluate();
        } finally {
          fileSystem.close();
        }
      }

    };
  }

}