package jp.co.ntt.atrs.domain.repository.faretype;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ntt.atrs.domain.model.FareType;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = {"classpath:META-INF/spring/test-context-dbunit.xml"})
@Rollback
public class FareTypeRepositoryTest extends DataSourceBasedDBTestCase {
	
	private final String RESOURCE_DIR = "src/test/resources/META-INF/dbunit/";
	
	@Inject
	private TransactionAwareDataSourceProxy dataSourceTest;
	
	@Inject
	FareTypeRepository target;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		IDataSet databaseDataSet = getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("fare_type");
		
		//IDataSet expectedDataSet = new FlatXmlDataSet(new File(RESOURCE_DIR + "afterupdate_Unitdata.xml"));
		IDataSet expectedDataSet =new FlatXmlDataSetBuilder().build(new File(RESOURCE_DIR + "afterupdate_Unitdata.xml")); 
		ITable expectedTable = expectedDataSet.getTable("fare_type");
		
		Assertion.assertEquals(expectedTable, actualTable);
	}
	
	@Override
	protected void setUpDatabaseConfig(DatabaseConfig config) {
		// TODO 自動生成されたメソッド・スタブ
		config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
	}
	
	@Override
	protected DataSource getDataSource(){
		return dataSourceTest;
	}
	
	@Override
	protected IDataSet getDataSet() throws Exception {
		//return new FlatXmlDataSet(new File(RESOURCE_DIR + "test_data.xml"));
		return new FlatXmlDataSetBuilder().build(new FileInputStream(RESOURCE_DIR + "test_data.xml"));
	}
	
	@Test
	public void testFindAll1() {
		List<FareType> actFareType;
		
		actFareType = target.findAll();
		
		assertThat(actFareType.size(), is(10));
		
	}

}
