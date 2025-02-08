package ind.venture.remindercore;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class NotionClient {

    private final WebClient webClient;

    public NotionClient(WebClient webClient) {
        this.webClient = webClient;
    }
}
