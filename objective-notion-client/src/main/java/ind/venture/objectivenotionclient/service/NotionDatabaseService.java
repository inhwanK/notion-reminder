package ind.venture.objectivenotionclient.service;


import ind.venture.objectivenotion.model.database.Database;
import ind.venture.objectivenotion.model.database.DatabaseProperty;
import ind.venture.objectivenotion.model.page.Page;
import ind.venture.objectivenotionclient.client.NotionClient;
import ind.venture.objectivenotionclient.util.DatabaseRequestFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class NotionDatabaseService {
    private final NotionClient notionClient;

    public NotionDatabaseService(NotionClient notionClient) {
        this.notionClient = notionClient;
    }

    public Mono<Database> getDatabaseInfo(String apiKey, String databaseId) {
        return notionClient.fetchDatabase(apiKey, databaseId)
                .doOnNext(db -> log.info("[노션 데이터베이스 정보 요청] {}", db.toString()))
                .doOnError(error -> log.error("[노션 API 에러] {}\n {}", error, error.getMessage()));
    }

    public Mono<Boolean> checkIsReminderDatabase(String apiKey, String databaseId) {
        return notionClient.fetchDatabase(apiKey, databaseId)
                .doOnSubscribe(s -> log.info("[리마인더 확인 요청] databaseId: {}", databaseId))
                .map(this::hasReminderProperty)
                .doOnNext(result -> log.info("[리마인더 확인 결과] databaseId: {}, hasReminder: {}", databaseId, result))
                .doOnError(e -> log.error("[리마인더 요청 실패] databaseId: {}, error: {}", databaseId, e.getMessage()));
    }

    public Mono<List<Page>> findAllReminderPage(String apiKey, String databaseId) {
        return notionClient.queryDatabase(apiKey, databaseId, DatabaseRequestFactory.createNotEmptyReminderRequest())
                .doOnNext(pages -> {
                    log.info("[리마인더 페이지 조회 결과] 총 {}건", pages.size());
                    pages.forEach(page -> log.info("{}", page));
                });
    }

    public Mono<List<Page>> findWeeklyReminderPage(String apiKey, String databaseId) {
        return notionClient.queryDatabase(apiKey, databaseId, DatabaseRequestFactory.createWeeklyReminderRequest());
    }

    public Mono<List<Page>> findTodayReminderPage(String apiKey, String databaseId) {
        return notionClient.queryDatabase(apiKey, databaseId, DatabaseRequestFactory.createTodayReminderRequest());
    }

    private boolean hasReminderProperty(Database database) {
        DatabaseProperty reminderProperty = database.getProperties().get("리마인더");
        return reminderProperty != null && "date".equals(reminderProperty.getType());
    }
}
