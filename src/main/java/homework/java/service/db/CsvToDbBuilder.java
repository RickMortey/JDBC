package homework.java.service.db;

import java.io.FileReader;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import homework.java.service.dao.AbstractDao;
import homework.java.service.db.Jdbc;

@RequiredArgsConstructor
public class CsvToDbBuilder<DataType, DaoType extends AbstractDao<DataType>> {
    private Class<? extends DataType> dataType = null;
    private Class<? extends DaoType> daoType = null;

//    public CsvToDbBuilder(Class<? extends DataType> givenDataType, Class<? extends DaoType> givenDaoType) {
//        this.dataType = givenDataType;
//        this.daoType = givenDaoType;
//
//    }

    public CsvToDbBuilder<DataType, DaoType> usingDataType(Class<? extends DataType> type) {
        this.dataType = type;
        return this;
    }

    public CsvToDbBuilder<DataType, DaoType> usingDaoType(Class<? extends DaoType> type) {
        this.daoType = type;
        return this;
    }

    public void build(String csvPath, Jdbc source) throws Exception {
        List<DataType> data = new CsvToBeanBuilder<DataType>(new FileReader(csvPath)).withType(this.dataType).build().parse();

        this.daoType.getConstructor(Jdbc.class).newInstance(source).save(data);
    }
}
