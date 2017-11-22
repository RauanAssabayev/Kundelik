package kz.edu.sdu.rauanassabayev.kundelik.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;
import io.realm.Realm;
import io.realm.RealmResults;
import kz.edu.sdu.rauanassabayev.kundelik.Models.Subject;
import kz.edu.sdu.rauanassabayev.kundelik.R;

/**
 * Created by rauanassabayev on 10/30/17.
 */

public class RecyclerViewSwipeHelper extends ItemTouchHelper.Callback {
    Context mContext;
    private final RecyclerView.Adapter mAdapter;
    public RecyclerViewSwipeHelper(RecyclerView.Adapter adapter,Context mContext) {
        mAdapter = adapter;
        this.mContext = mContext;
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
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }
    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle("Удаление");
        alertDialog.setMessage("Вы действительно хотите удалить предмет ?");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ия",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Realm mRealm = Realm.getDefaultInstance();
                        mRealm.beginTransaction();
                        Toast.makeText(mContext,viewHolder.getPosition()+"",Toast.LENGTH_SHORT).show();
                        RealmResults<Subject> row = mRealm.where(Subject.class).equalTo("number",viewHolder.getPosition()+1).findAll();
                        row.deleteFirstFromRealm();
                        mRealm.commitTransaction();
                        mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                        dialog.dismiss();
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