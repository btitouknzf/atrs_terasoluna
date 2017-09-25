package jp.co.ntt.atrs.app.c2;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath:META-INF/spring/test-context.xml",
    "classpath:META-INF/spring/test-mvc.xml",
    "classpath:META-INF/spring/existincodelist-test.xml"})
@WebAppConfiguration
public class MemberUpdateControllerTest {

    @Inject
    MemberUpdateController target;
    
    MockMvc mockMvc;
    
    @Before
    public void setUp() throws Exception {
        //make mockMvc for testController
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();
    }
    
    @Test
    //@WithUserDetails("0000000001")
    //@WithMockUser(username = "0000000001")
    public void testUpdateForm() throws Exception {
        
        //Collection<GrantedAuthority> authorities =new ArrayList<>() ;
        //authorities.add(new SimpleGrantedAuthority("ADMIN"));
        
        //make request mock
        MockHttpServletRequestBuilder getRequest =
                MockMvcRequestBuilders.get("/member/update").param("form", "");
                //.param("username", "0000000001");
        
        //run test
        ResultActions results = mockMvc.perform(getRequest);
        
        //result Confirmation
        results.andDo(print());
        results.andExpect(status().isOk());
    }

}
