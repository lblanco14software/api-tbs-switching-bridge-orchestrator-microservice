package com.novo.microservices.components.helpers;

import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;

/**
 * CsvWriter
 * <p>
 * CsvWriter class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author duhernandez@novopayment.com
 * @since 24/5/22
 */
@Component
public class CsvWriter {
    private static final String CSV_FILTER_NAME = "csvFilter";
    private static final String CSV_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public byte[] writeObjects(List<?> objects, CsvSchema csvSchema) throws IOException {
        HashSet<String> columnNames = new HashSet<>();
        for (CsvSchema.Column column : csvSchema) {
            columnNames.add(column.getName());
        }

        var csvResponseFilter = new SimpleBeanPropertyFilter.FilterExceptFilter(columnNames);
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter(CSV_FILTER_NAME, csvResponseFilter);

        var csvMapper = new CsvMapper();
        csvMapper.setFilterProvider(filterProvider);
        csvMapper.setAnnotationIntrospector(new CsvAnnotationIntrospect());
        csvMapper.setDateFormat(new SimpleDateFormat(CSV_DATE_FORMAT));

        var objectWriter = csvMapper.writer(csvSchema);
        return objectWriter.writeValueAsBytes(objects);
    }

    private static class CsvAnnotationIntrospect extends JacksonAnnotationIntrospector {
        private static final long serialVersionUID = 8098270982709827098L;

        @Override
        public Object findFilterId(Annotated a) {
            return CSV_FILTER_NAME;
        }
    }
}