package jp.co.ntt.atrs.domain.repository.route;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ntt.atrs.domain.model.Airport;
import jp.co.ntt.atrs.domain.model.Route;
import jp.co.ntt.atrs.domain.repository.route.RouteRepositoryTestVerJunitSetupData.RouteSetupList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/test-context.xml"})
@Transactional
@Rollback
public class RouteRepositoryTestVerJunit {

	@Inject
	RouteRepository target;
	
	@Inject
	JdbcTemplate jdbctemplate;
	
	@Before
	public void setUp() throws Exception{
		
		//１、routeとflight_master間の参照制約を削除
		//２、routeを削除
		//３、routeをセット
		//４、routeとflight_master間の参照制約を追加
		
		//INIT_DB
		jdbctemplate.execute("ALTER TABLE FLIGHT_MASTER DROP CONSTRAINT FK_FLIGHT_MASTER_1");
		jdbctemplate.update("DELETE FROM ROUTE");
		
		System.out.println("OK dbinit");
		
		//SETUP_DB(ROUTE)
		for(RouteSetupList route : RouteSetupList.values()){
			jdbctemplate.update("INSERT INTO ROUTE VALUES (?,?,?,?,?)"
					,route.getRoute_no()
					,route.getDep_airport_cd()
					,route.getArr_airport_cd()
					,route.getFlight_time()
					,route.getBasic_fare());
		}

		System.out.println("OK setup");
		//ADD CONSTRAINT KEY
		jdbctemplate.execute("ALTER TABLE FLIGHT_MASTER ADD CONSTRAINT FK_FLIGHT_MASTER_1 FOREIGN KEY (ROUTE_NO) REFERENCES ROUTE (ROUTE_NO)");
		
		//Act DB_data
		List<ActRoute> actRoute = jdbctemplate.query("SELECT * FROM ROUTE", new RowMapper<ActRoute>() {
			public ActRoute mapRow(ResultSet rs, int rowNum) throws SQLException {
				ActRoute actroute = new ActRoute();
				actroute.setRouteNo(rs.getInt("route_no"));
				
				Airport Depairport = new Airport();
				Depairport.setCode(rs.getString("dep_airport_cd"));
				actroute.setDepartureAirport(Depairport);
				
				Airport Arrairport = new Airport();
				Arrairport.setCode(rs.getString("arr_airport_cd"));
				actroute.setArrivalAirport(Arrairport);
				
				actroute.setFlightTime(rs.getString("flight_time"));
				
				actroute.setBasicFare(rs.getInt("basic_fare"));
				return actroute;
			}
		});
		
		/* 値の確認
		for(ActRoute actroute : actRoute){
			System.out.println(actroute.getRouteNo());
			System.out.println(actroute.getDepartureAirport().getCode());
			System.out.println(actroute.getArrivalAirport().getCode());
			System.out.println(actroute.getFlightTime());
			System.out.println(actroute.getBasicFare());
		}
		*/
		int i = 0;
		for(RouteSetupList route : RouteSetupList.values()){
			
			assertThat(actRoute.get(i).getRouteNo(), is(route.getRoute_no()));
			assertThat(actRoute.get(i).getDepartureAirport().getCode(), is(route.getDep_airport_cd()));
			assertThat(actRoute.get(i).getArrivalAirport().getCode(), is(route.getArr_airport_cd()));
			assertThat(actRoute.get(i).getFlightTime(), is(route.getFlight_time()));
			assertThat(actRoute.get(i).getBasicFare(), is(route.getBasic_fare()));
			i++;
		}
		
	}
	

	@Test
	public void testFindAll() {
		
		List<Route> actRoute;
		actRoute = target.findAll();
		
		assertThat(actRoute.size(), is(20));
	}

}