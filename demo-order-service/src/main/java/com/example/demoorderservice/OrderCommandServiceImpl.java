package com.example.demoorderservice;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.democore.CreateOrderCommand;

@Service
public class OrderCommandServiceImpl implements OrderCommandService {

	private final CommandGateway commandGateway;

	public OrderCommandServiceImpl(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
	}

	@Autowired
	private JwtResponseRepository jwtResponseRepository;

	@Override
	public CompletableFuture<String> createOrder(OrderCreateDTO orderCreateDTO) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return commandGateway.send(new CreateOrderCommand(UUID.randomUUID().toString(), orderCreateDTO.getItemType(),
				orderCreateDTO.getPrice(), orderCreateDTO.getCurrency(), String.valueOf(OrderStatus.CREATED),
				jwtResponseRepository.findByUsername(userDetails.getUsername()).getToken()));
	}
}
