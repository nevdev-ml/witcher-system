package com.nevdev.witcher.repository;

import org.testcontainers.containers.PostgreSQLContainer;

public class CustomPostgresqlContainer extends PostgreSQLContainer<CustomPostgresqlContainer> {

    private static final String IMAGE_VERSION = "postgres:13.1";

    private static CustomPostgresqlContainer container;


    private CustomPostgresqlContainer() {
        super(IMAGE_VERSION);
    }

    static CustomPostgresqlContainer getInstance() {
        if (container == null) {
            container = new CustomPostgresqlContainer().withInitScript("init.sql");
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {}
}
