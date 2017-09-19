package jp.co.ntt.atrs.app.b1;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ntt.atrs.domain.model.BoardingClassCd;
import jp.co.ntt.atrs.domain.model.FlightType;


public class TicketSearchControllerTestVerMockclass {

	
	TicketSearchController target;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		
		//targetClass new
		target = new TicketSearchController();
		
		//injection mocks
		TicketSearchHelper ticketSerchHelper = new TicketSearchHelperMock();
		target.ticketSearchHelper = ticketSerchHelper;
		
		//make mockMvc for testController
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
		
	}
	
	
	@Test
	public void testSearchForm() throws Exception {
		
		//serviceHelperMockの設定値
		//現在時刻
		String strBeginDate = "2017/09/15 00:00:00";
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		Date begindate = sdFormat.parse(strBeginDate);

		//現在日付+90日　固定値
		String strEndDate = "2017/12/14 00:00:00";
		Date ecddate = sdFormat.parse(strEndDate);
		
		//120 固定値
		int intervalTime = 120;
		
		//make request mock
		MockHttpServletRequestBuilder getRequest =
				MockMvcRequestBuilders.get("/ticket/search").param("form", "");
		
		//run test
		ResultActions results = mockMvc.perform(getRequest);
		
		System.out.println("aaaa");
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
		assertEquals(actForm.getOutwardDate(), begindate);
		assertEquals(actForm.getHomewardDate(), begindate);
		assertEquals(actForm.getBoardingClassCd(), BoardingClassCd.N);
		
		FlightSearchOutputDto actDto = (FlightSearchOutputDto)mav.getModel().get("flightSearchOutputDto");
		assertEquals(actDto.getBeginningPeriod(), begindate);
		assertEquals(actDto.getEndingPeriod(), ecddate);
		assertEquals(actDto.getReserveIntervalTime(), (Integer)intervalTime);
		
		boolean actisInitialSearchUnnecessary = (boolean) mav.getModel().get("isInitialSearchUnnecessary");
		assertEquals(actisInitialSearchUnnecessary, true);
		
	}

}
