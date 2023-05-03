package it.luzzetti.justdrink.backoffice.infrastructure.output.filesystem.adapters.shared;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Directory {

  public static void init(Path path) {

    if (Files.exists(path)) {
      return;
    }
    try {
      Files.createDirectories(path);
    } catch (IOException e) {
      throw new RuntimeException("Impossibile creare la directory");
    }
  }

}
