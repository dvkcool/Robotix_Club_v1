package org.robotixnitrr.robotix_club_v1;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

/**
 * Created by dvk on 26/09/17.
 */

public class Workshop_view_holder extends ViewHolder {
    TextView wor_name;
    CardView cardView;
    public Workshop_view_holder(View itemView) {
        super(itemView);
         wor_name = (TextView) itemView.findViewById(R.id.workshop_name);
        cardView = itemView.findViewById(R.id.cardview1);

    }
}
