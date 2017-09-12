package jp.co.ntt.atrs.domain.repository.route;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;
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
import jp.co.ntt.atrs.domain.model.Route;
import jp.co.ntt.atrs.domain.repository.route.RouteRepositoryTestVerJunitSetupData.AirportSetupList;
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
		
		//expect DB_data
		
	}
	

	@Test
	public void testFindAll() {
		
		List<Route> actRoute;
		actRoute = target.findAll();
		
		assertThat(actRoute.size(), is(20));
	}

}