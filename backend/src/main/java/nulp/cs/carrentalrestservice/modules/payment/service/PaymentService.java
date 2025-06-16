package nulp.cs.carrentalrestservice.modules.payment.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import nulp.cs.carrentalrestservice.modules.payment.dto.StripeRequest;
import nulp.cs.carrentalrestservice.modules.payment.dto.StripeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {
    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Value("${api.domain}")
    private String domain;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    public StripeResponse createPaymentLink(StripeRequest request) {
        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams sessionParams = createSessionParams(request);

        Session session;
        try {
            session = Session.create(sessionParams);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }

        return StripeResponse.builder()
                .sessionId(session.getId())
                .status("SUCCESS")
                .url(session.getUrl())
                .build();
    }

    private void createPayment (StripeRequest request) {


    }

    private SessionCreateParams createSessionParams(StripeRequest request) {
        SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams
                .LineItem.PriceData.ProductData.builder()
                .setName(request.getName())
                .build();

        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams
                .LineItem.PriceData.builder()
                .setCurrency(request.getCurrency())
                .setProductData(productData)
                .setUnitAmountDecimal(request.getAmount().multiply(BigDecimal.valueOf(100)))
                .build();

        SessionCreateParams.LineItem lineItem = SessionCreateParams
                .LineItem.builder()
                .setPriceData(priceData)
                .setQuantity(1L)
                .build();

        return SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(domain + "/api/v1/success")
                .setCancelUrl(domain + "/api/v1/cancel")
                .addLineItem(lineItem)
                .build();
    }

}
