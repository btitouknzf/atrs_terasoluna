package jp.co.ntt.atrs.domain.repository.boardingclass;

import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

public class DbsetupOperations {
	
	public static final Operation INIT_TABLE =
			Operations.sequenceOf(
					Operations.deleteAllFrom("boarding_class"));

	public static final Operation INSERT_BOARDING_CLASS =
			Operations.sequenceOf(
					Operations.insertInto("boarding_class")
						.columns("boarding_class_cd","boarding_class_name","extra_charge","display_order")
						.values("N","一般席","0","1")
						.values("S","特別席","5000","2")
						.build()
					);
	
	public static final Operation INSERT_BOADING_CLASS =
			Operations.sequenceOf(
					Operations.insertInto("boarding_class")
					//row0
					.row()
						.column("boarding_class_cd","N")
						.column("boarding_class_name","一般席")
						.column("extra_charge","0")
						.column("display_order","1")
						.end()
					//row0
					.row()
						.column("boarding_class_cd","S")
						.column("boarding_class_name","特別席")
						.column("extra_charge","5000")
						.column("display_order","2")
						.end()
						
					.build()
					);
	
	
}
