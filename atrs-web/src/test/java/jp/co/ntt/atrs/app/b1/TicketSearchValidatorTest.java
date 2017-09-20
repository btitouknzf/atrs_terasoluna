package jp.co.ntt.atrs.app.b1;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.ObjectError;

import jp.co.ntt.atrs.domain.model.BoardingClassCd;
import jp.co.ntt.atrs.domain.model.FlightType;
import jp.co.ntt.atrs.domain.service.b1.TicketSearchErrorCode;

/**
 * 空席照会フォームのバリデータテスト。
 * <p>下記の場合をエラーとする。</p>
 * <ul>
 * <li>出発空港と到着空港が同じ場合。</li>
 * <li>フライト種別が往復の場合に復路搭乗日が入力されていない場合。</li>
 * <li>復路搭乗日が往路搭乗日以降ではない場合。</li>
 * </ul>
 * 
 * @author NTT 電電次郎
 */
public class TicketSearchValidatorTest {

	private TicketSearchValidator validator;
	
	private TicketSearchForm ticketSearchForm;
	
	private BindingResult result;
	
	@Before
	public void setUp() throws Exception {
		
		validator = new TicketSearchValidator();
		ticketSearchForm = new TicketSearchForm();
		result = new DirectFieldBindingResult(ticketSearchForm, "TicketSearchForm");
	}
	
	/**
	 * 正常系
	 * @throws Exception
	 */
	@Test
	public void testValidator01() throws Exception {
		
		/**
		 * テストデータ作成
		 */
		/**
	     * フライト種別。
	     */
		ticketSearchForm.setFlightType(FlightType.RT);
		/**
	     * 出発空港コード。
	     */
		ticketSearchForm.setDepAirportCd("HND");
		/**
	     * 到着空港コード。
	     */
		ticketSearchForm.setArrAirportCd("ITM");
		/**
	     * 往路搭乗日。
	     */
		String strBeginDate = "2017/09/15";
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date begindate = sdFormat.parse(strBeginDate);
		ticketSearchForm.setOutwardDate(begindate);
		/**
	     * 復路搭乗日。
	     */
		String strEndDate = "2017/09/17";
		Date endDate = sdFormat.parse(strEndDate);
		ticketSearchForm.setHomewardDate(endDate);
		/**
	     * 搭乗クラスコード。
	     */
		ticketSearchForm.setBoardingClassCd(BoardingClassCd.N);
		
		
		/**
		 * test
		 */
		validator.validate(ticketSearchForm, result);
		
		/**
		 * 検証
		 */
		assertEquals(result.hasErrors(), false);
	}
	
	/**
	 * エラー系
	 * 出発空港と到着空港が同じ場合。
	 * @throws Exception
	 */
	@Test
	public void testValidator02() throws Exception {
		
		/**
		 * テストデータ作成
		 */
		/**
	     * フライト種別。
	     */
		ticketSearchForm.setFlightType(FlightType.RT);
		/**
	     * 出発空港コード。
	     */
		ticketSearchForm.setDepAirportCd("HND");
		/**
	     * 到着空港コード。
	     */
		ticketSearchForm.setArrAirportCd("HND");
		/**
	     * 往路搭乗日。
	     */
		String strBeginDate = "2017/09/15";
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date begindate = sdFormat.parse(strBeginDate);
		ticketSearchForm.setOutwardDate(begindate);
		/**
	     * 復路搭乗日。
	     */
		String strEndDate = "2017/09/17";
		Date endDate = sdFormat.parse(strEndDate);
		ticketSearchForm.setHomewardDate(endDate);
		/**
	     * 搭乗クラスコード。
	     */
		ticketSearchForm.setBoardingClassCd(BoardingClassCd.N);
		
		
		/**
		 * test
		 */
		validator.validate(ticketSearchForm, result);
		
		/**
		 * 検証
		 */
		assertEquals(result.hasErrors(), true);
		
		ObjectError error = result.getGlobalError();
		if(error == null){
			fail("エラー取得失敗");
		} else {
			String code = error.getCode();
			assertEquals(code, TicketSearchErrorCode.E_AR_B1_5001.code());
		}		
	}

}
