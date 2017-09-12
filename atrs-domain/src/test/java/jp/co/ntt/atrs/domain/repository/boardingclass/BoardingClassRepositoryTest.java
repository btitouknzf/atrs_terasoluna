package jp.co.ntt.atrs.domain.repository.boardingclass;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.assertj.db.api.Assertions.assertThat;

import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.assertj.db.type.Table;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.operation.Operation;

import jp.co.ntt.atrs.domain.model.BoardingClass;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/test-context.xml"})
@Transactional
@Rollback
public class BoardingClassRepositoryTest {

	@Inject
	DataSource dataSource;
	
	@Inject
	BoardingClassRepository target;
	
	private static DbSetupTracker TRACKER = new DbSetupTracker();
	
	@Before
	public void setUp(){
		Destination dest = new DataSourceDestination(dataSource);
		//dbsetup
		/* 
		Operation ops = Operations.sequenceOf(DbsetupOperations.INIT_TABLE,
												DbsetupOperations.INSERT_BOARDING_CLASS);
		*/
		
		//仮
		Operation ops = Operations.sql("update boarding_class set boarding_class_name = '一般席' where display_order = 1");
		
		DbSetup dbSetup = new DbSetup(dest, ops);
		TRACKER.launchIfNecessary(dbSetup);
		
		//DBcompare
		Table table = new Table(dataSource, "boarding_class");
		//check row1 values
		assertThat(table).row(0)
						.value().isEqualTo("N")
						.value().isEqualTo("一般席")
						.value().isEqualTo(0)
						.value().isEqualTo(1);
		//check row1 values
		assertThat(table).row(1)
						.value().isEqualTo("S")
						.value().isEqualTo("特別席")
						.value().isEqualTo(5000)
						.value().isEqualTo(2);
		
	}
	
	@Test
	public void testFindAll() {
		
		List<BoardingClass> actdata;
		actdata = target.findAll();
		
		assertThat(actdata.size(), is(2));
		
		TRACKER.skipNextLaunch();
	}

}
