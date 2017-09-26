package jp.co.ntt.atrs.domain.repository.member;

import java.io.InputStream;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.springframework.core.io.Resource;

import com.github.springtestdbunit.dataset.AbstractDataSetLoader;

public class XlsDataSetLoader extends AbstractDataSetLoader {

    //Dbunitでxml形式以外のフォーマットを使用する場合に必要
    @Override
    protected IDataSet createDataSet(Resource resource) throws Exception {
        // TODO Auto-generated method stub
        try (InputStream inputStream = resource.getInputStream()) {
            return new XlsDataSet(inputStream);
        }
    }

}
