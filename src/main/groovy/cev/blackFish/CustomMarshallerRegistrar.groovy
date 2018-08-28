package cev.blackFish

import grails.util.Holders
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class CustomMarshallerRegistrar {

	@javax.annotation.PostConstruct
	void registerMarshallers() {

		JSON.registerObjectMarshaller(cev.blackFish.Measurement) {

			List l = []
			//l << it.net_id
			l << it.sensor_id
			l << it.temp0
			l << it.temp1
			l << it.temp2
			l << it.temp3
			l << it.temp4
			l << it.temp5
			//l << it.depth0
			//l << it.depth1
			//l << it.flash_id
			//l << it.ROM0
			//l << it.ROM1
			//l << it.lat
			//l << it.lon
			//l << it.date.getTime()
			l << it.date

			return l
		}
	}

}
