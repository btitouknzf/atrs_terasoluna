package jp.co.ntt.atrs.domain.repository.member;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import jp.co.ntt.atrs.domain.model.CreditType;
import jp.co.ntt.atrs.domain.model.Gender;
import jp.co.ntt.atrs.domain.model.Member;
import jp.co.ntt.atrs.domain.repository.member.MemberRepositoryTestSetupDataExp.MemberLoginSetupList;
import jp.co.ntt.atrs.domain.repository.member.MemberRepositoryTestSetupDataExp.MemberSetupList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/test-context-dbunit-xlsx.xml"})
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	TransactionDbUnitTestExecutionListener.class,
	SqlScriptsTestExecutionListener.class
})
//spring-test-dbunitのアノテーション、xlsx用にクラスをoverride
@DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)
@Transactional
@Rollback
public class MemberRepositoryTestVerDbunitExl {

	@Inject
	MemberRepository target;
	
	@Inject
	JdbcTemplate jdbctemplate;
	
	@Before
	public void setUp() throws Exception {
		
		//spring-test-Dbunitは外部キー制約もよしなにやってくれている
		//memberとmember_login間の参照制約を削除
		//jdbctemplate.execute("ALTER TABLE MEMBER_LOGIN DROP CONSTRAINT FK_MEMBER_LOGIN_1");
		//memberとreservation間の参照制約を削除
		//jdbctemplate.execute("ALTER TABLE RESERVATION DROP CONSTRAINT FK_RESERVATION_1");
		//memberとpassenger間の参照制約を削除
		//jdbctemplate.execute("ALTER TABLE PASSENGER DROP CONSTRAINT FK_PASSENGER_2");
		
		
		//ALTER TABLE RESERVE_FLIGHT ADD CONSTRAINT FK_RESERVE_FLIGHT_1 FOREIGN KEY (RESERVE_NO) REFERENCES RESERVATION (RESERVE_NO);
		
		//DBのセットアップ整合性確認
		//Memeberテーブル
		List<Member> actMember = DBsetUpDataListMember();
		int i = 0;
		for(MemberSetupList expMember : MemberSetupList.values()){
			assertThat(actMember.get(i).getMembershipNumber(), is(expMember.getCustomer_no()));
			assertThat(actMember.get(i).getKanjiFamilyName(), is(expMember.getKanji_family_name()));
			assertThat(actMember.get(i).getKanjiGivenName(), is(expMember.getKanji_given_name()));
			assertThat(actMember.get(i).getKanaFamilyName(), is(expMember.getKana_family_name()));
			assertThat(actMember.get(i).getKanaGivenName(), is(expMember.getKana_given_name()));
			assertThat(actMember.get(i).getBirthday(), is(expMember.getBirthday()));
			assertThat(actMember.get(i).getGender().getCode(), is(expMember.getGender()));
			assertThat(actMember.get(i).getTel(), is(expMember.getTel()));
			assertThat(actMember.get(i).getZipCode(), is(expMember.getZip_code()));
			assertThat(actMember.get(i).getAddress(), is(expMember.getAddress()));
			assertThat(actMember.get(i).getMail(), is(expMember.getMail()));
			assertThat(actMember.get(i).getCreditNo(), is(expMember.getCredit_no()));
			assertThat(actMember.get(i).getCreditTerm(), is(expMember.getCredit_term()));
			assertThat(actMember.get(i).getCreditType().getCreditTypeCd(), is(expMember.getCredit_type_cd()));
			
			i++;
		}
		
		//Memeber_Loginテーブル
		int j = 0;
		List<ActMemberLogin> actMemberLogin = DBsetUpDataListMemberLogin();
		for(MemberLoginSetupList expMemberLogin : MemberLoginSetupList.values()){
			assertThat(actMemberLogin.get(j).getCustomerNo(), is(expMemberLogin.getCustomer_no()));
			assertThat(actMemberLogin.get(j).getPassword(), is(expMemberLogin.getPassword()));
			assertThat(actMemberLogin.get(j).getLastPassword(), is(expMemberLogin.getLast_password()));
			assertThat(actMemberLogin.get(j).getLoginDateTime(), is((Date)expMemberLogin.getLogin_date_time()));
			assertThat(actMemberLogin.get(j).getLoginFlg(), is(expMemberLogin.Login_flg()));
		
			j++;
		}
		
		
	}
	
	@Test
	/*
	 * excel設定のポイント
	 * 値を挿入するところの表示形式は「文字列」に
	 * 挿入しない場所は「標準」にしておく。
	 * 挿入していない場所が「文字列」になっていた場合、null項目と判断される。
	 */
	@DatabaseSetup("classpath:META-INF/dbunit/test_data_member.xlsx")
	@ExpectedDatabase(value = "classpath:META-INF/dbunit/afterupdate_data_member_test1.xlsx", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testUpdate1() {
		
		/*
		 *  ※重要　@DatabaseSetupと、@ExpectedDatabaseの実行タイミング
		 * 			@DatabaseSetup
		 * 			@Before
		 * 			@Test
		 * 			@ExpectDatabase
		 * 			@After
		 * 			@DatabaseSetup			
		 *		 	@Before
		 * 			@Test2
		 * 			@ExpectDatabase
		 * 			@After
		 *  
		*/
		//spring-test-Dbunitは外部キー制約もよしなにやってくれている
		//memberとmember_login間の参照制約を追加
		//jdbctemplate.execute("ALTER TABLE MEMBER_LOGIN ADD CONSTRAINT FK_MEMBER_LOGIN_1 FOREIGN KEY (CUSTOMER_NO) REFERENCES MEMBER (CUSTOMER_NO)");	
		//memberとreservation間の参照制約を追加
		//jdbctemplate.execute("ALTER TABLE RESERVATION ADD CONSTRAINT FK_RESERVATION_1 FOREIGN KEY (REP_CUSTOMER_NO) REFERENCES MEMBER (CUSTOMER_NO)");
		//memberとpassenger間の参照制約を追加
		//jdbctemplate.execute("ALTER TABLE PASSENGER ADD CONSTRAINT FK_PASSENGER_2 FOREIGN KEY (CUSTOMER_NO) REFERENCES MEMBER (CUSTOMER_NO)");
		
		//update元情報作成
		String customerNo = "0000000001";
		Member member = createMember(customerNo);
		
		//updateDataSet
		member.setKanjiFamilyName("電信柱");
		
		//test
		int actupdate = 0;
		actupdate = target.update(member);
		//更新件数の確認
		assertThat(actupdate, is(1));
		//更新された中身の確認
		assertDB(customerNo, member);
		
	}
	
	
	@Test
	@DatabaseSetup("classpath:META-INF/dbunit/test_data_member.xlsx")
	//DBの更新が無いので、検証は不要
	//@ExpectedDatabase(value = "classpath:META-INF/dbunit/afterupdate_data_member_test2.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testUpdate2() {
		
		//findDataSet
		String membershipNumber = "0000000001";
				
		//test
		Member member = target.findOne(membershipNumber);

		//中身の確認
		assertDB(membershipNumber, member);
		
	}
	
	
	//DBassertThat
	private void assertDB(String customerNo, Member expMember){
		Member actMember = createMember(customerNo);
		
		assertThat(actMember.getMembershipNumber(), is(expMember.getMembershipNumber()));
		assertThat(actMember.getKanjiFamilyName(), is(expMember.getKanjiFamilyName()));
		assertThat(actMember.getKanjiGivenName(), is(expMember.getKanjiGivenName()));
		assertThat(actMember.getKanaFamilyName(), is(expMember.getKanaFamilyName()));
		assertThat(actMember.getKanaGivenName(), is(expMember.getKanaGivenName()));
		assertThat(actMember.getBirthday(), is(expMember.getBirthday()));
		assertThat(actMember.getGender(), is(expMember.getGender()));
		assertThat(actMember.getTel(), is(expMember.getTel()));
		assertThat(actMember.getZipCode(), is(expMember.getZipCode()));
		assertThat(actMember.getAddress(), is(expMember.getAddress()));
		assertThat(actMember.getMail(), is(expMember.getMail()));
		assertThat(actMember.getCreditNo(), is(expMember.getCreditNo()));
		assertThat(actMember.getCreditTerm(), is(expMember.getCreditTerm()));
		assertThat(actMember.getCreditType().getCreditTypeCd(), is(expMember.getCreditType().getCreditTypeCd()));
		assertThat(actMember.getCreditType().getCreditFirm(), is(expMember.getCreditType().getCreditFirm()));
		
	}
	
	
	
	
	//DBから対象のデータを取得
	private Member createMember(String customerNo){
		
		String sql = "SELECT * FROM member WHERE customer_no=?";
		Member member = (Member)jdbctemplate.queryForObject(sql, new Object[] {customerNo}, 
				new RowMapper<Member>(){
					public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
						Member rmember = new Member();
						
						rmember.setMembershipNumber(rs.getString("customer_no"));
						rmember.setKanjiFamilyName(rs.getString("kanji_family_name"));
						rmember.setKanjiGivenName(rs.getString("kanji_given_name"));
						rmember.setKanaFamilyName(rs.getString("kana_family_name"));
						rmember.setKanaGivenName(rs.getString("kana_given_name"));
						rmember.setBirthday(rs.getDate("birthday"));
						
						String gender = rs.getString("gender");
						if(gender.equals("F")){
							rmember.setGender(Gender.F);
						}else{
							rmember.setGender(Gender.M);
						}
						
						rmember.setTel(rs.getString("tel"));
						rmember.setZipCode(rs.getString("zip_code"));
						rmember.setAddress(rs.getString("address"));
						rmember.setMail(rs.getString("mail"));
						rmember.setCreditNo(rs.getString("credit_no"));
						rmember.setCreditTerm(rs.getString("credit_term"));
						
						String creditTypeCd = rs.getString("credit_type_cd");
						String sql = "SELECT * FROM credit_type WHERE credit_type_cd=?";
						CreditType creditType = (CreditType)jdbctemplate.queryForObject(sql, new Object[] {creditTypeCd},
								new RowMapper<CreditType>(){
									public CreditType mapRow(ResultSet rs, int rowNum) throws SQLException {
										CreditType creditType = new CreditType();
										
										creditType.setCreditTypeCd(rs.getString("credit_type_cd"));
										creditType.setCreditFirm(rs.getString("credit_firm"));
										
										return creditType;
									}
						});
						rmember.setCreditType(creditType);

						return rmember;
					}
		});
		return member;
	}
	
	//DB全取得(MEMBER)
	private List<Member> DBsetUpDataListMember(){
		
		List<Member> actMember = jdbctemplate.query("SELECT * FROM MEMBER", new RowMapper<Member>() {
			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
					Member rmember = new Member();
				
					rmember.setMembershipNumber(rs.getString("customer_no"));
					rmember.setKanjiFamilyName(rs.getString("kanji_family_name"));
					rmember.setKanjiGivenName(rs.getString("kanji_given_name"));
					rmember.setKanaFamilyName(rs.getString("kana_family_name"));
					rmember.setKanaGivenName(rs.getString("kana_given_name"));
					rmember.setBirthday(rs.getDate("birthday"));
				
					String gender = rs.getString("gender");
					if(gender.equals("F")){
						rmember.setGender(Gender.F);
					}else{
						rmember.setGender(Gender.M);
					}
				
					rmember.setTel(rs.getString("tel"));
					rmember.setZipCode(rs.getString("zip_code"));
					rmember.setAddress(rs.getString("address"));
					rmember.setMail(rs.getString("mail"));
					rmember.setCreditNo(rs.getString("credit_no"));
					rmember.setCreditTerm(rs.getString("credit_term"));
				
					String creditTypeCd = rs.getString("credit_type_cd");
					String sql = "SELECT * FROM credit_type WHERE credit_type_cd=?";
					CreditType creditType = (CreditType)jdbctemplate.queryForObject(sql, new Object[] {creditTypeCd},
							new RowMapper<CreditType>(){
								public CreditType mapRow(ResultSet rs, int rowNum) throws SQLException {
									CreditType creditType = new CreditType();
								
									creditType.setCreditTypeCd(rs.getString("credit_type_cd"));
									creditType.setCreditFirm(rs.getString("credit_firm"));
								
									return creditType;
								}
					});
					rmember.setCreditType(creditType);

					return rmember;
			}
		});
		return actMember;
	}
	
	//DB全取得(MEMBER_LOGIN)
	private List<ActMemberLogin> DBsetUpDataListMemberLogin(){
			
		List<ActMemberLogin> actMemberLogin = jdbctemplate.query("SELECT * FROM MEMBER_LOGIN", new RowMapper<ActMemberLogin>() {
			public ActMemberLogin mapRow(ResultSet rs, int rowNum) throws SQLException {
					ActMemberLogin ractmemberLogin = new ActMemberLogin();
					
					ractmemberLogin.setCustomerNo(rs.getString("customer_no"));
					ractmemberLogin.setPassword(rs.getString("password"));
					ractmemberLogin.setLastPassword(rs.getString("last_password"));
					ractmemberLogin.setLoginDateTime(rs.getTimestamp("login_date_time"));
					ractmemberLogin.setLoginFlg(rs.getBoolean("login_flg"));
					
					return ractmemberLogin;
			}
		});
		return actMemberLogin;
	}
}
