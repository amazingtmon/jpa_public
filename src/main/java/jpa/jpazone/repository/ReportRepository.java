package jpa.jpazone.repository;

import jpa.jpazone.domain.Report;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ReportRepository {

    private final EntityManager em;


    public void saveReport(Report report) {
        log.info("[[ Repo - saveReport ]]");

        em.persist(report);
    }

    public int findAllReportsCount(){
        log.info("[[ Repo - findAllReportsCount ]]");
        Long result = em.createQuery("select count(*) from Report r", Long.class)
                .getSingleResult();
        return Math.toIntExact(result);
    }

    public List<Report> findAllReports() {
        log.info("[[ Repo - findAllReports ]]");
        return em.createQuery("select r from Report r join fetch r.member m", Report.class)
                .getResultList();
    }
}
