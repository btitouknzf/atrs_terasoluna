package jp.co.ntt.atrs.domain.repository.route;

public class RouteRepositoryTestVerJunitSetupData {
	
	public enum AirportSetupList {
		HND("HND","東京(羽田)",1),
		NRT("NRT","東京(成田)",2),
		ITM("ITM","大阪(伊丹)",3),
		KIX("KIX","大阪(関西)",4),
		UKB("UKB","大阪(神戸)",5),
		SPK("SPK","札幌(千歳)",6),
		NGO("NGO","名古屋(中部)",7),
		FUK("FUK","福岡",8),
		OKA("OKA","沖縄",9),
		OKD("OKD","札幌(丘珠)",100),
		RIS("RIS","利尻",101),
		WKJ("WKJ","稚内",102),
		MBE("MBE","オホーツク紋別",103),
		MMB("MMB","女満別",104),
		AKJ("AKJ","旭川",105),
		SHB("SHB","根室中標津",106),
		KUH("KUH","釧路",107),
		HKD("HKD","函館",108),
		ONJ("ONJ","大館能代",109),
		AXT("AXT","秋田",110),
		SYO("SYO","庄内",111),
		SDJ("SDJ","仙台",112),
		FKS("FKS","福島",113),
		OIM("OIM","大島",114),
		MYE("MYE","三宅島",115),
		HAC("HAC","八丈島",116),
		KIJ("KIJ","新潟",117),
		TOY("TOY","富山",118),
		KMQ("KMQ","小松",119),
		NTQ("NTQ","能登",120),
		OKJ("OKJ","岡山",121),
		HIJ("HIJ","広島",122),
		UBJ("UBJ","山口宇部",123),
		TTJ("TTJ","鳥取",124),
		YGJ("YGJ","米子",125),
		IWJ("IWJ","萩・石見",126),
		TAK("TAK","高松",127),
		TKS("TKS","徳島",128),
		MYJ("MYJ","松山",129),
		KCZ("KCZ","高知",130),
		KKJ("KKJ","北九州",131),
		HSG("HSG","佐賀",132),
		OIT("OIT","大分",133),
		KMJ("KMJ","熊本",134),
		NGS("NGS","長崎",135),
		TSJ("TSJ","対馬",136),
		FUJ("FUJ","五島福江",137),
		KMI("KMI","宮崎",138),
		KOJ("KOJ","鹿児島",139),
		MMY("MMY","宮古",140),
		ISG("ISG","石垣",141);
		
		private final String airport_cd;
		private final String airport_name;
		private final int display_order;
		
		private AirportSetupList(final String airport_cd, final String airport_name, final int display_order){
			this.airport_cd = airport_cd;
			this.airport_name = airport_name;
			this.display_order = display_order;
		}
		
		public String getAirport_cd(){
			return this.airport_cd;
		}
		
		public String getAirport_name(){
			return this.airport_name;
		}
		
		public int getDisplay_order(){
			return this.display_order;
		}
	}
	
	
	public enum RouteSetupList {
		HND_ITM(1,"HND","ITM","0100",20600),
		HND_MBE(2,"HND","MBE","0130",30700),
		FUK_NRT(3,"FUK","NRT","0130",33700),
		ISG_OIM(4,"ISG","OIM","0230",37400),
		KIX_SPK(5,"KIX","SPK","0150",37900),
		UKB_HSG(6,"UKB","HSG","0120",20100),
		ITM_OKA(7,"ITM","OKA","0205",31400),
		NGO_SPK(8,"NGO","SPK","0140",34200),
		OKD_KOJ(9,"OKD","KOJ","0335",52400),
		ISG_FUK(10,"ISG","FUK","0345",46400),
		HND_SPK(11,"HND","SPK","0130",30700),
		HND_OKA(12,"HND","OKA","0230",37400),
		HND_KIX(13,"HND","KIX","0115",20600),
		HND_FUK(14,"HND","FUK","0145",33700),
		ITM_HND(15,"ITM","HND","0110",20600),
		SPK_HND(16,"SPK","HND","0130",30700),
		OKA_HND(17,"OKA","HND","0220",37400),
		KIX_HND(18,"KIX","HND","0105",20600),
		FUK_HND(19,"FUK","HND","0135",33700),
		SPK_KIX(20,"SPK","KIX","0150",37900);
		
		private final int route_no;
		private final String dep_airport_cd;
		private final String arr_airport_cd;
		private final String flight_time;
		private final int basic_fare;
		
		private RouteSetupList(final int route_no, final String dep_airport_cd, final String arr_airport_cd, final String flight_time, final int basic_fare){
			this.route_no = route_no;
			this.dep_airport_cd = dep_airport_cd;
			this.arr_airport_cd = arr_airport_cd;
			this.flight_time = flight_time;
			this.basic_fare = basic_fare;
		}
		
		public int getRoute_no(){
			return this.route_no;
		}
		
		public String getDep_airport_cd(){
			return this.dep_airport_cd;
		}
		
		public String getArr_airport_cd(){
			return this.arr_airport_cd;
		}
		
		public String getFlight_time(){
			return this.flight_time;
		}
		
		public int getBasic_fare(){
			return this.basic_fare;
		}
		
	}
	
	
}
