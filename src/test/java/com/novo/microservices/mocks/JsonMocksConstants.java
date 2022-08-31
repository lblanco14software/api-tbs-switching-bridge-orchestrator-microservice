package com.novo.microservices.mocks;

public class JsonMocksConstants {

    public static final String MOCK_QUALIFIER_DEFAULT = "mocking";
    public static final String MOCK_COMPONENT_ID = "123e4567-e89b-12d3-a456-556642440271";
    public static final String MOCK_TENANT_ID = "mock-tenant-id";
    public static final String MOCK_SERVER_IP = "127.0.0.0";
    public static final int MOCK_SERVER_PORT = 2004;
    public static final String MOCK_CONFIGURATION_CONTEXT = "/configurations/v1/components/" + MOCK_COMPONENT_ID + "/tenants/" + MOCK_TENANT_ID + "/configurations";

    public static final String JSON_STANDARD_CONFIGURATION = "{\n" +
        "    \"code\": \"200.00.000\",\n" +
        "    \"datetime\": \"2021-12-20T17:25:05.155Z[UTC]\",\n" +
        "    \"message\": \"Process Ok\",\n" +
        "    \"data\": {\n" +
        "        \"configEntity\": {\n" +
        "            \"certificates\": [],\n" +
        "            \"datasources\": [\n" +
        "                {\n" +
        "                    \"driverClassName\": \"org.h2.Driver\",\n" +
        "                    \"name\": \"microservice.datasource.name\",\n" +
        "                    \"url\": \"jdbc:h2:mem:db;DB_CLOSE_DELAY=-1\",\n" +
        "                    \"username\": \"sa\",\n" +
        "                    \"password\": \"sa\"\n" +
        "                }\n" +
        "            ],\n" +
        "            \"integrationPaths\": [                \n" +
        "            ],\n" +
        "            \"params\": [\n" +
        "                {\n" +
        "                    \"name\": \"api.app.datasource.hikari.pool-name\",\n" +
        "                    \"value\": \"grupogenteAuditPool\"\n" +
        "                },\n" +
        "                {\n" +
        "                    \"name\": \"api.app.datasource.hikari.set-connection-test-query\",\n" +
        "                    \"value\": \"SELECT 1 FROM DUAL\"\n" +
        "                },\n" +
        "                {\n" +
        "                    \"name\": \"api.app.datasource.hikari.maximum-pool-size\",\n" +
        "                    \"value\": \"10\"\n" +
        "                },\n" +
        "                {\n" +
        "                    \"name\": \"api.app.datasource.hikari.set-minimum-idle\",\n" +
        "                    \"value\": \"2\"\n" +
        "                },\n" +
        "                {\n" +
        "                    \"name\": \"api.app.datasource.hikari.max-lifetime\",\n" +
        "                    \"value\": \"60000\"\n" +
        "                },\n" +
        "                {\n" +
        "                    \"name\": \"api.app.datasource.hikari.idle-timeout\",\n" +
        "                    \"value\": \"45000\"\n" +
        "                },\n" +
        "                {\n" +
        "                    \"name\": \"api.app.datasource.hikari.validation-timeout\",\n" +
        "                    \"value\": \"1900\"\n" +
        "                },\n" +
        "                {\n" +
        "                    \"name\": \"is.cvv2.requeired\",\n" +
        "                    \"value\": \"TRUE\"\n" +
        "                }\n" +
        "            ]\n" +
        "        },\n" +
        "        \"configInfo\": {\n" +
        "            \"description\": \"MOCK COMPONENT\",\n" +
        "            \"encrypted\": \"INACTIVE\",\n" +
        "            \"entityId\": \"123e4567-e89b-12d3-a456-556642440271\",\n" +
        "            \"name\": \"MOCK COMPONENT\",\n" +
        "            \"status\": \"ACTIVE\",\n" +
        "            \"version\": {\n" +
        "                \"description\": \"VERSION OFF MOCK COMPONENT\",\n" +
        "                \"name\": \"ALFA\",\n" +
        "                \"release\": \"1.0.0\"\n" +
        "            }\n" +
        "        },\n" +
        "        \"configType\": \"COMPONENT\",\n" +
        "        \"tenantInfo\": {\n" +
        "            \"clientId\": \"mock-tenant-id\",\n" +
        "            \"name\": \"MOCK TENANT\",\n" +
        "            \"tenantId\": \"2d658d28-a8cb-4cc8-bdfc-3a6f50162b12\",\n" +
        "            \"type\": \"BANK\"\n" +
        "        }\n" +
        "    }\n" +
        "}";
}
