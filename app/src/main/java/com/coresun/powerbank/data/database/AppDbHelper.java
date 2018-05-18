package com.coresun.powerbank.data.database;

/**
 * Created by Administrator on 2017/4/14 0014.
 */

//
//public class AppDbHelper implements DbHelper {
//
//    private final DaoSession mDaoSession;
//    private final String SQL_DISTINCT_ENAME = "SELECT DISTINCT "
//                                                + CachePswBeanDao.Properties.CachePsw.columnName
//                                                + " FROM "
//                                                + CachePswBeanDao.TABLENAME;
//
//    @Inject
//    public AppDbHelper(DbOpenHelper dbOpenHelper) {
//        mDaoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
//    }
//
//    @Override
//    public void saveCachePsw(CachePswBean cachePswBean) {
//        mDaoSession.getCachePswBeanDao().insert(cachePswBean);
//    }
//
//    @Override
//    public Observable<List<CachePswBean>> getAllCachePsw(final int offset) {
//
//        return Observable.fromCallable(new Callable<List<CachePswBean>>() {
//            @Override
//            public List<CachePswBean> call() throws Exception {
//                return  mDaoSession.getCachePswBeanDao()
//                        .queryBuilder()
//                        .offset((offset-1)*PAGE_COUNT)
//                        .limit(PAGE_COUNT)
//                        .build()
//                        .list();
//            }
//        });
//    }
//
//    @Override
//    public void deleteCachePswByKey(String id) {
//        mDaoSession.getCachePswBeanDao().deleteByKey(id);
//    }
//
//}
