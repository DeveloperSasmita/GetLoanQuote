package com.getquote.util;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.List;


@Slf4j
public class CSVUtility {

    //@Cacheable("csvFile")
    public static <T> List<T> loadCSVFile(String filepath, Class<T> clazz) throws IOException {

        log.info("Loading file from ------------" + filepath);

        Resource resource = new ClassPathResource("/" + filepath);
        InputStream inputStream = resource.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();

        ms.setType(clazz);

        CsvToBean csvBean = new CsvToBeanBuilder(reader)
                .withType(clazz)
                .withMappingStrategy(ms)
                .withSkipLines(1)
                .build();

        return csvBean.parse();

    }

}
