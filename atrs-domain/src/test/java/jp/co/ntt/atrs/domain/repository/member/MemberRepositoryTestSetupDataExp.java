package jp.co.ntt.atrs.domain.repository.member;


import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;




public class MemberRepositoryTestSetupDataExp {
	
	public enum MemberSetupList {
		Setup01("0000000001","電電","花子","デンデン","ハナコ","1979-01-25","F","111-1111-1111","1111111","東京都港区港南Ｘ－Ｘ－Ｘ","xxxxxx@ntt.co.jp","1111111111111111","VIS","01/20");
		
		private final String customer_no;
		private final String kanji_family_name;
		private final String kanji_given_name;
		private final String kana_family_name;
		private final String kana_given_name;
		private final String birthday;
		private final String gender;
		private final String tel;
		private final String zip_code;
		private final String address;
		private final String mail;
		private final String credit_no;
		private final String credit_type_cd;
		private final String credit_term;
		
		private MemberSetupList(final String customer_no,
								final String kanji_family_name,
								final String kanji_given_name,
								final String kana_family_name,
								final String kana_given_name,
								final String birthday,
								final String gender,
								final String tel,
								final String zip_code,
								final String address,
								final String mail,
								final String credit_no,
								final String credit_type_cd,
								final String credit_term)
		{
			this.customer_no = customer_no;
			this.kanji_family_name = kanji_family_name;
			this.kanji_given_name = kanji_given_name;
			this.kana_family_name = kana_family_name;
			this.kana_given_name = kana_given_name;
			this.birthday = birthday;
			this.gender = gender;
			this.tel = tel;
			this.zip_code = zip_code;
			this.address = address;
			this.mail = mail;
			this.credit_no = credit_no;
			this.credit_type_cd = credit_type_cd;
			this.credit_term = credit_term;
		}

		public String getCustomer_no() {
			return customer_no;
		}

		public String getKanji_family_name() {
			return kanji_family_name;
		}

		public String getKanji_given_name() {
			return kanji_given_name;
		}

		public String getKana_family_name() {
			return kana_family_name;
		}

		public String getKana_given_name() {
			return kana_given_name;
		}

		public Date getBirthday() {
			Date Datebirthday = null;
			try {
				String strDate = birthday;
				SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
				Datebirthday = sdFormat.parse(strDate);
			} catch (ParseException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return Datebirthday;
		}

		public String getGender() {
			return gender;
		}

		public String getTel() {
			return tel;
		}

		public String getZip_code() {
			return zip_code;
		}

		public String getAddress() {
			return address;
		}

		public String getMail() {
			return mail;
		}

		public String getCredit_no() {
			return credit_no;
		}

		public String getCredit_type_cd() {
			return credit_type_cd;
		}

		public String getCredit_term() {
			return credit_term;
		}	
	}
	
	public enum MemberLoginSetupList {
		Setup01("0000000001","$2a$10$AUvby7NA4i5MpFbks.lYd.pgUCv7Ze32FdnQFE03N4EeEePqVAH0C","$2a$10$bJ8HB/5LaMN/ntOQHpgiAu8tfG1Y/rP21MaoK4RBenghxcbhrLW5C","2017-09-13 16:47:04.283",false);
		
		private final String customer_no;
		private final String password;
		private final String last_password;
		private final String login_date_time;
		private final boolean login_flg;
		
		private MemberLoginSetupList(final String customer_no, final String password, final String last_password, String login_date_time, boolean login_flg) {
			// TODO 自動生成されたコンストラクター・スタブ
			this.customer_no = customer_no;
			this.password = password;
			this.last_password = last_password;
			this.login_date_time = login_date_time;
			this.login_flg = login_flg;
		}

		public String getCustomer_no() {
			return customer_no;
		}

		public String getPassword() {
			return password;
		}

		public String getLast_password() {
			return last_password;
		}

		public Timestamp getLogin_date_time() {
			Timestamp datetime = null;
			try {		
				datetime = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(login_date_time).getTime());
			} catch (ParseException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return datetime;
		}

		public boolean Login_flg() {
			return login_flg;
		}
	}
}
