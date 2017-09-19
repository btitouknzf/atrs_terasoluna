package jp.co.ntt.atrs.app.b1;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Date;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;

import jp.co.ntt.atrs.domain.model.BoardingClassCd;
import jp.co.ntt.atrs.domain.model.FlightType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"classpath:META-INF/spring/test-context.xml",
	"classpath:META-INF/spring/test-mvc.xml",
	"classpath:META-INF/spring/existincodelist-test.xml"})
@WebAppConfiguration
public class TicketSearchControllerTest {

	@Inject
	TicketSearchController target;
	
	MockMvc mockMvc;
	
	@Inject
	JodaTimeDateFactory dateFactory;
	
	@Before
	public void setUp() throws Exception {
			
		//make mockMvc for testController
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
		
	}
	
	
	@Test
	public void testSearchForm() throws Exception {
		
		//120 固定値
		int intervalTime = 120;
				
		//make request mock
		MockHttpServletRequestBuilder getRequest =
				MockMvcRequestBuilders.get("/ticket/search").param("form", "");
		
		
		//run test
		ResultActions results = mockMvc.perform(getRequest);
		

		//result Confirmation
		results.andDo(print());
		results.andExpect(status().isOk());
		results.andExpect(view().name("B1/flightSearch"));
		results.andExpect(model().attribute("ticketSearchForm", instanceOf(TicketSearchForm.class)));
		results.andExpect(model().attribute("flightSearchOutputDto", instanceOf(FlightSearchOutputDto.class)));
		
		//model Confirmation
		ModelAndView mav = results.andReturn().getModelAndView();
		
		TicketSearchForm actForm = (TicketSearchForm)mav.getModel().get("ticketSearchForm");
		assertEquals(actForm.getFlightType(), FlightType.RT);
		assertEquals(actForm.getDepAirportCd(), "HND");
		assertEquals(actForm.getArrAirportCd(), "HND");
		assertEquals(actForm.getOutwardDate().getClass(), Date.class);
		assertEquals(actForm.getHomewardDate().getClass(), Date.class);
		assertEquals(actForm.getBoardingClassCd(), BoardingClassCd.N);
		
		FlightSearchOutputDto actDto = (FlightSearchOutputDto)mav.getModel().get("flightSearchOutputDto");
		assertEquals(actDto.getBeginningPeriod().getClass(), Date.class);
		assertEquals(actDto.getEndingPeriod().getClass(), Date.class);
		assertEquals(actDto.getReserveIntervalTime(), (Integer)intervalTime);
		
		boolean actisInitialSearchUnnecessary = (boolean) mav.getModel().get("isInitialSearchUnnecessary");
		assertEquals(actisInitialSearchUnnecessary, true);
		
	}

}
