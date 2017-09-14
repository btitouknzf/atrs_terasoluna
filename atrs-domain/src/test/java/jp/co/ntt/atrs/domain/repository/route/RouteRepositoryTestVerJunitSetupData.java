package jp.co.ntt.atrs.domain.repository.route;

public class RouteRepositoryTestVerJunitSetupData {

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
