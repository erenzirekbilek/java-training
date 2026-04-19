package com.training.client.binary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Service
public class BinaryDataService {
    private static final Logger logger = LoggerFactory.getLogger(BinaryDataService.class);

    public byte[] compressData(byte[] data) throws IOException {
        logger.info("Compressing data: {} bytes", data.length);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(bos)) {
            gzip.write(data);
            gzip.finish();
        }
        return bos.toByteArray();
    }

    public byte[] decompressData(byte[] compressed) throws IOException {
        logger.info("Decompressing data: {} bytes", compressed.length);
        ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
        try (GZIPInputStream gzip = new GZIPInputStream(bis);
             BufferedInputStream reader = new BufferedInputStream(gzip)) {
            return reader.readAllBytes();
        }
    }

    public byte[] serializeObject(Object obj) throws IOException {
        logger.info("Serializing object: {}", obj.getClass().getName());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
        }
        return bos.toByteArray();
    }

    public Object deserializeObject(byte[] data) throws IOException, ClassNotFoundException {
        logger.info("Deserializing object");
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        try (ObjectInputStream ois = new ObjectInputStream(bis)) {
            return ois.readObject();
        }
    }
}