package jp.co.ntt.atrs.domain.repository.member;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import jp.co.ntt.atrs.domain.model.Member;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/test-context-dbunit.xml"})
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	TransactionDbUnitTestExecutionListener.class,
	SqlScriptsTestExecutionListener.class
})
@Transactional
@Rollback
public class MemberRepositoryTest {
	
	@Inject
	MemberRepository target;
	
	@Test
	@DatabaseSetup("classpath:META-INF/dbunit/test_data.xml")
	@ExpectedDatabase(value = "classpath:META-INF/dbunit/afterupdate_data.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void findOneForLoginTest1() throws Exception {
		String membershipNumber = "0000000001";
		
		Member actualMember = target.findOneForLogin(membershipNumber);
		assertThat(actualMember, notNullValue());
		assertEquals(actualMember, notNullValue());
	}
}
