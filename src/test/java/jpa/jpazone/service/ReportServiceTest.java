package jpa.jpazone.service;

import jpa.jpazone.domain.enumpackage.ReportItem;
import jpa.jpazone.domain.enumpackage.ReportReason;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReportServiceTest {


    @Test
    public void 신고이유테스트() throws Exception {
        // given
        ReportReason result = ReportReason.valueOf("ADULT");
        ReportReason[] values = ReportReason.values();

        ReportItem board = ReportItem.valueOf("BOARD");

        // when
        System.out.println("result = "+result+", "+result.getClass());
        System.out.println("ReportItem = "+board+", "+board.getClass());

        // then
    }
}