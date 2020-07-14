/**
 * (C) Karros Technologies LLC 2020 Karros Technologies are the owner of
 * the entire copyright in this work. Any unauthorised use of this work will
 * constitute an infringement of Karros's copyright. Copying, reproduction,
 * publication, performance, broadcasting, transmission to subscribers to a
 * diffusion service or adaptation of the work, without the express written
 * consent of Karros Technologies is strictly prohibited.
 */
package com.karros.app;

import com.karros.app.DemoApplication;
import com.karros.rest.TrackingController;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author chaul
 *
 */
@SpringBootTest (classes = DemoApplication.class)
class DemoApplicationTests {
    
    @Autowired
    private TrackingController trackingController;

	@Test
	void contextLoads() {
	    Assertions.assertThat(trackingController).isNotNull();
	}

}
