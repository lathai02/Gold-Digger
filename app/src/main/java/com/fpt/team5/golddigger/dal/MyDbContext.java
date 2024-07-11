package com.fpt.team5.golddigger.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


import com.fpt.team5.golddigger.Model.Budget;
import com.fpt.team5.golddigger.Model.Category;
import com.fpt.team5.golddigger.Model.Notification;
import com.fpt.team5.golddigger.Model.SubCategory;
import com.fpt.team5.golddigger.Model.Transaction;
import com.fpt.team5.golddigger.Model.User;
import com.fpt.team5.golddigger.NotificationActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyDbContext extends SQLiteOpenHelper {
    private static final String TAG = "ThaiLAError";
    private static final String DATABASE_NAME = "GoldDigger.db";
    private static final String TABLE_TRANSACTIONS = "transactions";
    private static final String TABLE_CATEGORY = "categories";
    private static final String TABLE_USER = "users";
    private static final String TABLE_SUBCATEGORY = "subCategories";
    private static final String TABLE_BUDGET = "budgets";
    private static final String TABLE_NOTIFICATION = "notifications";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_IMAGE_ID = "imageId";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_ID = "id";
    private static final String PATTERN = "yyyy-MM-dd";
    private static final String PATTERN_ORIGIN = "yyyy-MM-dd HH:mm:ss";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_CATEGORY_ID = "categoryId";
    private static final String COLUMN_SUBCATEGORY_ID = "subCategoryId";
    private static final String COLUMN_USER_ID = "userId";
    private static final String COLUMN_CREATE_DATE = "createDate";
    private static final String COLUMN_DUE_DATE = "dueDate";
    private static int DATABASE_VERSION = 1;

    public MyDbContext(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateTransition = "CREATE TABLE " +
                TABLE_TRANSACTIONS +
                "(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Title TEXT," +
                "UserId INTEGER," +
                "Description TEXT," +
                "CategoryId INTEGER," +
                "SubCategoryId INTEGER," +
                "Amount DOUBLE," +
                "CreateDate DATETIME," +
                "DueDate DATETIME," +
                "FOREIGN KEY(UserId) REFERENCES Users(Id)," +
                "FOREIGN KEY(CategoryId) REFERENCES Categories(Id)," +
                "FOREIGN KEY(SubCategoryId) REFERENCES SubCategories(Id))";
        db.execSQL(sqlCreateTransition);

        String sqlCreateCategory = "CREATE TABLE " +
                TABLE_CATEGORY +
                "(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ImageId INTEGER," +
                "Title TEXT)";
        db.execSQL(sqlCreateCategory);

        String sqlCreateSubCategory = "CREATE TABLE " +
                TABLE_SUBCATEGORY +
                "(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "CategoryId INTEGER," +
                "ImageId INTEGER," +
                "Title TEXT," +
                "FOREIGN KEY(CategoryId) REFERENCES Categories(Id))";
        db.execSQL(sqlCreateSubCategory);

        String sqlCreateUser = "CREATE TABLE " +
                TABLE_USER +
                "(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Email INTEGER," +
                "Phone TEXT," +
                "Name TEXT," +
                "ImageId INTEGER," +
                "Password TEXT)";
        db.execSQL(sqlCreateUser);

        String sqlCreateBudget = "CREATE TABLE " +
                TABLE_BUDGET +
                "(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Title TEXT," +
                "Amount DOUBLE," +
                "UserId INTEGER," +
                "CreateDate DATETIME," +
                "FOREIGN KEY(UserId) REFERENCES Users(Id))";
        db.execSQL(sqlCreateBudget);

        String sqlCreateNotification = "CREATE TABLE " +
                TABLE_NOTIFICATION +
                "(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Title TEXT," +
                "UserId INTEGER," +
                "CreateDate DATETIME," +
                "FOREIGN KEY(UserId) REFERENCES Users(Id))";
        db.execSQL(sqlCreateNotification);

        String sqlInsertDefaultData = "INSERT INTO " + TABLE_CATEGORY + " (Title,ImageId) VALUES " +
                "('Income',1)," +
                "('Expense',2)," +
                "('Borrow',3)," +
                "('Lending',4)";
        db.execSQL(sqlInsertDefaultData);

        String sqlInsertDefaultUser = "INSERT INTO " + TABLE_USER + " (Email,Phone,Name,Password,ImageId) VALUES " +
                "('thang@gmail.com','0123456789','Thang','123456',1)";
        db.execSQL(sqlInsertDefaultUser);

        String sqlInsertDefaultNoti = "INSERT INTO " + TABLE_NOTIFICATION + " (Title,UserId,CreateDate) VALUES " +
                "('Ban co thong bao ve khoan vay','1','07/07/2024')," +
                "('Ban co thong bao ve khoan vay 2','1','07/07/2024')";
        db.execSQL(sqlInsertDefaultNoti);

        String sqlInsertDefaultBudget = "INSERT INTO " + TABLE_BUDGET + " (Title,Amount,UserId,CreateDate) VALUES " +
                "('Tien tkhoan',23000000,1,'2003-15-10')";
        db.execSQL(sqlInsertDefaultBudget);

        String sqlInsertDefaultSubCategories = "INSERT INTO " + TABLE_SUBCATEGORY + " (CategoryId, Title) VALUES " +
                "(1, 'Salary')," +
                "(1, 'Family')," +
                "(1, 'Bonus')," +
                "(1, 'Interest')," +
                "(1, 'Others')," +
                "(2, 'Food and Dining')," +
                "(2, 'Utilities')," +
                "(2, 'Transport')," +
                "(2, 'Clothing')," +
                "(2, 'Personal')," +
                "(2, 'Entertainment')," +
                "(2, 'Home')," +
                "(2, 'Kids')," +
                "(3, 'Borrow')," +
                "(4, 'Lending')";
        db.execSQL(sqlInsertDefaultSubCategories);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

//    public Cursor getBudgetByUserId(int userId) {
//        String sql = "SELECT * FROM " + TABLE_BUDGET + " WHERE userId = ?";
//        return getReadableDatabase().rawQuery(sql, new String[]{String.valueOf(userId)});
//    }

    public long addBudget(Budget budget) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Title", budget.getTitle());
        values.put("Amount", budget.getAmount());
        values.put("UserId", budget.getUserId());
        values.put("CreateDate", budget.getCreateDate());

        long newRowId = db.insert(TABLE_BUDGET, null, values);
        db.close();
        return newRowId;
    }


    public Cursor getAllSubCateByCateId(int cateId) {
        String sql = "SELECT * FROM " + TABLE_SUBCATEGORY + " WHERE CategoryId = ?";
        return getReadableDatabase().rawQuery(sql, new String[]{String.valueOf(cateId)});
    }

    public Cursor getBudgetByUserId(int userId) {
        String sql = "SELECT * FROM " + TABLE_BUDGET + " WHERE UserId = ?";
        return getReadableDatabase().rawQuery(sql, new String[]{String.valueOf(userId)});
    }

    public Cursor getAllCate() {
        String sql = "SELECT * FROM " + TABLE_CATEGORY ;
        return getReadableDatabase().rawQuery(sql, null);
    }

    public Cursor getAllContact() {
        String sql = "select * from " + TABLE_CATEGORY;
        return getReadableDatabase().rawQuery(sql, null);
    }

    public Cursor getAllUser() {
        String sql = "select * from " + TABLE_USER;
        return getReadableDatabase().rawQuery(sql, null);
    }

    public int checkLogin(String emailPhone, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER,
                new String[]{"id"},
                "email" + "=? AND " + "password" + "=?",
                new String[]{emailPhone, password},
                null, null, null);

        int userId = 0;
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(0); // Use column index directly
        }
        cursor.close();
        db.close();
        return userId;
    }

    private boolean emailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER,
                new String[]{"email"},
                "email" + "=?",
                new String[]{email},
                null, null, null);

        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public User getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER,
                new String[]{COLUMN_EMAIL,COLUMN_PHONE, COLUMN_NAME, COLUMN_PASSWORD, COLUMN_IMAGE_ID},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);

        User user = null;
        if (cursor.moveToFirst()) {
            String email = cursor.getString(0);
            String phone = cursor.getString(1);
            String name = cursor.getString(2);
            String password = cursor.getString(3);
            int imageId = cursor.getInt(4);

            user = new User(id, email, phone, name, password, imageId); // Use constructor matching User class
        }
        cursor.close();
        db.close();
        return user;
    }

    public int addUser(User u) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if email already exists
        if (emailExists(u.getEmail())) {
            db.close();
            return -1; // Indicate that the email already exists
        }
        try{
            ContentValues values = new ContentValues();
            values.put(COLUMN_EMAIL, u.getEmail());
            values.put(COLUMN_NAME, u.getName());
            values.put(COLUMN_PASSWORD, u.getPassword());
            values.put(COLUMN_IMAGE_ID, u.getImageId());
            values.put(COLUMN_PHONE, u.getPhone());

            // Insert the new row and get the new row ID
            long newRowId = db.insert(TABLE_USER, null, values);
            db.close();

            return (newRowId == -1) ? -1 : (int) newRowId;
        }catch (Exception e){
            db.close();
            return -1;
        }

    }

    public boolean updateUser(int id, String name, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PHONE, phone);

        // Update the user with the specified ID
        int rowsAffected = db.update(TABLE_USER,
                values,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});

        db.close();
        return rowsAffected > 0;
    }

    public boolean checkEmailExist(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER,
                new String[]{COLUMN_EMAIL},
                COLUMN_EMAIL + "=?",
                new String[]{email},
                null, null, null);

        boolean exists = (cursor.getCount() > 0); // Check if at least one record is returned
        cursor.close();
        db.close();
        return exists;
    }

    public boolean resetPassword(String email, String newPass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, newPass);

        int rowsAffected = db.update(TABLE_USER,
                values,
                COLUMN_EMAIL + "=?",
                new String[]{email});

        db.close();
        return rowsAffected > 0; // Return true if at least one row was updated
    }

    public void updateCategoryImageId(int categoryId, int imageId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("imageId", imageId);

        db.update(TABLE_CATEGORY, values, "id = ?", new String[]{String.valueOf(categoryId)});
        db.close();
    }

    public void updateSubcategoryImageId(int subcategoryId, int imageId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("imageId", imageId);

        db.update(TABLE_SUBCATEGORY, values, "id = ?", new String[]{String.valueOf(subcategoryId)});
        db.close();
    }

    public static String convertDateFormat(String dateString) {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = inputDateFormat.parse(dateString);
            String result =  outputDateFormat.format(date);
            return ("'" + result + "'");
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getCategoryByName(String categoryName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CATEGORY,
                new String[]{COLUMN_ID},
                COLUMN_TITLE + "=?",
                new String[]{categoryName},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            int cateId = cursor.getInt(0);
            cursor.close();
            db.close();
            return cateId;
        } else {
            db.close();
            return -1;
        }
    }

    public int getSubCategoryByName(String subCategory) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SUBCATEGORY,
                new String[]{COLUMN_ID},
                COLUMN_TITLE + "=?",
                new String[]{subCategory},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            int subcateId = cursor.getInt(0);
            cursor.close();
            db.close();
            return subcateId;
        } else {
            db.close();
            return -1;
        }
    }

    public boolean addTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, transaction.getTitle());
        values.put(COLUMN_DESCRIPTION, transaction.getDescription());
        values.put(COLUMN_AMOUNT, transaction.getAmount());
        values.put(COLUMN_CATEGORY_ID, transaction.getCategoryId());
        values.put(COLUMN_SUBCATEGORY_ID, transaction.getSubCategoryId());
        values.put(COLUMN_USER_ID, transaction.getUserId());
        values.put(COLUMN_CREATE_DATE, transaction.getCreateDate());

        long result = db.insert(TABLE_TRANSACTIONS, null, values);
        db.close();

        return result != -1;
    }

    public double getBudgetAmountByUserId(int userId) {
        String sql = "SELECT amount FROM " + TABLE_BUDGET + " WHERE userId = ?";
        Cursor cursor = getReadableDatabase().rawQuery(sql, new String[]{String.valueOf(userId)});

        double budgetAmount = 0;
        if (cursor.moveToFirst()) {
            budgetAmount = cursor.getDouble(0);
        }
        cursor.close();
        return budgetAmount;
    }

    public boolean updateBalance(String category, double amount,int userId) {
        double currentBudget = getBudgetAmountByUserId(userId);
        double newAmount = 0;
        if(category.equals("Income") || category.equals("Borrow")){
            newAmount =  currentBudget + amount;
        }else{
            newAmount = currentBudget - amount;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("amount", newAmount);

        int rowsAffected = db.update(TABLE_BUDGET, values, "userId = ?", new String[]{String.valueOf(userId)});
        db.close();

        return rowsAffected > 0;
    }

    public boolean addTransaction2(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, transaction.getTitle());
        values.put(COLUMN_DESCRIPTION, transaction.getDescription());
        values.put(COLUMN_AMOUNT, transaction.getAmount());
        values.put(COLUMN_CATEGORY_ID, transaction.getCategoryId());
        values.put(COLUMN_SUBCATEGORY_ID, transaction.getSubCategoryId());
        values.put(COLUMN_USER_ID, transaction.getUserId());
        values.put(COLUMN_CREATE_DATE, transaction.getCreateDate());
        values.put(COLUMN_DUE_DATE, transaction.getDueDate());

        long result = db.insert(TABLE_TRANSACTIONS, null, values);
        db.close();

        return result != -1;
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TRANSACTIONS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                int userId = cursor.getInt(2);
                String description = cursor.getString(3);
                int categoryId = cursor.getInt(4);
                int subcategoryId = cursor.getInt(5);
                double amount = cursor.getDouble(6);
                String createDate = cursor.getString(7);
                String dueDate = cursor.getString(8);

                Transaction transaction = new Transaction(id,title,userId,description,amount,categoryId,subcategoryId,createDate,dueDate);
                transactions.add(transaction);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return transactions;
    }



    public String getCategoryById(int categoryId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CATEGORY,
                new String[]{COLUMN_TITLE},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(categoryId)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            String categoryName = cursor.getString(0);
            cursor.close();
            db.close();
            return categoryName;
        } else {
            db.close();
            return "";
        }
    }

    public String getSubCategoryById(int subCategoryId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SUBCATEGORY,
                new String[]{COLUMN_TITLE},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(subCategoryId)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            String categoryName = cursor.getString(0);
            cursor.close();
            db.close();
            return categoryName;
        } else {
            db.close();
            return "";
        }
    }

    public Transaction getTransactionById(int transactionId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_TRANSACTIONS,
                null, // null selects all columns
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(transactionId)},
                null, // groupBy
                null, // having
                null  // orderBy
        );

        if (cursor != null) {
            cursor.moveToFirst();

            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            int userId = cursor.getInt(2);
            String description = cursor.getString(3);
            int categoryId = cursor.getInt(4);
            int subcategoryId = cursor.getInt(5);
            double amount = cursor.getDouble(6);
            String createDate = cursor.getString(7);
            String dueDate = cursor.getString(8);

            cursor.close();
            db.close();

            return new Transaction(id,title,userId,description,amount,categoryId,subcategoryId,createDate,dueDate);
        } else {
            db.close();
            return null;
        }
    }

    public boolean updateTransaction(Transaction transaction,int transactionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, transaction.getTitle());
        values.put(COLUMN_DESCRIPTION, transaction.getDescription());
        values.put(COLUMN_AMOUNT, transaction.getAmount());
        values.put(COLUMN_CATEGORY_ID, transaction.getCategoryId());
        values.put(COLUMN_SUBCATEGORY_ID, transaction.getSubCategoryId());
        values.put(COLUMN_USER_ID, transaction.getUserId());
        values.put(COLUMN_CREATE_DATE, transaction.getCreateDate());
        values.put(COLUMN_DUE_DATE, transaction.getDueDate());

        // Updating row
        int rowsAffected = db.update(TABLE_TRANSACTIONS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(transactionId)});

        db.close();

        return rowsAffected > 0;
    }

    public boolean deleteTransaction(int transactionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_TRANSACTIONS, COLUMN_ID + " = ?", new String[]{String.valueOf(transactionId)});
        db.close();
        return rowsAffected > 0;
    }

    public List<Transaction> getTransactionByUserId(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TRANSACTIONS + " WHERE userId = ?", new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                int userIdDB = cursor.getInt(2);
                String description = cursor.getString(3);
                int categoryId = cursor.getInt(4);
                int subcategoryId = cursor.getInt(5);
                double amount = cursor.getDouble(6);
                String createDate = cursor.getString(7);
                String dueDate = cursor.getString(8);

                Transaction transaction = new Transaction(id,title,userIdDB,description,amount,categoryId,subcategoryId,createDate,dueDate);
                transactions.add(transaction);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return transactions;
    }



    public List<Notification> getAllNotiByUserId(int userId) {
        List<Notification> notifications = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NOTIFICATION + " WHERE userId = ?", new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                int userIdDB = cursor.getInt(2);
                String createDate = cursor.getString(3);

                Notification noti = new Notification(id,title,userIdDB,createDate);
                notifications.add(noti);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return notifications;
    }


//
//    public List<Transaction> getTransactionByMonth(int month){
//        List<Transaction> list = new ArrayList<>();
//        try{
//            String selection = "strftime('%m', CreateDate) = ?";
//            String[] selectionArgs = {String.format("%02d", month)};
//            String orderBy = "CreateDate DESC";
//            SQLiteDatabase st = getReadableDatabase();
//            Cursor cs = st.query(TABLE_TRANSACTIONS,null,selection,selectionArgs,null,null,orderBy);
//            while (cs != null && cs.moveToNext()){
//                int id = cs.getInt(0);
//                String title = cs.getString(1);
//                String categoryId = cs.getString(2);
//                String price = cs.getString(3);
//                String isIncome = cs.getString(4);
//                String createDateStr = cs.getString(5);
//                String[] parts = createDateStr.split(" ");
//                String date = parts[0];
//                list.add(new Transaction(id,title,price,categoryId,isIncome,date));
//            }
//        }catch (Exception ex){
//            Log.e(TAG,"MyDbContext - getTransactionByDate - " + ex.getMessage());
//        }
//        return list;
//    }
//
//    public List<Transaction> getTransactionByFilter(int month,String type,String _categoryId){
//        List<Transaction> list = new ArrayList<>();
//        try{
//            String selection = "strftime('%m', CreateDate) = ?";
//            List<String> selectionArgsList = new ArrayList<>();
//            selectionArgsList.add(String.format("%02d", month));
//            String orderBy = "CreateDate DESC";
//            if(type != null){
//                selection = selection + " AND IsIncome = ?";
//                selectionArgsList.add(type);
//            }
//            SQLiteDatabase st = getReadableDatabase();
//            String[] selectionArgs = selectionArgsList.toArray(new String[0]);
//            Cursor cs = st.query(TABLE_TRANSACTIONS,null,selection,selectionArgs,null,null,orderBy);
//            while (cs != null && cs.moveToNext()){
//                int id = cs.getInt(0);
//                String title = cs.getString(1);
//                String categoryId = cs.getString(2);
//                String price = cs.getString(3);
//                String isIncome = cs.getString(4);
//                String createDateStr = cs.getString(5);
//                String[] parts = createDateStr.split(" ");
//                String date = parts[0];
//                list.add(new Transaction(id,title,price,categoryId,isIncome,date));
//            }
//        }catch (Exception ex){
//            Log.e(TAG,"MyDbContext - getTransactionByDate - " + ex.getMessage());
//        }
//        return list;
//    }

//    public List<Transaction> getTransactionByFilter(Date startDate, Date endDate, Boolean isIncomeFilter, String categoryIdFilter){
//        List<Transaction> list = new ArrayList<>();
//        String type = null;
//        if(isIncomeFilter){
//            type = "y";
//        }else if(!isIncomeFilter){
//            type = "N";
//        }
//        try{
//            String selection = null;
//            List<String> selectionArgsList = new ArrayList<>();
//            if(startDate != null){
//                selection = "CreateDate >= ?";
//                selectionArgsList.add(String.valueOf(startDate.getTime()));
//            }
//            if(endDate != null){
//                if(selection != null){
//                    selection = "CreateDate <= ?";
//                    selectionArgsList.add(String.valueOf(endDate.getTime()));
//                }else{
//                    selection = "AND CreateDate <= ?";
//                    selectionArgsList.add(String.valueOf(endDate.getTime()));
//                }
//            }
//            if(type != null){
//                if(selection != null){
//                    selection = "IsIncome = ?";
//                    selectionArgsList.add(type);
//                }else{
//                    selection = "AND IsIncome = ?";
//                    selectionArgsList.add(type);
//                }
//            }
//            if(categoryIdFilter != null){
//                if(selection != null){
//                    selection = "CategoryId = ?";
//                    selectionArgsList.add(categoryIdFilter);
//                }else{
//                    selection = "AND CategoryId = ?";
//                    selectionArgsList.add(categoryIdFilter);
//                }
//            }
//            String[] selectionArgs = new String[selectionArgsList.size()];
//            selectionArgs = selectionArgsList.toArray(selectionArgs);
//
//            String orderBy = "CreateDate DESC";
//            SQLiteDatabase st = getReadableDatabase();
//            Cursor cs = st.query(TABLE_TRANSACTIONS,null,selection,selectionArgs,null,null,orderBy);
//            while (cs != null && cs.moveToNext()){
//                int id = cs.getInt(0);
//                String title = cs.getString(1);
//                String categoryId = cs.getString(2);
//                String price = cs.getString(3);
//                String isIncome = cs.getString(4);
//                String createDateStr = cs.getString(5);
//                Date date = new Date(createDateStr);
//                SimpleDateFormat outputFormat = new SimpleDateFormat(PATTERN, Locale.ENGLISH);
//                String formattedDate = outputFormat.format(date);
//                list.add(new Transaction(id,title,categoryId,price,isIncome,formattedDate));
//            }
//        }catch (Exception ex){
//            Log.e(TAG,"MyDbContext - getTransactionByFilter - " + ex.getMessage());
//        }
//        return list;
//    }



}
