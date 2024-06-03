package org.acme.service;

import com.mongodb.MongoWriteException;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.HttpHeaders;
import org.acme.dto.InvoiceDTO;
import org.acme.dto.PaymentsDTO;
import org.acme.model.Invoice;
import org.acme.model.Payments;
import org.acme.repository.InvoiceRepository;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class InvoiceService {

    @Inject
    InvoiceRepository invoiceRepository;

    @Inject
    JWTParser jwtParser;

    @Transactional
    public void addInvoice(InvoiceDTO invoiceDTO, HttpHeaders headers) throws ParseException {
        String token = extractToken(headers);
        if (token == null) {
            throw new ParseException("Token not found in the request headers");
        }

        try {
            JsonWebToken jwt = jwtParser.parse(token);
            String enterpriseIdString = jwt.getClaim("id");
            ObjectId enterpriseId = new ObjectId(enterpriseIdString);

            Invoice invoice = new Invoice();
            invoice.setEnterpriseId(enterpriseId);
            invoice.setAmount(invoiceDTO.getAmount());
            invoice.setIssueDate(invoiceDTO.getIssueDate());
            invoice.setDueDate(invoiceDTO.getDueDate());
            invoice.setStatus(invoiceDTO.getStatus());
            invoiceRepository.persist(invoice);

        } catch (Exception e) {
            throw new ParseException("Error parsing JWT token: " + e.getMessage(), e);
        }
    }

    public List<InvoiceDTO> getAllInvoices() {
        return invoiceRepository.listAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<InvoiceDTO> getInvoicesByIssueDate(String date) {
        return invoiceRepository.find("issueDate", date).list()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<InvoiceDTO> getInvoicesByDueDate(String date) {
        return invoiceRepository.find("dueDate", date).list()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<InvoiceDTO> getInvoicesByAmount(double amount) {
        return invoiceRepository.find("amount", amount).list()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<InvoiceDTO> getInvoicesByAmountRange(double minAmount, double maxAmount) {
        return invoiceRepository.find("amount >= ?1 and amount <= ?2", minAmount, maxAmount).list()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

//    public List<InvoiceDTO> getInvoicesByIssueDateRange(String startDate, String endDate) {
//        return invoiceRepository.find("issueDate >= ?1 and issueDate <= ?2", startDate, endDate).list()
//                .stream()
//                .map(this::toDTO)
//                .collect(Collectors.toList());
//    }
//
//    public List<InvoiceDTO> getInvoicesByDueDateRange(String startDate, String endDate) {
//        return invoiceRepository.find("dueDate >= ?1 and dueDate <= ?2", startDate, endDate).list()
//                .stream()
//                .map(this::toDTO)
//                .collect(Collectors.toList());
//    }

    private InvoiceDTO toDTO(Invoice invoice) {
        InvoiceDTO dto = new InvoiceDTO();
        dto.setEnterpriseId(invoice.getEnterpriseId());
        dto.setAmount(invoice.getAmount());
        dto.setIssueDate(invoice.getIssueDate());
        dto.setDueDate(invoice.getDueDate());
        dto.setStatus(invoice.getStatus());
        dto.setPaymentId(invoice.getId());
        return dto;
    }

    private String extractToken(HttpHeaders headers) {
        String authorization = headers.getRequestHeader(HttpHeaders.AUTHORIZATION).getFirst();
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring("Bearer ".length());
        }
        return null;
    }
}
