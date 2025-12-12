import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeleteAllData {
    
    private static final String BASE_URL = "http://localhost:8080/api";
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);
    
    // Liste aller Entitäten, die gelöscht werden sollen
    private static final List<String> ENTITIES = List.of(
        "contracts",
        "contacts", 
        "finance-accounts",
        "mobilities",
        "real-estates"
    );
    
    public static void main(String[] args) {
        System.out.println("Starte Löschvorgang für alle Datensätze...");
        
        try {
            // Lösche alle Datensätze für jede Entität
            for (String entity : ENTITIES) {
                deleteAllRecordsForEntity(entity);
            }
            
            System.out.println("Alle Datensätze wurden erfolgreich gelöscht!");
            
        } catch (Exception e) {
            System.err.println("Fehler beim Löschen der Daten: " + e.getMessage());
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
    
    private static void deleteAllRecordsForEntity(String entity) {
        System.out.println("Lösche alle Datensätze für Entität: " + entity);
        
        try {
            // 1. Hole alle Datensätze für diese Entität
            String getAllUrl = BASE_URL + "/" + entity + "?size=1000&sort=id,desc";
            HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create(getAllUrl))
                .header("Accept", "application/json")
                .GET()
                .build();
            
            HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                // Parse die Antwort um die IDs zu extrahieren
                String responseBody = response.body();
                List<Long> ids = extractIdsFromResponse(responseBody);
                
                System.out.println("Gefunden " + ids.size() + " Datensätze für " + entity);
                
                // 2. Lösche jeden Datensatz parallel
                List<CompletableFuture<Void>> deleteFutures = new ArrayList<>();
                
                for (Long id : ids) {
                    CompletableFuture<Void> deleteFuture = CompletableFuture.runAsync(() -> {
                        deleteRecord(entity, id);
                    }, executor);
                    
                    deleteFutures.add(deleteFuture);
                }
                
                // Warte auf alle Löschvorgänge
                CompletableFuture.allOf(deleteFutures.toArray(new CompletableFuture[0])).join();
                
                System.out.println("Alle " + ids.size() + " Datensätze für " + entity + " wurden gelöscht.");
                
            } else {
                System.err.println("Fehler beim Abrufen der Datensätze für " + entity + ": " + response.statusCode());
            }
            
        } catch (Exception e) {
            System.err.println("Fehler beim Löschen der Datensätze für " + entity + ": " + e.getMessage());
        }
    }
    
    private static void deleteRecord(String entity, Long id) {
        try {
            String deleteUrl = BASE_URL + "/" + entity + "/" + id;
            HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create(deleteUrl))
                .DELETE()
                .build();
            
            HttpResponse<String> response = httpClient.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 204) {
                System.out.println("Datensatz " + id + " für " + entity + " erfolgreich gelöscht");
            } else {
                System.err.println("Fehler beim Löschen von Datensatz " + id + " für " + entity + ": " + response.statusCode());
            }
            
        } catch (Exception e) {
            System.err.println("Fehler beim Löschen von Datensatz " + id + " für " + entity + ": " + e.getMessage());
        }
    }
    
    private static List<Long> extractIdsFromResponse(String responseBody) {
        List<Long> ids = new ArrayList<>();
        
        // Einfache Extraktion der IDs aus der JSON-Antwort
        // Dies ist eine vereinfachte Implementierung - in der Praxis würde man einen JSON-Parser verwenden
        String[] lines = responseBody.split("\n");
        for (String line : lines) {
            if (line.contains("\"id\":")) {
                String idPart = line.trim();
                if (idPart.startsWith("\"id\":")) {
                    String idStr = idPart.substring(5).trim();
                    if (idStr.endsWith(",")) {
                        idStr = idStr.substring(0, idStr.length() - 1);
                    }
                    try {
                        Long id = Long.parseLong(idStr);
                        ids.add(id);
                    } catch (NumberFormatException e) {
                        // Ignoriere ungültige IDs
                    }
                }
            }
        }
        
        return ids;
    }
} 