package com.example.demoshipmentservice;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.example.democore.CreateShippingCommand;
import com.example.democore.CreateShippingFailedCommand;
import com.example.democore.OrderShippedEvent;
import com.example.democore.OrderShippedFailedEvent;

@Aggregate
public class ShippingAggregate {

	@AggregateIdentifier
	private String shippingId;

	private String orderId;

	private String paymentId;

	private String userToken;

	private boolean isOrderShipped;

	private static int dailyShippments;

	public ShippingAggregate() {
	}

	@CommandHandler
	public ShippingAggregate(CreateShippingCommand createShippingCommand) {
		System.out.println("CreateShippingCommand :: " + createShippingCommand.shippingId);

		if (dailyShippments < 3) {
			AggregateLifecycle.apply(new OrderShippedEvent(createShippingCommand.shippingId,
					createShippingCommand.orderId, createShippingCommand.paymentId, createShippingCommand.userToken));
		} else {

			AggregateLifecycle.apply(new OrderShippedFailedEvent(createShippingCommand.shippingId,
					createShippingCommand.orderId, createShippingCommand.paymentId, createShippingCommand.userToken));
		}
	}

	@EventSourcingHandler
	protected void on(OrderShippedEvent orderShippedEvent) {
		System.out.println("OrderShippedEvent :: " + orderShippedEvent.shippingId);

		this.shippingId = orderShippedEvent.shippingId;
		this.orderId = orderShippedEvent.orderId;
		this.userToken = orderShippedEvent.userToken;
		++dailyShippments;
		System.out.println("Daily shippments count " + dailyShippments);
	}

	@EventSourcingHandler
	protected void on(OrderShippedFailedEvent orderShippedFailedEvent) {
		System.out.println("OrderShippedFailedEvent :: " + orderShippedFailedEvent.shippingId);
		// String res = shippingServiceImpl.performShippingBussLogic();
		// if (res.equals("done")) {
		// isOrderShipped=true;
		// }

		this.shippingId = orderShippedFailedEvent.shippingId;
		this.orderId = orderShippedFailedEvent.orderId;
		this.userToken = orderShippedFailedEvent.userToken;
	}
}
