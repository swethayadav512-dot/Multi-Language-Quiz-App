import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(
            new InetSocketAddress(8080), 0);

        server.createContext("/", Main::handleRequest);

        server.start();

        System.out.println("Quiz Application Started!");
        System.out.println("Open http://localhost:8080");
    }

    private static Map<String, String> getParams(String query)
            throws UnsupportedEncodingException {

        Map<String, String> params = new HashMap<>();

        if (query != null) {
            for (String pair : query.split("&")) {

                String[] parts = pair.split("=", 2);

                if (parts.length == 2) {
                    params.put(
                        URLDecoder.decode(parts[0], "UTF-8"),
                        URLDecoder.decode(parts[1], "UTF-8")
                    );
                }
            }
        }

        return params;
    }

    private static void handleRequest(HttpExchange exchange)
            throws IOException {

        String path = exchange.getRequestURI().getPath();

        if (path.equals("/api/questions")) {

            Map<String, String> params =
                getParams(exchange.getRequestURI().getRawQuery());

            String language = params.getOrDefault("language", "Java");

            Quiz quiz = new Quiz(language);

            sendResponse(exchange, quiz.getQuestionsJson(),
                "application/json");

        } else if (path.equals("/api/submit")) {

            Map<String, String> params =
                getParams(exchange.getRequestURI().getRawQuery());

            String language = params.getOrDefault("language", "Java");

            Quiz quiz = new Quiz(language);

            String answerData =
                params.getOrDefault("answers", "");

            String[] answers = answerData.isEmpty()
                ? new String[0] : answerData.split(",", -1);

            int score = quiz.calculateScore(answers);

            int total = quiz.getQuestions().length;

            double percentage = (score * 100.0) / total;

            String result = "{\"score\":" + score
                + ",\"total\":" + total
                + ",\"percentage\":" + percentage
                + ",\"passed\":" + (percentage >= 50) + "}";

            sendResponse(exchange, result, "application/json");

        } else {

            String file = path.equals("/")
                ? "index.html" : path.substring(1);

            if (!file.equals("index.html")
                    && !file.equals("style.css")
                    && !file.equals("script.js")) {

                sendResponse(exchange, "Not Found", "text/plain");
                return;
            }

            Path filePath = Paths.get("web", file);

            if (!Files.exists(filePath)) {
                sendResponse(exchange, "File Not Found",
                    "text/plain");
                return;
            }

            String content = Files.readString(filePath);

            String type = file.endsWith(".css") ? "text/css"
                : file.endsWith(".js") ? "text/javascript"
                : "text/html";

            sendResponse(exchange, content, type);
        }
    }

    private static void sendResponse(
            HttpExchange exchange,
            String response,
            String type) throws IOException {

        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set(
            "Content-Type", type + "; charset=UTF-8");

        exchange.sendResponseHeaders(200, bytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}