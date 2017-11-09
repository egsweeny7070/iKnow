package ai.exemplar.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class LocallyCachedS3CSVContent<SchemaClass> {

    static final Logger log = Logger.getLogger(LocallyCachedS3CSVContent.class);

    private final ListeningExecutorService executor;

    private final AmazonS3 s3;

    private final String contentBucket;

    private final String contentFileName;

    private final Class<SchemaClass> contentClass;

    private volatile List<SchemaClass> content;

    private volatile LocalDateTime lastModifiedTime;

    private volatile LocalDateTime lastCheckTime;

    public LocallyCachedS3CSVContent(
            ListeningExecutorService executor, AmazonS3 s3,
            String contentBucket, String contentFileName,
            Class<SchemaClass> contentClass
    ) {
        this.executor = executor;
        this.s3 = s3;
        this.contentBucket = contentBucket;
        this.contentFileName = contentFileName;
        this.contentClass = contentClass;
    }

    private LocalDateTime lastModified() {
        return LocalDateTime.ofInstant(
                s3.getObjectMetadata(contentBucket, contentFileName).getLastModified().toInstant(),
                ZoneId.systemDefault()
        );
    }

    private Map.Entry<LocalDateTime, List<SchemaClass>> load() {
        try {
            log.info(String.format("loading %s file content", contentFileName));

            ObjectReader reader = new CsvMapper()
                    .readerWithSchemaFor(contentClass);

            S3Object s3Object = s3.getObject(contentBucket, contentFileName);

            MappingIterator<SchemaClass> iterator = reader
                    .readValues(s3Object.getObjectContent());

            return new AbstractMap.SimpleImmutableEntry<>(
                    LocalDateTime.ofInstant(s3Object.getObjectMetadata()
                            .getLastModified().toInstant(), ZoneId.systemDefault()),
                    iterator.readAll()
            );

        } catch (IOException e) {
            log.error(String.format("%s file load error", contentFileName), e);
            throw new RuntimeException(e);
        }
    }

    private List<SchemaClass> checkAndGet() {
        lastCheckTime = LocalDateTime.now();

        if (lastModifiedTime.isBefore(lastModified())) {
            log.debug(String.format("%s file content update detected", contentFileName));

            return loadAndGet();
        }

        return content;
    }

    private List<SchemaClass> loadAndGet() {
        lastCheckTime = LocalDateTime.now();

        Map.Entry<LocalDateTime, List<SchemaClass>> loadedContent = load();

        lastModifiedTime = loadedContent.getKey();
        content = loadedContent.getValue();

        return content;
    }

    public ListenableFuture<LocalDateTime> contentLastUpdated() {
        if (lastModifiedTime == null) {
            return executor.submit(this::lastModified);
        }

        return Futures.immediateFuture(this.lastModifiedTime);
    }

    public ListenableFuture<List<SchemaClass>> get() {
        if (content == null) {
            return executor.submit(this::loadAndGet);
        }

        if (lastCheckTime.isBefore(LocalDateTime.now().minus(1L, ChronoUnit.HOURS))) {
            return executor.submit(this::checkAndGet);
        }

        return Futures.immediateFuture(this.content);
    }
}
