package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.dto.PaymentsDTO;
import org.acme.model.Invoice;
import org.acme.model.Payments;
import org.acme.repository.InvoiceRepository;
import org.acme.repository.PaymentsRepository;

import java.util.List;

    @ApplicationScoped
    public class PaymentsService {

    @Inject
    PaymentsRepository paymentRepository;
    @Inject
    InvoiceRepository invoiceRepository;

    public void addPayment(PaymentsDTO paymentDTO) {
        try {
            Invoice invoice = invoiceRepository.find("status", "pendente").firstResult();
            if (invoice == null) {
                throw new Exception("nenhuma fatura pendente de pagamento");
            }

            Payments payment = new Payments();
            payment.setInvoiceId(invoice.getId());
            payment.setAmount(paymentDTO.getAmount());
            payment.setPaymentDate(paymentDTO.getPaymentDate());
            payment.setPaymentMethod(paymentDTO.getPaymentMethod());
            invoice.setStatus("realizado");

            paymentRepository.persist(payment);
            invoiceRepository.persistOrUpdate(invoice);

        } catch (Exception e) {
            throw new RuntimeException("Error adding payment " + e);
        }
    }

    public List<Payments> getAllPayments() {
        return paymentRepository.listAll();
    }

    public List<Payments> getPaymentsByDate(String date) {
        return paymentRepository.find("paymentDate", date).list();
    }

    public List<Payments> getPaymentsByMethod(String method) {
        return paymentRepository.find("paymentMethod", method).list();
    }

    public List<Payments> getPaymentsByValue(double minValue, double maxValue) {
        return paymentRepository.find("amount >= ?1 and amount <= ?2", minValue, maxValue).list();
    }
}
