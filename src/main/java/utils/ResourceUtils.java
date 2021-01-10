package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ResourceUtils {

    public ArrayList<String> tryAndGetFilesFromZip(String searchTerm) {
        CodeSource src = ResourceUtils.class.getProtectionDomain().getCodeSource();
        ArrayList<String> fileName = new ArrayList<>();
        if (src != null) {
            URL jar = src.getLocation();
            ZipInputStream zip = null;
            try {
                zip = new ZipInputStream(jar.openStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (true) {
                ZipEntry e = null;
                try {
                    e = zip.getNextEntry();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                if (e == null)
                    break;
                String name = e.getName();
                if (name.contains(searchTerm)) {
                    String specific = name.replace(searchTerm, "").replace("/", "");
                    if (!specific.isEmpty()) {
                        fileName.add(specific);
                    }
                }
            }
        }
        return fileName;
    }

    public List<String> getResourceFiles(String path) throws IOException {
        List<String> filenames = new ArrayList<>();

        InputStream in = getResourceAsStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String resource;

        while ((resource = br.readLine()) != null) {
            filenames.add(resource);
        }

        if (filenames.isEmpty()) {
            filenames = tryAndGetFilesFromZip(path);
        }

        return filenames;
    }

    private InputStream getResourceAsStream(String resource) {
        final InputStream in
                = getContextClassLoader().getResourceAsStream(resource);

        return in == null ? getClass().getResourceAsStream(resource) : in;
    }

    private ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
