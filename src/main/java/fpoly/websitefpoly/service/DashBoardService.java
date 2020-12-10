package fpoly.websitefpoly.service;

public interface DashBoardService {

    long countInvoiceByStatus(String status);

    long countInvoiceNew();
}
