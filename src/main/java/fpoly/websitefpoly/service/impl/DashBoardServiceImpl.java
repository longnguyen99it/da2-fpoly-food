package fpoly.websitefpoly.service.impl;

import fpoly.websitefpoly.dto.ChartDto;
import fpoly.websitefpoly.entity.Invoice;
import fpoly.websitefpoly.repository.InvoiceRepository;
import fpoly.websitefpoly.service.DashBoardService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class DashBoardServiceImpl implements DashBoardService {
    private final InvoiceRepository invoiceRepository;
    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;

    public DashBoardServiceImpl(InvoiceRepository invoiceRepository, EntityManagerFactory entityManagerFactory, EntityManager entityManager) {
        this.invoiceRepository = invoiceRepository;
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = entityManager;
    }

    @Override
    public long countInvoiceNew() {
        long count1 = invoiceRepository.countAllByStatus(Invoice.NEW);
        long count2 = invoiceRepository.countAllByStatus(Invoice.WATCHED);
        return count1 + count2;
    }

    @Override
    public ChartDto chart(String type, int moth, int year) throws ParseException {
        if (type.equals("month")) {
            return chartMonth(moth, year);
        }
        return null;
    }


    public ChartDto chartMonth(int month, int year) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
        int day = 0;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 10 || month == 12) {
            day = 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            day = 30;
        }
        if (month == 2) {
            if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
                day = 29;
            } else {
                day = 28;
            }
        }
        String key[] = new String[day];
        Double value[] = new Double[day];
        for (int i = 0; i < day; i++) {
            key[i] = "Ngày : " + (i + 1);
            String startDate = "2020-12-" + (i + 1) + " 00:00:00";
            String endDate = "2020-12-" + (i + 1) + " 23:59:59";
            String sql = "select sum(inv.amountTotal) from Invoice inv where inv.createdAt " +
                    "between '" + startDate + "' and '" + endDate + "'";
            TypedQuery<Double> doubleTypedQuery = (TypedQuery<Double>) entityManager.createQuery(sql);
            value[i] = (doubleTypedQuery.getSingleResult() == null) ? 0D : doubleTypedQuery.getSingleResult();
        }
        ChartDto chartDto = new ChartDto(key, value);
        return chartDto;
    }

    @Override
    public long countInvoiceByStatus(String status) {
        if (status.equals(Invoice.NEW) || status.equals(Invoice.WATCHED)) {
            return countInvoiceNew();
        }
        return invoiceRepository.countAllByStatus(status);
    }
}
