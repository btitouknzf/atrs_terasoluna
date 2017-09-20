package jp.co.ntt.atrs.domain.common.validate;

import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

public class FullWidthKatakanaTest {

	
	private static Validator validator;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}
	
	@Test
	public void testValidator01() {
		
		FullWidthKatakanaBean bean = new FullWidthKatakanaBean();
		
		//入力情報を設定
		bean.setTestString("デンデン");
		
		//test
		Set<ConstraintViolation<FullWidthKatakanaBean>> violations = validator.validate(bean);
		
		//検証(エラーが発生していないこと)
		assertEquals(violations.size(), 0);		
	}
	
	@Test
	public void testValidator02() {
		
		FullWidthKatakanaBean bean = new FullWidthKatakanaBean();
		
		//入力情報を設定
		bean.setTestString("denden");
			
		//test
		Set<ConstraintViolation<FullWidthKatakanaBean>> violations = validator.validate(bean);
		
		//検証(エラーが発生していること)
		assertEquals(violations.size(), 1);
	}

	
	/*
	 * テスト用Beanクラス
	 * 
	 */
	private static class FullWidthKatakanaBean {
		
		/*
		 * @Pattern(regexp = "^[\\u30A0-\\u30FF]+$")
		 * 全角カタカナのチェック
		 */
		@FullWidthKatakana
		private String testString;
		
		public FullWidthKatakanaBean(){
			//空のコンストラクタ
		}

		public String getTestString() {
			return testString;
		}

		public void setTestString(String testString) {
			this.testString = testString;
		}
	}
	
}


