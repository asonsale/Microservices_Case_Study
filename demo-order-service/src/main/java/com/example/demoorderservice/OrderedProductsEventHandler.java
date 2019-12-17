package com.example.demoorderservice;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.democore.OrderCreatedEvent;
import com.example.democore.OrderUpdatedEvent;

@Service
public class OrderedProductsEventHandler {

	@Autowired
	private OrdersRepository ordersRepository;

	@EventHandler
	public void on(OrderCreatedEvent orderCreatedEvent) {

		Orders order1 = new Orders(orderCreatedEvent.orderId, orderCreatedEvent.itemType, orderCreatedEvent.price,
				orderCreatedEvent.currency, orderCreatedEvent.orderStatus);
		System.out.println(order1);
		ordersRepository.save(order1);
	}

	@EventHandler
	public void on(OrderUpdatedEvent orderUpdatedEvent) {

		Orders orders = ordersRepository.findById(orderUpdatedEvent.orderId).get();
		System.out.println("Before orders in orderUpdatedEvent :: " + orders);
		if (null != orders) {
			orders.setOrderStatus(orderUpdatedEvent.orderStatus);
			ordersRepository.save(orders);
		}
		orders = ordersRepository.findById(orderUpdatedEvent.orderId).get();
		System.out.println("After orders in orderUpdatedEvent :: " + orders);

	}

	// Event Handlers for OrderConfirmedEvent and OrderShippedEvent...
}