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

import javax.inject.Inject;
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
public class TicketSearchControllerTestvweb3 {

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();
    
    @Inject
    TicketSearchController target;
    
    /*
     * 
     * The "webAppContextSetup" loads the actual Spring MVC configuration 
     * resulting in a more complete integration test. Since the TestContext framework 
     * caches the loaded Spring configuration, it helps to keep tests running fast even 
     * as more tests get added. Furthermore, you can inject mock services into controllers 
     * through Spring configuration, in order to remain focused on testing the web layer.
     * 
     * "webAppContextSetup"は実際のSpring MVC設定をロードし、より完全な統合テストになります。 
     * TestContextフレームワークはロードされたSpring設定をキャッシュするので、テストが追加されてもテストが速く実行されます。
     * さらに、Webレイヤのテストに集中するために、Springコンフィグレーションを通じてコン​​トローラにモックサービスをインジェクトすることができます。
     */
    
    /*
     * The "standaloneSetup" on the other hand is a little closer to a unit test. 
     * It tests one controller at a time, the controller can be injected with mock dependencies manually, 
     * and it doesn’t involve loading Spring configuration. Such tests are more focused in style 
     * and make it easier to see which controller is being tested, whether any specific Spring MVC configuration
     *  is required to work, and so on. The "standaloneSetup" is also a very convenient way to 
     *  write ad-hoc tests to verify some behavior or to debug an issue.
     * 
     * 一方、 "standaloneSetup"は単体テストに少し近づいています。
     * 一度に1つのコントローラをテストし、コントローラにモック依存関係を手動で注入することができます。
     * また、Springのコンフィグレーションをロードする必要はありません。このようなテストはスタイルに焦点が当てられており、
     * どのコントローラがテストされているか、特定のSpring MVC設定が必要かどうかなどを簡単に確認できます。
     *  "standaloneSetup"は、何らかの動作を確認したり、問題をデバッグするためのアドホックテストを書くのに非常に便利な方法です。
     * 
     */
    //@Mock
    ///TicketSearchHelper ticketSearchHelper;
    
    MockMvc mockMvc;
    
    @Before
    public void setUp() throws Exception {
        
        //serviceHelperMockのmockito設定値
        FlightSearchOutputDto mockDto = createDto();
        
        //when(ticketSearchHelper.createFlightSearchOutputDto()).thenReturn(mockDto);
        
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
