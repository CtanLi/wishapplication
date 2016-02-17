package com.example.ruanlopes.wishapplication.Adapters;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ruanlopes.wishapplication.Model.Person;
import com.example.ruanlopes.wishapplication.R;

import java.util.List;


/**
 * Created by ruanlopes on 16-01-13.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerItemViewHolder> {

    public static class RecyclerItemViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        Context mContext;

        CardView mCv;
        TextView mPersonName;
        TextView mPersonAge;
        ImageView mPersonPhoto;
        ImageButton mAddButton;
        LinearLayout mExpandable;

        private ValueAnimator mAnimator;

        public RecyclerItemViewHolder(View itemView) {
            super(itemView);
        }

        public RecyclerItemViewHolder(Context context, final View parent, CardView cv, TextView personName, TextView personAge, ImageView personPhoto, ImageButton addButton,LinearLayout expandable) {

            super(parent);

            mContext = context;
            mCv = cv;
            mPersonName = personName;
            mPersonAge = personAge;
            mPersonPhoto = personPhoto;

            mAddButton = addButton;
            mExpandable = expandable;

            mAddButton.setOnClickListener(this);
            mCv.setOnClickListener(this);


            /**
             * A view tree observer is used to register listeners that can be notified of global
             * changes in the view tree. Such global events include, but are not limited to,
             * layout of the whole tree, beginning of the drawing pass, touch mode change....
             *
             * A ViewTreeObserver should never be instantiated by applications as it is provided
             * by the views hierarchy. Refer to {@link View#getViewTreeObserver()}
             * for more information.
             */
            mExpandable.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {

             /**
              * OnPreDraw will get invoked after onLayout and onMeasure have run,but before anything
              * been draw. This means that the final post layout properties for all the items
              * have already been determined, but still have not been rendered on the screen*/
                @Override
                public boolean onPreDraw() {

                    mExpandable.getViewTreeObserver().removeOnPreDrawListener(this);
                    mExpandable.setVisibility(View.GONE);

                    final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                    final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                    mExpandable.measure(widthSpec, heightSpec);

                    mAnimator = slideAnimator(0, mExpandable.getMeasuredHeight());

                    return true;

                }
            });

        }


        public static RecyclerItemViewHolder newInstance(Context context, View parent) {

            CardView cv = (CardView) parent.findViewById(R.id.cardview_large);
            TextView personName = (TextView) parent.findViewById(R.id.person_name);
            TextView personAge = (TextView) parent.findViewById(R.id.person_age);
            ImageView personPhoto = (ImageView) parent.findViewById(R.id.person_photo);

            ImageButton addButton = (ImageButton) parent.findViewById((R.id.action_add_item));
            LinearLayout expandable = (LinearLayout) parent.findViewById(R.id.expanding);


            return new RecyclerItemViewHolder(context, parent, cv, personName, personAge, personPhoto, addButton, expandable);
        }

        @Override
        public void onClick(View view) {


            if (view instanceof CardView) {
                Snackbar.make(view, "Clicked", Snackbar.LENGTH_SHORT).show();



            } else if (view instanceof ImageButton) {

                if (mExpandable.getVisibility() == View.GONE) {
                    expand();
                } else collapse();
            }

        }


        private void expand() {

            //set Visible
            mExpandable.setVisibility(View.VISIBLE);

            mAnimator.start();
        }


        private void collapse() {

            int finalHeight = mExpandable.getHeight();

            ValueAnimator mAnimator = slideAnimator(finalHeight, 0);

            mAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationEnd(Animator animator) {
                    //Height=0, but it set visibility to GONE
                    mExpandable.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
            mAnimator.start();
        }

        private ValueAnimator slideAnimator(int start, int end) {

            ValueAnimator animator = ValueAnimator.ofInt(start, end);


            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    //Update Height
                    int value = (Integer) valueAnimator.getAnimatedValue();

                    ViewGroup.LayoutParams layoutParams = mExpandable.getLayoutParams();
                    layoutParams.height = value;
                    mExpandable.setLayoutParams(layoutParams);
                }
            });
            return animator;
        }

    }


    /*-----------------
    -----Adapter-----
    -----------------*/

    private List<Person> mPersons;
    private Context mContext;

    public RecyclerAdapter(Context context, List<Person> persons){

        this.mPersons = persons;
        this.mContext = context;
    }



    @Override
    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_thumbnail_layout, parent, false);


        return RecyclerItemViewHolder.newInstance(mContext, view);
    }

    @Override
    public void onBindViewHolder(RecyclerItemViewHolder holder, int position) {

        holder.mPersonName.setText(mPersons.get(position).getName());
        holder.mPersonAge.setText(mPersons.get(position).getAge());
        holder.mPersonPhoto.setImageResource(mPersons.get(position).getPhotoId());
        holder.mExpandable.setVisibility(View.GONE);

    }


    @Override
    public int getItemCount() {

        return mPersons == null ? 0 : mPersons.size();
    }


}
