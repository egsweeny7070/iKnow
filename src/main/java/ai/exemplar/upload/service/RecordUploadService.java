package ai.exemplar.upload.service;

import java.nio.ByteBuffer;
import java.util.List;

public interface RecordUploadService {

    void upload(List<ByteBuffer> records);
}
