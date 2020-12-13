package fpoly.websitefpoly.service.impl;

import fpoly.websitefpoly.dto.ChartDto;
import fpoly.websitefpoly.entity.Invoice;
import fpoly.websitefpoly.repository.InvoiceRepository;
import fpoly.websitefpoly.service.DashBoardService;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DashBoardServiceImpl implements DashBoardService {
    private final InvoiceRepository invoiceRepository;

    public DashBoardServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
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
        if (type.equals("year")) {

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
            Date startDate = simpleDateFormat.parse("2020-12-11 00:00:00");
            Date endDate = simpleDateFormat.parse("2020-12-11 23:59:59");
            Double total = invoiceRepository.chartInvoice(startDate, endDate);
            value[i] = total;
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
