package jp.co.ntt.atrs.domain.repository.flight;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ntt.atrs.domain.model.Airport;
import jp.co.ntt.atrs.domain.model.BoardingClassCd;
import jp.co.ntt.atrs.domain.model.FareTypeCd;
import jp.co.ntt.atrs.domain.model.Flight;
import jp.co.ntt.atrs.domain.model.Route;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/test-context.xml"})
@Transactional
@Rollback
public class FlightRepositoryTest {

	@Inject
	FlightRepository target;
	
	@Inject
	JdbcTemplate jdbctemplate;
	
	
	@Before
	public void setUp() throws Exception{
		
		//データの初期化
		//jdbctemplate.update("DELETE FROM ROUTE");
		//jdbctemplate.update("DELETE FROM FLIGHT_MASTER");
		//jdbctemplate.update("DELETE FROM FARE_TYPE");
		//jdbctemplate.update("DELETE FROM FLIGHT");
		
		//テストデータの作成
		/*
		List<Route> testRouteList = createList();
		String flightTime = "0100";
		
		for(int i = 0; i < testRouteList.size(); i++){
			Route testRoute = testRouteList.get(i);
			
			jdbctemplate.update("INSERT INTO ROUTE(ROUTE_NO,DEP_AIRPORT_CD,ARR_AIRPORT_CD,FLIGHT_TIME,BASIC_FARE) VALUES(?,?,?,?,?)"
					,testRoute.getRouteNo()
					,testRoute.getDepartureAirport()
					,flightTime
					,testRoute.getArrivalAirport()
					,testRoute.getBasicFare());
		}
		*/
	}
	
	@Test
	public void testfindByVacantSeatSearchCriteria1(){
		
		//引数データの作成
		Date depDate = new Date();
		try{
			//depDate
			String strDate = "2017-09-13";
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
			depDate = sdFormat.parse(strDate);
		}catch (ParseException e){
			e.printStackTrace();
		}
		//route
		Route route = new Route();
		route.setRouteNo(1);
		route.setDepartureAirport(setUpDepAirport());
		route.setArrivalAirport(setUpArrAirport());
		route.setBasicFare(20600);

		//boardingClass
		BoardingClassCd boardingClass = BoardingClassCd.N;
		
		//biforDayNum
		Integer beforeDayNum = 2;
		
		//fareTypeList
		List<FareTypeCd> fareTypeList = new ArrayList<>();
		
		for(FareTypeCd faretype : FareTypeCd.values()){
			fareTypeList.add(faretype);
		}
			
		VacantSeatSearchCriteriaDto testData = new VacantSeatSearchCriteriaDto(depDate, route, boardingClass, beforeDayNum, fareTypeList);
		
		List<Flight> actual = target.findByVacantSeatSearchCriteria(testData);
		
		assertThat(actual.size(), is(90));
	}
	
	
	
	private List<Route> createList(){
		
		List<Route> testRouteList = new ArrayList<>();
		
		//データ膨らまし
		for( int i = 0; i < 10; i++){
			
			Route r1 = new Route();
			
			r1.setRouteNo(i);
			r1.setDepartureAirport(setUpDepAirport());
			r1.setArrivalAirport(setUpArrAirport());
			r1.setBasicFare(100);
			
			testRouteList.add(r1);
		}
		
		//検索用
		Route r2 = new Route();
		
		r2.setRouteNo(11);
		r2.setDepartureAirport(setUpDepAirport());
		r2.setArrivalAirport(setUpArrAirport());
		r2.setBasicFare(100);
		
		testRouteList.add(r2);
		
		return testRouteList;
	}
	
	private Airport setUpDepAirport(){
		
		Airport airport = new Airport();
		
		airport.setCode("HND");
		airport.setName("東京(羽田)");
		airport.setDisplayOrder(1);
		
		return airport;
	}
	
	private Airport setUpArrAirport(){
		
		Airport airport = new Airport();
		
		airport.setCode("ITM");
		airport.setName("大阪(伊丹)");
		airport.setDisplayOrder(3);
		
		return airport;
	}
	
}
