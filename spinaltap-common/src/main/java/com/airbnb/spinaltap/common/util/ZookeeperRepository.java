/**
 * Copyright 2018 Airbnb. Licensed under Apache-2.0. See License in the project root for license
 * information.
 */
package com.airbnb.spinaltap.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import lombok.RequiredArgsConstructor;
import org.apache.curator.framework.CuratorFramework;

@RequiredArgsConstructor
public class ZookeeperRepository<T> implements Repository<T> {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private final CuratorFramework zkClient;
  private final String path;
  private final TypeReference<? extends T> propertyClass;

  static {
    OBJECT_MAPPER.registerModule(new JodaModule());
  }

  @Override
  public boolean exists() throws Exception {
    return zkClient.checkExists().forPath(path) != null;
  }

  @Override
  public void create(T data) throws Exception {
    zkClient
        .create()
        .creatingParentsIfNeeded()
        .forPath(path, OBJECT_MAPPER.writeValueAsBytes(data));
  }

  @Override
  public void set(T data) throws Exception {
    zkClient.setData().forPath(path, OBJECT_MAPPER.writeValueAsBytes(data));
  }

  @Override
  public void update(T data, DataUpdater<T> updater) throws Exception {
    if (exists()) {
      set(updater.apply(get(), data));
    } else {
      create(data);
    }
  }

  @Override
  public T get() throws Exception {
    byte[] value = zkClient.getData().forPath(path);
    return OBJECT_MAPPER.readValue(value, propertyClass);
  }
}
