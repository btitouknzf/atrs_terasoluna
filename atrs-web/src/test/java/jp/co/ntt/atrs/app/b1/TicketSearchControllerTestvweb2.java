package jp.co.ntt.atrs.app.b1;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.validation.ValidationException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath:META-INF/spring/test-context.xml",
    "classpath:META-INF/spring/test-mvc.xml",
    "classpath:META-INF/spring/existincodelist-test.xml"})
@WebAppConfiguration
public class TicketSearchControllerTestvweb2 {

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();
    
    @InjectMocks
    TicketSearchController target;
    
    @Mock
    TicketSearchHelper ticketSearchHelper;
    
    MockMvc mockMvc;
    
    @Before
    public void setUp() throws Exception {
        
        //serviceHelperMockのmockito設定値
        FlightSearchOutputDto mockDto = createDto();
        
        when(ticketSearchHelper.createFlightSearchOutputDto()).thenReturn(mockDto);
        
        //make mockMvc for testController
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();
    }
    @Test
    //@Test(expected = ServletException.class)
    public void testSearchFlightForm() throws Exception{
        //Formに@ExistInCodeListがいるため、ビルドできない。
        //Form入力値を仮設定
        //make request mock
        MockHttpServletRequestBuilder getRequest =
                MockMvcRequestBuilders.get("/ticket/search")
                                    .param("flightForm", "")
                                    .param("flightType", "RT")
                                    .param("depAirportCd", "HND")
                                    .param("arrAirportCd", "ITM")
                                    .param("outwardDate", "2017/12/14")
                                    .param("homewardDate", "2017/12/14")
                                    .param("boardingClassCd", "N");
            
        //run test
        ResultActions results = mockMvc.perform(getRequest);
        
        //result Confirmation
        results.andDo(print());
        results.andExpect(status().isOk());
        results.andExpect(view().name("B1/flightSearch"));
        results.andExpect(model().attributeHasNoErrors("ticketSearchForm"));
        results.andExpect(model().attribute("flightSearchOutputDto", instanceOf(FlightSearchOutputDto.class)));
            
        //model Confirmation
        ModelAndView mav = results.andReturn().getModelAndView();
        FlightSearchOutputDto actDto = (FlightSearchOutputDto)mav.getModel().get("flightSearchOutputDto");
        FlightSearchOutputDto expDto = createDto();
        assertEquals(actDto.getBeginningPeriod(), expDto.getBeginningPeriod());
        assertEquals(actDto.getEndingPeriod(), expDto.getEndingPeriod());
        assertEquals(actDto.getReserveIntervalTime(), expDto.getReserveIntervalTime());
    }
    
    private FlightSearchOutputDto createDto() throws Exception {
        
        FlightSearchOutputDto flightSearchOutputDto = new FlightSearchOutputDto();
        
      //現在時刻
        String strBeginDate = "2017/09/15 00:00:00";
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        Date begindate = sdFormat.parse(strBeginDate);

        //現在日付+90日　固定値
        String strEndDate = "2017/12/14 00:00:00";
        Date ecddate = sdFormat.parse(strEndDate);
                
        //120 固定値
        int intervalTime = 120;
        
        flightSearchOutputDto.setBeginningPeriod(begindate);
        flightSearchOutputDto.setEndingPeriod(ecddate);
        flightSearchOutputDto.setReserveIntervalTime(intervalTime);
        
        return flightSearchOutputDto;
    }
}
