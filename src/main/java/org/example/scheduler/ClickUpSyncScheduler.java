package org.example.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClickUpSyncScheduler {

    /**
     * Her 60.000 ms (1 dakika) çalışır.
     * fixedRate: Metot bittikten sonra değil, başlama zamanından itibaren süreyi sayar.
     */
    @Scheduled(fixedRate = 60000)
    public void runPeriodicSync() {
        log.info("Zamanlanmış ClickUp senkronizasyonu başlatıldı.");
    }
}
