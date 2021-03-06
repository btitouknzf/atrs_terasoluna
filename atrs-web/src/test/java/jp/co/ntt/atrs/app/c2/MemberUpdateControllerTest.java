package jp.co.ntt.atrs.app.c2;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import jp.co.ntt.atrs.domain.model.CreditType;
import jp.co.ntt.atrs.domain.model.Gender;
import jp.co.ntt.atrs.domain.model.Member;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath:META-INF/spring/test-context.xml",
    "classpath:META-INF/spring/test-mvc.xml",
    "classpath:META-INF/spring/existincodelist-test.xml"})
@WebAppConfiguration
public class MemberUpdateControllerTest {

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();
    
    @Inject
    MemberUpdateController target;
    
    MockMvc mockMvc;
    
    /*
     * @InjectMockは使用できなかった。（DIコンテナの紐付けをうまく取り込めない・・・？）
     * モック化が必要な場合は、モッククラスを作成してtargetに埋め込むのがいいか
     */
    
    @Before
    public void setUp() throws Exception {
        
        //make mockMvc for testController
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();
    }
    
    @Test
    public void testUpdateForm() throws Exception {
        
        Principal principal = new Principal() {
            
            @Override
            public String getName() {
                // TODO Auto-generated method stub
                return "0000000001";
            }
        };
        
        //make request mock
        MockHttpServletRequestBuilder getRequest =
                MockMvcRequestBuilders.get("/member/update").param("form", "").principal(principal);
        
        //run test
        ResultActions results = mockMvc.perform(getRequest);
        
        //result Confirmation
        results.andDo(print());
        results.andExpect(status().isOk());
    }
    
    private Member createMemberMock() throws Exception {
        Member member = new Member();
        
        member.setMembershipNumber("0000000001");
        member.setKanjiFamilyName("電電");
        member.setKanjiGivenName("花子");
        member.setKanaFamilyName("デンデン");
        member.setKanaGivenName("ハナコ");
        String strDate = "1979/01/25";
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date birthday = sdFormat.parse(strDate);
        member.setBirthday(birthday);
        member.setGender(Gender.F);
        member.setTel("111-1111-1111");
        member.setZipCode("1111111");
        member.setAddress("東京都港区港南Ｘ－Ｘ－Ｘ");
        member.setMail("xxxxxx@ntt.co.jp");
        member.setCreditNo("1111111111111111");
        CreditType creditType = new CreditType();
        creditType.setCreditTypeCd("VIS");
        creditType.setCreditFirm("VISA");
        member.setCreditType(creditType);
        member.setCreditTerm("01/20");
        
        return member;
    }

}
