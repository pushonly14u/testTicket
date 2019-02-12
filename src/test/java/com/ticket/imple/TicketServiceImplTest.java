/**
 * Created by Darpan Shah Jul 19, 2017
 */
package com.ticket.imple;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ticket.defination.SeatHold;
import com.ticket.defination.Venue;
import com.ticket.imple.TicketServiceImpl;

public class TicketServiceImplTest {
	private TicketServiceImpl service;
	private int second = 3;
	private int wait = 4000;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		service = new TicketServiceImpl(new Venue(1,1), second);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void numSeatsAvailable() throws InterruptedException{
		int no = service.numSeatsAvailable();
		assert(no == 1);
		service = new TicketServiceImpl(new Venue(2,3), second);
		no = service.numSeatsAvailable();
		assert(no == (2*3));
		service.findAndHoldSeats(2, "darpan@dp.com");
		no = service.numSeatsAvailable();
		assert(no == ((2*3)-2));
		Thread.sleep(wait); // default expire time is 5s. prior hold should be gone. (Timing is check on the scale of seconds)
		System.out.println("After waiting: " + service.numSeatsAvailable());
		assert((2*3) == service.numSeatsAvailable());
		service.findAndHoldSeats((2*3), "darpan@dp.com");
		assert(0 == service.numSeatsAvailable());
		Thread.sleep(wait); // default expire time is 5s. prior hold should be gone.
		assert((2*3) == service.numSeatsAvailable());
		SeatHold sh = service.findAndHoldSeats((2*3), "darpan@dp.com");
		no = service.numSeatsAvailable();
		service.reserveSeats(sh.getId(), "darpan@dp.com");
		assert(no == service.numSeatsAvailable()); // reserving seats should not change no of seats. (assuming we don`t pass expiry)
		System.gc();
		service = new TicketServiceImpl(new Venue(2,3), second);
		sh = service.findAndHoldSeats((2*3), "darpan@dp.com");
		no = service.numSeatsAvailable();
		Thread.sleep(wait);
		service.reserveSeats(sh.getId(), "darpan@dp.com");
		assert((no + sh.getSeatsHeld().size()) == service.numSeatsAvailable()); // After expiration, all held seats should be available.
	}
	
	@Test
	public void findAndHoldSeats() throws InterruptedException{
		SeatHold s1 = service.findAndHoldSeats(1, "xyz@abc.com");
		assertNotNull(s1);
		assert(1 == s1.getSeatsHeld().size());
		s1 = service.findAndHoldSeats(1, "xyz@abc.com");
		assert(null == s1);
		Thread.sleep(wait);
		s1 = service.findAndHoldSeats(1, "xyz@abc.com");
		assertNotNull(s1);
		assert(1 == s1.getSeatsHeld().size());
		Thread.sleep(wait);
		s1 = service.findAndHoldSeats(2, "xyz@abc.com");
		assert(null == s1);
	}
	
	@Test
	public void reserveSeats() throws InterruptedException{
		SeatHold s1 = service.findAndHoldSeats(1, "xyz@abc.com");
		String conf = service.reserveSeats(s1.getId(), "xyz@abc.com");
		assertNotNull(conf);
		assertTrue(conf.contains("reserved!"));
		conf = service.reserveSeats(0, "xyz@abc.com");
		assert(null == conf);
	}
	
	
}
