package io.github.westonal.giphyapi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

final class ResourceLoader {

    private ResourceLoader() {
    }

    static String resource(final String s) {
        final InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(s);
        if (inputStream == null) {
            throw new RuntimeException("Did not find resource: " + s);
        }
        try {
            return streamToString(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String streamToString(InputStream inputStream) throws IOException {
        final ByteArrayOutputStream result = new ByteArrayOutputStream();
        final byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString(StandardCharsets.UTF_8.name());
    }
}
