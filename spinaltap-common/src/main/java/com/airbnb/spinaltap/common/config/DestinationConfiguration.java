/**
 * Copyright 2018 Airbnb. Licensed under Apache-2.0. See License in the project root for license
 * information.
 */
package com.airbnb.spinaltap.common.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DestinationConfiguration {
  public static final int DEFAULT_BUFFER_SIZE = 0;
  public static final int DEFAULT_POOL_SIZE = 0;

  @Min(0)
  @JsonProperty("buffer_size")
  private int bufferSize = DEFAULT_BUFFER_SIZE;

  @Min(0)
  @JsonProperty("pool_size")
  private int poolSize = DEFAULT_POOL_SIZE;
}
