package me.jayna.web.rest;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Minimaler Stub für Overview/KPIs, wird nur geladen wenn feature.overview.enabled=true
 */
@RestController
@RequestMapping("/api/overview")
@ConditionalOnProperty(prefix = "feature.overview", name = "enabled", havingValue = "true", matchIfMissing = false)
public class OverviewResource {

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("overview-enabled");
    }

    @GetMapping(value = "/kpis", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KpisDTO> kpis() {
        KpisDTO dto = new KpisDTO();
        dto.contracts = 124;
        dto.documents = 3421;
        dto.upcomingAppointments = 7;
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/cashflow-trend", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CashflowTrendDTO> cashflowTrend() {
        CashflowTrendDTO dto = new CashflowTrendDTO();
        dto.values = new int[]{10,20,15,30,18,40,22,35,28,32,26,38};
        return ResponseEntity.ok(dto);
    }

    public static class KpisDTO {
        public int contracts;
        public int documents;
        public int upcomingAppointments;
    }

    public static class CashflowTrendDTO {
        public int[] values;
    }
}


