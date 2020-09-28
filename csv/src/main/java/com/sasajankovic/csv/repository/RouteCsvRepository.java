package com.sasajankovic.csv.repository;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180ParserBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.sasajankovic.csv.entities.RouteCsv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Component
public class RouteCsvRepository {
    @Value("${spring.data.source.csv.routes}")
    private String filePath;

    public List<RouteCsv> load() throws IOException {
        try (Reader reader =
                new InputStreamReader(new ClassPathResource(filePath).getInputStream())) {
            CSVReader csvReader =
                    new CSVReaderBuilder(reader)
                            .withCSVParser(new RFC4180ParserBuilder().build())
                            .build();
            CsvToBean<RouteCsv> routeParser =
                    new CsvToBeanBuilder(reader).withType(RouteCsv.class).build();
            routeParser.setCsvReader(csvReader);
            return routeParser.parse();
        }
    }
}
