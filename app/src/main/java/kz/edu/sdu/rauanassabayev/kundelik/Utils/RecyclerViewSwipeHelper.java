package kz.edu.sdu.rauanassabayev.kundelik.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import kz.edu.sdu.rauanassabayev.kundelik.Adapters.ScheduleAdapter;
import kz.edu.sdu.rauanassabayev.kundelik.Models.Subject;
import kz.edu.sdu.rauanassabayev.kundelik.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by rauanassabayev on 10/30/17.
 */

public class RecyclerViewSwipeHelper extends ItemTouchHelper.Callback {

    private boolean mOrderChanged;
    Context mContext;
    Activity mActivity;
    RealmResults<Subject> mSubjects;
    RecyclerView mrv;
    RecyclerView.ViewHolder newPosition;
    RecyclerView.ViewHolder oldPosition;
    int removedSubjectNumber = 0;
    int selectedDay = -1;

    private RecyclerView.Adapter mAdapter;

    public RecyclerViewSwipeHelper(RecyclerView rv, RecyclerView.Adapter adapter, RealmResults<Subject> subjects, Context context, Activity activity) {
        mAdapter = adapter;
        mContext = context;
        mSubjects = subjects;
        mActivity = activity;
        mrv= rv;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }


    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.LEFT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        if(!mOrderChanged){
            this.oldPosition = target;
        }
        mOrderChanged = true;
        this.newPosition = viewHolder;
        return false;
    }

    void myOnMoved(){

        Realm mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();
        Subject from = mRealm.where(Subject.class).equalTo("id", mSubjects.get(oldPosition.getAdapterPosition()).getId() + "").findFirst();
        from.setNumber(newPosition.getAdapterPosition()+1);
        String id = from.getId();
        mRealm.commitTransaction();

        mRealm.beginTransaction();

        SharedPreferences prefs = mActivity.getSharedPreferences("DEFAULT_PREFS", MODE_PRIVATE);
        selectedDay = prefs.getInt("day", -1);

        if(oldPosition.getAdapterPosition()>newPosition.getAdapterPosition()) {
            RealmResults<Subject> to = mRealm.where(Subject.class).equalTo("day",selectedDay).greaterThan("number", newPosition.getAdapterPosition()).findAll().sort("number", Sort.ASCENDING);
            int i = newPosition.getAdapterPosition() + 1;
            for (Subject subjectItem : to) {
                if (!id.equals(subjectItem.getId())) {
                    subjectItem.setNumber(++i);
                }
            }
        }else {
            RealmResults<Subject> to = mRealm.where(Subject.class).equalTo("day",selectedDay).lessThan("number", newPosition.getAdapterPosition()+2).findAll().sort("number", Sort.DESCENDING);
            System.out.println(to.size()+"");
            int i = newPosition.getAdapterPosition()+1;
            for (Subject subjectItem : to) {
                System.out.println(subjectItem.getName());
                if (!id.equals(subjectItem.getId())) {
                    subjectItem.setNumber(--i);
                }
            }
        }
        mRealm.commitTransaction();

        mRealm.beginTransaction();
        RealmResults<Subject> subjects = mRealm.where(Subject.class).equalTo("day",selectedDay).findAllSorted("number");
        if(!subjects.isEmpty()) {
            mAdapter = new ScheduleAdapter(subjects);
            mRealm.commitTransaction();
            mrv.setAdapter(mAdapter);
        }else{
            Log.d("MYLOGS","EMPTY");
            mRealm.cancelTransaction();
        }

    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState == ItemTouchHelper.ACTION_STATE_IDLE && mOrderChanged) {
            mOrderChanged = false;
            myOnMoved();
        }
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {

        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle("Удаление");
        alertDialog.setMessage("Вы действительно хотите удалить предмет ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ия",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Realm mRealm = Realm.getDefaultInstance();
                        mRealm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                Subject subject = realm.where(Subject.class).equalTo("id", mSubjects.get(viewHolder.getAdapterPosition()).getId() + "").findFirst();
                                removedSubjectNumber = subject.getNumber();
                                subject.deleteFromRealm();
                                //mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                                //mAdapter.notifyItemRangeChanged(viewHolder.getAdapterPosition(), mAdapter.getItemCount());
                                mAdapter.notifyDataSetChanged();
                                mrv.setAdapter(mAdapter);
                                Log.d("KEY","SUCCESS");
                            }
                        });

                        mRealm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                RealmResults<Subject> to = realm.where(Subject.class).equalTo("day",selectedDay).greaterThan("number",removedSubjectNumber).findAll().sort("number",Sort.ASCENDING);
                                int i = removedSubjectNumber;
                                for(Subject subjectItem : to){
                                    subjectItem.setNumber(i++);
                                }
                            }
                        });

                        //Toast.makeText(mContext,mSubjects.get(viewHolder.getPosition()).getId(),Toast.LENGTH_LONG).show();
                        //mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Жоқ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mrv.setAdapter(mAdapter);
                dialogInterface.cancel();
            }
        });
        alertDialog.show();
    }

    Drawable background;
    Drawable xMark;
    int xMarkMargin;
    boolean initiated;

    private void init() {
        background = new ColorDrawable(Color.RED);
        xMark = ContextCompat.getDrawable(mContext, R.drawable.ic_clear_24dp);
        xMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        xMarkMargin = (int) mContext.getResources().getDimension(R.dimen.ic_clear_margin);
        initiated = true;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;
        // not sure why, but this method get's called for viewholder that are already swiped away
        if (viewHolder.getAdapterPosition() == -1) {
            // not interested in those
            return;
        }
        if (!initiated) {
            init();
        }
        // draw red background
        background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        background.draw(c);
        // draw x mark

        int itemHeight = itemView.getBottom() - itemView.getTop();
        int intrinsicWidth = xMark.getIntrinsicWidth();
        int intrinsicHeight = xMark.getIntrinsicWidth();
        int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
        int xMarkRight = itemView.getRight() - xMarkMargin;
        int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight)/2;
        int xMarkBottom = xMarkTop + intrinsicHeight;

        xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);
        xMark.draw(c);
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

    }
}