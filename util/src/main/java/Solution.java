import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;

public class Solution {
    private int year;
    private int day;

    public Solution(int year, int day) {
        this.year = year;
        this.day = day;
    }

    public List<String> readInput(String fileName) {
        List<String> fullFileString = new ArrayList<>();
        try (var reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fullFileString.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File could was not found");
        } catch (IOException e) {
            throw new RuntimeException("Problem has occurred when reading file");
        }


        return fullFileString;

    }



}

