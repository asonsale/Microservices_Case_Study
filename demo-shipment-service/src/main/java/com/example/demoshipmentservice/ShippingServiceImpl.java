package com.example.demoshipmentservice;

import org.springframework.stereotype.Service;

@Service
public class ShippingServiceImpl implements ShippingService {

	@Override
	public String performShippingBussLogic() {
		return "done";
	}

}
