package com.organization.imagesearch;

import com.organization.imagesearch.controller.ImageSearchController;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ImageSearchApplicationTests {

	@Autowired
	ImageSearchController imageSearchController;

	@Test
	void contextLoads() {
		Assert.assertNotNull(imageSearchController);
	}

}
