package com.kuismatematika;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


class TriviaQuizHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DB_NAME = "TQuiz.db";

    //If you want to add more questions or wanna update table values
    //or any kind of modification in db just increment version no.
    private static final int DB_VERSION = 3;
    //Table name
    private static final String TABLE_NAME = "TQ";
    //Id of question
    private static final String UID = "_UID";
    //Question
    private static final String QUESTION = "QUESTION";
    //Option A
    private static final String OPTA = "OPTA";
    //Option B
    private static final String OPTB = "OPTB";
    //Option C
    private static final String OPTC = "OPTC";
    //Option D
    private static final String OPTD = "OPTD";
    //Answer
    private static final String ANSWER = "ANSWER";
    //So basically we are now creating table with first column-id , sec column-question , third column -option A, fourth column -option B , Fifth column -option C , sixth column -option D , seventh column - answer(i.e ans of  question)
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + UID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + QUESTION + " VARCHAR(255), " + OPTA + " VARCHAR(255), " + OPTB + " VARCHAR(255), " + OPTC + " VARCHAR(255), " + OPTD + " VARCHAR(255), " + ANSWER + " VARCHAR(255));";
    //Drop table query
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    TriviaQuizHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //OnCreate is called only once
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //OnUpgrade is called when ever we upgrade or increment our database version no
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    void allQuestion() {
        ArrayList<TriviaQuestion> arraylist = new ArrayList<>();

        arraylist.add(new TriviaQuestion("Tino mempunyai 4 kelereng dan Andi mempunyai 1 kelereng. Jumlah kelereng yang mereka punyai adalah?", "3", "5", "6", "4", "5"));

        arraylist.add(new TriviaQuestion("Amel mempunyai 2 buah anggur dan 2 buah apel. Berapa jumlah buah yang dimiliki Amel?", "2", "5", "4", "3", "4"));

        arraylist.add(new TriviaQuestion("Wulan memiliki 4 pensil, dia memberikan 2 pensil miliknya kepada sang adik. Pensil Wulan sekarang berjumlah ?", "2", "3", "4", "1", "2"));

        arraylist.add(new TriviaQuestion("Riko mempunyai 6 pesawat kertas, ada 2 pesawat yang diterbangkan oleh Riko. Jadi jumlah pesawat kertas yang belum diterbangkan Riko adalah …. buah?", "2", "5", "4", "3", "4"));

        arraylist.add(new TriviaQuestion("Yanti membawa 7 buku. Rita membawa 2 buku. Jumlah buku yang mereka bawa adalah …?", "9", "8", "2", "10", "9"));

        arraylist.add(new TriviaQuestion("Pak Burhan memiliki 14 ekor ikan lele, dia menjual 11 ekor ke pasar. Ikan lele milik Pak Burhan sekarang sisa … ekor", "10", "4", "3", "9", "3"));

        arraylist.add(new TriviaQuestion("Vivi mempunyai 20 permen, ia ingin membagi permen itu dengan ketiga temannya. Jadi berapa jumlah permah yang diterima setiap anak?", "2", "4", "5", "7", "5"));

        arraylist.add(new TriviaQuestion("Vivi mempunyai 17 lembar kertas origami, sebanyak 12 lembar telah ia gunakan untuk membuat kupu-kupu. Sisa kertas origami Vivi sekarang adalah … lembar?", "3", "5", "7", "4", "5"));

        arraylist.add(new TriviaQuestion("Nino memiliki 12 kelereng merah dan 8 kelereng biru. Jadi jumlah kelereng yang dimiliki Nino adalah ?", "23", "21", "19", "20", "20"));

        arraylist.add(new TriviaQuestion("Pak Budi membeli 29 kotak susu, sebanyak 21 kotak susu telah dibagikan kepada murid-muridnya. Sisa susu yang dibeli Pak Budi sekarang ada … kotak?", "8", "9", "11", "10", "8"));

        this.addAllQuestions(arraylist);

    }


    private void addAllQuestions(ArrayList<TriviaQuestion> allQuestions) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (TriviaQuestion question : allQuestions) {
                values.put(QUESTION, question.getQuestion());
                values.put(OPTA, question.getOptA());
                values.put(OPTB, question.getOptB());
                values.put(OPTC, question.getOptC());
                values.put(OPTD, question.getOptD());
                values.put(ANSWER, question.getAnswer());
                db.insert(TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }


    List<TriviaQuestion> getAllOfTheQuestions() {

        List<TriviaQuestion> questionsList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String coloumn[] = {UID, QUESTION, OPTA, OPTB, OPTC, OPTD, ANSWER};
        Cursor cursor = db.query(TABLE_NAME, coloumn, null, null, null, null, null);


        while (cursor.moveToNext()) {
            TriviaQuestion question = new TriviaQuestion();
            question.setId(cursor.getInt(0));
            question.setQuestion(cursor.getString(1));
            question.setOptA(cursor.getString(2));
            question.setOptB(cursor.getString(3));
            question.setOptC(cursor.getString(4));
            question.setOptD(cursor.getString(5));
            question.setAnswer(cursor.getString(6));
            questionsList.add(question);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        cursor.close();
        db.close();
        return questionsList;
    }
}
