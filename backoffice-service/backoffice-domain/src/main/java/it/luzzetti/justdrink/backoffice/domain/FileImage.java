package it.luzzetti.justdrink.backoffice.domain;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record FileImage(@NonNull String name, @NonNull String url) {

}
