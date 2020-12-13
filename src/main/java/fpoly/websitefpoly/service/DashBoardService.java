package fpoly.websitefpoly.service;

import fpoly.websitefpoly.dto.ChartDto;

import java.text.ParseException;

public interface DashBoardService {

    long countInvoiceByStatus(String status);

    long countInvoiceNew();

    ChartDto chart(String type, int moth, int year) throws ParseException;
}
