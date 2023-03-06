package jpa.jpazone.repository;

import jpa.jpazone.domain.Report;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ReportRepository {

    private final EntityManager em;


    public void saveReportByBoard(Report report) {
        log.info("[[ Repo - saveReportByBoard ]]");

        em.persist(report);
    }

    public void saveReportByComment(Report report) {
        log.info("[[ Repo - saveReportByComment ]]");

        em.persist(report);
    }
}
