package ai.exemplar.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.google.common.util.concurrent.ListeningExecutorService;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class ListS3CSVContent<SchemaClass> {

    static final Logger log = Logger.getLogger(ListS3CSVContent.class);

    private final AmazonS3 s3;

    private final String contentBucket;

    private final String contentFileName;

    private final Class<SchemaClass> contentClass;

    public ListS3CSVContent(
            ListeningExecutorService executor, AmazonS3 s3,
            String contentBucket, String contentFileName,
            Class<SchemaClass> contentClass
    ) {
        this.s3 = s3;
        this.contentBucket = contentBucket;
        this.contentFileName = contentFileName;
        this.contentClass = contentClass;
    }

    public List<SchemaClass> get() {
        try {
            ObjectReader reader = new CsvMapper()
                    .readerWithSchemaFor(contentClass);

            S3Object s3Object = s3.getObject(contentBucket, contentFileName);

            MappingIterator<SchemaClass> iterator = reader
                    .readValues(s3Object.getObjectContent());

            return iterator.readAll();
        } catch (IOException e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }
}
