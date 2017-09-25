package jp.co.ntt.atrs.app.a0;

import static org.junit.Assert.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:META-INF/spring/test-context.xml",
        "classpath:META-INF/spring/test-mvc.xml",
        "classpath:META-INF/spring/existincodelist-test.xml" })
@WebAppConfiguration
public class MemberApiControllerTest {

    @Inject
    MemberApiController target;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

        // make mockMvc for testController
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();

    }

    @Test
    public void testGetAvailableOK() throws Exception {

        // make request mock
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get(
                "/api/member/available").param("membershipNumber",
                        "0000000001");

        // run test
        ResultActions results = mockMvc.perform(getRequest);

        // result Confirmation
        results.andDo(print());
        results.andExpect(status().isOk());
    }

    @Test
    public void testGetAvailableEmpty() throws Exception {

        // make request mock
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get(
                "/api/member/available").param("membershipNumber", "");

        // run test
        ResultActions results = mockMvc.perform(getRequest);

        // result Confirmation
        results.andDo(print());
        results.andExpect(status().isOk());
    }

    @Test
    public void testGetAvailableNG() throws Exception {

        // make request mock
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get(
                "/api/member/available").param("membershipNumber",
                        "1000000000");

        // run test
        ResultActions results = mockMvc.perform(getRequest);

        // result Confirmation
        results.andDo(print());
        results.andExpect(status().isNotFound());
    }

}
