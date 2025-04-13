package ru.yandex.practicum.catsgram.exception;

import lombok.Getter;

@Getter
public class ParameterNotValidException extends IllegalArgumentException {
  private final String parameter;
  private final String reason;

  public ParameterNotValidException(String parameter, String reason) {
    super("Invalid parameter: " + parameter + ". Reason: " + reason);
    this.parameter = parameter;
    this.reason = reason;
  }
}