package com.novo.microservices.components.helpers;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvWriterTest {

    @TempDir
    Path tmpDir;

    @Test
    void writeCsv() throws IOException {
        var csvSchema = CsvSchema.builder()
            .addColumn("bankCode")
            .addColumn("messageType")
            .build();

        String bankCode = "0066";
        String messageType = "123456";
        var entity = new CsvEntity(bankCode, messageType);

        final Path csvFile = Files.createFile(tmpDir.resolve("test.csv"));

        var csvWriter = new CsvWriter();
        Files.write(csvFile, csvWriter.writeObjects(List.of(entity), csvSchema.withHeader()), StandardOpenOption.APPEND);
        Files.write(csvFile, csvWriter.writeObjects(List.of(entity), csvSchema.withoutHeader()), StandardOpenOption.APPEND);

        var lines = Files.readAllLines(csvFile);
        assertEquals(3, lines.size());
        // header
        assertEquals("bankCode,messageType", lines.get(0));
        // body
        assertEquals(bankCode + "," + messageType, lines.get(1));
        assertEquals(bankCode + "," + messageType, lines.get(2));
    }

    static class CsvEntity {
        private final String bankCode;
        private final String messageType;

        public CsvEntity(String bankCode, String messageType) {
            this.bankCode = bankCode;
            this.messageType = messageType;
        }

        public String getBankCode() {
            return bankCode;
        }

        public String getMessageType() {
            return messageType;
        }
    }
}