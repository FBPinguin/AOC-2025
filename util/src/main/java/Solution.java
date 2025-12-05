import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;

public class Solution {
    private int year;
    private int day;
    private String cookie;

    public Solution(int year, int day, String cookie) {
        this.year = year;
        this.day = day;
    }

    public String getInput() {
        return "";

    }

    public static void main(String[] args) {
        var solution = new Solution(2025, 2,
                                    "");
    }


}

