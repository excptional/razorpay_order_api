package com.te.razorpay.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    @PostMapping("/createOrder")
    public ArrayList<String> createOrder(@RequestBody ArrayList<Long> data) {
        ArrayList<String> response = new ArrayList<>();

        try {
            RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

            JSONObject orderRequest;
            Order order;

            for(Long item : data) {
                orderRequest = new JSONObject();
                orderRequest.put("amount", item);
                orderRequest.put("currency", "INR");
                orderRequest.put("receipt", "txn_" + System.currentTimeMillis());
                order = razorpayClient.orders.create(orderRequest);
                response.add(order.get("id"));
            }
            response.add("Success");
        } catch (Exception e) {
            response.add(e.getMessage());
        }

        return response;
    }
}

