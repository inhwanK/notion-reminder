package ind.venture.objectivenotion.controller;

import ind.venture.objectivenotion.domain.Database;
import ind.venture.objectivenotion.service.NotionDatabaseService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class NotionDatabaseController {

    private final NotionDatabaseService notionDatabaseService;

    public NotionDatabaseController(NotionDatabaseService notionDatabaseService) {
        this.notionDatabaseService = notionDatabaseService;
    }

    @GetMapping("/{databaseId}/info")
    public Mono<Database> getDatabaseInfo(
            @RequestHeader("Authorization") String apiKey,
            @PathVariable("databaseId") String databaseId
    ) {
        return notionDatabaseService.getDatabaseInfo(apiKey, databaseId);
    }

    @GetMapping("/{databaseId}/check")
    public Mono<Boolean> checkReminderDatabase(
            @RequestHeader("Authorization") String apiKey,
            @PathVariable("databaseId") String databaseId
    ) {
        return notionDatabaseService.checkHasReminderProperty(apiKey, databaseId);
    }
}
