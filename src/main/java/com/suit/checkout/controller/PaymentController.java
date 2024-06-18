package com.suit.checkout.controller;

import com.suit.checkout.models.Pagamentos;
import com.suit.checkout.models.dtos.MPDTOS.NotificationMP;
import com.suit.checkout.models.dtos.PagamentoRequestDTO;
import com.suit.checkout.models.dtos.RequestApiPaymentDTO;
import com.suit.checkout.models.dtos.ResponseRifaValues;
import com.suit.checkout.models.dtos.ReturnSuitPay;
import com.suit.checkout.models.enums.StatusPagamento;
import com.suit.checkout.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private static final String keySenha = "dropgf3212024";

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public Object createPaymentWithSuitPay(@RequestBody RequestApiPaymentDTO data) {
        System.out.println(data);
        return paymentService.createPaymentMercadoPago(data);
    }

    @PostMapping("/callback")
    public void callback(@RequestBody NotificationMP data) {
        System.out.println(data);
         paymentService.verifyStatusPaymentInMp(data.data().id());
    }

    @GetMapping
    public List<Pagamentos> getAllPayments() {
        return paymentService.findAllPayments();
    }

    @GetMapping("/{id}")
    public Pagamentos getPaymentById(@PathVariable String id) {
        return paymentService.findPaymentById(UUID.fromString(id));
    }

    @GetMapping("/status/{status}")
    public List<Pagamentos> getPaymentsByStatus(@PathVariable StatusPagamento status) {
        return paymentService.findPaymentsByStatusPagamento(status);
    }

    @GetMapping("/transaction/{id}")
    public Pagamentos getPaymentByTransactionId(@PathVariable String id) {
        return paymentService.getPagamentoByIdTransactionSuitPay(id);
    }

    @GetMapping("/rifa/{nomeRifa}")
    public ResponseRifaValues getPaymentsByRifa(@PathVariable String nomeRifa, @PathVariable("key") String key) {
        if (!key.equals(keySenha)) {
            return null;
        }
        return paymentService.findPaymentsByNomeRifa(nomeRifa);
    }

}
