package com.telecosmart.ratingengine;
import com.telecomsmart.model.*;
import com.telecomsmart.dao.*;
import java.util.List;
/**
 *
 * @author Mahmoud
 */

public class CdrHandling {
    public static List<CdrRecord> getCdrs() {
        return CdrRecordDao.getAndDeleteCdrRecords();
    }
}